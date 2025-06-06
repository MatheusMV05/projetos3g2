import React, {useEffect, useState} from 'react';
import {EventoService, Evento as EventoType} from '../../services/eventoService';
import {EventHeader} from '../../components/EventHeader/EventHeader';
import BlogPostHeader from '../../components/BlogPostHeader/BlogPostHeader';
import ProximosEventos from '../../components/ProximosEventos/ProximosEventos';
import Blog from '../../components/Blog/Blog';
import styles from './Evento.module.css'; // Usaremos um CSS module para esta página

const EventoPage: React.FC = () => {
    const [eventos, setEventos] = useState<EventoType[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchEventos = async () => {
            try {
                const data = await EventoService.listarTodos();
                // Ordenar por data mais recente primeiro, caso a API não o faça
                data.sort((a, b) => new Date(b.dataInicio).getTime() - new Date(a.dataInicio).getTime());
                setEventos(data);
            } catch (error) {
                console.error("Erro ao buscar eventos:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchEventos();
    }, []);

    const formatarData = (dataStr: string) => {
        const data = new Date(dataStr + 'T00:00:00'); // Adiciona T00:00:00 para evitar problemas de fuso horário
        return {
            dia: data.getDate().toString().padStart(2, '0'),
            mes: data.toLocaleString('pt-BR', {month: 'short'}).replace('.', ''),
            diaSemana: data.toLocaleString('pt-BR', {weekday: 'short'}).replace('.', ''),
        };
    };

    if (loading) {
        return <div className={styles.status}>Carregando eventos...</div>;
    }

    if (eventos.length === 0) {
        return <div className={styles.status}>Nenhum evento encontrado.</div>;
    }

    const eventoDestaque = eventos[0];
    const outrosEventos = eventos.slice(1);
    const dataDestaque = formatarData(eventoDestaque.dataInicio);

    return (
        <div className={styles.pageContainer}>
            <Blog />
            <div style={{textAlign: 'center', margin: '2rem 0'}}>
                <h1>Eventos</h1>
                <p>Fique por dentro da nossa agenda de workshops, webinars e conferências.</p>
            </div>

            <div className={styles.gridContainer}>
                {/* Evento em destaque */}
                <div className={styles.destaqueCard}>
                    <div className={styles.destaqueImage}>
                        <div className={styles.destaqueDate}>
                            {dataDestaque.dia}<br/>{dataDestaque.mes.toUpperCase()}
                        </div>
                    </div>
                    <div className={styles.destaqueContent}>
                        <div className={styles.destaqueCategory}>Destaque</div>
                        <div className={styles.destaqueTitle}>{eventoDestaque.titulo}</div>
                        <p className={styles.destaqueDescription}>{eventoDestaque.descricao}</p>
                        <a href="#" className={styles.destaqueLink}>Ver evento &gt;</a>
                    </div>
                </div>

                {/* Lista lateral de eventos */}
                <div className={styles.listaLateral}>
                    {outrosEventos.length > 0 ? (
                        outrosEventos.map(evento => {
                            const dataFormatada = formatarData(evento.dataInicio);
                            return (
                                <EventHeader
                                    key={evento.id}
                                    weekday={dataFormatada.diaSemana}
                                    day={dataFormatada.dia}
                                    month={dataFormatada.mes}
                                    title={evento.titulo}
                                    location={evento.local}
                                />
                            );
                        })
                    ) : (
                        <p>Não há outros eventos programados.</p>
                    )}
                </div>
            </div>

            {/* Seções adicionais podem ser mantidas */}
            <br/><br/><br/>
            <BlogPostHeader/>
            <ProximosEventos/>
        </div>
    );
};

export default EventoPage;