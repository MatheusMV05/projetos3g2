import React from 'react';
import styles from './Relato.module.css';
import { Star } from 'lucide-react';
import { motion } from 'framer-motion';

const Relato: React.FC = () => {
	return (
		<section className={styles.relatoSection}>
			<div className={styles.container}>
				<motion.h2
					className={styles.title}
					initial={{ opacity: 0, y: 40 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.5, ease: 'easeOut' }}
				>
					Lorem Ipsum
				</motion.h2>

				<motion.div
					className={styles.stars}
					initial={{ opacity: 0, y: 30 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.5, ease: 'easeOut', delay: 0.1 }}
				>
					{Array.from({ length: 5 }).map((_, i) => (
						<Star key={i} size={20} fill="#FFD700" stroke="#FFD700" />
					))}
				</motion.div>

				<motion.blockquote
					className={styles.quote}
					initial={{ opacity: 0, y: 30 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.5, ease: 'easeOut', delay: 0.2 }}
				>
					"Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit
					amet metus a nulla suscipit bibendum."
				</motion.blockquote>

				<motion.div
					className={styles.author}
					initial={{ opacity: 0, y: 30 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.5, ease: 'easeOut', delay: 0.3 }}
				>
					<div className={styles.avatar}>ES</div>
					<div className={styles.info}>
						<strong>Lorem Ipsum</strong>
						<span>Lorem Ipsum</span>
					</div>
				</motion.div>
			</div>
		</section>
	);
};

export default Relato;
