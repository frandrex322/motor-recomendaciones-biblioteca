import { useState } from 'react';
import { useBooks, useAuthors, useCreateBook, useUpdateBookStatus, useUpdateBookImage } from '@/hooks/useApi';
import { useToast } from '@/components/ui/Toast';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Modal } from '@/components/ui/Modal';
import { ConfirmDialog } from '@/components/common/ConfirmDialog';
import { TableSkeleton } from '@/components/ui/Skeleton';
import { EmptyState } from '@/components/common/EmptyState';
import { statusColor, formatDate } from '@/utils/format';
import { Plus, Search, Edit2, Image, BookOpen, Library } from 'lucide-react';
import { useQueryClient } from '@tanstack/react-query';

export function AdminBooksPage() {
  const { data: books, isLoading } = useBooks();
  const { data: authors } = useAuthors();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const createBook = useCreateBook();
  const updateStatus = useUpdateBookStatus();
  const updateImage = useUpdateBookImage();

  const [search, setSearch] = useState('');
  const [showCreate, setShowCreate] = useState(false);
  const [showImage, setShowImage] = useState<string | null>(null);
  const [confirmStatus, setConfirmStatus] = useState<{ id: string; status: string } | null>(null);
  const [form, setForm] = useState({ authorId: '', title: '', description: '', isbn: '', publicationYear: '', status: 'VAILABLE', price: 0, pages: 0, image: '' });
  const [imageUrl, setImageUrl] = useState('');

  const filtered = books?.filter(b => !search || b.title.toLowerCase().includes(search.toLowerCase())) || [];

  const handleCreate = () => {
    createBook.mutate({ ...form, publicationYear: `${form.publicationYear}-01-01T00:00:00` } as any, {
      onSuccess: () => { toast('success', 'Libro creado'); setShowCreate(false); setForm({ authorId: '', title: '', description: '', isbn: '', publicationYear: '', status: 'VAILABLE', price: 0, pages: 0, image: '' }); },
      onError: (e) => toast('error', e.message),
    });
  };

  const handleStatus = () => {
    if (!confirmStatus) return;
    updateStatus.mutate({ bookId: confirmStatus.id, newStatus: confirmStatus.status }, {
      onSuccess: () => { toast('success', 'Estado actualizado'); setConfirmStatus(null); },
      onError: (e) => toast('error', e.message),
    });
  };

  const handleImage = () => {
    if (!showImage || !imageUrl) return;
    updateImage.mutate({ bookId: showImage, image: imageUrl }, {
      onSuccess: () => { toast('success', 'Imagen actualizada'); setShowImage(null); setImageUrl(''); },
      onError: (e) => toast('error', e.message),
    });
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Gestión de Libros</h1>
          <p className="text-sm text-gray-500 mt-1">{filtered.length} libros</p>
        </div>
        <Button onClick={() => setShowCreate(true)}><Plus className="h-4 w-4 mr-2" />Nuevo Libro</Button>
      </div>

      <div className="relative max-w-md">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
        <input value={search} onChange={e => setSearch(e.target.value)} className="w-full pl-10 pr-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" placeholder="Buscar libros..." />
      </div>

      {isLoading ? <TableSkeleton /> : !filtered.length ? (
        <EmptyState icon={Library} title="No hay libros" description={search ? 'Intenta otra búsqueda' : 'Crea tu primer libro'} />
      ) : (
        <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-gray-100 bg-gray-50">
                <th className="text-left p-3 font-medium text-gray-500">Título</th>
                <th className="text-left p-3 font-medium text-gray-500">ISBN</th>
                <th className="text-left p-3 font-medium text-gray-500">Año</th>
                <th className="text-left p-3 font-medium text-gray-500">Estado</th>
                <th className="text-left p-3 font-medium text-gray-500">Imagen</th>
                <th className="text-right p-3 font-medium text-gray-500">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filtered.map(book => (
                <tr key={book.id} className="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                  <td className="p-3 font-medium text-gray-900">{book.title}</td>
                  <td className="p-3 text-gray-500">{book.bookInformation.isbn}</td>
                  <td className="p-3 text-gray-500">{book.bookInformation.publicationYear}</td>
                  <td className="p-3">
                    <Badge className={statusColor(book.status)}>{book.status === 'VAILABLE' ? 'Disponible' : book.status}</Badge>
                  </td>
                  <td className="p-3">
                    <Button size="sm" variant="ghost" onClick={() => { setShowImage(book.id); setImageUrl(book.image || ''); }}>
                      <Image className="h-4 w-4" />
                    </Button>
                  </td>
                  <td className="p-3 text-right">
                    <div className="flex items-center justify-end gap-2">
                      <select
                        value={book.status}
                        onChange={e => setConfirmStatus({ id: book.id, status: e.target.value })}
                        className="text-xs px-2 py-1 rounded border border-gray-300"
                      >
                        <option value="VAILABLE">Disponible</option>
                        <option value="RESERVED">Reservado</option>
                        <option value="BORROWED">Prestado</option>
                        <option value="LOST">Perdido</option>
                        <option value="DAMAGED">Dañado</option>
                        <option value="INACTIVE">Inactivo</option>
                      </select>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <Modal open={showCreate} onClose={() => setShowCreate(false)} title="Nuevo Libro">
        <div className="space-y-3">
          <div><label className="text-xs font-medium text-gray-500">Título</label><input value={form.title} onChange={e => setForm({ ...form, title: e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" /></div>
          <div><label className="text-xs font-medium text-gray-500">Descripción</label><textarea value={form.description} onChange={e => setForm({ ...form, description: e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none resize-none h-20" /></div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="text-xs font-medium text-gray-500">ISBN</label><input value={form.isbn} onChange={e => setForm({ ...form, isbn: e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" /></div>
            <div><label className="text-xs font-medium text-gray-500">Año</label><input type="number" value={form.publicationYear} onChange={e => setForm({ ...form, publicationYear: e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" /></div>
            <div><label className="text-xs font-medium text-gray-500">Precio</label><input type="number" step="0.01" value={form.price} onChange={e => setForm({ ...form, price: +e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" /></div>
            <div><label className="text-xs font-medium text-gray-500">Páginas</label><input type="number" value={form.pages} onChange={e => setForm({ ...form, pages: +e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" /></div>
          </div>
          <div><label className="text-xs font-medium text-gray-500">URL Imagen (opcional)</label><input value={form.image} onChange={e => setForm({ ...form, image: e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" /></div>
          <div>
            <label className="text-xs font-medium text-gray-500">Autor ID</label>
            <select value={form.authorId} onChange={e => setForm({ ...form, authorId: e.target.value })} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none">
              <option value="">Seleccionar...</option>
              {authors?.map(a => <option key={a.id} value={a.id}>{a.fullName}</option>)}
            </select>
          </div>
          <Button className="w-full" onClick={handleCreate} loading={createBook.isPending}>Crear Libro</Button>
        </div>
      </Modal>

      <Modal open={!!showImage} onClose={() => setShowImage(null)} title="Actualizar Imagen">
        <div className="space-y-3">
          <input value={imageUrl} onChange={e => setImageUrl(e.target.value)} className="w-full px-3 py-2 rounded-lg border border-gray-300 text-sm focus:ring-2 focus:ring-primary-500 outline-none" placeholder="URL de la imagen" />
          <Button className="w-full" onClick={handleImage} loading={updateImage.isPending}>Guardar</Button>
        </div>
      </Modal>

      <ConfirmDialog open={!!confirmStatus} onClose={() => setConfirmStatus(null)} onConfirm={handleStatus}
        title="Cambiar Estado" message="¿Estás seguro de cambiar el estado de este libro?" loading={updateStatus.isPending} />
    </div>
  );
}
