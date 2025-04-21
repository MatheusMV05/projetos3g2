export interface Pagina {
    id: number;
    titulo: string;
    slug: string;
    conteudo: string;
    dataCriacao?: string;
    dataAtualizacao?: string;
  }
  
  export interface PaginaInput {
    titulo: string;
    slug: string;
    conteudo: string;
  }