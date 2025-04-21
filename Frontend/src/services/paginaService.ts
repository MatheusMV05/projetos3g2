import { api } from './api';
import { Pagina, PaginaInput } from '../types/pagina';

export const PaginaService = {
  listarTodas: async (): Promise<Pagina[]> => {
    const response = await api.get<Pagina[]>('/paginas');
    return response.data;
  },

  buscarPorId: async (id: number): Promise<Pagina> => {
    const response = await api.get<Pagina>(`/paginas/${id}`);
    return response.data;
  },

  buscarPorSlug: async (slug: string): Promise<Pagina> => {
    const response = await api.get<Pagina>(`/paginas/slug/${slug}`);
    return response.data;
  },

  criar: async (pagina: PaginaInput): Promise<Pagina> => {
    const response = await api.post<Pagina>('/paginas', pagina);
    return response.data;
  },

  atualizar: async (id: number, pagina: PaginaInput): Promise<Pagina> => {
    const response = await api.put<Pagina>(`/paginas/${id}`, pagina);
    return response.data;
  },

  excluir: async (id: number): Promise<void> => {
    await api.delete(`/paginas/${id}`);
  },
};