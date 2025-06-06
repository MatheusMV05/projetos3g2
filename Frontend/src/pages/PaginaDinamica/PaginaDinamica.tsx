import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { PaginaService } from '../../services/paginaService';
import { Pagina } from '../../types/pagina';

const PaginaDinamica: React.FC = () => {
  const { slug } = useParams<{ slug: string }>();
  const [pagina, setPagina] = useState<Pagina | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (slug) {
      const fetchPagina = async () => {
        try {
          setLoading(true);
          const data = await PaginaService.buscarPorSlug(slug);
          setPagina(data);
        } catch (err) {
          setError('Página não encontrada.');
          console.error(err);
        } finally {
          setLoading(false);
        }
      };
      fetchPagina();
    }
  }, [slug]);

  if (loading) {
    return <div>Carregando...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div style={{ padding: '2rem' }}>
      <h1>{pagina?.titulo}</h1>
      <div dangerouslySetInnerHTML={{ __html: pagina?.conteudo || '' }} />
    </div>
  );
};

export default PaginaDinamica;