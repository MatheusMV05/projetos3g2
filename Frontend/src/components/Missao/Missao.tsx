import React from 'react';
import styles from './Missao.module.css';

const Missao: React.FC = () => {
	return (
		<section className={styles.container}>
			<div className={styles.left}>
				<h2>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h2>
				<p>
					Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit
					amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet
					consectetur adipiscing elit.
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
