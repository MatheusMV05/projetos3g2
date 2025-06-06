import React, {useEffect, useState} from 'react';
import styles from './Partners.module.css';
import {motion} from 'framer-motion';
import {ParceiroService, ParceiroSummary} from '../../services/parceiroService'; // 1. Importar

const containerVariants = {
    hidden: {opacity: 0, y: 40},
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
    hidden: {opacity: 0, y: 20},
    visible: {opacity: 1, y: 0},
};

const Partners: React.FC = () => {
    // 2. Adicionar estado para os parceiros e loading
    const [partners, setPartners] = useState<ParceiroSummary[]>([]);
    const [loading, setLoading] = useState(true);

    // 3. Buscar os dados da API
    useEffect(() => {
        const fetchPartners = async () => {
            try {
                // Busca a primeira página com até 6 parceiros
                const response = await ParceiroService.listarPublicos(0, 6);
                setPartners(response.content);
            } catch (error) {
                console.error("Erro ao buscar parceiros:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchPartners();
    }, []);

    return (
        <motion.section
            className={styles.partnersSection}
            variants={containerVariants}
            initial="hidden"
            whileInView="visible"
            viewport={{once: true, amount: 0.3}}
        >
            <motion.h2 className={styles.title} variants={itemVariants}>
                Lorem ipsum dolor sit amet
            </motion.h2>

            <motion.div className={styles.logos} variants={itemVariants}>
                {/* 4. Renderizar a lista dinâmica de parceiros */}
                {loading ? (
                    <p>Carregando parceiros...</p>
                ) : (
                    partners.map(partner => (
                        <motion.a
                            key={partner.id}
                            href={partner.siteUrl}
                            target="_blank"
                            rel="noopener noreferrer"
                            title={partner.nomeOrganizacao}
                            variants={itemVariants}
                        >
                            <img
                                src={partner.logoUrl}
                                alt={partner.nomeOrganizacao}
                                loading="lazy"
                            />
                        </motion.a>
                    ))
                )}
            </motion.div>
        </motion.section>
    );
};

export default Partners;