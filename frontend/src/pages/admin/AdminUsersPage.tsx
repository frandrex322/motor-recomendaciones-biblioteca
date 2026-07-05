import { useState } from 'react';
import { useUsers, useDeactivateUser, useLoansByUser } from '@/hooks/useApi';
import { useToast } from '@/components/ui/Toast';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Modal } from '@/components/ui/Modal';
import { TableSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor, formatDate } from '@/utils/format';
import { Search, Users, Eye, BookMarked, CalendarCheck } from 'lucide-react';
import type { UserModel } from '@/types';

export function AdminUsersPage() {
  const { data: users, isLoading } = useUsers();
  const { toast } = useToast();
  const deactivate = useDeactivateUser();
  const [search, setSearch] = useState('');
  const [selectedUser, setSelectedUser] = useState<UserModel | null>(null);
  const { data: userLoans } = useLoansByUser(selectedUser?.id || '');

  const filtered = users?.filter(u =>
    !search || u.name.toLowerCase().includes(search.toLowerCase()) || u.email.toLowerCase().includes(search.toLowerCase())
  ) || [];

  const handleDeactivate = (id: string) => {
    deactivate.mutate(id, {
      onSuccess: () => toast('success', 'Usuario desactivado'),
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Usuarios</h1>
          <p className="text-sm text-gray-500 mt-1">{filtered.length} usuarios</p>
        </div>
      </div>

      <div className="relative max-w-md">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
        <input value={search} onChange={e => setSearch(e.target.value)} className="w-full pl-10 pr-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Buscar por nombre o email..." />
      </div>

      {isLoading ? <TableSkeleton /> : !filtered.length ? (
        <EmptyState icon={Users} title="No hay usuarios" />
      ) : (
        <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-gray-100 bg-gray-50">
                <th className="text-left p-3 font-medium text-gray-500">Nombre</th>
                <th className="text-left p-3 font-medium text-gray-500">Email</th>
                <th className="text-left p-3 font-medium text-gray-500">Rol</th>
                <th className="text-left p-3 font-medium text-gray-500">Estado</th>
                <th className="text-left p-3 font-medium text-gray-500">Creado</th>
                <th className="text-right p-3 font-medium text-gray-500">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filtered.map(user => (
                <tr key={user.id} className="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                  <td className="p-3 font-medium text-gray-900">{user.name}</td>
                  <td className="p-3 text-gray-500">{user.email}</td>
                  <td className="p-3"><Badge className="bg-gray-100 text-gray-700">{user.role}</Badge></td>
                  <td className="p-3"><Badge className={statusColor(user.status)}>{user.status}</Badge></td>
                  <td className="p-3 text-gray-500">{formatDate(user.createdAt)}</td>
                  <td className="p-3 text-right">
                    <div className="flex items-center justify-end gap-2">
                      <Button size="sm" variant="ghost" onClick={() => setSelectedUser(user)}>
                        <Eye className="h-4 w-4" />
                      </Button>
                      {user.status === 'ACTIVE' && (
                        <Button size="sm" variant="danger" onClick={() => handleDeactivate(user.id)} loading={deactivate.isPending}>
                          Desactivar
                        </Button>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <Modal open={!!selectedUser} onClose={() => setSelectedUser(null)} title={`Detalles de ${selectedUser?.name || ''}`}>
        {selectedUser && (
          <div className="space-y-4">
            <div className="grid grid-cols-2 gap-3 text-sm">
              <div><p className="text-gray-400">Email</p><p className="font-medium">{selectedUser.email}</p></div>
              <div><p className="text-gray-400">Rol</p><p className="font-medium">{selectedUser.role}</p></div>
              <div><p className="text-gray-400">Estado</p><Badge className={statusColor(selectedUser.status)}>{selectedUser.status}</Badge></div>
              <div><p className="text-gray-400">Creado</p><p className="font-medium">{formatDate(selectedUser.createdAt)}</p></div>
            </div>
            <div className="pt-3 border-t border-gray-100">
              <h4 className="text-sm font-semibold text-gray-900 mb-2 flex items-center gap-2"><BookMarked className="h-4 w-4" />Préstamos ({userLoans?.length || 0})</h4>
              {userLoans?.slice(0, 5).map(loan => (
                <div key={loan.id} className="flex items-center justify-between py-2 text-sm border-b border-gray-50">
                  <span className="text-gray-500">ID: {loan.id.slice(0, 8)}...</span>
                  <Badge className={statusColor(loan.status)}>{loan.status}</Badge>
                </div>
              ))}
            </div>
          </div>
        )}
      </Modal>
    </div>
  );
}
