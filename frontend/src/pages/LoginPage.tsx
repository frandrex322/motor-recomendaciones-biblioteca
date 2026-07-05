import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useCreateUser, useUserByEmail } from '@/hooks/useApi';
import { useApp } from '@/contexts/AppContext';
import { useToast } from '@/components/ui/Toast';
import { Button } from '@/components/ui/Button';
import { BookOpen, Mail, Lock, UserPlus, LogIn, User } from 'lucide-react';

const registerSchema = z.object({
  name: z.string().min(1, 'El nombre es obligatorio'),
  email: z.string().min(1, 'El email es obligatorio').email('Email inválido'),
  password: z.string().min(6, 'La contraseña debe tener al menos 6 caracteres'),
  role: z.string().min(1, 'Selecciona un rol'),
});

type RegisterForm = z.infer<typeof registerSchema>;

export function LoginPage() {
  const [email, setEmail] = useState('');
  const [mode, setMode] = useState<'login' | 'register'>('login');
  const { login } = useApp();
  const navigate = useNavigate();
  const { toast } = useToast();
  const { data: userData, isFetching: searching } = useUserByEmail(email, mode === 'login' && email.length > 3);
  const createUser = useCreateUser();

  const { register, handleSubmit, formState: { errors } } = useForm<RegisterForm>({
    resolver: zodResolver(registerSchema),
  });

  const handleLogin = async () => {
    if (!userData) {
      toast('error', 'Usuario no encontrado con ese email');
      return;
    }
    login(userData);
    toast('success', `Bienvenido ${userData.name}`);
    navigate('/');
  };

  const handleRegister = async (data: RegisterForm) => {
    try {
      const user = await createUser.mutateAsync(data);
      login(user);
      toast('success', 'Cuenta creada exitosamente');
      navigate('/');
    } catch (e: any) {
      toast('error', e.message);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-primary-50 via-white to-primary-50 flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-14 h-14 rounded-2xl bg-primary-600 mb-4">
            <BookOpen className="h-7 w-7 text-white" />
          </div>
          <h1 className="text-2xl font-bold text-gray-900">Biblioteca Digital</h1>
          <p className="text-gray-500 mt-1">{mode === 'login' ? 'Inicia sesión para continuar' : 'Crea tu cuenta'}</p>
        </div>

        <div className="bg-white rounded-2xl shadow-xl border border-gray-200 p-6 space-y-4">
          <div className="flex bg-gray-100 rounded-lg p-1">
            <button onClick={() => setMode('login')} className={`flex-1 py-2 text-sm font-medium rounded-md transition-all ${mode === 'login' ? 'bg-white shadow-sm text-gray-900' : 'text-gray-500'}`}>
              <LogIn className="h-4 w-4 inline mr-1.5" />Iniciar Sesión
            </button>
            <button onClick={() => setMode('register')} className={`flex-1 py-2 text-sm font-medium rounded-md transition-all ${mode === 'register' ? 'bg-white shadow-sm text-gray-900' : 'text-gray-500'}`}>
              <UserPlus className="h-4 w-4 inline mr-1.5" />Registrarse
            </button>
          </div>

          {mode === 'login' ? (
            <>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <div className="relative">
                  <Mail className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                  <input value={email} onChange={e => setEmail(e.target.value)}
                    className="w-full pl-10 pr-3 py-2.5 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="correo@ejemplo.com" />
                </div>
              </div>
              <Button className="w-full" size="lg" onClick={handleLogin} loading={searching}>
                Iniciar Sesión
              </Button>
            </>
          ) : (
            <form onSubmit={handleSubmit(handleRegister)} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nombre</label>
                <div className="relative">
                  <User className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                  <input {...register('name')}
                    className="w-full pl-10 pr-3 py-2.5 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="Tu nombre" />
                </div>
                {errors.name && <p className="text-xs text-red-500 mt-1">{errors.name.message}</p>}
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <div className="relative">
                  <Mail className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                  <input {...register('email')}
                    className="w-full pl-10 pr-3 py-2.5 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="correo@ejemplo.com" />
                </div>
                {errors.email && <p className="text-xs text-red-500 mt-1">{errors.email.message}</p>}
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Contraseña</label>
                <div className="relative">
                  <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                  <input type="password" {...register('password')}
                    className="w-full pl-10 pr-3 py-2.5 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none" placeholder="Mínimo 6 caracteres" />
                </div>
                {errors.password && <p className="text-xs text-red-500 mt-1">{errors.password.message}</p>}
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Rol</label>
                <select {...register('role')}
                  className="w-full px-3 py-2.5 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none">
                  <option value="">Seleccionar...</option>
                  <option value="STUDENT">Estudiante</option>
                  <option value="TEACHER">Profesor</option>
                  <option value="STANDARD_USER">Usuario Regular</option>
                  <option value="LIBRARIAN">Bibliotecario</option>
                  <option value="ADMIN">Administrador</option>
                </select>
                {errors.role && <p className="text-xs text-red-500 mt-1">{errors.role.message}</p>}
              </div>
              <Button className="w-full" size="lg" type="submit" loading={createUser.isPending}>
                Crear Cuenta
              </Button>
            </form>
          )}
        </div>

        <p className="text-center text-xs text-gray-400 mt-6">
          Modo demo — ingresa con cualquier email registrado
        </p>
      </div>
    </div>
  );
}
