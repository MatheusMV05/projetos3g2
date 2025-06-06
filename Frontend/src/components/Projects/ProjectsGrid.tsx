import React from 'react';
import {motion} from 'framer-motion';
import ProjectCard from './ProjectCard';
import styles from './ProjectsGrid.module.css';

interface Project {
    title: string;
    description: string;
    tags: string[];
    image: string;
}

interface ProjectsGridProps {
    projects: Project[];
}

const containerVariants = {
    hidden: {},
    show: {
        transition: {
            staggerChildren: 0.15,
        },
    },
};

const ProjectsGrid: React.FC<ProjectsGridProps> = ({projects}) => {
    return (
        <motion.div
            className={styles.grid}
            variants={containerVariants}
            initial="hidden"
            animate="show"
        >
            {projects.map((project, idx) => (
                <ProjectCard key={idx} {...project} />
            ))}
        </motion.div>
    );
};

export default ProjectsGrid;