import { api } from './api';
import { AuthService } from './authService';

/**
 * Configura interceptors para o axios para lidar com autenticação
 */
export const setupAuthInterceptors = () => {
    // Interceptor para requisições - adiciona o token automaticamente
    api.interceptors.request.use(
        (config) => {
            const token = AuthService.getToken();
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    // Interceptor para respostas - lida com tokens expirados
    api.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            // Se o token expirou ou é inválido
            if (error.response?.status === 401) {
                const currentPath = window.location.pathname;

                // Só faz logout automático se não estivermos em páginas públicas
                const publicPaths = ['/', '/sobre', '/servicos', '/noticias', '/evento'];
                const isPublicPath = publicPaths.some(path => currentPath === path);

                if (!isPublicPath && AuthService.isAuthenticated()) {
                    console.warn('🔒 Token expirado ou inválido. Fazendo logout...');
                    AuthService.logout();

                    // Opcional: redirecionar para home ou mostrar modal de login
                    if (window.location.pathname.startsWith('/admin') ||
                        window.location.pathname.startsWith('/conteudo')) {
                        window.location.href = '/';
                    }
                }
            }

            return Promise.reject(error);
        }
    );

    console.log('🛡️ Interceptors de autenticação configurados');
};

// Auto-executa a configuração quando o módulo é importado
setupAuthInterceptors();