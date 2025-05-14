import React from 'react';
import styles from './HeroSection.module.css';

const HeroSection: React.FC = () => {
	return (
		<section className={styles.hero}>
			<div className={styles.content}>
				<h1>
					Desenvolvendo líderes
					<br />e viabilizando soluções
				</h1>
				<p>
					Bem-vindo à Aliança Brasileira de Finanças e Investimentos
					Sustentáveis, onde buscamos promover a transformação sustentável no
					Brasil, atuando como catalisadora no desenvolvimento de lideranças e
					soluções em finanças e investimentos sustentáveis.
				</p>
				<div className={styles.buttons}>
					<button className={styles.primary}>Botão 1</button>
					<button className={styles.secondary}>Botão 2</button>
				</div>
			</div>
		</section>
	);
};

export default HeroSection;
