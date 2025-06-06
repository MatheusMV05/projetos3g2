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

// DTO para criação de eventos
export interface EventoCreateDTO {
    titulo: string;
    descricao: string;
    dataInicio: string;
    dataFim?: string;
    local: string;
    capacidadeMaxima?: number;
}

// DTO para atualização de eventos
export interface EventoUpdateDTO {
    titulo: string;
    descricao: string;
    dataInicio: string;
    dataFim?: string;
    local: string;
    capacidadeMaxima?: number;
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

    /**
     * Busca um evento por ID.
     * @param id - O ID do evento.
     * @returns Uma Promise com o evento.
     */
    buscarPorId: async (id: number): Promise<Evento> => {
        const response = await api.get<Evento>(`/eventos/${id}`);
        return response.data;
    },

    /**
     * Cria um novo evento.
     * @param evento - Os dados do evento a ser criado.
     * @returns Uma Promise com o evento criado.
     */
    criar: async (evento: EventoCreateDTO): Promise<Evento> => {
        const response = await api.post<Evento>('/eventos', evento);
        return response.data;
    },

    /**
     * Atualiza um evento existente.
     * @param id - O ID do evento a ser atualizado.
     * @param evento - Os novos dados do evento.
     * @returns Uma Promise com o evento atualizado.
     */
    atualizar: async (id: number, evento: EventoUpdateDTO): Promise<Evento> => {
        const response = await api.put<Evento>(`/eventos/${id}`, evento);
        return response.data;
    },

    /**
     * Exclui um evento.
     * @param id - O ID do evento a ser excluído.
     * @returns Uma Promise que resolve quando a exclusão é concluída.
     */
    excluir: async (id: number): Promise<void> => {
        await api.delete(`/eventos/${id}`);
    },

    /**
     * Busca eventos por título.
     * @param titulo - O título a ser buscado.
     * @returns Uma Promise com um array de eventos.
     */
    buscarPorTitulo: async (titulo: string): Promise<Evento[]> => {
        const response = await api.get<Evento[]>('/eventos/search', {
            params: { titulo }
        });
        return response.data;
    },

    /**
     * Busca eventos por local.
     * @param local - O local a ser buscado.
     * @returns Uma Promise com um array de eventos.
     */
    buscarPorLocal: async (local: string): Promise<Evento[]> => {
        const response = await api.get<Evento[]>('/eventos/search', {
            params: { local }
        });
        return response.data;
    },

    /**
     * Busca eventos por período.
     * @param dataInicio - Data de início do período.
     * @param dataFim - Data de fim do período.
     * @returns Uma Promise com um array de eventos.
     */
    buscarPorPeriodo: async (dataInicio: string, dataFim: string): Promise<Evento[]> => {
        const response = await api.get<Evento[]>('/eventos/periodo', {
            params: { dataInicio, dataFim }
        });
        return response.data;
    },

    /**
     * Busca eventos públicos (para páginas que não requerem autenticação).
     * @returns Uma Promise com um array de eventos públicos.
     */
    listarPublicos: async (): Promise<Evento[]> => {
        const response = await api.get<Evento[]>('/public/eventos');
        return response.data;
    },

    /**
     * Valida os dados de um evento antes do envio.
     * @param evento - Os dados do evento a serem validados.
     * @returns Um objeto com os erros encontrados ou null se válido.
     */
    validarEvento: (evento: EventoCreateDTO | EventoUpdateDTO): {[key: string]: string} | null => {
        const erros: {[key: string]: string} = {};

        if (!evento.titulo?.trim()) {
            erros.titulo = 'O título é obrigatório';
        } else if (evento.titulo.length > 200) {
            erros.titulo = 'O título deve ter no máximo 200 caracteres';
        }

        if (!evento.descricao?.trim()) {
            erros.descricao = 'A descrição é obrigatória';
        } else if (evento.descricao.length > 1000) {
            erros.descricao = 'A descrição deve ter no máximo 1000 caracteres';
        }

        if (!evento.dataInicio) {
            erros.dataInicio = 'A data de início é obrigatória';
        } else {
            const dataInicio = new Date(evento.dataInicio + 'T00:00:00');
            const hoje = new Date();
            hoje.setHours(0, 0, 0, 0);

            if (dataInicio < hoje) {
                erros.dataInicio = 'A data de início não pode ser anterior a hoje';
            }
        }

        if (evento.dataFim) {
            const dataInicio = new Date(evento.dataInicio + 'T00:00:00');
            const dataFim = new Date(evento.dataFim + 'T00:00:00');

            if (dataFim < dataInicio) {
                erros.dataFim = 'A data de fim não pode ser anterior à data de início';
            }
        }

        if (!evento.local?.trim()) {
            erros.local = 'O local é obrigatório';
        } else if (evento.local.length > 300) {
            erros.local = 'O local deve ter no máximo 300 caracteres';
        }

        if (evento.capacidadeMaxima !== undefined && evento.capacidadeMaxima !== null) {
            if (evento.capacidadeMaxima < 1) {
                erros.capacidadeMaxima = 'A capacidade deve ser maior que zero';
            } else if (evento.capacidadeMaxima > 10000) {
                erros.capacidadeMaxima = 'A capacidade deve ser menor que 10.000';
            }
        }

        return Object.keys(erros).length > 0 ? erros : null;
    }
};