import { useParams, useNavigate } from 'react-router-dom';
import { useBook, useAverageRating, useRatingsByBook, useReviewsByBook, useCreateRating, useCreateReview, useCreateLoan, useCreateReservation } from '@/hooks/useApi';
import { useApp } from '@/contexts/AppContext';
import { useToast } from '@/components/ui/Toast';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Skeleton } from '@/components/ui/Skeleton';
import { statusColor, formatDate } from '@/utils/format';
import { BookOpen, Star, ArrowLeft, BookMarked, BookmarkPlus, MessageSquare, Send, Calendar } from 'lucide-react';
import { useState } from 'react';

export function BookDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { user } = useApp();
  const { toast } = useToast();
  const navigate = useNavigate();
  const { data: book, isLoading } = useBook(id!);
  const { data: avg } = useAverageRating(id!);
  const { data: ratings } = useRatingsByBook(id!);
  const { data: reviews } = useReviewsByBook(id!);
  const createRating = useCreateRating();
  const createReview = useCreateReview();
  const createLoan = useCreateLoan();
  const createReservation = useCreateReservation();

  const [score, setScore] = useState(5);
  const [comment, setComment] = useState('');

  if (isLoading) return <div className="space-y-4"><Skeleton className="h-8 w-48" /><Skeleton className="h-64 w-full" /></div>;
  if (!book) return <p className="text-gray-500">Libro no encontrado</p>;

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

  const handleRate = () => {
    if (!user) { toast('error', 'Debes iniciar sesión'); return; }
    createRating.mutate({ userId: user.id, bookId: book.id, score }, {
      onSuccess: () => { toast('success', 'Calificación guardada'); setScore(5); },
      onError: (e) => toast('error', e.message),
    });
  };

  const handleReview = () => {
    if (!user) { toast('error', 'Debes iniciar sesión'); return; }
    if (!comment.trim()) return;
    createReview.mutate({ userId: user.id, bookId: book.id, comment }, {
      onSuccess: () => { toast('success', 'Reseña publicada'); setComment(''); },
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <button onClick={() => navigate(-1)} className="flex items-center gap-2 text-sm text-gray-500 hover:text-gray-900 transition-colors">
        <ArrowLeft className="h-4 w-4" />Volver
      </button>

      <Card>
        <CardContent className="p-6">
          <div className="flex flex-col md:flex-row gap-6">
            <div className="w-full md:w-48 h-56 rounded-xl bg-gradient-to-br from-primary-100 to-primary-50 flex items-center justify-center">
              {book.image ? <img src={book.image} alt={book.title} className="w-full h-full object-cover rounded-xl" />
                : <BookOpen className="h-20 w-20 text-primary-300" />}
            </div>
            <div className="flex-1 space-y-3">
              <div className="flex items-start justify-between">
                <div>
                  <h1 className="text-2xl font-bold text-gray-900">{book.title}</h1>
                  <p className="text-gray-500">ISBN: {book.bookInformation.isbn}</p>
                </div>
                <Badge className={`${statusColor(book.status)} text-sm px-3 py-1`}>
                  {book.status === 'VAILABLE' ? 'Disponible' : book.status}
                </Badge>
              </div>
              <p className="text-gray-600 text-sm leading-relaxed">{book.description}</p>
              <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 pt-2">
                <div><p className="text-xs text-gray-400">Año</p><p className="text-sm font-medium">{book.bookInformation.publicationYear}</p></div>
                <div><p className="text-xs text-gray-400">Páginas</p><p className="text-sm font-medium">{book.bookInformation.pages}</p></div>
                <div><p className="text-xs text-gray-400">Precio</p><p className="text-sm font-medium">${book.bookInformation.price.toFixed(2)}</p></div>
                <div><p className="text-xs text-gray-400">Rating</p>
                  <div className="flex items-center gap-1 text-amber-500">
                    <Star className="h-3.5 w-3.5 fill-current" />
                    <span className="text-sm font-medium text-gray-700">{avg ? avg.average.toFixed(1) : '—'}</span>
                  </div>
                </div>
              </div>
              {book.status === 'VAILABLE' && (
                <div className="flex gap-3 pt-2">
                  <Button onClick={handleLoan} loading={createLoan.isPending}><BookMarked className="h-4 w-4 mr-2" />Solicitar Préstamo</Button>
                  <Button variant="secondary" onClick={handleReserve} loading={createReservation.isPending}>
                    <BookmarkPlus className="h-4 w-4 mr-2" />Reservar
                  </Button>
                </div>
              )}
            </div>
          </div>
        </CardContent>
      </Card>

      <div className="grid md:grid-cols-2 gap-6">
        <Card>
          <CardContent className="p-5">
            <h3 className="font-semibold text-gray-900 mb-3 flex items-center gap-2"><Star className="h-4 w-4 text-amber-500" />Calificar</h3>
            <div className="flex items-center gap-2 mb-3">
              {[1, 2, 3, 4, 5].map(n => (
                <button key={n} onClick={() => setScore(n)} className={`p-1 rounded transition-colors ${n <= score ? 'text-amber-500' : 'text-gray-300'}`}>
                  <Star className="h-6 w-6 fill-current" />
                </button>
              ))}
            </div>
            <Button size="sm" onClick={handleRate} loading={createRating.isPending}>Enviar Calificación</Button>
            <p className="text-xs text-gray-400 mt-2">{ratings?.length || 0} calificaciones</p>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-5">
            <h3 className="font-semibold text-gray-900 mb-3 flex items-center gap-2"><MessageSquare className="h-4 w-4 text-primary-500" />Dejar una Reseña</h3>
            <textarea value={comment} onChange={e => setComment(e.target.value)}
              className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none resize-none h-20"
              placeholder="Escribe tu reseña..." />
            <Button size="sm" className="mt-2" onClick={handleReview} loading={createReview.isPending}>
              <Send className="h-3 w-3 mr-1" />Publicar
            </Button>
          </CardContent>
        </Card>
      </div>

      {reviews && reviews.length > 0 && (
        <Card>
          <CardContent className="p-5">
            <h3 className="font-semibold text-gray-900 mb-4">Reseñas ({reviews.length})</h3>
            <div className="space-y-3">
              {reviews.filter(r => r.status !== 'DELETED').map(review => (
                <div key={review.id} className="p-3 rounded-lg bg-gray-50 border border-gray-100">
                  <p className="text-sm text-gray-700">{review.comment}</p>
                  <div className="flex items-center gap-2 mt-2 text-xs text-gray-400">
                    <Calendar className="h-3 w-3" />
                    {formatDate(review.createdAt)}
                    <Badge className={statusColor(review.status)}>{review.status}</Badge>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  );
}
