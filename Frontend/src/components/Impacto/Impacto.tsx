import React, {useEffect, useRef, useState} from 'react';
import styles from './Impacto.module.css';
import {motion, useAnimation, useInView} from 'framer-motion';
import {MetricaImpactoService, MetricaImpacto} from '../../services/metricaService'; // Importar com o tipo corrigido

// A interface de props já espera um 'number', o que está correto.
interface ImpactoItemProps {
    value: number;
    title: string;
    desc: string;
    unidade: string;
    delay?: number;
}

const ImpactoItem: React.FC<ImpactoItemProps> = ({value, title, desc, unidade, delay = 0}) => {
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
            <strong>+{count}{unidade}</strong>
            <h3>{title}</h3>
            <p>{desc}</p>
        </motion.div>
    );
};


// Componente principal que busca os dados
const Impacto: React.FC = () => {
    const [metricas, setMetricas] = useState<MetricaImpacto[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchMetricas = async () => {
            try {
                const data = await MetricaImpactoService.listarAtivas();
                setMetricas(data);
            } catch (error) {
                console.error("Erro ao buscar métricas de impacto:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchMetricas();
    }, []);

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
                    Nosso Impacto em Números
                </motion.h2>

                <div className={styles.grid}>
                    {loading ? (
                        <p>Carregando métricas...</p>
                    ) : (
                        metricas.map((metrica, index) => (
                            <ImpactoItem
                                key={metrica.id}
                                value={metrica.valor} // Agora é um number, que é o esperado pelo ImpactoItem
                                title={metrica.titulo}
                                desc={metrica.descricao}
                                unidade={metrica.unidade}
                                delay={index * 0.1}
                            />
                        ))
                    )}
                </div>
            </div>
        </section>
    );
};

export default Impacto;