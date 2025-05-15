import React from 'react';
import styles from './Missao.module.css';

const Missao: React.FC = () => {
  return (
    <section className={styles.container}>
      <div className={styles.left}>
        <h2>Conheça a Aliança de Finanças Sustentáveis e seu impacto no futuro.</h2>
        <p>
          Fundada com o objetivo de promover soluções financeiras sustentáveis, nossa aliança busca transformar
          o cenário econômico atual. As finanças sustentáveis são essenciais para garantir um futuro próspero
          e equilibrado, beneficiando tanto empresas quanto a sociedade.
        </p>
      </div>
      <div className={styles.right}>
        {/* Substitua a div por uma <img> se usar imagem real */}
        <div className={styles.image}></div>
      </div>
    </section>
  );
};

export default Missao;
