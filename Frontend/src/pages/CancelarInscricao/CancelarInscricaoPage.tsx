import React, {useState} from 'react';
import {useSearchParams} from 'react-router-dom';
import {NewsletterService} from '../../services/newsletterService';
import styles from './CancelarInscricaoPage.module.css';

const CancelarInscricaoPage: React.FC = () => {
    const [searchParams] = useSearchParams();
    const email = searchParams.get('email');

    const [status, setStatus] = useState<'idle' | 'loading' | 'success' | 'error'>('idle');
    const [errorMessage, setErrorMessage] = useState('');

    const handleCancel = async () => {
        if (!email) {
            setErrorMessage('Nenhum e-mail fornecido para o cancelamento.');
            setStatus('error');
            return;
        }

        setStatus('loading');
        try {
            await NewsletterService.cancelarInscricao(email);
            setStatus('success');
        } catch (err: any) {
            const message = err.response?.data?.message || 'Ocorreu um erro ao processar sua solicitação.';
            setErrorMessage(message);
            setStatus('error');
        }
    };

    const renderContent = () => {
        switch (status) {
            case 'loading':
                return <p>Processando sua solicitação...</p>;
            case 'success':
                return (
                    <div className={styles.feedback}>
                        <h3>Inscrição Cancelada</h3>
                        <p>A inscrição para o e-mail <strong>{email}</strong> foi cancelada com sucesso. Você não
                            receberá mais nossas comunicações.</p>
                    </div>
                );
            case 'error':
                return (
                    <div className={styles.feedback}>
                        <h3>Ocorreu um Erro</h3>
                        <p>{errorMessage}</p>
                    </div>
                );
            case 'idle':
            default:
                return (
                    <>
                        <h3>Confirmar Cancelamento</h3>
                        <p>Você tem certeza que deseja cancelar a inscrição na nossa newsletter para o e-mail:</p>
                        <p className={styles.email}>{email || 'E-mail não especificado'}</p>
                        <button
                            onClick={handleCancel}
                            className={styles.confirmButton}
                            disabled={!email}
                        >
                            Sim, desejo cancelar a inscrição
                        </button>
                    </>
                );
        }
    };

    return (
        <div className={styles.pageContainer}>
            <div className={styles.card}>
                <h2>Cancelamento de Newsletter</h2>
                <hr/>
                {renderContent()}
            </div>
        </div>
    );
};

export default CancelarInscricaoPage;