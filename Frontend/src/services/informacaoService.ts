import {api} from './api';

/**
 * Interface que representa a estrutura de uma Informação Institucional
 * conforme a entidade do backend.
 */
export interface InformacaoInstitucional {
    id: number;
    chave: string;
    valor: string;
    tipo: string;
    descricao: string;
    ativo: boolean;
    dataCriacao: string;
    dataAtualizacao: string;
}

/**
 * Serviço para buscar informações institucionais da API.
 */
export const InformacaoInstitucionalService = {
    /**
     * Busca uma informação institucional específica pela sua chave.
     * @param chave A chave única da informação (ex: 'missao_titulo').
     * @returns Uma Promise com a informação institucional.
     */
    buscarPorChave: async (chave: string): Promise<InformacaoInstitucional> => {
        const response = await api.get<InformacaoInstitucional>(`/public/institucionais/${chave}`);
        return response.data;
    },

    /**
     * Busca todas as informações institucionais ativas como um mapa de chave-valor.
     * Útil para carregar várias informações de uma vez.
     * @returns Um mapa onde a chave é a 'chave' da informação e o valor é o seu 'valor'.
     */
    buscarMapa: async (): Promise<Record<string, string>> => {
        const response = await api.get<Record<string, string>>('/public/institucionais/mapa');
        return response.data;
    },

    /**
     * Busca uma lista de informações institucionais por um tipo específico.
     * @param tipo O tipo a ser buscado (ex: 'FAQ').
     * @returns Uma Promise com um array de informações institucionais.
     */
    buscarPorTipo: async (tipo: string): Promise<InformacaoInstitucional[]> => {
        const response = await api.get<InformacaoInstitucional[]>(`/public/institucionais/tipo/${tipo}`);
        return response.data;
    },

    /**
     * Busca um mapa de chave-valor de informações institucionais por um tipo específico.
     * @param tipo O tipo a ser buscado (ex: 'SOBRE_SECAO').
     * @returns Um mapa onde a chave é a 'chave' da informação e o valor é o seu 'valor'.
     */
    buscarMapaPorTipo: async (tipo: string): Promise<Record<string, string>> => {
        const response = await api.get<Record<string, string>>(`/public/institucionais/mapa/tipo/${tipo}`);
        return response.data;
    }
};