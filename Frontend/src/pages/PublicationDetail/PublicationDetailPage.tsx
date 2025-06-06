import React, {useEffect, useState} from 'react';
import {useParams, Link} from 'react-router-dom';
import {PublicationService, PublicationDTO} from '../../services/publicationService';
import styles from './PublicationDetailPage.module.css';

const PublicationDetailPage: React.FC = () => {
    const {slug} = useParams<{ slug: string }>();
    const [publication, setPublication] = useState<PublicationDTO | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (slug) {
            setLoading(true);
            PublicationService.buscarPorSlug(slug)
                .then(data => {
                    setPublication(data);
                })
                .catch(err => {
                    console.error("Erro ao buscar publicação:", err);
                    setError("Publicação não encontrada ou erro ao carregar.");
                })
                .finally(() => {
                    setLoading(false);
                });
        }
    }, [slug]);

    if (loading) {
        return <div className={styles.status}>Carregando publicação...</div>;
    }

    if (error) {
        return <div className={styles.status}>{error}</div>;
    }

    if (!publication) {
        return <div className={styles.status}>Nenhuma publicação para exibir.</div>;
    }

    return (
        <article className={styles.pageContainer}>
            <header className={styles.header}>
                <div className={styles.category}>
                    <Link to={`/category/${publication.category.slug}`}>{publication.category.name}</Link>
                </div>
                <h1 className={styles.title}>{publication.title}</h1>
                <div className={styles.meta}>
                    <span>Por {publication.authorName || 'Brasfi'}</span>
                    <span>•</span>
                    <span>{new Date(publication.publishedAt).toLocaleDateString('pt-BR', {
                        day: 'numeric',
                        month: 'long',
                        year: 'numeric'
                    })}</span>
                </div>
            </header>

            {publication.summary && (
                <p className={styles.summary}>{publication.summary}</p>
            )}

            <div
                className={styles.content}
                dangerouslySetInnerHTML={{__html: publication.content}}
            />

            {publication.tags && publication.tags.length > 0 && (
                <footer className={styles.footer}>
                    <h4>Tags:</h4>
                    <div className={styles.tagsContainer}>
                        {publication.tags.map(tag => (
                            <Link key={tag.id} to={`/tag/${tag.slug}`} className={styles.tag}>
                                {tag.name}
                            </Link>
                        ))}
                    </div>
                </footer>
            )}
        </article>
    );
};

export default PublicationDetailPage;