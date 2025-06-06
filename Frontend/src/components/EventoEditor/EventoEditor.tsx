import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import styles from './EventoEditor.module.css';
import { FaSave, FaArrowLeft, FaCalendarAlt, FaMapMarkerAlt, FaUsers } from 'react-icons/fa';
import { EventoService, EventoCreateDTO, EventoUpdateDTO } from '../../services/eventoService';

const EventoEditor: React.FC = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const eventoId = location.state?.id;
    const [formData, setFormData] = useState({
        titulo: location.state?.titulo || '',
        descricao: location.state?.descricao || '',
        dataInicio: location.state?.dataInicio || '',
        dataFim: location.state?.dataFim || '',
        local: location.state?.local || '',
        capacidadeMaxima: location.state?.capacidadeMaxima || ''
    });

    const [statusMessage, setStatusMessage] = useState({ text: '', type: '' });
    const [isLoading, setIsLoading] = useState(false);

    // Carrega dados do evento se estivermos editando
    useEffect(() => {
        if (eventoId && !location.state?.titulo) {
            const fetchEvento = async () => {
                try {
                    setIsLoading(true);
                    const evento = await EventoService.buscarPorId(eventoId);
                    setFormData({
                        titulo: evento.titulo,
                        descricao: evento.descricao,
                        dataInicio: evento.dataInicio,
                        dataFim: evento.dataFim || '',
                        local: evento.local,
                        capacidadeMaxima: evento.capacidadeMaxima?.toString() || ''
                    });
                } catch (error) {
                    console.error('Erro ao carregar evento:', error);
                    setStatusMessage({ text: 'Erro ao carregar evento.', type: 'error' });
                } finally {
                    setIsLoading(false);
                }
            };
            fetchEvento();
        }
    }, [eventoId, location.state]);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const validateForm = (): boolean => {
        if (!formData.titulo.trim()) {
            setStatusMessage({ text: 'O título é obrigatório.', type: 'error' });
            return false;
        }
        if (!formData.descricao.trim()) {
            setStatusMessage({ text: 'A descrição é obrigatória.', type: 'error' });
            return false;
        }
        if (!formData.dataInicio) {
            setStatusMessage({ text: 'A data de início é obrigatória.', type: 'error' });
            return false;
        }
        if (!formData.local.trim()) {
            setStatusMessage({ text: 'O local é obrigatório.', type: 'error' });
            return false;
        }
        if (formData.dataFim && formData.dataFim < formData.dataInicio) {
            setStatusMessage({ text: 'A data de fim não pode ser anterior à data de início.', type: 'error' });
            return false;
        }
        return true;
    };

    const handleSave = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        setIsLoading(true);
        setStatusMessage({ text: 'Salvando...', type: 'info' });

        try {
            const payload = {
                titulo: formData.titulo.trim(),
                descricao: formData.descricao.trim(),
                dataInicio: formData.dataInicio,
                dataFim: formData.dataFim || undefined,
                local: formData.local.trim(),
                capacidadeMaxima: formData.capacidadeMaxima ? parseInt(formData.capacidadeMaxima) : undefined
            };

            if (eventoId) {
                await EventoService.atualizar(eventoId, payload as EventoUpdateDTO);
                setStatusMessage({ text: 'Evento atualizado com sucesso!', type: 'success' });
            } else {
                await EventoService.criar(payload as EventoCreateDTO);
                setStatusMessage({ text: 'Evento criado com sucesso!', type: 'success' });
            }

            setTimeout(() => navigate('/conteudo'), 1500);
        } catch (error) {
            console.error('Erro ao salvar evento:', error);
            setStatusMessage({ text: 'Erro ao salvar evento. Tente novamente.', type: 'error' });
        } finally {
            setIsLoading(false);
        }
    };

    const handleCancel = () => {
        navigate('/conteudo');
    };

    if (isLoading && !formData.titulo) {
        return (
            <div className={styles.container}>
                <p>Carregando evento...</p>
            </div>
        );
    }

    return (
        <motion.div
            className={styles.container}
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -30 }}
            transition={{ duration: 0.3 }}
        >
            <div className={styles.header}>
                <button className={styles.backButton} onClick={handleCancel} disabled={isLoading}>
                    <FaArrowLeft /> Voltar
                </button>

                <h1>{eventoId ? 'Editar Evento' : 'Novo Evento'}</h1>

                {statusMessage.text && (
                    <div className={`${styles.statusMessage} ${styles[statusMessage.type]}`}>
                        {statusMessage.text}
                    </div>
                )}
            </div>

            <form onSubmit={handleSave} className={styles.form}>
                <div className={styles.formGroup}>
                    <label htmlFor="titulo">
                        <FaCalendarAlt className={styles.icon} />
                        Título do Evento *
                    </label>
                    <input
                        type="text"
                        id="titulo"
                        name="titulo"
                        value={formData.titulo}
                        onChange={handleInputChange}
                        placeholder="Digite o título do evento"
                        required
                        disabled={isLoading}
                        className={styles.input}
                    />
                </div>

                <div className={styles.formGroup}>
                    <label htmlFor="descricao">Descrição *</label>
                    <textarea
                        id="descricao"
                        name="descricao"
                        value={formData.descricao}
                        onChange={handleInputChange}
                        placeholder="Descreva o evento, objetivos, público-alvo, etc."
                        required
                        disabled={isLoading}
                        className={styles.textarea}
                        rows={5}
                    />
                </div>

                <div className={styles.formRow}>
                    <div className={styles.formGroup}>
                        <label htmlFor="dataInicio">
                            <FaCalendarAlt className={styles.icon} />
                            Data de Início *
                        </label>
                        <input
                            type="date"
                            id="dataInicio"
                            name="dataInicio"
                            value={formData.dataInicio}
                            onChange={handleInputChange}
                            required
                            disabled={isLoading}
                            className={styles.input}
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label htmlFor="dataFim">
                            <FaCalendarAlt className={styles.icon} />
                            Data de Fim
                        </label>
                        <input
                            type="date"
                            id="dataFim"
                            name="dataFim"
                            value={formData.dataFim}
                            onChange={handleInputChange}
                            disabled={isLoading}
                            className={styles.input}
                        />
                    </div>
                </div>

                <div className={styles.formRow}>
                    <div className={styles.formGroup}>
                        <label htmlFor="local">
                            <FaMapMarkerAlt className={styles.icon} />
                            Local *
                        </label>
                        <input
                            type="text"
                            id="local"
                            name="local"
                            value={formData.local}
                            onChange={handleInputChange}
                            placeholder="Local do evento (endereço, plataforma online, etc.)"
                            required
                            disabled={isLoading}
                            className={styles.input}
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label htmlFor="capacidadeMaxima">
                            <FaUsers className={styles.icon} />
                            Capacidade Máxima
                        </label>
                        <input
                            type="number"
                            id="capacidadeMaxima"
                            name="capacidadeMaxima"
                            value={formData.capacidadeMaxima}
                            onChange={handleInputChange}
                            placeholder="Número máximo de participantes"
                            min="1"
                            disabled={isLoading}
                            className={styles.input}
                        />
                    </div>
                </div>

                <div className={styles.actions}>
                    <button
                        type="button"
                        onClick={handleCancel}
                        className={styles.cancelButton}
                        disabled={isLoading}
                    >
                        Cancelar
                    </button>
                    <button
                        type="submit"
                        className={styles.saveButton}
                        disabled={isLoading}
                    >
                        <FaSave />
                        {isLoading ? 'Salvando...' : (eventoId ? 'Atualizar' : 'Criar')} Evento
                    </button>
                </div>
            </form>
        </motion.div>
    );
};

export default EventoEditor;