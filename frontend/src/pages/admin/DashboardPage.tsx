import { useBooks, useUsers, useLoans } from '@/hooks/useApi';
import { Card, CardContent } from '@/components/ui/Card';
import { Skeleton } from '@/components/ui/Skeleton';
import { BookOpen, Users, BookMarked, CalendarCheck, Library, AlertCircle, TrendingUp, BookCheck } from 'lucide-react';

function StatCard({ icon: Icon, label, value, color }: { icon: any; label: string; value: string | number; color: string }) {
  return (
    <Card>
      <CardContent className="p-5 flex items-center gap-4">
        <div className={`p-3 rounded-xl ${color}`}>
          <Icon className="h-6 w-6 text-white" />
        </div>
        <div>
          <p className="text-sm text-gray-500">{label}</p>
          <p className="text-2xl font-bold text-gray-900">{value}</p>
        </div>
      </CardContent>
    </Card>
  );
}

export function DashboardPage() {
  const { data: books } = useBooks();
  const { data: users } = useUsers();
  const { data: loans } = useLoans();

  const totalBooks = books?.length || 0;
  const available = books?.filter(b => b.status === 'VAILABLE').length || 0;
  const borrowed = books?.filter(b => b.status === 'BORROWED').length || 0;
  const reserved = books?.filter(b => b.status === 'RESERVED').length || 0;
  const activeLoans = loans?.filter(l => l.status === 'ACTIVE').length || 0;
  const overdueLoans = loans?.filter(l => l.status === 'OVERDUE').length || 0;

  if (!books || !users || !loans) return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
      {Array.from({ length: 8 }).map((_, i) => <Skeleton key={i} className="h-28" />)}
    </div>
  );

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="text-sm text-gray-500 mt-1">Resumen de la biblioteca</p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard icon={Library} label="Total Libros" value={totalBooks} color="bg-blue-600" />
        <StatCard icon={BookCheck} label="Disponibles" value={available} color="bg-green-600" />
        <StatCard icon={BookMarked} label="Prestados" value={borrowed} color="bg-amber-600" />
        <StatCard icon={BookOpen} label="Reservados" value={reserved} color="bg-purple-600" />
        <StatCard icon={Users} label="Usuarios" value={users.length} color="bg-indigo-600" />
        <StatCard icon={TrendingUp} label="Préstamos Activos" value={activeLoans} color="bg-teal-600" />
        <StatCard icon={AlertCircle} label="Vencidos" value={overdueLoans} color="bg-red-600" />
        <StatCard icon={CalendarCheck} label="Total Préstamos" value={loans.length} color="bg-cyan-600" />
      </div>
    </div>
  );
}
