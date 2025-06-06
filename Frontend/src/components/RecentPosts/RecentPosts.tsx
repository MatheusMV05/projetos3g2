import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {PublicationService, PublicationDTO} from '../../services/publicationService';
import styles from './RecentPosts.module.css';

const RecentPosts: React.FC = () => {
    const [posts, setPosts] = useState<PublicationDTO[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchRecentPosts = async () => {
            try {
                // Busca os 3 posts mais recentes
                const recentPosts = await PublicationService.listarRecentes(3);
                setPosts(recentPosts);
            } catch (error) {
                console.error("Erro ao buscar posts recentes:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchRecentPosts();
    }, []);

    if (loading) {
        return <section className={styles.recentPostsSection}><p>Carregando...</p></section>;
    }

    if (posts.length === 0) {
        return null; // Não renderiza nada se não houver posts
    }

    return (
        <section className={styles.recentPostsSection}>
            <h2 className={styles.sectionTitle}>Últimas Notícias e Análises</h2>
            <div className={styles.postsGrid}>
                {posts.map(post => (
                    <Link to={`/publicacao/${post.slug}`} key={post.id} className={styles.cardLink}>
                        <div className={styles.card}>
                            <div className={styles.cardImagePlaceholder}></div>
                            <div className={styles.cardContent}>
                                <span className={styles.cardCategory}>{post.category?.name || 'Geral'}</span>
                                <h3 className={styles.cardTitle}>{post.title}</h3>
                                <p className={styles.cardSummary}>{post.summary}</p>
                                <span className={styles.readMore}>Leia mais →</span>
                            </div>
                        </div>
                    </Link>
                ))}
            </div>
        </section>
    );
};

export default RecentPosts;