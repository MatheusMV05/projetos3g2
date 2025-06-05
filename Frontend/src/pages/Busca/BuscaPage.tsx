import React, {useEffect, useState} from 'react';
import {Link, useSearchParams} from 'react-router-dom';
import {PaginaService, Page} from '../../services/paginaService';
import {Pagina} from '../../types/pagina';
import styles from './BuscaPage.module.css';

const BuscaPage: React.FC = () => {
    const [searchParams] = useSearchParams();
    const query = searchParams.get('q');

    const [resultados, setResultados] = useState<Page<Pagina> | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (query) {
            setLoading(true);
            PaginaService.pesquisar(query)
                .then(data => {
                    setResultados(data);
                })
                .catch(err => {
                    console.error("Erro na busca:", err);
                    setResultados(null);
                })
                .finally(() => {
                    setLoading(false);
                });
        } else {
            setLoading(false);
            setResultados(null);
        }
    }, [query]);

    return (
        <div className={styles.buscaContainer}>
            <h1>Resultados da Busca</h1>
            {query ? (
                <p className={styles.termoBuscado}>Você buscou por: <strong>"{query}"</strong></p>
            ) : (
                <p>Por favor, digite um termo na busca.</p>
            )}

            {loading && <p>Buscando...</p>}

            {!loading && resultados && (
                <>
                    <p className={styles.totalResultados}>{resultados.totalElements} resultado(s) encontrado(s).</p>
                    <div className={styles.resultadosList}>
                        {resultados.content.map(pagina => (
                            <div key={pagina.id} className={styles.resultadoItem}>
                                <Link to={`/pagina/${pagina.slug}`}>
                                    <h3>{pagina.titulo}</h3>
                                </Link>
                                {/* O ideal seria exibir um trecho do conteúdo onde o termo foi encontrado */}
                                <p>{pagina.conteudo.substring(0, 150)}...</p>
                            </div>
                        ))}
                    </div>
                    {/* Aqui poderia ser adicionada a lógica de paginação */}
                </>
            )}

            {!loading && (!resultados || resultados.totalElements === 0) && query && (
                <p>Nenhum resultado encontrado.</p>
            )}
        </div>
    );
};

export default BuscaPage;