import React from 'react';
import styles from './Metrics.module.css';
import { motion, useAnimation, useInView } from 'framer-motion';
import { useEffect, useRef, useState } from 'react';

const metrics = [
	{
		value: 30,
		title: 'Lorem ipsum dolor',
		desc: 'Lorem ipsum dolor sit amet consectetur',
	},
	{
		value: 30,
		title: 'Lorem ipsum dolor',
		desc: 'Lorem ipsum dolor sit amet consectetur',
	},
	{
		value: 30,
		title: 'Lorem ipsum dolor',
		desc: 'Lorem ipsum dolor sit amet consectetur',
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
				<h2>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h2>
				<p>
					Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit
					amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet
					consectetur adipiscing elit.
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
