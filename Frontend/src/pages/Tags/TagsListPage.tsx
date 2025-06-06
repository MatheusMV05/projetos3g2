import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {TagService} from '../../services/tagService';
import {TagDTO} from '../../services/publicationService';
import styles from './TagsListPage.module.css';

const TagsListPage: React.FC = () => {
    const [tags, setTags] = useState<TagDTO[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchTags = async () => {
            try {
                const data = await TagService.listarTodas();
                setTags(data);
            } catch (error) {
                console.error("Erro ao buscar as tags:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchTags();
    }, []);

    if (loading) {
        return <div className={styles.pageContainer}><p>Carregando tags...</p></div>;
    }

    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.title}>Todos os Tópicos</h1>
            <p className={styles.subtitle}>Navegue pelas tags para encontrar conteúdos de seu interesse.</p>
            <div className={styles.tagCloud}>
                {tags.map(tag => (
                    <Link to={`/tag/${tag.slug}`} key={tag.id} className={styles.tag}>
                        {tag.name}
                    </Link>
                ))}
            </div>
        </div>
    );
};

export default TagsListPage;