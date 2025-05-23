import React from 'react';
import styles from './blogwhite.module.css';

const Blogwhite: React.FC = () => {
  return (
    <section className={styles.blogSection}>
      {/* Destaque principal */}
      <div className={styles.mainHighlight}>
        <div className={styles.mainImage}>
          <div className={styles.imagePlaceholder}>
                    <a href="/PlaceholderImage.svg" target="_blank" rel="noopener noreferrer">
                    <img src="/PlaceholderImage.svg" alt="PlaceHolder" />
                    </a>
                  </div>
        </div>
        <div className={styles.mainContent}>
          <span className={styles.mainCategory}>Sustentabilidade</span>
          <div className={styles.mainTitle}>Explorando a 
          <br></br>Economia verde</div>
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
        <a><strong>Categoria Um</strong></a>
        <a><strong>Categoria Dois</strong></a>
        <a><strong>Categoria Três</strong></a>
        <a><strong>Categoria Quatro</strong></a>
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

export default Blogwhite;
