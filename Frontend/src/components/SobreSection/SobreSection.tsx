import React from 'react';
import styles from './SobreSection.module.css';
import { Box } from 'lucide-react';
import { ChevronRight } from 'lucide-react';
import { motion } from 'framer-motion';

const SobreSection: React.FC = () => {
  return (
    <section className={styles.sobreSection}>
      <div className={styles.content}>
        <motion.h2 
          className={styles.title}
          initial={{ opacity: 0, y: 50 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true, amount: 0.4 }}
          transition={{ duration: 0.6, ease: 'easeOut' }}
        >
          Formando líderes e <br/> viabilizando soluções
        </motion.h2>
        
        <motion.p 
          className={styles.subtitle}
          initial={{ opacity: 0, y: 50 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true, amount: 0.4 }}
          transition={{ duration: 0.6, ease: 'easeOut', delay: 0.1 }}
        >
          Atuamos como catalisadora no desenvolvimento de lideranças e soluções em finanças e investimentos sustentáveis. Buscamos inspirar e capacitar uma nova geração de líderes que impulsionem mudanças positivas, contribuindo para o avanço socioeconômico e ambiental do país.
        </motion.p>

        <div className={styles.grid}>
          <motion.div 
            className={styles.card}
            initial={{ opacity: 0, y: 50 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, amount: 0.4 }}
            transition={{ duration: 0.6, ease: 'easeOut', delay: 0.2 }}
          >
            <div className={styles.icon}>
                <Box size={40} strokeWidth={1.5} />
            </div>
            <h3>Formação e fortalecimento de Líderes</h3>
            <p>Acreditamos que a união de esforços é fundamental para criar um futuro mais verde.</p>
          </motion.div>
          
          <motion.div 
            className={styles.card}
            initial={{ opacity: 0, y: 50 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, amount: 0.4 }}
            transition={{ duration: 0.6, ease: 'easeOut', delay: 0.3 }}
          >
            <div className={styles.icon}>
                <Box size={40} strokeWidth={1.5} />
            </div>
            <h3>Desenvolvimento de soluções inovadoras</h3>
            <p>Nossas iniciativas visam gerar um impacto positivo na economia global.</p>
          </motion.div>
          
          <motion.div 
            className={styles.card}
            initial={{ opacity: 0, y: 50 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, amount: 0.4 }}
            transition={{ duration: 0.6, ease: 'easeOut', delay: 0.4 }}
          >
            <div className={styles.icon}>
                <Box size={40} strokeWidth={1.5} />
            </div>
            <h3>Construção de uma Rede Engajada e Influente</h3>
            <p>Estamos comprometidos em apoiar projetos que promovam a sustentabilidade.</p>
          </motion.div>
        </div>

        <motion.div 
          className={styles.buttons}
          initial={{ opacity: 0, y: 50 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true, amount: 0.4 }}
          transition={{ duration: 0.6, ease: 'easeOut', delay: 0.5 }}
        >
          <button className={styles.primary}>Saiba Mais</button>
          <button className={styles.link}>
            Inscreva-se <ChevronRight size={16} />
          </button>
        </motion.div>
      </div>
    </section>
  );
};

export default SobreSection;