import { api } from './api';
import { CategoryDTO } from './publicationService'; // Reutilizando a interface já definida

export const CategoryService = {
    /**
     * Busca todas as categorias de publicações.
     * @returns Uma Promise com um array de categorias.
     */
    listarTodas: async (): Promise<CategoryDTO[]> => {
        const response = await api.get<CategoryDTO[]>('/categories');
        return response.data;
    },
};