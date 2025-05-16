import React from 'react';
import styles from './Contato.module.css';
import { Mail, Phone, MapPin } from 'lucide-react';

const Contato: React.FC = () => {
  return (
    <section className={styles.container}>
      <div className={styles.grid}>
        <div className={styles.card}>
          <Mail className={styles.icone} />
          <h3 className={styles.titulo}>Email</h3>
          <p className={styles.descricao}>
            Entre em contato conosco para mais informações sobre nossas iniciativas.
          </p>
          <a href="mailto:hello@relume.io" className={styles.link}>
            hello@relume.io
          </a>
        </div>

        <div className={styles.card}>
          <Phone className={styles.icone} />
          <h3 className={styles.titulo}>Telefone</h3>
          <p className={styles.descricao}>
            Estamos disponíveis para atender suas dúvidas e propostas.
          </p>
          <a href="tel:+551112345678" className={styles.link}>
            +55 (11) 1234-5678
          </a>
        </div>

        <div className={styles.card}>
          <MapPin className={styles.icone} />
          <h3 className={styles.titulo}>Escritório</h3>
          <p className={styles.descricao}>
            Visite-nos para discutir colaborações e soluções financeiras sustentáveis.
          </p>
          <a
            href="https://www.google.com/maps?q=Avenida+Verde,+456,+São+Paulo+SP+01234-567"
            target="_blank"
            rel="noopener noreferrer"
            className={styles.link}
          >
            Avenida Verde, 456, São Paulo SP 01234-567
          </a>
        </div>
      </div>
    </section>
  );
};

export default Contato;
