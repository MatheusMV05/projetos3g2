import React from 'react';
import styles from './CallToAction.module.css';

const CallToAction: React.FC = () => {
	return (
		<div className={styles.container}>
			<h1 className={styles.title}>Lorem ipsum dolor sit amet</h1>
			<p className={styles.subtitle}>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit.
			</p>
			<div className={styles.buttonGroup}>
				<button className={styles.supportButton}>Apoiar</button>
				<button className={styles.participateButton}>Participar</button>
			</div>
		</div>
	);
};

export default CallToAction;
