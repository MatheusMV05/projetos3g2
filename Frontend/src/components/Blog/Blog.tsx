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
          <h2 className={styles.mainTitle}>Explorando a Economia verde</h2>
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
        <h5>Categoria Um</h5>
        <a>Categoria Dois</a>
        <a>Categoria Três</a>
        <a>Categoria Quatro</a>
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
                <span>👤 Lorem ipsum dolor sit amet consectetur adipiscing elit.<br></br></span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default Blog;
