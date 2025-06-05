import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {PaginaService} from '../../services/paginaService';
import {Pagina} from '../../types/pagina';
import styles from './PaginaViewPage.module.css';

const PaginaViewPage: React.FC = () => {
    // useParams() irá extrair o 'id' da URL, ex: /pagina/view/1
    const {id} = useParams<{ id: string }>();

    const [pagina, setPagina] = useState<Pagina | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Converte o ID da string da URL para número
        const paginaId = Number(id);

        if (paginaId) {
            const fetchPagina = async () => {
                setLoading(true);
                setError(null);
                try {
                    const data = await PaginaService.buscarPorId(paginaId);
                    setPagina(data);
                } catch (err) {
                    setError('Página não encontrada ou erro ao carregar.');
                    console.error(err);
                } finally {
                    setLoading(false);
                }
            };
            fetchPagina();
        } else {
            setError('ID da página inválido.');
            setLoading(false);
        }
    }, [id]); // O efeito re-executa se o ID na URL mudar

    const renderContent = () => {
        if (loading) {
            return <p>Carregando página...</p>;
        }
        if (error) {
            return <div className={styles.error}>{error}</div>;
        }
        if (pagina) {
            return (
                <>
                    <h1>{pagina.titulo}</h1>
                    <hr/>
                    {/* Usar dangerouslySetInnerHTML para renderizar conteúdo HTML do backend */}
                    <div dangerouslySetInnerHTML={{__html: pagina.conteudo || ''}}/>
                </>
            );
        }
        return null;
    }

    return (
        <div className={styles.pageContainer}>
            {renderContent()}
        </div>
    );
};

export default PaginaViewPage;