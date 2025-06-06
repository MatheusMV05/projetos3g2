import React from 'react';
import styles from './HeroSection.module.css';

const HeroSection: React.FC = () => {
	return (
		<section className={styles.hero}>
			<div className={styles.content}>
				<h1>
					Lorem ipsum dolor
					<br />
					sit amet
				</h1>
				<p>
					Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit
					amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet
					consectetur adipiscing elit.
				</p>
			</div>
		</section>
	);
};

export default HeroSection;
