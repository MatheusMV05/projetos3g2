import React, {useEffect, useState} from 'react';
import styles from './Testimonial.module.css';
import {motion} from 'framer-motion';
import {DepoimentoService, Depoimento} from '../../services/depoimentoService'; // 1. Importar

const Testimonial: React.FC = () => {
    // 2. Adicionar estados para o depoimento, loading e erro
    const [testimonial, setTestimonial] = useState<Depoimento | null>(null);
    const [loading, setLoading] = useState(true);

    // 3. Buscar os dados da API
    useEffect(() => {
        const fetchFeaturedTestimonial = async () => {
            try {
                const destaques = await DepoimentoService.listarDestaques();
                if (destaques.length > 0) {
                    setTestimonial(destaques[0]); // Pega o primeiro depoimento da lista de destaques
                }
            } catch (error) {
                console.error("Erro ao buscar depoimento em destaque:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchFeaturedTestimonial();
    }, []);

    if (loading) {
        return <section className={styles.container}><p>Carregando depoimento...</p></section>;
    }

    if (!testimonial) {
        return null; // Não renderiza nada se nenhum depoimento em destaque for encontrado
    }

    return (
        <motion.div
            className={styles.container}
            initial={{opacity: 0, y: 50}}
            whileInView={{opacity: 1, y: 0}}
            viewport={{once: true, amount: 0.4}}
            transition={{duration: 0.6, ease: 'easeOut'}}
        >
            <div className={styles.video}>
                {/* Lógica do vídeo/imagem pode ser tornada dinâmica também */}
                <div className={styles.playButton}>▶</div>
            </div>

            {/* 4. Renderizar os dados dinâmicos */}
            <div className={styles.content}>
                <div className={styles.stars}>★★★★★</div>
                <blockquote>
                    <p>“{testimonial.texto}”</p>
                </blockquote>
                <div className={styles.author}>
                    <strong>{testimonial.nome}</strong>
                    <span>{testimonial.cargo}, {testimonial.organizacao}</span>
                </div>
            </div>
        </motion.div>
    );
};

export default Testimonial;