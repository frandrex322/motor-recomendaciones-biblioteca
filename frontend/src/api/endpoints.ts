import apiClient from './client';
import type {
  Author, BookModel, UserModel, Loan, Rate, Review, Reservation, Favorite, AverageRating,
} from '@/types';

// ─── Authors ───
export const getAuthors = () => apiClient.get<Author[]>('/authors').then(r => r.data);
export const getAuthor = (id: string) => apiClient.get<Author>(`/authors/${id}`).then(r => r.data);
export const getAuthorByName = (name: string) => apiClient.get<Author>(`/authors/name/${name}`).then(r => r.data);
export const createAuthor = (data: Partial<Author>) => apiClient.post<Author>('/authors', data).then(r => r.data);
export const deleteAuthor = (id: string) => apiClient.delete(`/authors/${id}`);

// ─── Books ───
export const getBooks = () => apiClient.get<BookModel[]>('/books').then(r => r.data);
export const getBook = (id: string) => apiClient.get<BookModel>(`/books/${id}`).then(r => r.data);
export const getBooksByStatus = (status: string) => apiClient.get<BookModel[]>(`/books/status/${status}`).then(r => r.data);
export const createBook = (data: Partial<BookModel> & { authorId: string; title: string; description: string; isbn: string; publicationYear: string; status: string; price: number; pages: number; image?: string }) =>
  apiClient.post<BookModel>('/books', data).then(r => r.data);
export const updateBookStatus = (id: string, status: string) =>
  apiClient.patch<BookModel>(`/books/${id}/status`, { status }).then(r => r.data);
export const updateBookImage = (id: string, image: string) =>
  apiClient.patch<BookModel>(`/books/${id}/image`, { image }).then(r => r.data);

// ─── Users ───
export const getUsers = () => apiClient.get<UserModel[]>('/users').then(r => r.data);
export const getUser = (id: string) => apiClient.get<UserModel>(`/users/${id}`).then(r => r.data);
export const getUserByEmail = (email: string) => apiClient.get<UserModel>(`/users/email/${email}`).then(r => r.data);
export const createUser = (data: { name: string; email: string; password: string; role: string }) =>
  apiClient.post<UserModel>('/users', data).then(r => r.data);
export const deactivateUser = (id: string) =>
  apiClient.patch<UserModel>(`/users/${id}/deactivate`).then(r => r.data);

// ─── Loans ───
export const getLoans = () => apiClient.get<Loan[]>('/loans').then(r => r.data);
export const getLoan = (id: string) => apiClient.get<Loan>(`/loans/${id}`).then(r => r.data);
export const getLoansByUser = (userId: string) => apiClient.get<Loan[]>(`/loans/user/${userId}`).then(r => r.data);
export const createLoan = (data: { userId: string; bookId: string; loanDate: string; dueDate: string }) =>
  apiClient.post<Loan>('/loans', data).then(r => r.data);
export const returnLoan = (id: string) => apiClient.patch<Loan>(`/loans/${id}/return`).then(r => r.data);
export const cancelLoan = (id: string) => apiClient.patch<Loan>(`/loans/${id}/cancel`).then(r => r.data);

// ─── Ratings ───
export const getRatingsByBook = (bookId: string) => apiClient.get<Rate[]>(`/ratings/book/${bookId}`).then(r => r.data);
export const getAverageRating = (bookId: string) => apiClient.get<AverageRating>(`/ratings/book/${bookId}/average`).then(r => r.data);
export const createRating = (data: { userId: string; bookId: string; score: number }) =>
  apiClient.post<Rate>('/ratings', data).then(r => r.data);

// ─── Reviews ───
export const getReviewsByBook = (bookId: string) => apiClient.get<Review[]>(`/reviews/book/${bookId}`).then(r => r.data);
export const createReview = (data: { userId: string; bookId: string; comment: string }) =>
  apiClient.post<Review>('/reviews', data).then(r => r.data);
export const editReview = (id: string, comment: string) =>
  apiClient.patch<Review>(`/reviews/${id}`, { comment }).then(r => r.data);
export const deleteReview = (id: string) =>
  apiClient.patch<Review>(`/reviews/${id}/delete`).then(r => r.data);

// ─── Reservations ───
export const createReservation = (data: { userId: string; bookId: string }) =>
  apiClient.post<Reservation>('/reservations', data).then(r => r.data);
export const getReservationsByUser = (userId: string) =>
  apiClient.get<Reservation[]>(`/reservations/user/${userId}`).then(r => r.data);
export const confirmReservation = (id: string) =>
  apiClient.patch<Reservation>(`/reservations/${id}/confirm`).then(r => r.data);
export const cancelReservation = (id: string) =>
  apiClient.patch<Reservation>(`/reservations/${id}/cancel`).then(r => r.data);

// ─── Favorites ───
export const addFavorite = (data: { userId: string; bookId: string }) =>
  apiClient.post<Favorite>('/favorites', data).then(r => r.data);
export const getFavoritesByUser = (userId: string) =>
  apiClient.get<Favorite[]>(`/favorites/user/${userId}`).then(r => r.data);
export const removeFavorite = (id: string) =>
  apiClient.patch<Favorite>(`/favorites/${id}/remove`).then(r => r.data);
