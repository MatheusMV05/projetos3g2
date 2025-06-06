import React, {useState} from 'react';
import {ContatoService, MensagemPayload} from '../../services/contatoService';
import styles from './ContatoModal.module.css';

interface ContatoModalProps {
    isOpen: boolean;
    onClose: () => void;
}

const ContatoModal: React.FC<ContatoModalProps> = ({isOpen, onClose}) => {
    const [formData, setFormData] = useState<Omit<MensagemPayload, 'recaptchaToken'>>({
        nome: '',
        email: '',
        assunto: '',
        mensagem: '',
    });
    const [status, setStatus] = useState({submitting: false, success: false, error: ''});

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target;
        setFormData((prev) => ({...prev, [name]: value}));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setStatus({submitting: true, success: false, error: ''});

        try {
            // Em um app real, integre o Google reCAPTCHA v3 para gerar este token.
            const payload: MensagemPayload = {
                ...formData,
                recaptchaToken: 'dummy-recaptcha-token-for-dev',
            };
            await ContatoService.enviarMensagem(payload);
            setStatus({submitting: false, success: true, error: ''});
            // Limpar o formulário e fechar o modal após um tempo, ou deixar o usuário fechar
            setTimeout(() => {
                onClose();
                setFormData({nome: '', email: '', assunto: '', mensagem: ''});
                setStatus({submitting: false, success: false, error: ''});
            }, 3000);
        } catch (err: any) {
            const errorMessage = err.response?.data?.message || 'Falha ao enviar a mensagem. Tente novamente.';
            setStatus({submitting: false, success: false, error: errorMessage});
        }
    };

    if (!isOpen) return null;

    return (
        <div className={styles.modalBackground} onClick={onClose}>
            <div className={styles.formContainer} onClick={(e) => e.stopPropagation()}>
                <button className={styles.closeBtn} onClick={onClose}>×</button>
                <h2>Entre em Contato</h2>

                {status.success ? (
                    <div className={styles.successMessage}>
                        <p>✅ Mensagem enviada com sucesso! Entraremos em contato em breve.</p>
                    </div>
                ) : (
                    <form onSubmit={handleSubmit} className={styles.form}>
                        <div className={styles.formGroup}>
                            <label htmlFor="nome">Nome Completo</label>
                            <input type="text" id="nome" name="nome" value={formData.nome} onChange={handleChange}
                                   required/>
                        </div>
                        <div className={styles.formGroup}>
                            <label htmlFor="email">Email</label>
                            <input type="email" id="email" name="email" value={formData.email} onChange={handleChange}
                                   required/>
                        </div>
                        <div className={styles.formGroup}>
                            <label htmlFor="assunto">Assunto</label>
                            <input type="text" id="assunto" name="assunto" value={formData.assunto}
                                   onChange={handleChange} required/>
                        </div>
                        <div className={styles.formGroup}>
                            <label htmlFor="mensagem">Mensagem</label>
                            <textarea id="mensagem" name="mensagem" value={formData.mensagem} onChange={handleChange}
                                      required rows={4}></textarea>
                        </div>

                        {status.error && <p className={styles.errorMessage}>{status.error}</p>}

                        <button type="submit" className={styles.submitBtn} disabled={status.submitting}>
                            {status.submitting ? 'Enviando...' : 'Enviar Mensagem'}
                        </button>
                    </form>
                )}
            </div>
        </div>
    );
};

export default ContatoModal;