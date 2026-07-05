import { useState } from 'react';
import { useLoans, useReturnLoan, useCancelLoan } from '@/hooks/useApi';
import { useToast } from '@/components/ui/Toast';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Modal } from '@/components/ui/Modal';
import { ConfirmDialog } from '@/components/common/ConfirmDialog';
import { TableSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor, formatDate } from '@/utils/format';
import { BookMarked, Search, RotateCcw, XCircle } from 'lucide-react';

export function AdminLoansPage() {
  const { data: loans, isLoading } = useLoans();
  const { toast } = useToast();
  const returnLoan = useReturnLoan();
  const cancelLoan = useCancelLoan();
  const [search, setSearch] = useState('');
  const [confirmAction, setConfirmAction] = useState<{ id: string; action: 'return' | 'cancel' } | null>(null);

  const filtered = loans?.filter(l => !search || l.id.includes(search) || l.userId.includes(search)) || [];

  const handleConfirm = () => {
    if (!confirmAction) return;
    const mut = confirmAction.action === 'return' ? returnLoan : cancelLoan;
    mut.mutate(confirmAction.id, {
      onSuccess: () => { toast('success', confirmAction.action === 'return' ? 'Devolución registrada' : 'Préstamo cancelado'); setConfirmAction(null); },
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Préstamos</h1>
          <p className="text-sm text-gray-500 mt-1">{filtered.length} préstamos</p>
        </div>
      </div>

      <div className="relative max-w-md">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
        <input value={search} onChange={e => setSearch(e.target.value)} className="w-full pl-10 pr-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Buscar por ID..." />
      </div>

      {isLoading ? <TableSkeleton /> : !filtered.length ? (
        <EmptyState icon={BookMarked} title="No hay préstamos" />
      ) : (
        <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-gray-100 bg-gray-50">
                <th className="text-left p-3 font-medium text-gray-500">ID</th>
                <th className="text-left p-3 font-medium text-gray-500">Usuario</th>
                <th className="text-left p-3 font-medium text-gray-500">Libro</th>
                <th className="text-left p-3 font-medium text-gray-500">Inicio</th>
                <th className="text-left p-3 font-medium text-gray-500">Vence</th>
                <th className="text-left p-3 font-medium text-gray-500">Devuelto</th>
                <th className="text-left p-3 font-medium text-gray-500">Estado</th>
                <th className="text-right p-3 font-medium text-gray-500">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filtered.map(loan => (
                <tr key={loan.id} className="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                  <td className="p-3 font-mono text-xs text-gray-500">{loan.id.slice(0, 8)}...</td>
                  <td className="p-3 font-mono text-xs text-gray-500">{loan.userId.slice(0, 8)}...</td>
                  <td className="p-3 font-mono text-xs text-gray-500">{loan.bookId.slice(0, 8)}...</td>
                  <td className="p-3 text-gray-500">{formatDate(loan.loanPeriod.loanDate)}</td>
                  <td className="p-3 text-gray-500">{formatDate(loan.loanPeriod.dueDate)}</td>
                  <td className="p-3 text-gray-500">{formatDate(loan.returnDate)}</td>
                  <td className="p-3"><Badge className={statusColor(loan.status)}>{loan.status}</Badge></td>
                  <td className="p-3 text-right">
                    <div className="flex items-center justify-end gap-2">
                      {loan.status === 'ACTIVE' && (
                        <>
                          <Button size="sm" variant="primary" onClick={() => setConfirmAction({ id: loan.id, action: 'return' })}>
                            <RotateCcw className="h-3 w-3 mr-1" />Devolver
                          </Button>
                          <Button size="sm" variant="ghost" className="text-red-500" onClick={() => setConfirmAction({ id: loan.id, action: 'cancel' })}>
                            <XCircle className="h-4 w-4" />
                          </Button>
                        </>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <ConfirmDialog
        open={!!confirmAction}
        onClose={() => setConfirmAction(null)}
        onConfirm={handleConfirm}
        title={confirmAction?.action === 'return' ? 'Registrar Devolución' : 'Cancelar Préstamo'}
        message={confirmAction?.action === 'return' ? '¿Registrar la devolución de este libro?' : '¿Estás seguro de cancelar este préstamo?'}
        loading={returnLoan.isPending || cancelLoan.isPending}
      />
    </div>
  );
}
