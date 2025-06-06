import {api} from './api';

// Interface para o payload do evento, baseada no DTO do backend
export interface EventoAnalyticsPayload {
    tipo: 'PAGEVIEW' | 'EVENT';
    categoria: string;
    acao: string;
    rotulo?: string;
    valor?: number;
}

/**
 * Serviço para registrar eventos de analytics.
 */
export const AnalyticsService = {
    /**
     * Envia um evento para o backend.
     * @param payload - Os dados do evento a serem registrados.
     */
    registrarEvento: async (payload: EventoAnalyticsPayload): Promise<void> => {
        try {
            // Adiciona informações automáticas do navegador
            const fullPayload = {
                ...payload,
                paginaReferencia: document.referrer,
                paginaAtual: window.location.href,
            };

            // Envia para a API sem esperar pela resposta para não bloquear a UI
            api.post('/public/analytics/evento', fullPayload);

            // Log no console para depuração
            console.log('Analytics Evento:', fullPayload);

        } catch (error) {
            // Silencia o erro para não impactar a experiência do usuário
            console.error('Falha ao registrar evento de analytics:', error);
        }
    },
};