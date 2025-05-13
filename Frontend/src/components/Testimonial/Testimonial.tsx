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
						"Os projetos que realizamos não apenas transformaram nossa empresa,
						mas também impactaram positivamente a comunidade ao nosso redor."
					</p>
				</blockquote>
				<div className={styles.author}>
					<strong>Ana Silva</strong>
					<span>Gerente, EcoCorp</span>
				</div>
			</div>
		</motion.div>
	);
};

export default Testimonial;
