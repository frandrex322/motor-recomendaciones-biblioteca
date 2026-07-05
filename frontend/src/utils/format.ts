export function formatDate(date: string | null | undefined): string {
  if (!date) return '—';
  return new Date(date).toLocaleDateString('es-ES', {
    year: 'numeric', month: 'short', day: 'numeric',
  });
}

export function statusColor(status: string): string {
  const map: Record<string, string> = {
    ACTIVE: 'text-green-600 bg-green-50',
    RETURNED: 'text-blue-600 bg-blue-50',
    OVERDUE: 'text-red-600 bg-red-50',
    CANCELLED: 'text-gray-600 bg-gray-100',
    PENDING: 'text-yellow-600 bg-yellow-50',
    COMPLETED: 'text-green-600 bg-green-50',
    EXPIRED: 'text-red-600 bg-red-50',
    VAILABLE: 'text-green-600 bg-green-50',
    RESERVED: 'text-yellow-600 bg-yellow-50',
    BORROWED: 'text-blue-600 bg-blue-50',
    LOST: 'text-red-600 bg-red-50',
    DAMAGED: 'text-orange-600 bg-orange-50',
    INACTIVE: 'text-gray-600 bg-gray-100',
    PUBLISHED: 'text-green-600 bg-green-50',
    EDITED: 'text-yellow-600 bg-yellow-50',
    DELETED: 'text-red-600 bg-red-50',
  };
  return map[status] || 'text-gray-600 bg-gray-100';
}
