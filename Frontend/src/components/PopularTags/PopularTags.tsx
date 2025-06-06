import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {TagService} from '../../services/tagService';
import {TagDTO} from '../../services/publicationService';
import styles from './PopularTags.module.css';

const PopularTags: React.FC = () => {
    const [tags, setTags] = useState<TagDTO[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchPopularTags = async () => {
            try {
                const data = await TagService.listarPopulares(7); // Busca as 7 tags mais populares
                setTags(data);
            } catch (error) {
                console.error("Erro ao buscar tags populares:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchPopularTags();
    }, []);

    if (loading || tags.length === 0) {
        return null; // Não renderiza nada se estiver carregando ou se não houver tags
    }

    return (
        <aside className={styles.popularTagsWidget}>
            <h3 className={styles.widgetTitle}>Tópicos Populares</h3>
            <div className={styles.tagList}>
                {tags.map(tag => (
                    <Link to={`/tag/${tag.slug}`} key={tag.id} className={styles.tagItem}>
                        {tag.name}
                    </Link>
                ))}
            </div>
        </aside>
    );
};

export default PopularTags;