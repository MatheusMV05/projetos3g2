import React from 'react';
import styles from './Testimonial.module.css';
import { motion } from 'framer-motion';

const Testimonial: React.FC = () => {
	return (
		<motion.div
			className={styles.container}
			initial={{ opacity: 0, y: 50 }}
			whileInView={{ opacity: 1, y: 0 }}
			viewport={{ once: true, amount: 0.4 }}
			transition={{ duration: 0.6, ease: 'easeOut' }}
		>
			<div className={styles.video}>
				<div className={styles.playButton}>▶</div>
			</div>

			<div className={styles.content}>
				<div className={styles.stars}>★★★★★</div>
				<blockquote>
					<p>
						"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean sit
						amet metus a nulla suscipit bibendum."
					</p>
				</blockquote>
				<div className={styles.author}>
					<strong>Lorem Ipsum</strong>
					<span>Lorem Ipsum, Dolor Sit</span>
				</div>
			</div>
		</motion.div>
	);
};

export default Testimonial;
