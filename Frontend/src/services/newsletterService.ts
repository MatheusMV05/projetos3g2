import {api} from './api';

export const NewsletterService = {
    /**
     * Solicita o cancelamento da inscrição de um e-mail na newsletter.
     * @param email - O e-mail a ser cancelado.
     */
    cancelarInscricao: async (email: string): Promise<void> => {
        // O Axios envia os parâmetros para requisições DELETE através do objeto de configuração 'params'
        await api.delete('/public/newsletter/cancelar', {
            params: {email}
        });
    },
};