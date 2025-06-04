import React from 'react';
import styles from './Contato.module.css';
import { Mail, Phone, MapPin } from 'lucide-react';

const Contato: React.FC = () => {
	return (
		<section className={styles.container}>
			<div className={styles.grid}>
				<div className={styles.card}>
					<Mail className={styles.icone} />
					<h3 className={styles.titulo}>Lorem ipsum</h3>
					<p className={styles.descricao}>
						Lorem ipsum dolor sit amet consectetur adipiscing elit.
					</p>
					<a href="mailto:hello@relume.io" className={styles.link}>
						lorem@ipsum.com
					</a>
				</div>

				<div className={styles.card}>
					<Phone className={styles.icone} />
					<h3 className={styles.titulo}>Lorem ipsum</h3>
					<p className={styles.descricao}>
						Lorem ipsum dolor sit amet consectetur adipiscing elit.
					</p>
					<a href="tel:+551112345678" className={styles.link}>
						+55 (11) 1234-5678
					</a>
				</div>

				<div className={styles.card}>
					<MapPin className={styles.icone} />
					<h3 className={styles.titulo}>Lorem ipsum</h3>
					<p className={styles.descricao}>
						Lorem ipsum dolor sit amet consectetur adipiscing elit.
					</p>
					<a
						href="https://www.google.com/maps?q=Avenida+Verde,+456,+São+Paulo+SP+01234-567"
						target="_blank"
						rel="noopener noreferrer"
						className={styles.link}
					>
						Lorem ipsum dolor sit amet, 123, Lorem Ipsum 01234-567
					</a>
				</div>
			</div>
		</section>
	);
};

export default Contato;
