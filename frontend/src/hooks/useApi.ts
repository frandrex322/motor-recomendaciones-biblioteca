import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import * as api from '@/api/endpoints';

export const useAuthors = () =>
  useQuery({ queryKey: ['authors'], queryFn: api.getAuthors });

export const useAuthor = (id: string) =>
  useQuery({ queryKey: ['authors', id], queryFn: () => api.getAuthor(id) });

export const useCreateAuthor = () => {
  const qc = useQueryClient();
  return useMutation({ mutationFn: api.createAuthor, onSuccess: () => qc.invalidateQueries({ queryKey: ['authors'] }) });
};

export const useDeleteAuthor = () => {
  const qc = useQueryClient();
  return useMutation({ mutationFn: api.deleteAuthor, onSuccess: () => qc.invalidateQueries({ queryKey: ['authors'] }) });
};

export const useBooks = () =>
  useQuery({ queryKey: ['books'], queryFn: api.getBooks });

export const useBook = (id: string) =>
  useQuery({ queryKey: ['books', id], queryFn: () => api.getBook(id) });

export const useBooksByStatus = (status: string) =>
  useQuery({ queryKey: ['books', 'status', status], queryFn: () => api.getBooksByStatus(status) });

export const useCreateBook = () => {
  const qc = useQueryClient();
  return useMutation({ mutationFn: api.createBook, onSuccess: () => qc.invalidateQueries({ queryKey: ['books'] }) });
};

export const useUpdateBookStatus = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: string }) => api.updateBookStatus(id, status),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['books'] }),
  });
};

export const useUpdateBookImage = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, image }: { id: string; image: string }) => api.updateBookImage(id, image),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['books'] }),
  });
};

export const useUsers = () =>
  useQuery({ queryKey: ['users'], queryFn: api.getUsers });

export const useUser = (id: string) =>
  useQuery({ queryKey: ['users', id], queryFn: () => api.getUser(id) });

export const useUserByEmail = (email: string, enabled: boolean) =>
  useQuery({
    queryKey: ['users', 'email', email],
    queryFn: () => api.getUserByEmail(email),
    enabled,
  });

export const useCreateUser = () => {
  const qc = useQueryClient();
  return useMutation({ mutationFn: api.createUser, onSuccess: () => qc.invalidateQueries({ queryKey: ['users'] }) });
};

export const useDeactivateUser = () => {
  const qc = useQueryClient();
  return useMutation({ mutationFn: api.deactivateUser, onSuccess: () => qc.invalidateQueries({ queryKey: ['users'] }) });
};

export const useLoans = () =>
  useQuery({ queryKey: ['loans'], queryFn: api.getLoans });

export const useLoan = (id: string) =>
  useQuery({ queryKey: ['loans', id], queryFn: () => api.getLoan(id) });

export const useLoansByUser = (userId: string) =>
  useQuery({ queryKey: ['loans', 'user', userId], queryFn: () => api.getLoansByUser(userId) });

export const useCreateLoan = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.createLoan,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['loans', 'books'] }),
  });
};

export const useReturnLoan = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.returnLoan,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['loans', 'books'] }),
  });
};

export const useCancelLoan = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.cancelLoan,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['loans', 'books'] }),
  });
};

export const useRatingsByBook = (bookId: string) =>
  useQuery({ queryKey: ['ratings', 'book', bookId], queryFn: () => api.getRatingsByBook(bookId) });

export const useAverageRating = (bookId: string) =>
  useQuery({ queryKey: ['ratings', 'book', bookId, 'average'], queryFn: () => api.getAverageRating(bookId) });

export const useCreateRating = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.createRating,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['ratings'] }),
  });
};

export const useReviewsByBook = (bookId: string) =>
  useQuery({ queryKey: ['reviews', 'book', bookId], queryFn: () => api.getReviewsByBook(bookId) });

export const useCreateReview = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.createReview,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['reviews'] }),
  });
};

export const useEditReview = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, comment }: { id: string; comment: string }) => api.editReview(id, comment),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['reviews'] }),
  });
};

export const useDeleteReview = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.deleteReview,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['reviews'] }),
  });
};

export const useReservationsByUser = (userId: string) =>
  useQuery({ queryKey: ['reservations', 'user', userId], queryFn: () => api.getReservationsByUser(userId) });

export const useCreateReservation = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.createReservation,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['reservations', 'books'] }),
  });
};

export const useConfirmReservation = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.confirmReservation,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['reservations'] }),
  });
};

export const useCancelReservation = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.cancelReservation,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['reservations'] }),
  });
};

export const useFavoritesByUser = (userId: string) =>
  useQuery({ queryKey: ['favorites', 'user', userId], queryFn: () => api.getFavoritesByUser(userId) });

export const useAddFavorite = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.addFavorite,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['favorites'] }),
  });
};

export const useRemoveFavorite = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: api.removeFavorite,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['favorites'] }),
  });
};
