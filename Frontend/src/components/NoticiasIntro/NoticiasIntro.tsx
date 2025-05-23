import React from 'react';
import styles from './NoticiasIntro.module.css';

const NoticiasIntro: React.FC = () => {
	return (
		<div className={styles.container}>
			<div className={styles.card}>
				<div className={styles.image}>
					<div className={styles.imagePlaceholder}>
						<a href="/PlaceholderImage.svg" target="_blank" rel="noopener noreferrer">
						<img src="/PlaceholderImage.svg" alt="PlaceHolder" />
						</a>
					</div>
				</div>
				<div className={styles.info}>
					<div className={styles.tags}>
						<span className={styles.tag}>Sustentabilidade</span>
						<span className={styles.time}>5 min leitura</span>
					</div>
					<h2 className={styles.title}>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h2>
					<p className={styles.description}>
						Lorem ipsum dolor sit amet consectetur adipiscing elit.
					</p>
					<a href="#" className={styles.readMore}>
						Leia mais &gt;
					</a>
				</div>
			</div>

			<div className={styles.card}>
				<div className={styles.image}>
					<div className={styles.imagePlaceholder}>
						<a href="/PlaceholderImage.svg" target="_blank" rel="noopener noreferrer">
						<img src="/PlaceholderImage.svg" alt="PlaceHolder" />
						</a>
					</div>
				</div>
				<div className={styles.info}>
					<div className={styles.tags}>
						<span className={styles.tag}>Inovação</span>
						<span className={styles.time}>5 min leitura</span>
					</div>
					<h2 className={styles.title}>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h2>
					<p className={styles.description}>
						Lorem ipsum dolor sit amet consectetur adipiscing elit.
					</p>
					<a href="#" className={styles.readMore}>
						Leia mais &gt;
					</a>
				</div>
			</div>
		</div>
	);
};

export default NoticiasIntro;
