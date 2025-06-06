import { api } from './api';

// Corresponde ao UsuarioDTO do backend
export interface Usuario {
    id: number;
    email: string;
    nome: string;
    perfis: string[];
    ativo: boolean;
    ultimoLogin: string | null;
    dataCriacao: string;
}

export const EquipeService = {
    /**
     * Busca todos os usuários da equipe.
     * Requer autenticação de ADMIN.
     */
    listarMembros: async (): Promise<Usuario[]> => {
        const response = await api.get<Usuario[]>('/usuarios');
        return response.data;
    },

    /**
     * Exclui um membro da equipe.
     * Requer autenticação de ADMIN.
     * @param id - O ID do usuário a ser excluído.
     */
    excluirMembro: async (id: number): Promise<void> => {
        await api.delete(`/usuarios/${id}`);
    }
};