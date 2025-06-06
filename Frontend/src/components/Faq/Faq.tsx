import React from 'react';
import { useState } from 'react';
import styles from './Faq.module.css';
import { X, Plus } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const faqs = [
	{
		question: 'Lorem ipsum dolor sit amet?',
		answer:
			'Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet consectetur adipiscing elit. Lorem ipsum dolor sit amet.',
	},
	{
		question: 'Lorem ipsum dolor sit amet?',
		answer:
			'Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet consectetur adipiscing elit. Lorem ipsum dolor sit amet.',
	},
	{
		question: 'Lorem ipsum dolor sit amet?',
		answer:
			'Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet consectetur adipiscing elit. Lorem ipsum dolor sit amet.',
	},
	{
		question: 'Lorem ipsum dolor sit amet?',
		answer:
			'Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet consectetur adipiscing elit. Lorem ipsum dolor sit amet.',
	},
	{
		question: 'Lorem ipsum dolor sit amet?',
		answer:
			'Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit amet metus a nulla suscipit bibendum. Lorem ipsum dolor sit amet consectetur adipiscing elit. Lorem ipsum dolor sit amet.',
	},
];

const Faq: React.FC = () => {
	const [openIndex, setOpenIndex] = useState<number | null>(0);

	const toggle = (index: number) => {
		setOpenIndex(openIndex === index ? null : index);
	};

	return (
		<section className={styles.faqSection}>
			<div className={styles.container}>
				<div className={styles.header}>
					<h2 className={styles.title}>Lorem ipsum</h2>
					<p className={styles.subtitle}>
						Lorem ipsum dolor sit amet consectetur
						<br />
						adipiscing elit.
					</p>
				</div>

				<div className={styles.list}>
					{faqs.map((item, index) => (
						<div
							key={index}
							className={`${styles.item} ${
								openIndex === index ? styles.open : ''
							}`}
							onClick={() => toggle(index)}
						>
							<div className={styles.questionRow}>
								<h3 className={styles.question}>{item.question}</h3>
								{openIndex === index ? (
									<X size={18} strokeWidth={2} />
								) : (
									<Plus size={18} strokeWidth={2} />
								)}
							</div>
							<AnimatePresence>
								{openIndex === index && (
									<motion.div
										key="content"
										initial={{ opacity: 0, height: 0 }}
										animate={{ opacity: 1, height: 'auto' }}
										exit={{ opacity: 0, height: 0 }}
										transition={{ duration: 0.3, ease: 'easeInOut' }}
									>
										<p className={styles.answer}>{item.answer}</p>
									</motion.div>
								)}
							</AnimatePresence>
						</div>
					))}
				</div>
			</div>
		</section>
	);
};

export default Faq;