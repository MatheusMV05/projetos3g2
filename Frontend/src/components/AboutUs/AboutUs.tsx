import React from 'react';
import styles from './AboutUs.module.css'

const AboutUs: React.FC = () => {
  return (
    <section className={styles.aboutSection}>
      <div className={styles.content}>
        <h1>
          Conheça a Aliança de Finanças Sustentáveis <br />
          e sua missão transformadora
        </h1>
        <p>
          Nossa missão é promover soluções financeiras sustentáveis que impactem positivamente o futuro do planeta.
        </p>
        <div className={styles.buttons}>
          <button className={styles.primaryButton}>Saiba</button>
          <button className={styles.secondaryButton}>Junte-se</button>
        </div>
      </div>
      <div className={styles.imagePlaceholder}>
        {/* Aqui você pode substituir por uma imagem real */}
        <div className={styles.placeholderIcon}>📷</div>
      </div>
    </section>
  );
};

export default AboutUs;
