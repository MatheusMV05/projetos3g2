import React from 'react';
import { motion } from 'framer-motion';
import ProjectCard from './ProjectCard';
import styles from './SustainableProjectsSection.module.css';

const allProjects = [
	{
		title: 'Lorem Ipsum',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Lorem'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Lorem Ipsum',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Ipsum'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Lorem Ipsum',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Dolor'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Lorem Ipsum',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Lorem'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Lorem Ipsum',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Ipsum'],
		image: 'https://via.placeholder.com/300x200',
	},
	{
		title: 'Lorem Ipsum',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
		tags: ['Ipsum', 'Dolor'],
		image: 'https://via.placeholder.com/300x200',
	},
];

const categories = ['Todos', 'Lorem', 'Ipsum', 'Dolor', 'Sit', 'Amet'];

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
				<h2>Lorem ipsum dolor sit amet consectetur</h2>
				<p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
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
				<button className={styles.viewAllButton}>Lorem ipsum</button>
			</motion.div>
		</section>
	);
};

export default SustainableProjectsSection;
