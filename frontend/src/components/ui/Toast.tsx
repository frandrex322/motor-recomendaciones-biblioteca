import { useState, useEffect, createContext, useContext, useCallback, type ReactNode } from 'react';
import { CheckCircle, XCircle, AlertCircle, X } from 'lucide-react';
import { cn } from '@/utils/cn';

type ToastType = 'success' | 'error' | 'info';

interface Toast {
  id: number;
  type: ToastType;
  message: string;
}

interface ToastCtx {
  toast: (type: ToastType, message: string) => void;
}

const ToastContext = createContext<ToastCtx>({ toast: () => {} });

export function useToast() {
  return useContext(ToastContext);
}

let idCounter = 0;

export function ToastProvider({ children }: { children: ReactNode }) {
  const [toasts, setToasts] = useState<Toast[]>([]);

  const toast = useCallback((type: ToastType, message: string) => {
    const id = ++idCounter;
    setToasts(prev => [...prev, { id, type, message }]);
  }, []);

  const remove = (id: number) => setToasts(prev => prev.filter(t => t.id !== id));

  return (
    <ToastContext.Provider value={{ toast }}>
      {children}
      <div className="fixed bottom-4 right-4 z-[100] flex flex-col gap-2 max-w-sm">
        {toasts.map(t => (
          <ToastItem key={t.id} toast={t} onClose={() => remove(t.id)} />
        ))}
      </div>
    </ToastContext.Provider>
  );
}

function ToastItem({ toast: t, onClose }: { toast: Toast; onClose: () => void }) {
  useEffect(() => { const tm = setTimeout(onClose, 4000); return () => clearTimeout(tm); }, [onClose]);

  const icons = {
    success: <CheckCircle className="h-5 w-5 text-green-500" />,
    error: <XCircle className="h-5 w-5 text-red-500" />,
    info: <AlertCircle className="h-5 w-5 text-blue-500" />,
  };

  return (
    <div className={cn(
      'flex items-start gap-3 p-4 rounded-xl shadow-lg border backdrop-blur-sm bg-white animate-in slide-in-from-right',
      t.type === 'success' && 'border-green-200',
      t.type === 'error' && 'border-red-200',
      t.type === 'info' && 'border-blue-200',
    )}>
      {icons[t.type]}
      <p className="text-sm text-gray-700 flex-1">{t.message}</p>
      <button onClick={onClose} className="p-0.5 hover:bg-gray-100 rounded"><X className="h-4 w-4 text-gray-400" /></button>
    </div>
  );
}
