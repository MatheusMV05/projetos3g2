import React from 'react';
import styles from './Metrics.module.css';
import { motion, useAnimation, useInView } from 'framer-motion';
import { useEffect, useRef, useState } from 'react';

const metrics = [
	{
		value: 30,
		title: 'Crescimento Sustentável',
		desc: 'Aumento na adoção de práticas sustentáveis',
	},
	{
		value: 30,
		title: 'Investimentos Verdes',
		desc: 'Crescimento em investimentos sustentáveis',
	},
	{
		value: 30,
		title: 'Impacto Social',
		desc: 'Benefícios para comunidades locais',
	},
];

interface MetricCardProps {
	value: number;
	title: string;
	desc: string;
}

const MetricCard = ({ value, title, desc }: MetricCardProps) => {
	const ref = useRef(null);
	const isInView = useInView(ref, { once: true });
	const controls = useAnimation();
	const [count, setCount] = useState(0);

	useEffect(() => {
		if (isInView) {
			controls.start('visible');
			let start = 0;
			const duration = 800;
			const step = value / (duration / 16);

			const animate = () => {
				start += step;
				if (start < value) {
					setCount(Math.round(start));
					requestAnimationFrame(animate);
				} else {
					setCount(value);
				}
			};
			animate();
		}
	}, [isInView, controls, value]);

	return (
		<motion.div
			className={styles.card}
			ref={ref}
			initial="hidden"
			animate={controls}
			variants={{
				hidden: { opacity: 0, y: 40 },
				visible: { opacity: 1, y: 0 },
			}}
			transition={{ duration: 0.5, ease: 'easeOut' }}
		>
			<strong className={styles.value}>{count}%</strong>
			<div>
				<h3 className={styles.cardTitle}>{title}</h3>
				<p className={styles.cardDesc}>{desc}</p>
			</div>
		</motion.div>
	);
};

const Metrics: React.FC = () => {
	return (
		<section className={styles.metricsSection}>
			<div className={styles.header}>
				<h2>Nosso impacto e progresso em números e estatísticas relevantes</h2>
				<p>
					Este projeto tem gerado resultados significativos na promoção da
					economia verde. Nossas métricas demonstram um avanço contínuo em
					direção à sustentabilidade. Através de parcerias estratégicas, estamos
					transformando o setor financeiro.
				</p>
			</div>

			<div className={styles.grid}>
				{metrics.map((item, index) => (
					<MetricCard key={index} {...item} />
				))}
				<div className={styles.imagePlaceholder}></div>
				<div className={styles.imagePlaceholder}></div>
			</div>
		</section>
	);
};

export default Metrics;
