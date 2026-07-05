import { useReservationsByUser, useConfirmReservation, useCancelReservation } from '@/hooks/useApi';
import { useToast } from '@/components/ui/Toast';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { TableSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor, formatDate } from '@/utils/format';
import { CalendarCheck, CheckCircle, XCircle, Search } from 'lucide-react';
import { useState } from 'react';
import { useUsers } from '@/hooks/useApi';

export function AdminReservationsPage() {
  const { data: users } = useUsers();
  const { toast } = useToast();
  const [selectedUserId, setSelectedUserId] = useState('');
  const { data: reservations, isLoading } = useReservationsByUser(selectedUserId);
  const confirmRes = useConfirmReservation();
  const cancelRes = useCancelReservation();

  const handleConfirm = (id: string) => {
    confirmRes.mutate(id, {
      onSuccess: () => toast('success', 'Reserva confirmada'),
      onError: (e) => toast('error', e.message),
    });
  };

  const handleCancel = (id: string) => {
    cancelRes.mutate(id, {
      onSuccess: () => toast('success', 'Reserva cancelada'),
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Reservas</h1>
        <p className="text-sm text-gray-500 mt-1">Gestiona las reservas de libros</p>
      </div>

      <div className="max-w-md">
        <select value={selectedUserId} onChange={e => setSelectedUserId(e.target.value)}
          className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none">
          <option value="">Seleccionar usuario...</option>
          {users?.map(u => <option key={u.id} value={u.id}>{u.name} ({u.email})</option>)}
        </select>
      </div>

      {!selectedUserId ? (
        <EmptyState icon={CalendarCheck} title="Selecciona un usuario" description="Para ver sus reservas" />
      ) : isLoading ? <TableSkeleton /> : !reservations?.length ? (
        <EmptyState icon={CalendarCheck} title="Sin reservas" description="Este usuario no tiene reservas" />
      ) : (
        <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-gray-100 bg-gray-50">
                <th className="text-left p-3 font-medium text-gray-500">ID</th>
                <th className="text-left p-3 font-medium text-gray-500">Libro</th>
                <th className="text-left p-3 font-medium text-gray-500">Creada</th>
                <th className="text-left p-3 font-medium text-gray-500">Expira</th>
                <th className="text-left p-3 font-medium text-gray-500">Estado</th>
                <th className="text-right p-3 font-medium text-gray-500">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {reservations.map(res => (
                <tr key={res.id} className="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                  <td className="p-3 font-mono text-xs text-gray-500">{res.id.slice(0, 8)}...</td>
                  <td className="p-3 font-mono text-xs text-gray-500">{res.bookId.slice(0, 8)}...</td>
                  <td className="p-3 text-gray-500">{formatDate(res.createdAt)}</td>
                  <td className="p-3 text-gray-500">{formatDate(res.expiresAt)}</td>
                  <td className="p-3"><Badge className={statusColor(res.status)}>{res.status}</Badge></td>
                  <td className="p-3 text-right">
                    <div className="flex items-center justify-end gap-2">
                      {res.status === 'PENDING' && (
                        <>
                          <Button size="sm" variant="primary" onClick={() => handleConfirm(res.id)} loading={confirmRes.isPending}>
                            <CheckCircle className="h-3 w-3 mr-1" />Confirmar
                          </Button>
                          <Button size="sm" variant="ghost" className="text-red-500" onClick={() => handleCancel(res.id)} loading={cancelRes.isPending}>
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
    </div>
  );
}
