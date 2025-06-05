import { api } from './api';
import './authInterceptor'; // Importa e configura os interceptors automaticamente

// Interfaces baseadas na documentação do backend
export interface AuthenticationRequest {
    email: string;
    senha: string; // O backend espera 'senha', não 'password'
}

export interface AuthenticationResponse {
    token: string;
    requiresMfa: boolean;
    mfaSecret?: string;
    email: string;
}

export interface RegisterRequest {
    nome: string;
    email: string;
    senha: string;
    mfaAtivado?: boolean;
}

export interface MfaVerificationRequest {
    email: string;
    code: string;
}

/**
 * Serviço de autenticação para integração com a API do backend.
 */
export const AuthService = {
    /**
     * Realiza o login do usuário.
     * @param email - Email do usuário
     * @param password - Senha do usuário
     * @returns Promise com os dados de resposta da autenticação
     */
    login: async (email: string, password: string): Promise<AuthenticationResponse> => {
        const payload: AuthenticationRequest = {
            email,
            senha: password, // Mapeando 'password' para 'senha'
        };

        const response = await api.post<AuthenticationResponse>('/auth/authenticate', payload);

        // Se o login for bem-sucedido e não requerer MFA, armazena o token
        if (response.data.token && !response.data.requiresMfa) {
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('userEmail', response.data.email);

            // Configurar o token no header para próximas requisições
            api.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
        }

        return response.data;
    },

    /**
     * Registra um novo usuário.
     * @param userData - Dados do usuário para registro
     */
    register: async (userData: RegisterRequest): Promise<AuthenticationResponse> => {
        const response = await api.post<AuthenticationResponse>('/auth/register', userData);
        return response.data;
    },

    /**
     * Verifica o código MFA.
     * @param email - Email do usuário
     * @param code - Código MFA
     */
    verifyMfa: async (email: string, code: string): Promise<AuthenticationResponse> => {
        const payload: MfaVerificationRequest = { email, code };
        const response = await api.post<AuthenticationResponse>('/auth/mfa/verify', payload);

        // Armazena o token após verificação MFA bem-sucedida
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('userEmail', response.data.email);
            api.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
        }

        return response.data;
    },

    /**
     * Realiza o logout do usuário.
     */
    logout: (): void => {
        localStorage.removeItem('token');
        localStorage.removeItem('userEmail');
        delete api.defaults.headers.common['Authorization'];
    },

    /**
     * Verifica se o usuário está autenticado.
     */
    isAuthenticated: (): boolean => {
        const token = localStorage.getItem('token');
        return !!token;
    },

    /**
     * Obtém o token armazenado.
     */
    getToken: (): string | null => {
        return localStorage.getItem('token');
    },

    /**
     * Obtém o email do usuário logado.
     */
    getUserEmail: (): string | null => {
        return localStorage.getItem('userEmail');
    },

    /**
     * Inicializa o serviço configurando o token se já estiver armazenado.
     */
    initialize: (): void => {
        const token = localStorage.getItem('token');
        if (token) {
            api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        }
    },
};