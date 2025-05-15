import React from 'react';
import { useState } from 'react';
import styles from './Faq.module.css';
import { X, Plus } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const faqs = [
    {
        question: 'O que é a Aliança?',
        answer: 'A aliança é uma iniciativa que promove soluções financeiras sustentáveis. Nosso objetivo é apoiar empresas na transição para práticas mais verdes. Acreditamos que a colaboração é essencial para um futuro sustentável.',
    },
    {
        question: 'Como posso me envolver?',
        answer: 'Você pode se envolver participando de nossos eventos e workshops. Além disso, oferecemos oportunidades de parceria para empresas interessadas. Junte-se a nós na promoção de finanças sustentáveis.',
    },
    {
        question: 'Qual é o impacto?',
        answer: 'O impacto das finanças sustentáveis é significativo, pois ajuda a reduzir a pegada de carbono. Além disso, promove a responsabilidade social entre as empresas. Estamos criando um futuro mais sustentável para todos.',
    },
    {
        question: 'Quais projetos estão em andamento?',
        answer: 'Atualmente, temos vários projetos focados em energias renováveis e eficiência energética. Também apoiamos iniciativas de educação financeira sustentável. Nossos projetos visam criar um impacto positivo nas comunidades.',
    },
    {
        question: 'Onde posso aprender mais?',
        answer: 'Você pode visitar nosso site para acessar recursos e informações detalhadas. Além disso, oferecemos newsletters com atualizações sobre nossas atividades. Fique por dentro das novidades sobre finanças sustentáveis.',
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
                    <h2 className={styles.title}>FAQ</h2>
                    <p className={styles.subtitle}>
                        Aqui estão algumas perguntas frequentes<br />
                        sobre a aliança e finanças sustentáveis.
                    </p>
                </div>


                <div className={styles.list}>
                    {faqs.map((item, index) => (
                        <div
                            key={index}
                            className={`${styles.item} ${openIndex === index ? styles.open : ''}`}
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
