export interface Author {
  id: string;
  fullName: string;
  biography: string;
  birthDate: string;
  nationality: string;
}

export interface BookModel {
  id: string;
  authorId: string;
  title: string;
  description: string;
  bookInformation: BookInformation;
  status: BookStatus;
  image: string | null;
}

export interface BookInformation {
  publicationYear: number;
  isbn: string;
  price: number;
  pages: number;
}

export type BookStatus = 'VAILABLE' | 'RESERVED' | 'BORROWED' | 'LOST' | 'DAMAGED' | 'INACTIVE';

export interface UserModel {
  id: string;
  name: string;
  email: string;
  password: string;
  status: UserStatus;
  createdAt: string;
  role: UserRole;
}

export type UserStatus = 'ACTIVE' | 'INACTIVE';
export type UserRole = 'STUDENT' | 'TEACHER' | 'LIBRARIAN' | 'ADMIN' | 'STANDARD_USER';

export interface Loan {
  id: string;
  userId: string;
  bookId: string;
  loanPeriod: LoanPeriod;
  returnDate: string | null;
  status: LoanStatus;
}

export interface LoanPeriod {
  loanDate: string;
  dueDate: string;
}

export type LoanStatus = 'ACTIVE' | 'RETURNED' | 'OVERDUE' | 'CANCELLED';

export interface Rate {
  id: string;
  userId: string;
  bookId: string;
  score: Score;
  createdAt: string;
}

export interface Score {
  score: number;
}

export interface Review {
  id: string;
  user: string;
  book: string;
  comment: string;
  status: ReviewStatus;
  createdAt: string;
  updatedAt: string | null;
}

export type ReviewStatus = 'PUBLISHED' | 'EDITED' | 'DELETED';

export interface Reservation {
  id: string;
  userId: string;
  bookId: string;
  status: ReservationStatus;
  createdAt: string;
  expiresAt: string;
}

export type ReservationStatus = 'PENDING' | 'COMPLETED' | 'CANCELLED' | 'EXPIRED';

export interface Favorite {
  id: string;
  user: string;
  book: string;
  createdAt: string;
  status: FavoriteStatus;
}

export type FavoriteStatus = 'ACTIVE' | 'INACTIVE';

export interface AverageRating {
  average: number;
  total: number;
}
