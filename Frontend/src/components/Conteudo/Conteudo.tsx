import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Conteudo.module.css';

const Conteudo: React.FC = () => {
	const navigate = useNavigate();

	const [conteudos, setConteudos] = useState<
		{ titulo: string; autor: string; data: string }[]
	>([]);

	return (
		<div className={styles.container}>
			<aside className={styles.sidebar}>{/* Sidebar... */}</aside>

			<main className={styles.main}>
				<header className={styles.header}>
					<div>
						<h2>Brasfi.com.br</h2>
						<p>Gerencie seu conteúdo</p>
					</div>
					<button
						className={styles.createBtn}
						onClick={() =>
							navigate('/post-editor', {
								state: {
									titulo: 'Novo conteúdo',
									conteudo: '',
								},
							})
						}
					>
						+ Criar novo conteúdo
					</button>
				</header>

				{conteudos.length === 0 ? (
					<div className={styles.emptyState}>
						<div
							style={{
								textAlign: 'center',
								display: 'flex',
								flexDirection: 'column',
								alignItems: 'center',
								justifyContent: 'center',
								gap: '1rem',
								padding: '3rem 1rem',
								maxWidth: '300px',
								margin: '0 auto',
							}}
						>
							<p style={{ fontSize: '1.4rem', margin: 0 }}>Bem-vindo(a)!</p>
							<p
								style={{
									fontSize: '1rem',
									margin: 0,
									lineHeight: '1.5',
									color: '#444',
								}}
							>
								Você ainda não criou nenhum conteúdo
								<br />
								para o seu site.
							</p>
							<button
								className={styles.createLink}
								onClick={() =>
									navigate('/post-editor', {
										state: { titulo: 'Novo conteúdo', conteudo: '' },
									})
								}
							>
								+ Criar novo conteúdo agora
							</button>
						</div>
					</div>
				) : (
					<div className={styles.cards}>{/* Render cards... */}</div>
				)}
			</main>

			<button
				className={styles.fabButton}
				onClick={() =>
					navigate('/post-editor', {
						state: { titulo: 'Novo conteúdo', conteudo: '' },
					})
				}
			>
				+
			</button>
		</div>
	);
};

export default Conteudo;
