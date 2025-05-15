import React from 'react';
import styles from './Blog.module.css';

const Blog: React.FC = () => {
  return (
    <section className={styles.blogSection}>
      {/* Destaque principal */}
      <div className={styles.mainHighlight}>
        <div className={styles.mainImage}>📷</div>
        <div className={styles.mainContent}>
          <span className={styles.mainCategory}>Sustentabilidade</span>
          <h2 className={styles.mainTitle}>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h2>
          <p className={styles.mainDescription}>
            Lorem ipsum dolor sit amet consectetur adipiscing elit.
          </p>
          <div className={styles.mainAuthor}>
            <span>👤 Lorem ipsum dolor sit amet consectetur adipiscing elit.</span>
          </div>
        </div>
      </div>
    <br></br>
    <br></br>
      {/* Filtros simulados */}
      <div className={styles.filters}>
        <button>Ver todos</button>
        <button>Categoria Um</button>
        <button>Categoria Dois</button>
        <button>Categoria Três</button>
        <button>Categoria Quatro</button>
      </div>
    <br></br>
    <br></br>
      {/* Cards */}
      <div className={styles.grid}>
        {[...Array(6)].map((_, i) => (
          <div className={styles.card} key={i}>
            <div className={styles.cardImage}>📷</div>
            <div className={styles.cardContent}>
              <div className={styles.cardCategory}>Tag</div>
              <div className={styles.cardTitle}>Lorem ipsum dolor sit amet consectetur adipiscing elit.</div>
              <div className={styles.cardAuthor}>
                <span>👤 <br></br></span>
                <span>Lorem ipsum dolor sit amet consectetur adipiscing elit.</span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default Blog;
