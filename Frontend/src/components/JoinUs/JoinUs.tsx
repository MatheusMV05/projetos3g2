import React from 'react';
import styles from './JoinUs.module.css';

const JoinUs: React.FC = () => {
	return (
		<section className={styles.joinUsSection}>
			<div className={styles.container}>
				<div className={styles.left}>
					<h2>Lorem ipsum dolor sit?</h2>
				</div>
				<div className={styles.right}>
					<div className={styles.contentBox}>
						<p>
							Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit
							amet metus a nulla suscipit bibendum.
						</p>
						<button className={styles.cta}>Clique aqui</button>
					</div>
				</div>
			</div>
		</section>
	);
};

export default JoinUs;
