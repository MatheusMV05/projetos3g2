import React, { useState } from 'react';
import { motion } from 'framer-motion';
import ProjectsGrid from './ProjectsGrid';
import styles from './SustainableProjectsSection.module.css';

const allProjects = [
	{
		title: 'Projeto Verde',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Energia'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Iniciativa Azul',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Água'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Cidade Sustentável',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Urbanismo'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Projeto Solar',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Energia'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Amazônia Viva',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Conservação'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Reuso Inteligente',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Água', 'Reciclagem'],
		image: 'https://via.placeholder.com/300x200',
	},
];

const categories = [
	'Todos',
	'Energia',
	'Água',
	'Urbanismo',
	'Conservação',
	'Reciclagem',
];

const SustainableProjectsSection: React.FC = () => {
	const [selectedCategory, setSelectedCategory] = useState('Todos');

	const filteredProjects =
		selectedCategory === 'Todos'
			? allProjects
			: allProjects.filter((p) => p.tags.includes(selectedCategory));

	return (
		<section className={styles.section}>
			<motion.div
				className={styles.header}
				initial={{ opacity: 0, y: 50 }}
				whileInView={{ opacity: 1, y: 0 }}
				transition={{ duration: 0.6 }}
				viewport={{ once: true }}
			>
				<h2>Nossos Projetos Sustentáveis</h2>
				<p>Explore nossos projetos em andamento e concluídos.</p>
			</motion.div>

			<div className={styles.filters}>
				{categories.map((cat) => (
					<button
						key={cat}
						className={`${styles.filterBtn} ${
							selectedCategory === cat ? styles.active : ''
						}`}
						onClick={() => setSelectedCategory(cat)}
					>
						{cat}
					</button>
				))}
			</div>

			<ProjectsGrid projects={filteredProjects} />

			<motion.div
				className={styles.buttonWrapper}
				initial={{ opacity: 0, y: 50 }}
				whileInView={{ opacity: 1, y: 0 }}
				transition={{ delay: 0.2 }}
				viewport={{ once: true }}
			>
				<button className={styles.viewAllButton}>Ver todos</button>
			</motion.div>
		</section>
	);
};

export default SustainableProjectsSection;
