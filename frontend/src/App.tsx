import { Routes, Route, Navigate } from 'react-router-dom';
import { useApp } from '@/contexts/AppContext';
import { Layout } from '@/components/layout/Layout';
import { LoginPage } from '@/pages/LoginPage';
import { CatalogPage } from '@/pages/CatalogPage';
import { BookDetailPage } from '@/pages/BookDetailPage';
import { FavoritesPage } from '@/pages/FavoritesPage';
import { MyLoansPage } from '@/pages/MyLoansPage';
import { MyReservationsPage } from '@/pages/MyReservationsPage';
import { DashboardPage } from '@/pages/admin/DashboardPage';
import { AdminBooksPage } from '@/pages/admin/AdminBooksPage';
import { AdminUsersPage } from '@/pages/admin/AdminUsersPage';
import { AdminLoansPage } from '@/pages/admin/AdminLoansPage';
import { AdminReservationsPage } from '@/pages/admin/AdminReservationsPage';

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { user } = useApp();
  if (!user) return <Navigate to="/login" replace />;
  return <>{children}</>;
}

function AdminRoute({ children }: { children: React.ReactNode }) {
  const { user, isAdmin } = useApp();
  if (!user) return <Navigate to="/login" replace />;
  if (!isAdmin) return <Navigate to="/" replace />;
  return <>{children}</>;
}

export function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route element={<Layout />}>
        <Route path="/" element={<ProtectedRoute><CatalogPage /></ProtectedRoute>} />
        <Route path="/books/:id" element={<ProtectedRoute><BookDetailPage /></ProtectedRoute>} />
        <Route path="/favorites" element={<ProtectedRoute><FavoritesPage /></ProtectedRoute>} />
        <Route path="/my-loans" element={<ProtectedRoute><MyLoansPage /></ProtectedRoute>} />
        <Route path="/my-reservations" element={<ProtectedRoute><MyReservationsPage /></ProtectedRoute>} />
        <Route path="/admin" element={<AdminRoute><DashboardPage /></AdminRoute>} />
        <Route path="/admin/books" element={<AdminRoute><AdminBooksPage /></AdminRoute>} />
        <Route path="/admin/users" element={<AdminRoute><AdminUsersPage /></AdminRoute>} />
        <Route path="/admin/loans" element={<AdminRoute><AdminLoansPage /></AdminRoute>} />
        <Route path="/admin/reservations" element={<AdminRoute><AdminReservationsPage /></AdminRoute>} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
