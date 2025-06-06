import React from 'react';
import {useEffect, useRef, useState} from 'react';
import styles from './Impacto.module.css';
import {motion, useAnimation, useInView} from 'framer-motion';

interface ImpactoItemProps {
    value: number;
    title: string;
    desc: string;
    delay?: number;
}

const ImpactoItem: React.FC<ImpactoItemProps> = ({
                                                     value,
                                                     title,
                                                     desc,
                                                     delay = 0,
                                                 }) => {
    const ref = useRef(null);
    const isInView = useInView(ref, {once: true});
    const controls = useAnimation();
    const [count, setCount] = useState(0);

    useEffect(() => {
        if (isInView) {
            controls.start('visible');
            let start = 0;
            const duration = 1400;
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
            className={styles.item}
            ref={ref}
            initial="hidden"
            animate={controls}
            variants={{
                hidden: {opacity: 0, y: 40},
                visible: {opacity: 1, y: 0},
            }}
            transition={{duration: 0.5, ease: 'easeOut', delay}}
        >
            <strong>+{count}</strong>
            <h3>{title}</h3>
            <p>{desc}</p>
        </motion.div>
    );
};

const Impacto: React.FC = () => {
    return (
        <section className={styles.impactoSection}>
            <div className={styles.container}>
                <motion.h2
                    className={styles.title}
                    initial={{opacity: 0, y: 50}}
                    whileInView={{opacity: 1, y: 0}}
                    viewport={{once: true, amount: 0.4}}
                    transition={{duration: 0.6, ease: 'easeOut'}}
                >
                    Lorem ipsum dolor sit amet consectetur
                    <br/>
                    adipiscing elit
                </motion.h2>

                <div className={styles.grid}>
                    <ImpactoItem
                        value={20}
                        title="Lorem ipsum dolor"
                        desc="Lorem ipsum dolor sit amet consectetur adipiscing elit."
                        delay={0.1}
                    />
                    <ImpactoItem
                        value={20}
                        title="Lorem ipsum dolor"
                        desc="Lorem ipsum dolor sit amet consectetur adipiscing elit."
                        delay={0.2}
                    />
                    <ImpactoItem
                        value={40}
                        title="Lorem ipsum dolor"
                        desc="Lorem ipsum dolor sit amet consectetur adipiscing elit."
                        delay={0.3}
                    />
                    <ImpactoItem
                        value={50}
                        title="Lorem ipsum dolor"
                        desc="Lorem ipsum dolor sit amet consectetur adipiscing elit."
                        delay={0.4}
                    />
                    <ImpactoItem
                        value={10}
                        title="Lorem ipsum dolor"
                        desc="Lorem ipsum dolor sit amet consectetur adipiscing elit."
                        delay={0.5}
                    />
                </div>
            </div>
        </section>
    );
};

export default Impacto;