import React from 'react';
import styles from './AltProjects.module.css';

const projetos = [
	{
		titulo: 'Lorem ipsum dolor',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
		tags: ['Lorem', 'Ipsum', 'Dolor'],
	},
	{
		titulo: 'Lorem ipsum dolor',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
		tags: ['Lorem', 'Ipsum'],
	},
];

const AltProjects: React.FC = () => {
	return (
		<section className={styles.container}>
			<div className={styles.header}>
				<h2 className={styles.titulo}>
					Lorem ipsum dolor sit amet consectetur
				</h2>
				<p className={styles.subtitulo}>
					Lorem ipsum dolor sit amet consectetur adipiscing elit.
				</p>
			</div>
			<div className={styles.grid}>
				{projetos.map((projeto, index) => (
					<div key={index} className={styles.card}>
						<div className={styles.imagem}></div>
						<div className={styles.conteudo}>
							<h3 className={styles.cardTitulo}>{projeto.titulo}</h3>
							<p className={styles.cardDescricao}>{projeto.descricao}</p>
							<div className={styles.tags}>
								{projeto.tags.map((tag, i) => (
									<span key={i} className={styles.tag}>
										{tag}
									</span>
								))}
							</div>
							<a href="#" className={styles.link}>
								Lorem ipsum <span className={styles.seta}>&rsaquo;</span>
							</a>
						</div>
					</div>
				))}
			</div>
		</section>
	);
};

export default AltProjects;
