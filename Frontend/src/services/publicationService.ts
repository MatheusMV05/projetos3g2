import {api} from './api';

// Interfaces baseadas nos DTOs do backend
export interface CategoryDTO {
    id: number;
    name: string;
    slug: string;
}

export interface TagDTO {
    id: number;
    name: string;
    slug: string;
}

export interface PublicationDTO {
    id: number;
    title: string;
    slug: string;
    summary: string;
    content: string;
    authorName: string;
    publishedAt: string;
    category: CategoryDTO;
    tags: TagDTO[];
    // Adicione outros campos conforme necessário
}

// Interface para a resposta paginada
export interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

export const PublicationService = {
    /**
     * Lista publicações de forma paginada.
     * @param page O número da página (inicia em 0).
     * @param size O número de itens por página.
     * @returns Uma Promise com a página de publicações.
     */
    listarPublicacoes: async (page = 0, size = 6): Promise<Page<PublicationDTO>> => {
        const response = await api.get<Page<PublicationDTO>>('/publications', {
            params: {page, size, sort: 'publishedAt,desc'}
        });
        return response.data;
    },
};