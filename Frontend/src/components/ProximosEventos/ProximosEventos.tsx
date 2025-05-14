import React from 'react';
import styles from './ProximosEventos.module.css';

const ProximosEventos: React.FC = () => {
  return (
    <section className={styles.proximosEventosSection}>
      <h2 className={styles.title}>Próximos eventos</h2>
      <div className={styles.subtitle}>Lorem ipsum dolor sit amet consectetur adipiscing elit.</div>

      <div className={styles.cardsContainer}>
        {[1, 2, 3].map((_, index) => (
          <div className={styles.card} key={index}>
            <div className={styles.imagePlaceholder}>📷</div>
            <div className={styles.cardContent}>
              <div className={styles.cardTitle}>Nome do evento</div>
              <div className={styles.cardDescription}>
                Lorem ipsum dolor sit amet consectetur adipiscing elit.
              </div>
              <div className={styles.cardInfo}>Lorem ipsum dolor sit amet consectetur adipiscing elit.</div>
            </div>
          </div>
        ))}
      </div>

      <div className={styles.verTodos}>
        <button>Ver todos</button>
      </div>
    </section>
  );
};

export default ProximosEventos;
