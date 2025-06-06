import React, {useState} from 'react';
import {motion} from 'framer-motion';
import ProjectsGrid from './ProjectsGrid.tsx';
import styles from './SustainableProjectsSection.module.css';

const allProjects = [
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Tag 1'],
        image: 'https://via.placeholder.com/300x200',
    },
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Tag 2'],
        image: 'https://via.placeholder.com/300x200',
    },
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Tag 3'],
        image: 'https://via.placeholder.com/300x200',
    },
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Tag 1', 'Tag 2'],
        image: 'https://via.placeholder.com/300x200',
    },
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Tag 2', 'Tag 3'],
        image: 'https://via.placeholder.com/300x200',
    },
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Tag 1', 'Tag 3'],
        image: 'https://via.placeholder.com/300x200',
    },
];

const categories = ['Todos', 'Tag 1', 'Tag 2', 'Tag 3'];

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
                initial={{opacity: 0, y: 50}}
                whileInView={{opacity: 1, y: 0}}
                transition={{duration: 0.6}}
                viewport={{once: true}}
            >
                <h2>Nossos Projetos Sustentáveis</h2>
                <p>Explore nossos projetos em andamento e concluídos..</p>
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

            <ProjectsGrid projects={filteredProjects}/>

            <motion.div
                className={styles.buttonWrapper}
                initial={{opacity: 0, y: 50}}
                whileInView={{opacity: 1, y: 0}}
                transition={{delay: 0.2}}
                viewport={{once: true}}
            >
                <button className={styles.viewAllButton}>Lorem ipsum</button>
            </motion.div>
        </section>
    );
};

export default SustainableProjectsSection;