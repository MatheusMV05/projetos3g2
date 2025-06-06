import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './blogwhite.module.css';

type Publication = {
    id: number;
    title: string;
    slug: string;
    authorName: string;
    publishedAt: string;
    category: { name: string };
    tags: string[];
};

const dummyPublications: Publication[] = [
    {
        id: 1,
        title: 'Lorem Ipsum Dolor Sit Amet',
        slug: 'lorem-ipsum-1',
        authorName: 'Autor A',
        publishedAt: new Date().toISOString(),
        category: { name: 'Categoria X' },
        tags: ['Tag 1', 'Tag 2'],
    },
    {
        id: 2,
        title: 'Consectetur Adipiscing Elit',
        slug: 'lorem-ipsum-2',
        authorName: 'Autor B',
        publishedAt: new Date().toISOString(),
        category: { name: 'Categoria Y' },
        tags: ['Tag 2'],
    },
    {
        id: 3,
        title: 'Sed Do Eiusmod Tempor',
        slug: 'lorem-ipsum-3',
        authorName: 'Autor C',
        publishedAt: new Date().toISOString(),
        category: { name: 'Categoria Z' },
        tags: ['Tag 3'],
    },
];

const allTags = ['Tag 1', 'Tag 2', 'Tag 3'];

const Blogwhite: React.FC = () => {
    const [activeTag, setActiveTag] = useState<string | null>(null);
    const [publications, setPublications] = useState<Publication[]>([]);

    useEffect(() => {
        // Simula requisição de API
        setPublications(dummyPublications);
    }, []);

    const filteredPublications = activeTag
        ? publications.filter(pub => pub.tags.includes(activeTag))
        : publications;

    return (
        <section className={styles.blogSection}>
            <div className={styles.mainHighlight}>
                <div className={styles.mainImage}>🖼</div>
                <div className={styles.mainContent}>
                    <div className={styles.mainCategory}>Destaque</div>
                    <div className={styles.mainTitle}>Lorem Ipsum em Destaque</div>
                    <p className={styles.mainDescription}>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                    </p>
                    <div className={styles.mainAuthor}>
                        <img alt="autor" />
                        <span>Autor Destacado • 01/06/2025</span>
                    </div>
                </div>
            </div>

            <div className={styles.filters}>
                <button onClick={() => setActiveTag(null)}>Todos</button>
                {allTags.map(tag => (
                    <button
                        key={tag}
                        onClick={() => setActiveTag(tag)}
                        style={{
                            backgroundColor: activeTag === tag ? '#000' : '#fff',
                            color: activeTag === tag ? '#fff' : '#000',
                        }}
                    >
                        {tag}
                    </button>
                ))}
            </div>

            <div className={styles.grid}>
                {filteredPublications.map((pub) => (
                    <div className={styles.card} key={pub.id}>
                        <div className={styles.cardImage}>📷</div>
                        <div className={styles.cardContent}>
                            <div className={styles.cardCategory}>{pub.category.name}</div>
                            <Link to={`/publicacao/${pub.slug}`} className={styles.cardTitleLink}>
                                <div className={styles.cardTitle}>{pub.title}</div>
                            </Link>
                            <div className={styles.cardAuthor}>
                                <span>👤 Por {pub.authorName} em {new Date(pub.publishedAt).toLocaleDateString('pt-BR')}</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default Blogwhite;
