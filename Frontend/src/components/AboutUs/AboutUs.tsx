import React from 'react';
import styles from './AboutUs.module.css';

const AboutUs: React.FC = () => {
	return (
		<section className={styles.aboutSection}>
			<div className={styles.content}>
				<h1>
					Lorem ipsum dolor sit amet consectetur <br />
					adipiscing elit
				</h1>
				<p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
				<div className={styles.buttons}>
					<button className={styles.primaryButton}>Saiba mais</button>
					<button className={styles.secondaryButton}>Junte-se a nós</button>
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
