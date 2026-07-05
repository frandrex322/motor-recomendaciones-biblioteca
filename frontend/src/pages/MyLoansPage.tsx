import { useApp } from '@/contexts/AppContext';
import { useLoansByUser, useCancelLoan } from '@/hooks/useApi';
import { useToast } from '@/components/ui/Toast';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { TableSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor, formatDate } from '@/utils/format';
import { BookMarked, XCircle } from 'lucide-react';

export function MyLoansPage() {
  const { user } = useApp();
  const { toast } = useToast();
  const { data: loans, isLoading } = useLoansByUser(user?.id || '');
  const cancelLoan = useCancelLoan();

  if (!user) return <EmptyState icon={BookMarked} title="Inicia sesión" description="Para ver tus préstamos" />;

  const handleCancel = (id: string) => {
    cancelLoan.mutate(id, {
      onSuccess: () => toast('success', 'Préstamo cancelado'),
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Mis Préstamos</h1>
        <p className="text-sm text-gray-500 mt-1">{loans?.length || 0} préstamos</p>
      </div>

      {isLoading ? <TableSkeleton /> : !loans?.length ? (
        <EmptyState icon={BookMarked} title="Sin préstamos" description="Solicita un préstamo desde el catálogo" />
      ) : (
        <div className="space-y-3">
          {loans.map(loan => (
            <Card key={loan.id}>
              <CardContent className="p-4 flex items-center justify-between">
                <div className="space-y-1">
                  <p className="text-sm font-medium text-gray-900">ID: {loan.id.slice(0, 8)}...</p>
                  <div className="flex items-center gap-3 text-xs text-gray-500">
                    <span>Inicio: {formatDate(loan.loanPeriod.loanDate)}</span>
                    <span>Vence: {formatDate(loan.loanPeriod.dueDate)}</span>
                    {loan.returnDate && <span>Devuelto: {formatDate(loan.returnDate)}</span>}
                  </div>
                </div>
                <div className="flex items-center gap-3">
                  <Badge className={statusColor(loan.status)}>{loan.status}</Badge>
                  {loan.status === 'ACTIVE' && (
                    <Button size="sm" variant="ghost" className="text-red-500" onClick={() => handleCancel(loan.id)} loading={cancelLoan.isPending}>
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
