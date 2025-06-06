import {api} from './api';

// Interface para representar a Métrica de Impacto
export interface MetricaImpacto {
    id: number;
    titulo: string;
    valor: number; // Correção: Usando 'number' em vez de BigDecimal
    unidade: string;
    descricao: string;
    categoria: string;
    ano: number;
    icone: string;
    ordem: number;
    ativo: boolean;
}

export const MetricaImpactoService = {
    /**
     * Busca todas as métricas de impacto ativas.
     * @returns Uma Promise com um array de métricas.
     */
    listarAtivas: async (): Promise<MetricaImpacto[]> => {
        const response = await api.get<MetricaImpacto[]>('/public/metricas');
        return response.data;
    },
};