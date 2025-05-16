import React from 'react';
import styles from './AltProjects.module.css';

const projetos = [
  {
    titulo: 'Energia Solar',
    descricao: 'Projeto de energia solar para comunidades rurais.',
    tags: ['Energia Renovável', 'Sustentabilidade Ambiental', 'Inovação Verde'],
  },
  {
    titulo: 'Outro Projeto',
    descricao: 'Descrição do segundo projeto vai aqui.',
    tags: ['Mobilidade Sustentável', 'Baixo Carbono'],
  },
];

const AltProjects: React.FC = () => {
  return (
    <section className={styles.container}>
      <div className={styles.header}>
        <h2 className={styles.titulo}>Nossos Projetos Sustentáveis</h2>
        <p className={styles.subtitulo}>
          Explore nossos projetos em andamento e concluídos.
        </p>
      </div>
      <div className={styles.grid}>
        {projetos.map((projeto, index) => (
          <div key={index} className={styles.card}>
            <div className={styles.imagem}></div>
            <div className={styles.conteudo}>
              <h3 className={styles.cardTitulo}>{projeto.titulo}</h3>
              <p className={styles.cardDescricao}>{projeto.descricao}</p>
              <div className={styles.tags}>
                {projeto.tags.map((tag, i) => (
                  <span key={i} className={styles.tag}>
                    {tag}
                  </span>
                ))}
              </div>
              <a href="#" className={styles.link}>
                Ver projeto <span className={styles.seta}>&rsaquo;</span>
              </a>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default AltProjects;
