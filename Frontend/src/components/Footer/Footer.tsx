// Frontend/src/components/Footer/Footer.tsx
import React, { useState } from 'react';
import styles from './Footer.module.css';
import logo from '../../assets/Ativo 1.svg';
import { api } from '../../services/api'; // Importe a instância do axios

const Footer: React.FC = () => {
	const [email, setEmail] = useState('');
	const [message, setMessage] = useState('');
	const [messageType, setMessageType] = useState<'success' | 'error' | ''>('');

	const handleSubscribe = async (e: React.FormEvent) => {
		e.preventDefault();
		setMessage(''); // Limpa mensagens anteriores
		setMessageType('');

		if (!email) {
			setMessage('Por favor, insira seu e-mail.');
			setMessageType('error');
			return;
		}

		try {
			// Em uma aplicação real, você integraria uma biblioteca reCAPTCHA (ex: react-google-recaptcha-v3)
			// para gerar um token de verdade. Para este exemplo, usamos um token dummy.
			const recaptchaToken = 'dummy_recaptcha_token'; // Substitua pela geração real do token reCAPTCHA

			await api.post('/public/newsletter/inscrever', {
				email: email,
				recaptchaToken: recaptchaToken, // Envia o token reCAPTCHA
				// nome: 'Nome Opcional' // Se o formulário tiver um campo de nome
			});

			setMessage('Inscrição realizada com sucesso! Verifique seu e-mail.');
			setMessageType('success');
			setEmail(''); // Limpa o campo de e-mail
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
					<h3>Assine nossa newsletter</h3>
					<p>Fique por dentro das novidades sobre finanças sustentáveis.</p>
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

			<p className={styles.privacy}>
				<a href="#">
					Ao se inscrever, você concorda com nossa Política de Privacidade
				</a>
				.
			</p>

			<hr className={styles.divider} />

			<div className={styles.bottom}>
				<div className={styles.logoSection}>
					<img src={logo} alt="Logo BRASFI" className={styles.logo} />
				</div>
				<p className={styles.rights}>
					© 2025 Relume. Todos os direitos reservados.
				</p>
			</div>
		</footer>
	);
};

export default Footer;