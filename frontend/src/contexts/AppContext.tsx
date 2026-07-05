import { createContext, useContext, useState, type ReactNode } from 'react';
import type { UserModel, UserRole } from '@/types';

interface AppState {
  user: UserModel | null;
  isAdmin: boolean;
  login: (user: UserModel) => void;
  logout: () => void;
}

const AppContext = createContext<AppState | undefined>(undefined);

const ADMIN_ROLES: UserRole[] = ['ADMIN', 'LIBRARIAN'];

export function AppProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserModel | null>(null);

  const login = (u: UserModel) => setUser(u);
  const logout = () => setUser(null);

  return (
    <AppContext.Provider value={{ user, isAdmin: user ? ADMIN_ROLES.includes(user.role) : false, login, logout }}>
      {children}
    </AppContext.Provider>
  );
}

export function useApp() {
  const ctx = useContext(AppContext);
  if (!ctx) throw new Error('useApp must be used within AppProvider');
  return ctx;
}
