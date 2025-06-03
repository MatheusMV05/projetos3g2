import React, { useState } from 'react';
import styles from './Conteudo.module.css';

const Conteudo: React.FC = () => {
	// Estado dos conteúdos
	const [conteudos, setConteudos] = useState<
		{ titulo: string; autor: string; data: string }[]
	>([]);

	// Estado do popup
	const [showPopup, setShowPopup] = useState(false);

	// Estados dos inputs do popup
	const [titulo, setTitulo] = useState('');
	const [autor, setAutor] = useState('');
	const [data, setData] = useState('');

	// Abrir e fechar popup
	const openPopup = () => setShowPopup(true);
	const closePopup = () => {
		setShowPopup(false);
		setTitulo('');
		setAutor('');
		setData('');
	};

	// Função para adicionar conteúdo
	const handleAddContent = () => {
		if (!titulo || !autor || !data) return;
		setConteudos([...conteudos, { titulo, autor, data }]);
		closePopup();
	};

	return (
		<div className={styles.container}>
			{/* Sidebar */}
			<aside className={styles.sidebar}>
				<div className={styles.userSection}>
					<p className={styles.userName}>Usuário 1</p>
					<p className={styles.userRole}>Função: Proprietário</p>
				</div>

				<nav className={styles.menu}>
					<a href="#">Brasfi.com.br</a>
					<a className={styles.active} href="#">Conteúdo</a>
					<a href="#">Acessos</a>
					<a href="#">Recursos</a>
					<a href="#">Equipe</a>
					<a href="#">Experiência do cliente</a>
					<a href="#">Ajuda</a>
					<a href="#">Configurações</a>
				</nav>
			</aside>

			{/* Main */}
			<main className={styles.main}>
				<header className={styles.header}>
					<div>
						<h2>Brasfi.com.br</h2>
						<p>Gerencie seu conteúdo</p>
					</div>
					<button className={styles.createBtn} onClick={openPopup}>
						+ Criar novo conteúdo
					</button>
				</header>

				<div className={styles.topBar}>
					<div className={styles.leftOptions}>
						<button className={styles.dropdown}>Todos os conteúdos ({conteudos.length})</button>
						<button className={styles.linkBtn}>Gerenciar visualização</button>
					</div>

					<div className={styles.rightOptions}>
						<button className={styles.filterBtn}>Filtrar</button>
						<input type="text" className={styles.search} placeholder="Buscar" />
						<button className={styles.viewToggle}>🔲</button>
						<button className={styles.viewToggle}>📋</button>
					</div>
				</div>

				{/* Conteúdo */}
				{conteudos.length === 0 ? (
					<div className={styles.emptyState}>
						<button className={styles.createLink} onClick={openPopup}>
							+ Criar novo conteúdo
						</button>
					</div>
				) : (
					<div className={styles.cards}>
						{conteudos.map((item, index) => (
							<div key={index} className={styles.card}>
								<div className={styles.cardImage}></div>
								<div className={styles.cardContent}>
									<p className={styles.cardTitle}>{item.titulo}</p>
									<p className={styles.cardMeta}>
										by {item.autor} | {item.data}
									</p>
								</div>
							</div>
						))}
					</div>
				)}

				{/* Popup */}
				{showPopup && (
					<div className={styles.popupOverlay}>
						<div className={styles.popup}>
							<h3>Novo Conteúdo</h3>

							<label>
								Título:
								<input
									type="text"
									value={titulo}
									onChange={(e) => setTitulo(e.target.value)}
								/>
							</label>

							<label>
								Autor:
								<input
									type="text"
									value={autor}
									onChange={(e) => setAutor(e.target.value)}
								/>
							</label>

							<label>
								Data:
								<input
									type="date"
									value={data}
									onChange={(e) => setData(e.target.value)}
								/>
							</label>

							<div className={styles.popupActions}>
								<button onClick={handleAddContent} className={styles.saveBtn}>
									Salvar
								</button>
								<button onClick={closePopup} className={styles.cancelBtn}>
									Cancelar
								</button>
							</div>
						</div>
					</div>
				)}
			</main>
		</div>
	);
};

export default Conteudo;
