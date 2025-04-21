import { useState, useEffect } from 'react';
import { PaginaService } from '../services/paginaService';
import { Pagina, PaginaInput } from '../types/pagina';

const ApiTest = () => {
  const [paginas, setPaginas] = useState<Pagina[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [formData, setFormData] = useState<PaginaInput>({
    titulo: '',
    slug: '',
    conteudo: ''
  });
  const [success, setSuccess] = useState<string | null>(null);

  // Fetch all pages on component mount
  useEffect(() => {
    fetchPaginas();
  }, []);

  const fetchPaginas = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await PaginaService.listarTodas();
      setPaginas(data);
      setSuccess('Páginas carregadas com sucesso!');
    } catch (err) {
      console.error('Error fetching pages:', err);
      setError('Falha ao carregar páginas. Verifique se a API está rodando.');
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);
    
    try {
      await PaginaService.criar(formData);
      setSuccess('Página criada com sucesso!');
      setFormData({ titulo: '', slug: '', conteudo: '' });
      fetchPaginas(); // Refresh the list
    } catch (err: any) {
      console.error('Error creating page:', err);
      setError(`Falha ao criar página: ${err.response?.data?.mensagem || err.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Tem certeza que deseja excluir esta página?')) return;
    
    setLoading(true);
    setError(null);
    setSuccess(null);
    
    try {
      await PaginaService.excluir(id);
      setSuccess('Página excluída com sucesso!');
      fetchPaginas(); // Refresh the list
    } catch (err: any) {
      console.error('Error deleting page:', err);
      setError(`Falha ao excluir página: ${err.response?.data?.mensagem || err.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="api-test-container">
      <h2>Teste de Conectividade da API</h2>
      
      {/* Status Message */}
      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}
      
      {/* Create Page Form */}
      <form onSubmit={handleSubmit} className="page-form">
        <h3>Criar Nova Página</h3>
        <div className="form-group">
          <label htmlFor="titulo">Título:</label>
          <input 
            type="text" 
            id="titulo"
            name="titulo" 
            value={formData.titulo} 
            onChange={handleInputChange} 
            required 
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="slug">Slug:</label>
          <input 
            type="text" 
            id="slug"
            name="slug" 
            value={formData.slug} 
            onChange={handleInputChange} 
            required 
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="conteudo">Conteúdo:</label>
          <textarea 
            id="conteudo"
            name="conteudo" 
            value={formData.conteudo} 
            onChange={handleInputChange} 
            rows={5}
          />
        </div>
        
        <button type="submit" disabled={loading}>
          {loading ? 'Salvando...' : 'Criar Página'}
        </button>
      </form>
      
      {/* Pages List */}
      <div className="pages-list">
        <h3>Páginas Existentes</h3>
        {loading && <p>Carregando...</p>}
        
        {!loading && paginas.length === 0 && (
          <p>Nenhuma página encontrada.</p>
        )}
        
        {paginas.map(pagina => (
          <div key={pagina.id} className="page-item">
            <h4>{pagina.titulo}</h4>
            <p><strong>Slug:</strong> {pagina.slug}</p>
            <p><strong>Conteúdo:</strong> {pagina.conteudo?.substring(0, 100)}...</p>
            <p><strong>Criado em:</strong> {new Date(pagina.dataCriacao || '').toLocaleString()}</p>
            <button 
              onClick={() => handleDelete(pagina.id)}
              className="delete-button"
            >
              Excluir
            </button>
          </div>
        ))}
        
        <button onClick={fetchPaginas} disabled={loading} className="refresh-button">
          {loading ? 'Carregando...' : 'Atualizar Lista'}
        </button>
      </div>
    </div>
  );
};

export default ApiTest;