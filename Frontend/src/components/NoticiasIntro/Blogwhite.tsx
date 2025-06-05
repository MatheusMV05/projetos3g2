import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import styles from './blogwhite.module.css';
import {PublicationService, PublicationDTO} from '../../services/publicationService'; // 1. Importar

const Blogwhite: React.FC = () => {
    // 2. Adicionar estados para publicações, loading e erro
    const [publications, setPublications] = useState<PublicationDTO[]>([]);
    const [loading, setLoading] = useState(true);

    // 3. Buscar dados da API
    useEffect(() => {
        const fetchPublications = async () => {
            try {
                const response = await PublicationService.listarPublicacoes(0, 6); // Busca 6 itens para o grid
                setPublications(response.content);
            } catch (error) {
                console.error("Erro ao buscar publicações:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchPublications();
    }, []);

    if (loading) {
        return (
            <section className={styles.blogSection}>
                <p>Carregando publicações...</p>
            </section>
        );
    }

    return (
        <section className={styles.blogSection}>
            {/* Seção de Destaque pode ser mantida ou tornada dinâmica também */}
            <div className={styles.mainHighlight}>
                {/* ... (código existente do destaque) ... */}
            </div>
            <br/><br/>
            <div className={styles.filters}>
                {/* ... (código existente dos filtros) ... */}
            </div>
            <br/><br/>

            {/* 4. Renderizar o grid dinamicamente */}
            <div className={styles.grid}>
                {publications.map((pub) => (
                    <div className={styles.card} key={pub.id}>
                        <div className={styles.cardImage}>
                            {/* Placeholder para a imagem da publicação */}
                            📷
                        </div>
                        <div className={styles.cardContent}>
                            <div className={styles.cardCategory}>{pub.category?.name || 'Sem Categoria'}</div>
                            <Link to={`/publicacao/${pub.slug}`} className={styles.cardTitleLink}>
                                <div className={styles.cardTitle}>{pub.title}</div>
                            </Link>
                            <div className={styles.cardAuthor}>
								<span>
									👤 Por {pub.authorName || 'Autor Desconhecido'} em {new Date(pub.publishedAt).toLocaleDateString('pt-BR')}
								</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default Blogwhite;