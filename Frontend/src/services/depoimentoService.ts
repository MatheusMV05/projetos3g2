import {api} from './api';

// Interface para representar o Depoimento, baseada na entidade do backend
export interface Depoimento {
    id: number;
    nome: string;
    cargo: string;
    organizacao: string;
    texto: string;
    fotoUrl: string | null;
    ano: number;
    destaque: boolean;
    ativo: boolean;
}

export const DepoimentoService = {
    /**
     * Busca todos os depoimentos ativos.
     * @returns Uma Promise com um array de depoimentos.
     */
    listarAtivos: async (): Promise<Depoimento[]> => {
        const response = await api.get<Depoimento[]>('/public/depoimentos');
        return response.data;
    },
};