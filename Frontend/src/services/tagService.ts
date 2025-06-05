import {api} from './api';
import {TagDTO} from './publicationService'; // Reutilizando a interface já definida

export const TagService = {
    /**
     * Busca todas as tags de publicações.
     * @returns Uma Promise com um array de tags.
     */
    listarTodas: async (): Promise<TagDTO[]> => {
        const response = await api.get<TagDTO[]>('/tags');
        return response.data;
    },
};