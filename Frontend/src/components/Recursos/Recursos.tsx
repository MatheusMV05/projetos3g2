import React from 'react';
import styles from './Recursos.module.css';

const Recursos: React.FC = () => {
	return (
		<div className={styles.container}>
			<h2 className={styles.title}>Recursos para Aprendizado</h2>
			<div className={styles.subtitle}>
				Lorem ipsum dolor sit amet consectetur
			</div>

			<div className={styles.cardsWrapper}>
				<div className={styles.card}>
					<div className={styles.image}></div>
					<div className={styles.content}>
						<h3 className={styles.cardTitle}>Lorem ipsum dolor</h3>
						<p className={styles.cardDesc}>
							Lorem ipsum dolor sit amet consectetur adipiscing elit.
						</p>
						<div className={styles.authorSection}>
							<div className={styles.authorIcon}></div>
							<div>
								<strong>Lorem ipsum</strong>
								<p className={styles.meta}>11 Jan • 5 min</p>
							</div>
						</div>
					</div>
				</div>

				<div className={styles.card}>
					<div className={styles.image}></div>
					<div className={styles.content}>
						<h3 className={styles.cardTitle}>Lorem ipsum dolor</h3>
						<p className={styles.cardDesc}>
							Lorem ipsum dolor sit amet consectetur adipiscing elit.
						</p>
						<div className={styles.authorSection}>
							<div className={styles.authorIcon}></div>
							<div>
								<strong>Lorem ipsum</strong>
								<p className={styles.meta}>11 Jan • 5 min</p>
							</div>
						</div>
					</div>
				</div>

				<div className={styles.card}>
					<div className={styles.image}></div>
					<div className={styles.content}>
						<h3 className={styles.cardTitle}>Lorem ipsum dolor</h3>
						<p className={styles.cardDesc}>
							Lorem ipsum dolor sit amet consectetur adipiscing elit.
						</p>
						<div className={styles.authorSection}>
							<div className={styles.authorIcon}></div>
							<div>
								<strong>Lorem ipsum</strong>
								<p className={styles.meta}>11 Jan • 5 min</p>
							</div>
						</div>
					</div>
				</div>
			</div>

			<button className={styles.button}>Ver todos</button>
		</div>
	);
};

export default Recursos;
