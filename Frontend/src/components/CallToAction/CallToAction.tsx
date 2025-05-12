import React from 'react';
import styles from './CallToAction.module.css';

const CallToAction: React.FC = () => {
	return (
		<div className={styles.container}>
			<h1 className={styles.title}>Junte-se a nós pela mudança</h1>
			<p className={styles.subtitle}>
				Apoie nosso projeto e faça parte da transformação rumo a uma economia
				mais sustentável.
			</p>
			<div className={styles.buttonGroup}>
				<button className={styles.supportButton}>Apoiar</button>
				<button className={styles.participateButton}>Participar</button>
			</div>
		</div>
	);
};

export default CallToAction;
