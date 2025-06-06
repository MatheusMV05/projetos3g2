import {api} from './api';

// Interface baseada no ParceiroSummaryDTO do backend
export interface ParceiroSummary {
    id: number;
    nomeOrganizacao: string;
    descricaoCurta: string;
    logoUrl: string;
    siteUrl: string;
    tipoParceria: string;
    setorAtuacao: string;
    nomesCategorias: string[];
}

// Interface para a resposta paginada do Spring Boot
export interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

export const ParceiroService = {
    /**
     * Busca a lista pública e paginada de parceiros.
     * @param page O número da página (inicia em 0).
     * @param size O número de itens por página.
     * @returns Uma Promise com a página de parceiros.
     */
    listarPublicos: async (page = 0, size = 6): Promise<Page<ParceiroSummary>> => {
        const response = await api.get<Page<ParceiroSummary>>('/public/parceiros', {
            params: {page, size}
        });
        return response.data;
    },
};