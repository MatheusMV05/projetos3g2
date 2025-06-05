import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import styles from './NoticiasIntro.module.css';
import {PublicationService, PublicationDTO} from '../../services/publicationService';

const NoticiasIntro: React.FC = () => {
    const [featuredPosts, setFeaturedPosts] = useState<PublicationDTO[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchFeatured = async () => {
            try {
                const data = await PublicationService.listarDestaques();
                // Pega apenas os 2 primeiros destaques para este layout
                setFeaturedPosts(data.slice(0, 2));
            } catch (error) {
                console.error("Erro ao buscar publicações em destaque:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchFeatured();
    }, []);

    if (loading) {
        return (
            <div className={styles.container}>
                <p>Carregando destaques...</p>
            </div>
        );
    }

    return (
        <div className={styles.container}>
            {featuredPosts.map(post => (
                <div className={styles.card} key={post.id}>
                    <div className={styles.image}>
                        <div className={styles.imagePlaceholder}>
                            {/* Idealmente, a URL da imagem viria do DTO: <img src={post.imageUrl} /> */}
                            <img src="/PlaceholderImage.svg" alt={post.title}/>
                        </div>
                    </div>
                    <div className={styles.info}>
                        <div className={styles.tags}>
                            <span className={styles.tag}>{post.category?.name || 'Destaque'}</span>
                            {/* O tempo de leitura pode ser um campo no DTO ou calculado */}
                            <span className={styles.time}>5 min leitura</span>
                        </div>
                        <h2 className={styles.title}>{post.title}</h2>
                        <p className={styles.description}>{post.summary}</p>
                        <Link to={`/publicacao/${post.slug}`} className={styles.readMore}>
                            Leia mais &gt;
                        </Link>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default NoticiasIntro;