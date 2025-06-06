import React, {useEffect, useState} from 'react';
import Slider from 'react-slick';
import styles from './ClientFeedbackCarousel.module.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import {DepoimentoService, Depoimento} from '../../services/depoimentoService'; // 1. Importar

// O componente Arrow permanece o mesmo
const Arrow = ({className, onClick}: { className?: string; onClick?: () => void; }) => {
    // ... (código do Arrow existente, sem alterações)
};

const ClientFeedbackCarousel: React.FC = () => {
    // 2. Adicionar estados para depoimentos, loading e erro
    const [feedbacks, setFeedbacks] = useState<Depoimento[]>([]);
    const [loading, setLoading] = useState(true);

    // 3. Buscar os dados da API
    useEffect(() => {
        const fetchFeedbacks = async () => {
            try {
                const data = await DepoimentoService.listarAtivos();
                setFeedbacks(data);
            } catch (error) {
                console.error("Erro ao buscar depoimentos:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchFeedbacks();
    }, []);

    const settings = {
        dots: true,
        infinite: feedbacks.length > 2, // Desativa o loop infinito se houver poucos itens
        slidesToShow: 3,
        slidesToScroll: 1,
        speed: 500,
        autoplay: true,
        autoplaySpeed: 5000,
        pauseOnHover: true,
        arrows: true,
        nextArrow: <Arrow className={`${styles.arrow} ${styles.next}`}/>,
        prevArrow: <Arrow className={`${styles.arrow} ${styles.prev}`}/>,
        responsive: [
            {
                breakpoint: 1024,
                settings: {slidesToShow: 2, slidesToScroll: 1},
            },
            {
                breakpoint: 768,
                settings: {slidesToShow: 1, slidesToScroll: 1},
            },
        ],
    };

    if (loading) {
        return <section className={styles.carouselSection}><p>Carregando depoimentos...</p></section>;
    }

    if (feedbacks.length === 0) {
        return null; // Não renderiza a seção se não houver depoimentos
    }

    return (
        <section className={styles.carouselSection}>
            <h2 className={styles.title}>Depoimentos de Clientes</h2>
            <p className={styles.subtitle}>
                Veja o que nossos parceiros e clientes dizem sobre nosso trabalho.
            </p>
            <div className={styles.sliderContainer}>
                {/* 4. Mapear sobre os dados do estado */}
                <Slider {...settings} className={styles.slider}>
                    {feedbacks.map((feedback) => (
                        <div key={feedback.id} className={styles.card}>
                            <div className={styles.stars}>★★★★★</div>
                            <p className={styles.text}>“{feedback.texto}”</p>
                            <div className={styles.profile}>
                                <div className={styles.avatar}>
                                    {/* Se tiver fotoUrl, usar <img>, senão, as iniciais */}
                                    {feedback.fotoUrl
                                        ? <img src={feedback.fotoUrl} alt={feedback.nome}/>
                                        : <span>{feedback.nome.charAt(0)}</span>
                                    }
                                </div>
                                <div>
                                    <p className={styles.name}>{feedback.nome}</p>
                                    <p className={styles.role}>{feedback.cargo}, {feedback.organizacao}</p>
                                </div>
                            </div>
                        </div>
                    ))}
                </Slider>
            </div>
        </section>
    );
};

export default ClientFeedbackCarousel;