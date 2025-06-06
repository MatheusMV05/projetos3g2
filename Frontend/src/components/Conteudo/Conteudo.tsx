import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import styles from './Conteudo.module.css';
import {PaginaService} from '../../services/paginaService';
import {Pagina} from '../../types/pagina';
import {EventoService, Evento} from '../../services/eventoService';

type ContentTab = 'paginas' | 'eventos';

const Conteudo: React.FC = () => {
    const navigate = useNavigate();

    // Estado para abas
    const [activeTab, setActiveTab] = useState<ContentTab>('paginas');

    // Estados para páginas
    const [conteudos, setConteudos] = useState<Pagina[]>([]);
    const [loadingPaginas, setLoadingPaginas] = useState(true);
    const [errorPaginas, setErrorPaginas] = useState<string | null>(null);

    // Estados para eventos
    const [eventos, setEventos] = useState<Evento[]>([]);
    const [loadingEventos, setLoadingEventos] = useState(true);
    const [errorEventos, setErrorEventos] = useState<string | null>(null);

    // Buscar páginas
    useEffect(() => {
        const fetchConteudos = async () => {
            try {
                const data = await PaginaService.listarTodas();
                setConteudos(data);
            } catch (err) {
                setErrorPaginas('Falha ao carregar o conteúdo. Tente novamente mais tarde.');
                console.error(err);
            } finally {
                setLoadingPaginas(false);
            }
        };

        fetchConteudos();
    }, []);

    // Buscar eventos
    useEffect(() => {
        const fetchEventos = async () => {
            try {
                const data = await EventoService.listarTodos();
                setEventos(data);
            } catch (err) {
                setErrorEventos('Falha ao carregar os eventos. Tente novamente mais tarde.');
                console.error(err);
            } finally {
                setLoadingEventos(false);
            }
        };

        fetchEventos();
    }, []);

    const handleEditPagina = (pagina: Pagina) => {
        navigate('/post-editor', {
            state: {
                id: pagina.id,
                titulo: pagina.titulo,
                conteudo: pagina.conteudo,
            },
        });
    };

    const handleEditEvento = (evento: Evento) => {
        navigate('/evento-editor', {
            state: {
                id: evento.id,
                titulo: evento.titulo,
                descricao: evento.descricao,
                dataInicio: evento.dataInicio,
                dataFim: evento.dataFim,
                local: evento.local,
                capacidadeMaxima: evento.capacidadeMaxima,
            },
        });
    };

    const handleDeleteEvento = async (eventoId: number) => {
        if (window.confirm('Tem certeza que deseja excluir este evento?')) {
            try {
                await EventoService.excluir(eventoId);
                setEventos(eventos.filter(e => e.id !== eventoId));
            } catch (error) {
                console.error('Erro ao excluir evento:', error);
                alert('Erro ao excluir evento. Tente novamente.');
            }
        }
    };

    const formatarData = (data: string) => {
        return new Date(data + 'T00:00:00').toLocaleDateString('pt-BR');
    };

    const renderPaginasContent = () => {
        if (loadingPaginas) {
            return <p className={styles.loadingState}>Carregando conteúdo...</p>;
        }

        if (errorPaginas) {
            return <p className={styles.errorState}>{errorPaginas}</p>;
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

        return (
            <div className={styles.cardsGrid}>
                {conteudos.map((pagina) => (
                    <div key={pagina.id} className={styles.card} onClick={() => handleEditPagina(pagina)}>
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

    const renderEventosContent = () => {
        if (loadingEventos) {
            return <p className={styles.loadingState}>Carregando eventos...</p>;
        }

        if (errorEventos) {
            return <p className={styles.errorState}>{errorEventos}</p>;
        }

        if (eventos.length === 0) {
            return (
                <div className={styles.emptyState}>
                    <button
                        className={styles.createLink}
                        onClick={() => navigate('/evento-editor', {state: {titulo: '', descricao: ''}})}
                    >
                        + Criar novo evento agora
                    </button>
                </div>
            );
        }

        return (
            <div className={styles.cardsGrid}>
                {eventos.map((evento) => (
                    <div key={evento.id} className={`${styles.card} ${styles.eventoCard}`}>
                        <div className={styles.cardContent}>
                            <div className={styles.eventoHeader}>
                                <h4 className={styles.cardTitle}>{evento.titulo}</h4>
                                <div className={styles.eventoActions}>
                                    <button
                                        className={styles.editButton}
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleEditEvento(evento);
                                        }}
                                        title="Editar evento"
                                    >
                                        ✏️
                                    </button>
                                    <button
                                        className={styles.deleteButton}
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleDeleteEvento(evento.id);
                                        }}
                                        title="Excluir evento"
                                    >
                                        🗑️
                                    </button>
                                </div>
                            </div>
                            <p className={styles.eventoDescricao}>{evento.descricao}</p>
                            <div className={styles.eventoInfo}>
                                <p className={styles.eventoData}>
                                    📅 {formatarData(evento.dataInicio)}
                                    {evento.dataFim && evento.dataFim !== evento.dataInicio && (
                                        <span> até {formatarData(evento.dataFim)}</span>
                                    )}
                                </p>
                                <p className={styles.eventoLocal}>📍 {evento.local}</p>
                                {evento.capacidadeMaxima && (
                                    <p className={styles.eventoCapacidade}>
                                        👥 Capacidade: {evento.capacidadeMaxima} pessoas
                                    </p>
                                )}
                            </div>
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
                    <div className={styles.headerActions}>
                        {activeTab === 'paginas' ? (
                            <button
                                className={styles.createBtn}
                                onClick={() => navigate('/post-editor', {state: {titulo: 'Novo conteúdo', conteudo: ''}})}
                            >
                                + Criar novo conteúdo
                            </button>
                        ) : (
                            <button
                                className={styles.createBtn}
                                onClick={() => navigate('/evento-editor', {state: {titulo: '', descricao: ''}})}
                            >
                                + Criar novo evento
                            </button>
                        )}
                    </div>
                </header>

                {/* Navegação por abas */}
                <div className={styles.tabNavigation}>
                    <button
                        className={`${styles.tabButton} ${activeTab === 'paginas' ? styles.activeTab : ''}`}
                        onClick={() => setActiveTab('paginas')}
                    >
                        📄 Páginas ({conteudos.length})
                    </button>
                    <button
                        className={`${styles.tabButton} ${activeTab === 'eventos' ? styles.activeTab : ''}`}
                        onClick={() => setActiveTab('eventos')}
                    >
                        📅 Eventos ({eventos.length})
                    </button>
                </div>

                {/* Conteúdo baseado na aba ativa */}
                <div className={styles.tabContent}>
                    {activeTab === 'paginas' ? renderPaginasContent() : renderEventosContent()}
                </div>
            </main>
        </div>
    );
};

export default Conteudo;