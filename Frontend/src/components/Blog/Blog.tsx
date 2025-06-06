import React, {useState} from 'react';
import styles from './Blog.module.css';

const mockCards = [
    {id: 1, tag: 'Tag 1', title: 'Lorem ipsum dolor sit amet.'},
    {id: 2, tag: 'Tag 2', title: 'Consectetur adipiscing elit.'},
    {id: 3, tag: 'Tag 3', title: 'Sed do eiusmod tempor.'},
    {id: 4, tag: 'Tag 1', title: 'Incididunt ut labore et dolore.'},
    {id: 5, tag: 'Tag 2', title: 'Magna aliqua ut enim ad minim.'},
    {id: 6, tag: 'Tag 3', title: 'Veniam quis nostrud exercitation.'}
];

const Blog: React.FC = () => {
    const [selectedTag, setSelectedTag] = useState<string | null>(null);

    const filteredCards = selectedTag
        ? mockCards.filter(card => card.tag === selectedTag)
        : mockCards;

    return (
        <section className={styles.blogSection}>
            {/* Destaque principal */}
            <div className={styles.mainHighlight}>
                <div className={styles.mainImage}>
                    <div className={styles.imagePlaceholder}>
                        <a
                            href="/PlaceholderImage.svg"
                            target="_blank"
                            rel="noopener noreferrer"
                        >
                            <img src="/PlaceholderImage.svg" alt="PlaceHolder"/>
                        </a>
                    </div>
                </div>
                <div className={styles.mainContent}>
                    <span className={styles.mainCategory}>Lorem ipsum</span>
                    <div className={styles.mainTitle}>
                        Lorem ipsum <br/> dolor sit amet
                    </div>
                    <p className={styles.mainDescription}>
                        Lorem ipsum dolor sit amet consectetur adipiscing elit.
                    </p>
                    <div className={styles.mainAuthor}>
						<span>
							👤 Lorem ipsum dolor sit amet consectetur adipiscing elit.
						</span>
                    </div>
                </div>
            </div>

            {/* Filtros dinâmicos */}
            <div className={styles.filters}>
                <button
                    onClick={() => setSelectedTag(null)}
                    style={{
                        backgroundColor: selectedTag === null ? '#000' : '#fff',
                        color: selectedTag === null ? '#fff' : '#000'
                    }}
                >
                    Todos
                </button>
                {['Tag 1', 'Tag 2', 'Tag 3'].map(tag => (
                    <button
                        key={tag}
                        onClick={() => setSelectedTag(tag)}
                        style={{
                            backgroundColor: selectedTag === tag ? '#000' : '#fff',
                            color: selectedTag === tag ? '#fff' : '#000'
                        }}
                    >
                        {tag}
                    </button>
                ))}
            </div>

            {/* Cards filtrados */}
            <div className={styles.grid}>
                {filteredCards.map(card => (
                    <div className={styles.card} key={card.id}>
                        <div className={styles.cardImage}>📷</div>
                        <div className={styles.cardContent}>
                            <div className={styles.cardCategory}>{card.tag}</div>
                            <div className={styles.cardTitle}>{card.title}</div>
                            <div className={styles.cardAuthor}>
								<span>
									👤 Lorem ipsum dolor sit amet consectetur adipiscing elit.
								</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default Blog;
