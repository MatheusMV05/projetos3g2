import React from 'react';
import styles from './Partners.module.css';
import { motion } from 'framer-motion';
import unClimateLogo from '../../assets/United_Nations_Climate_Change_Conference_logo.svg';
import weeseg from '../../assets/Wikimedia_Sustainability_Initiative_Logo.svg.png';
import unifor from '../../assets/Unifor_2.png';

const containerVariants = {
    hidden: { opacity: 0, y: 40 },
    visible: {
        opacity: 1,
        y: 0,
        transition: {
            duration: 0.6,
            ease: 'easeOut',
            staggerChildren: 0.1,
        },
    },
};

const itemVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: { opacity: 1, y: 0 },
};

const Partners: React.FC = () => {
    return (
        <motion.section
            className={styles.partnersSection}
            variants={containerVariants}
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, amount: 0.3 }}
        >
            <motion.h2 className={styles.title} variants={itemVariants}>
                Lorem ipsum dolor sit amet
            </motion.h2>

            <motion.div className={styles.logos} variants={itemVariants}>
                <motion.img
                    src={unClimateLogo}
                    alt="Climate & Company"
                    loading="lazy"
                    variants={itemVariants}
                />
                <motion.img
                    src={weeseg}
                    alt="Weesg"
                    loading="lazy"
                    variants={itemVariants}
                />
                <motion.img
                    src={unifor}
                    alt="Universidade de Fortaleza"
                    loading="lazy"
                    variants={itemVariants}
                />
            </motion.div>
        </motion.section>
    );
};

export default Partners;