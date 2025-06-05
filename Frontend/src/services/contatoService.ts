import {api} from './api';

// Interface para o payload da mensagem, baseada no MensagemDTO do backend
export interface MensagemPayload {
    nome: string;
    email: string;
    assunto: string;
    mensagem: string;
    recaptchaToken: string; // Essencial para o backend
}

/**
 * Serviço para enviar mensagens do formulário de contato.
 */
export const ContatoService = {
    /**
     * Envia uma nova mensagem para a API.
     * @param dados - Os dados do formulário a serem enviados.
     */
    enviarMensagem: async (dados: MensagemPayload): Promise<void> => {
        await api.post('/public/contato', dados);
    },
};