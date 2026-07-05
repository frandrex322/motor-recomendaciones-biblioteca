import { useApp } from '@/contexts/AppContext';
import { useReservationsByUser, useCancelReservation } from '@/hooks/useApi';
import { useToast } from '@/components/ui/Toast';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { TableSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor, formatDate } from '@/utils/format';
import { CalendarCheck, XCircle } from 'lucide-react';

export function MyReservationsPage() {
  const { user } = useApp();
  const { toast } = useToast();
  const { data: reservations, isLoading } = useReservationsByUser(user?.id || '');
  const cancelRes = useCancelReservation();

  if (!user) return <EmptyState icon={CalendarCheck} title="Inicia sesión" description="Para ver tus reservas" />;

  const handleCancel = (id: string) => {
    cancelRes.mutate(id, {
      onSuccess: () => toast('success', 'Reserva cancelada'),
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Mis Reservas</h1>
        <p className="text-sm text-gray-500 mt-1">{reservations?.length || 0} reservas</p>
      </div>

      {isLoading ? <TableSkeleton /> : !reservations?.length ? (
        <EmptyState icon={CalendarCheck} title="Sin reservas" description="Reserva un libro desde el catálogo" />
      ) : (
        <div className="space-y-3">
          {reservations.map(res => (
            <Card key={res.id}>
              <CardContent className="p-4 flex items-center justify-between">
                <div className="space-y-1">
                  <p className="text-sm font-medium text-gray-900">ID: {res.id.slice(0, 8)}...</p>
                  <div className="flex items-center gap-3 text-xs text-gray-500">
                    <span>Creada: {formatDate(res.createdAt)}</span>
                    <span>Expira: {formatDate(res.expiresAt)}</span>
                  </div>
                </div>
                <div className="flex items-center gap-3">
                  <Badge className={statusColor(res.status)}>{res.status}</Badge>
                  {res.status === 'PENDING' && (
                    <Button size="sm" variant="ghost" className="text-red-500" onClick={() => handleCancel(res.id)} loading={cancelRes.isPending}>
                      <XCircle className="h-4 w-4 mr-1" />Cancelar
                    </Button>
                  )}
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
