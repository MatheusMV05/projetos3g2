import React from 'react';
import styles from './Footer.module.css';

const Footer: React.FC = () => {
	return (
		<footer className={styles.footer}>
			<div className={styles.newsletter}>
				<div className={styles.newsText}>
					<h3>Assine nossa newsletter</h3>
					<p>Fique por dentro das novidades sobre finanças sustentáveis.</p>
				</div>

				<form className={styles.form} onSubmit={(e) => e.preventDefault()}>
					<input
						type="email"
						placeholder="Digite seu e-mail"
						className={styles.input}
						required
					/>
					<button type="submit" className={styles.submit}>
						Inscrever
					</button>
				</form>
			</div>

			<p className={styles.privacy}>
				<a href="#">
					Ao se inscrever, você concorda com nossa Política de Privacidade
				</a>
				.
			</p>

			<hr className={styles.divider} />

			<div className={styles.bottom}>
				<div className={styles.logoSection}>
					<img src="/logo.png" alt="Logo BRASFI" className={styles.logo} />
					<span className={styles.logoText}>BRASFI</span>
				</div>
				<p className={styles.rights}>
					© 2025 Relume. Todos os direitos reservados.
				</p>
			</div>
		</footer>
	);
};

export default Footer;
