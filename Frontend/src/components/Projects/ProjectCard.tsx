import React from 'react';
import {motion} from 'framer-motion';
import styles from './ProjectCard.module.css';

interface ProjectCardProps {
    title: string;
    description: string;
    tags: string[];
    image: string;
}

const cardVariants = {
    hidden: {opacity: 0, y: 30},
    visible: {opacity: 1, y: 0},
};

const ProjectCard: React.FC<ProjectCardProps> = ({
                                                     title,
                                                     description,
                                                     tags,
                                                     image,
                                                 }) => {
    return (
        <motion.div
            className={styles.card}
            variants={cardVariants}
            initial="hidden"
            whileInView="visible"
            viewport={{once: true}}
            transition={{duration: 0.6, ease: 'easeOut'}}
        >
            <div className={styles.imageWrapper}>
                <img src={image} alt={title} className={styles.image}/>
            </div>
            <div className={styles.content}>
                <h3>{title}</h3>
                <p>{description}</p>
                <div className={styles.tags}>
                    {tags.map((tag, idx) => (
                        <span key={idx} className={styles.tag}>
							{tag}
						</span>
                    ))}
                </div>
                <button className={styles.button}>Ver projeto →</button>
            </div>
        </motion.div>
    );
};

export default ProjectCard;