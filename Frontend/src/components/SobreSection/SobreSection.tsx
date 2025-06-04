import React from 'react';
import styles from './SobreSection.module.css';
import { Box } from 'lucide-react';
import { ChevronRight } from 'lucide-react';
import { motion } from 'framer-motion';

const SobreSection: React.FC = () => {
	return (
		<section className={styles.sobreSection}>
			<div className={styles.content}>
				<motion.h2
					className={styles.title}
					initial={{ opacity: 0, y: 50 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.6, ease: 'easeOut' }}
				>
					Lorem ipsum dolor <br /> sit amet
				</motion.h2>

				<motion.p
					className={styles.subtitle}
					initial={{ opacity: 0, y: 50 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.6, ease: 'easeOut', delay: 0.1 }}
				>
					Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit
					amet metus a nulla suscipit bibendum.
				</motion.p>

				<div className={styles.grid}>
					<motion.div
						className={styles.card}
						initial={{ opacity: 0, y: 50 }}
						whileInView={{ opacity: 1, y: 0 }}
						viewport={{ once: true, amount: 0.4 }}
						transition={{ duration: 0.6, ease: 'easeOut', delay: 0.2 }}
					>
						<div className={styles.icon}>
							<Box size={40} strokeWidth={1.5} />
						</div>
						<h3>Lorem ipsum dolor</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
					</motion.div>

					<motion.div
						className={styles.card}
						initial={{ opacity: 0, y: 50 }}
						whileInView={{ opacity: 1, y: 0 }}
						viewport={{ once: true, amount: 0.4 }}
						transition={{ duration: 0.6, ease: 'easeOut', delay: 0.3 }}
					>
						<div className={styles.icon}>
							<Box size={40} strokeWidth={1.5} />
						</div>
						<h3>Lorem ipsum dolor</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
					</motion.div>

					<motion.div
						className={styles.card}
						initial={{ opacity: 0, y: 50 }}
						whileInView={{ opacity: 1, y: 0 }}
						viewport={{ once: true, amount: 0.4 }}
						transition={{ duration: 0.6, ease: 'easeOut', delay: 0.4 }}
					>
						<div className={styles.icon}>
							<Box size={40} strokeWidth={1.5} />
						</div>
						<h3>Lorem ipsum dolor</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
					</motion.div>
				</div>

				<motion.div
					className={styles.buttons}
					initial={{ opacity: 0, y: 50 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.6, ease: 'easeOut', delay: 0.5 }}
				>
					<button className={styles.primary}>Lorem Ipsum</button>
					<button className={styles.link}>
						Lorem ipsum <ChevronRight size={16} />
					</button>
				</motion.div>
			</div>
		</section>
	);
};

export default SobreSection;
