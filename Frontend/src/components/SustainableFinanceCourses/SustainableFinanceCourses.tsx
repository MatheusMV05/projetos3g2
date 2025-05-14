import React from 'react';
import styles from './SustainableFinanceCourses.module.css';
import { useNavigate } from 'react-router-dom';

interface Course {
	id: number;
	title: string;
	description: string;
	category: string;
	readTime: string;
}

const mockCourses: Course[] = [
	{
		id: 1,
		title: 'Introdução às Finanças Sustentáveis',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...',
		category: 'Educação',
		readTime: '5 min read',
	},
];

const SustainableFinanceCourses: React.FC = () => {
	const navigate = useNavigate();

	return (
		<div className={styles.container}>
			<h1 className={styles.title}>Cursos em Finanças Sustentáveis</h1>
			<p className={styles.subtitle}>
				Descubra cursos que transformam sua carreira financeira
			</p>

			{mockCourses.map((course) => (
				<div key={course.id} className={styles.cardWrapper}>
					<div className={styles.imagePlaceholder}>🖼</div>

					<div className={styles.cardContent}>
						<div className={styles.tagTime}>
							<span className={styles.tag}>{course.category}</span>
							<span className={styles.time}>{course.readTime}</span>
						</div>

						<h2 className={styles.cardTitle}>{course.title}</h2>
						<p className={styles.description}>{course.description}</p>

						<a href="#" className={styles.readMore}>
							Leia mais &rarr;
						</a>
					</div>
				</div>
			))}

			<button
				className={styles.viewAllButton}
				onClick={() => navigate('/cursos')}
			>
				Ver todos
			</button>
		</div>
	);
};

export default SustainableFinanceCourses;
