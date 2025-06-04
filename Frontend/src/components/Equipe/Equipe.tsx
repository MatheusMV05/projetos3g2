import React from 'react';
import styles from './Equipe.module.css';
import { FaLinkedin, FaXTwitter, FaDribbble } from 'react-icons/fa6';

const equipe = [
	{
		nome: 'Lorem Ipsum',
		cargo: 'Lorem Ipsum',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
	},
	{
		nome: 'Lorem Ipsum',
		cargo: 'Lorem Ipsum',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
	},
	{
		nome: 'Lorem Ipsum',
		cargo: 'Lorem Ipsum',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
	},
	{
		nome: 'Lorem Ipsum',
		cargo: 'Lorem Ipsum',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
	},
	{
		nome: 'Lorem Ipsum',
		cargo: 'Lorem Ipsum',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
	},
	{
		nome: 'Lorem Ipsum',
		cargo: 'Lorem Ipsum',
		descricao: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
	},
];

const Equipe: React.FC = () => {
	return (
		<section className={styles.section}>
			<div className={styles.header}>
				<h2 className={styles.title}>Lorem Ipsum</h2>
				<p className={styles.subtitle}>
					Lorem ipsum dolor sit amet consectetur adipiscing elit.
				</p>
			</div>
			<div className={styles.grid}>
				{equipe.map((membro, index) => (
					<div className={styles.card} key={index}>
						<div className={styles.fotoPlaceholder}></div>
						<h3 className={styles.nome}>{membro.nome}</h3>
						<p className={styles.cargo}>{membro.cargo}</p>
						<p className={styles.descricao}>{membro.descricao}</p>
						<div className={styles.icones}>
							<FaLinkedin />
							<FaXTwitter />
							<FaDribbble />
						</div>
					</div>
				))}
			</div>
		</section>
	);
};

export default Equipe;
