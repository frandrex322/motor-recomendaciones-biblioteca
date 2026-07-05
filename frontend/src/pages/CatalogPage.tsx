import { useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useBooks, useAuthors, useAverageRating, useCreateLoan, useCreateReservation, useAddFavorite } from '@/hooks/useApi';
import { useApp } from '@/contexts/AppContext';
import { useToast } from '@/components/ui/Toast';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { CardSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor } from '@/utils/format';
import { Search, BookOpen, Star, Heart, BookmarkPlus, BookMarked, Filter } from 'lucide-react';

function BookCard({ book }: { book: import('@/types').BookModel }) {
  const navigate = useNavigate();
  const { user } = useApp();
  const { toast } = useToast();
  const { data: avg } = useAverageRating(book.id);
  const createLoan = useCreateLoan();
  const createReservation = useCreateReservation();
  const addFav = useAddFavorite();

  const handleLoan = () => {
    if (!user) { toast('error', 'Debes iniciar sesión'); return; }
    const today = new Date().toISOString().split('T')[0];
    const due = new Date(Date.now() + 14 * 86400000).toISOString().split('T')[0];
    createLoan.mutate({ userId: user.id, bookId: book.id, loanDate: today, dueDate: due }, {
      onSuccess: () => toast('success', 'Préstamo creado'),
      onError: (e) => toast('error', e.message),
    });
  };

  const handleReserve = () => {
    if (!user) { toast('error', 'Debes iniciar sesión'); return; }
    createReservation.mutate({ userId: user.id, bookId: book.id }, {
      onSuccess: () => toast('success', 'Reserva creada'),
      onError: (e) => toast('error', e.message),
    });
  };

  const handleFavorite = () => {
    if (!user) { toast('error', 'Debes iniciar sesión'); return; }
    addFav.mutate({ userId: user.id, bookId: book.id }, {
      onSuccess: () => toast('success', 'Agregado a favoritos'),
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <Card className="group overflow-hidden">
      <div className="relative h-44 bg-gradient-to-br from-primary-100 to-primary-50 flex items-center justify-center overflow-hidden">
        {book.image ? (
          <img src={book.image} alt={book.title} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300" />
        ) : (
          <BookOpen className="h-16 w-16 text-primary-300" />
        )}
        <Badge className={`absolute top-3 right-3 ${statusColor(book.status)}`}>
          {book.status === 'VAILABLE' ? 'Disponible' : book.status}
        </Badge>
        <button onClick={handleFavorite} className="absolute top-3 left-3 p-1.5 rounded-full bg-white/80 hover:bg-white text-gray-400 hover:text-red-500 transition-colors">
          <Heart className="h-4 w-4" />
        </button>
      </div>
      <CardContent className="p-4 space-y-2">
        <h3 className="font-semibold text-gray-900 truncate">{book.title}</h3>
        <p className="text-xs text-gray-500">{book.bookInformation.isbn && `ISBN: ${book.bookInformation.isbn}`}</p>
        <div className="flex items-center gap-1 text-amber-500">
          <Star className="h-3.5 w-3.5 fill-current" />
          <span className="text-sm font-medium text-gray-700">{avg ? avg.average.toFixed(1) : '—'}</span>
          <span className="text-xs text-gray-400">({avg ? avg.total : 0})</span>
        </div>
        <div className="flex gap-2 pt-1">
          <Button size="sm" variant="secondary" className="flex-1 text-xs" onClick={() => navigate(`/book/${book.id}`)}>
            Detalles
          </Button>
          {book.status === 'VAILABLE' && (
            <>
              <Button size="sm" className="flex-1 text-xs" onClick={handleLoan} loading={createLoan.isPending}>
                <BookMarked className="h-3 w-3 mr-1" />Prestar
              </Button>
              <Button size="sm" variant="secondary" className="flex-1 text-xs" onClick={handleReserve} loading={createReservation.isPending}>
                <BookmarkPlus className="h-3 w-3 mr-1" />
              </Button>
            </>
          )}
        </div>
      </CardContent>
    </Card>
  );
}

export function CatalogPage() {
  const [search, setSearch] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  const { data: books, isLoading } = useBooks();
  const { data: authors } = useAuthors();

  const filtered = useMemo(() => {
    if (!books) return [];
    return books.filter(b => {
      const matchesSearch = !search || b.title.toLowerCase().includes(search.toLowerCase());
      const matchesStatus = !statusFilter || b.status === statusFilter;
      return matchesSearch && matchesStatus;
    });
  }, [books, search, statusFilter]);

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row gap-4 items-start sm:items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Catálogo de Libros</h1>
          <p className="text-sm text-gray-500 mt-1">{books?.length || 0} libros disponibles</p>
        </div>
        <div className="flex gap-3 w-full sm:w-auto">
          <div className="relative flex-1 sm:flex-initial">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
            <input value={search} onChange={e => setSearch(e.target.value)}
              className="w-full sm:w-64 pl-10 pr-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none"
              placeholder="Buscar libros..." />
          </div>
          <select value={statusFilter} onChange={e => setStatusFilter(e.target.value)}
            className="px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none">
            <option value="">Todos</option>
            <option value="VAILABLE">Disponibles</option>
            <option value="BORROWED">Prestados</option>
            <option value="RESERVED">Reservados</option>
          </select>
        </div>
      </div>

      {isLoading ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-5">
          {Array.from({ length: 8 }).map((_, i) => <CardSkeleton key={i} />)}
        </div>
      ) : filtered.length === 0 ? (
        <EmptyState icon={BookOpen} title="No se encontraron libros"
          description={search ? 'Intenta con otros términos de búsqueda' : 'No hay libros disponibles'} />
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-5">
          {filtered.map(book => <BookCard key={book.id} book={book} />)}
        </div>
      )}
    </div>
  );
}
