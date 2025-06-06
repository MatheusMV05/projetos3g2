import {api} from './api';
import {Pagina, PaginaInput} from '../types/pagina';

// Interface para representar a resposta paginada do Spring Boot
export interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number; // Página atual
}

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

    /**
     * Realiza uma busca full-text por páginas.
     * @param termo O termo a ser buscado.
     * @param page O número da página (começa em 0).
     * @param size O tamanho da página.
     * @returns Uma Promise com o resultado paginado das páginas.
     */
    pesquisar: async (termo: string, page = 0, size = 10): Promise<Page<Pagina>> => {
        const response = await api.get<Page<Pagina>>('/paginas/search', {
            params: {
                q: termo,
                page,
                size,
            },
        });
        return response.data;
    },
};