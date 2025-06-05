import React, { useState, useEffect } from 'react';
import styles from './Footer.module.css';
import logo from '../../assets/Ativo 1.svg';
import { api } from '../../services/api';
import { InformacaoInstitucionalService } from '../../services/informacaoService'; // 1. Importar o serviço

const Footer: React.FC = () => {
	const [email, setEmail] = useState('');
	const [message, setMessage] = useState('');
	const [messageType, setMessageType] = useState<'success' | 'error' | ''>('');

	// 2. Criar estado para armazenar as informações institucionais
	const [info, setInfo] = useState<Record<string, string>>({});
	const [loading, setLoading] = useState(true);

	// 3. Buscar os dados quando o componente for montado
	useEffect(() => {
		const fetchInfo = async () => {
			try {
				const infoMap = await InformacaoInstitucionalService.buscarMapa();
				setInfo(infoMap);
			} catch (error) {
				console.error("Erro ao buscar informações institucionais:", error);
			} finally {
				setLoading(false);
			}
		};
		fetchInfo();
	}, []);

	const handleSubscribe = async (e: React.FormEvent) => {
		e.preventDefault();
		// ... (lógica de inscrição existente permanece a mesma)
		setMessage('');
		setMessageType('');
		if (!email) {
			setMessage('Por favor, insira seu e-mail.');
			setMessageType('error');
			return;
		}
		try {
			const recaptchaToken = 'dummy_recaptcha_token';
			await api.post('/public/newsletter/inscrever', {
				email: email,
				recaptchaToken: recaptchaToken,
			});
			setMessage('Inscrição realizada com sucesso! Verifique seu e-mail.');
			setMessageType('success');
			setEmail('');
		} catch (error: any) {
			console.error('Erro ao inscrever na newsletter:', error.response?.data || error.message);
			setMessage(error.response?.data?.message || 'Erro ao inscrever. Tente novamente.');
			setMessageType('error');
		}
	};

	return (
		<footer className={styles.footer}>
			<div className={styles.newsletter}>
				<div className={styles.newsText}>
					{/* 4. Usar os dados do estado ou um fallback */}
					<h3>{loading ? 'Carregando...' : (info.footer_newsletter_titulo || 'Assine nossa newsletter')}</h3>
					<p>{loading ? '...' : (info.footer_newsletter_subtitulo || 'Fique por dentro das novidades sobre finanças sustentáveis.')}</p>
				</div>
				<form className={styles.form} onSubmit={handleSubscribe}>
					<input
						type="email"
						placeholder="Digite seu e-mail"
						className={styles.input}
						value={email}
						onChange={(e) => setEmail(e.target.value)}
						required
					/>
					<button type="submit" className={styles.submit}>
						Inscrever
					</button>
				</form>
			</div>

			{message && (
				<p className={`${styles.statusMessage} ${styles[messageType]}`}>
					{message}
				</p>
			)}

			{/* 5. Usar os dados do estado ou um fallback */}
			<p className={styles.privacy}>
				<a href={info.footer_privacidade_url || '#'}>
					{loading ? '' : (info.footer_privacidade_texto || 'Ao se inscrever, você concorda com nossa Política de Privacidade')}
				</a>.
			</p>

			<hr className={styles.divider} />

			<div className={styles.bottom}>
				<div className={styles.logoSection}>
					<img src={logo} alt="Logo BRASFI" className={styles.logo} />
				</div>
				{/* 6. Usar os dados do estado ou um fallback */}
				<p className={styles.rights}>
					{loading ? '' : (info.footer_direitos_autorais || '© 2025 Relume. Todos os direitos reservados.')}
				</p>
			</div>
		</footer>
	);
};

export default Footer;