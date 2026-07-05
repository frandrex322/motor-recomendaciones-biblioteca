import { useApp } from '@/contexts/AppContext';
import { useFavoritesByUser, useBooks, useRemoveFavorite } from '@/hooks/useApi';
import { useToast } from '@/components/ui/Toast';
import { Card, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { CardSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor } from '@/utils/format';
import { Heart, BookOpen, Trash2 } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

export function FavoritesPage() {
  const { user } = useApp();
  const { toast } = useToast();
  const navigate = useNavigate();
  const { data: favs, isLoading } = useFavoritesByUser(user?.id || '');
  const { data: books } = useBooks();
  const removeFav = useRemoveFavorite();

  if (!user) return <EmptyState icon={Heart} title="Inicia sesión" description="Para ver tus favoritos" />;

  const favBooks = books?.filter(b => favs?.some(f => f.book === b.id && f.status === 'ACTIVE')) || [];

  const handleRemove = (id: string) => {
    removeFav.mutate(id, {
      onSuccess: () => toast('success', 'Eliminado de favoritos'),
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Mis Favoritos</h1>
        <p className="text-sm text-gray-500 mt-1">{favBooks.length} libros guardados</p>
      </div>

      {isLoading ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-5">
          {Array.from({ length: 4 }).map((_, i) => <CardSkeleton key={i} />)}
        </div>
      ) : favBooks.length === 0 ? (
        <EmptyState icon={Heart} title="Sin favoritos" description="Agrega libros a favoritos desde el catálogo" />
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-5">
          {favBooks.map(book => (
            <Card key={book.id}>
              <div className="h-36 bg-gradient-to-br from-primary-100 to-primary-50 flex items-center justify-center">
                <BookOpen className="h-12 w-12 text-primary-300" />
              </div>
              <CardContent className="p-4 space-y-2">
                <h3 className="font-semibold text-gray-900 truncate">{book.title}</h3>
                <Badge className={statusColor(book.status)}>{book.status === 'VAILABLE' ? 'Disponible' : book.status}</Badge>
                <div className="flex gap-2 pt-1">
                  <Button size="sm" variant="secondary" className="flex-1" onClick={() => navigate(`/book/${book.id}`)}>Ver</Button>
                  <Button size="sm" variant="ghost" className="text-red-500 hover:text-red-700" onClick={() => handleRemove(favs?.find(f => f.book === book.id)?.id || '')}>
                    <Trash2 className="h-4 w-4" />
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
