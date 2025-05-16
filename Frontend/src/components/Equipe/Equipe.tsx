import React from 'react';
import styles from './Equipe.module.css';
import { FaLinkedin, FaXTwitter, FaDribbble } from 'react-icons/fa6';

const equipe = [
  {
    nome: 'Ana Silva',
    cargo: 'Gerente Financeira',
    descricao: 'Especialista em finanças sustentáveis com mais de 10 anos de experiência no setor.',
  },
  {
    nome: 'Carlos Mendes',
    cargo: 'Analista Sênior',
    descricao: 'Focado em desenvolver soluções financeiras inovadoras para projetos sustentáveis.',
  },
  {
    nome: 'Fernanda Lima',
    cargo: 'Consultora Ambiental',
    descricao: 'Experiência em políticas ambientais e sua integração nas finanças corporativas.',
  },
  {
    nome: 'João Pereira',
    cargo: 'Coordenador de Projetos',
    descricao: 'Lidera iniciativas de financiamento para projetos de energia renovável.',
  },
  {
    nome: 'Mariana Costa',
    cargo: 'Especialista em Risco',
    descricao: 'Avalia e mitiga riscos financeiros em projetos sustentáveis.',
  },
  {
    nome: 'Lucas Almeida',
    cargo: 'Analista de Dados',
    descricao: 'Utiliza dados para otimizar investimentos em finanças verdes.',
  },
];

const Equipe: React.FC = () => {
  return (
    <section className={styles.section}>
      <div className={styles.header}>
        <h2 className={styles.title}>Nossa Equipe</h2>
      <p className={styles.subtitle}>
        Conheça os profissionais que impulsionam a sustentabilidade financeira.
      </p>
      </div>  
      <div className={styles.grid}>
        {equipe.map((membro, index) => (
          <div className={styles.card} key={index}>
            <div className={styles.fotoPlaceholder}></div>
            <h3 className={styles.nome}>{membro.nome}</h3>
            <p className={styles.cargo}>{membro.cargo}</p>
            <p className={styles.descricao}>{membro.descricao}</p>
            <div className={styles.icones}>
              <FaLinkedin />
              <FaXTwitter />
              <FaDribbble />
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default Equipe;
