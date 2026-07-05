import { NavLink } from 'react-router-dom';
import { cn } from '@/utils/cn';
import {
  BookOpen, Users, LayoutDashboard, BookMarked, Star, MessageSquare,
  CalendarCheck, Heart, Library, LogOut, ChevronLeft,
} from 'lucide-react';
import { useApp } from '@/contexts/AppContext';
import { useState } from 'react';

const userLinks = [
  { to: '/', label: 'Catálogo', icon: BookOpen },
  { to: '/favorites', label: 'Favoritos', icon: Heart },
  { to: '/my-loans', label: 'Mis Préstamos', icon: BookMarked },
  { to: '/my-reservations', label: 'Mis Reservas', icon: CalendarCheck },
];

const adminLinks = [
  { to: '/admin', label: 'Dashboard', icon: LayoutDashboard },
  { to: '/admin/books', label: 'Libros', icon: Library },
  { to: '/admin/users', label: 'Usuarios', icon: Users },
  { to: '/admin/loans', label: 'Préstamos', icon: BookMarked },
  { to: '/admin/reservations', label: 'Reservas', icon: CalendarCheck },
];

export function Sidebar() {
  const { user, isAdmin, logout } = useApp();
  const [collapsed, setCollapsed] = useState(false);

  return (
    <aside className={cn(
      'h-screen sticky top-0 bg-white border-r border-gray-200 flex flex-col transition-all duration-200',
      collapsed ? 'w-16' : 'w-60'
    )}>
      <div className="flex items-center gap-2 p-4 border-b border-gray-100">
        <div className="w-8 h-8 rounded-lg bg-primary-600 flex items-center justify-center">
          <BookOpen className="h-4 w-4 text-white" />
        </div>
        {!collapsed && <span className="font-semibold text-gray-900">Biblioteca</span>}
      </div>

      <button
        onClick={() => setCollapsed(!collapsed)}
        className="absolute -right-3 top-12 w-6 h-6 rounded-full bg-white border border-gray-200 flex items-center justify-center text-gray-400 hover:text-gray-600"
      >
        <ChevronLeft className={cn('h-3 w-3 transition-transform', collapsed && 'rotate-180')} />
      </button>

      <nav className="flex-1 p-3 space-y-1 overflow-y-auto">
        {!collapsed && <p className="text-xs font-medium text-gray-400 uppercase tracking-wider px-3 py-2">Usuario</p>}
        {userLinks.map(({ to, label, icon: Icon }) => (
          <NavLink key={to} to={to} end={to === '/'}
            className={({ isActive }) => cn(
              'flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-colors',
              isActive ? 'bg-primary-50 text-primary-700' : 'text-gray-600 hover:bg-gray-100 hover:text-gray-900'
            )}
          >
            <Icon className="h-4 w-4 shrink-0" />
            {!collapsed && label}
          </NavLink>
        ))}

        {isAdmin && (
          <>
            <div className="pt-4 pb-1">
              {!collapsed && <p className="text-xs font-medium text-gray-400 uppercase tracking-wider px-3 py-2">Admin</p>}
            </div>
            {adminLinks.map(({ to, label, icon: Icon }) => (
              <NavLink key={to} to={to} end={to === '/admin'}
                className={({ isActive }) => cn(
                  'flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-colors',
                  isActive ? 'bg-primary-50 text-primary-700' : 'text-gray-600 hover:bg-gray-100 hover:text-gray-900'
                )}
              >
                <Icon className="h-4 w-4 shrink-0" />
                {!collapsed && label}
              </NavLink>
            ))}
          </>
        )}
      </nav>

      <div className="p-3 border-t border-gray-100">
        {user ? (
          <button onClick={logout} className="flex items-center gap-3 w-full px-3 py-2 rounded-lg text-sm text-gray-600 hover:bg-gray-100 transition-colors">
            <LogOut className="h-4 w-4 shrink-0" />
            {!collapsed && <span className="truncate">{user.name}</span>}
          </button>
        ) : (
          <NavLink to="/login"
            className="flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium text-primary-600 hover:bg-primary-50 transition-colors"
          >
            <Users className="h-4 w-4 shrink-0" />
            {!collapsed && 'Iniciar Sesión'}
          </NavLink>
        )}
      </div>
    </aside>
  );
}
