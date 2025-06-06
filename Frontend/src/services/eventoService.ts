import {api} from './api';

// Interface para representar o Evento, baseada na entidade do backend
export interface Evento {
    id: number;
    titulo: string;
    descricao: string;
    dataInicio: string; // A data virá como string no formato ISO (ex: "2024-12-31")
    dataFim: string | null;
    local: string;
    capacidadeMaxima: number | null;
}

export const EventoService = {
    /**
     * Busca todos os eventos.
     * @returns Uma Promise com um array de eventos.
     */
    listarTodos: async (): Promise<Evento[]> => {
        const response = await api.get<Evento[]>('/eventos');
        return response.data;
    },
};