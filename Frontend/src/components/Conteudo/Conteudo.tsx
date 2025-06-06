import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import styles from './Conteudo.module.css';
import {PaginaService} from '../../services/paginaService'; // 1. Importar o serviço
import {Pagina} from '../../types/pagina'; // 2. Importar o tipo

const Conteudo: React.FC = () => {
    const navigate = useNavigate();

    // 3. Tipar o estado e adicionar estados de loading e erro
    const [conteudos, setConteudos] = useState<Pagina[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    // 4. Buscar os dados da API ao montar o componente
    useEffect(() => {
        const fetchConteudos = async () => {
            try {
                const data = await PaginaService.listarTodas();
                setConteudos(data);
            } catch (err) {
                setError('Falha ao carregar o conteúdo. Tente novamente mais tarde.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchConteudos();
    }, []); // Array vazio garante que a busca ocorra apenas uma vez

    const handleEdit = (pagina: Pagina) => {
        navigate('/post-editor', {
            state: {
                id: pagina.id,
                titulo: pagina.titulo,
                conteudo: pagina.conteudo,
            },
        });
    };

    const renderContent = () => {
        if (loading) {
            return <p className={styles.loadingState}>Carregando conteúdo...</p>;
        }

        if (error) {
            return <p className={styles.errorState}>{error}</p>;
        }

        if (conteudos.length === 0) {
            return (
                <div className={styles.emptyState}>
                    <button
                        className={styles.createLink}
                        onClick={() => navigate('/post-editor', {state: {titulo: 'Novo conteúdo', conteudo: ''}})}
                    >
                        + Criar novo conteúdo agora
                    </button>
                </div>
            );
        }

        // 5. Renderizar a lista de páginas
        return (
            <div className={styles.cardsGrid}>
                {conteudos.map((pagina) => (
                    <div key={pagina.id} className={styles.card} onClick={() => handleEdit(pagina)}>
                        <div className={styles.cardContent}>
                            <h4 className={styles.cardTitle}>{pagina.titulo}</h4>
                            <p className={styles.cardMeta}>
                                Atualizado em: {new Date(pagina.dataAtualizacao || '').toLocaleDateString('pt-BR')}
                            </p>
                        </div>
                    </div>
                ))}
            </div>
        );
    };

    return (
        <div className={styles.container}>
            <main className={styles.main}>
                <header className={styles.header}>
                    <div>
                        <h2>Brasfi.com.br</h2>
                        <p>Gerencie seu conteúdo</p>
                    </div>
                    <button
                        className={styles.createBtn}
                        onClick={() => navigate('/post-editor', {state: {titulo: 'Novo conteúdo', conteudo: ''}})}
                    >
                        + Criar novo conteúdo
                    </button>
                </header>
                {renderContent()}
            </main>
        </div>
    );
};

export default Conteudo;