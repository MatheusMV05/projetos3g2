import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Header.module.css';

const Header: React.FC = () => {
	return (
		<header className={styles.header}>
			<div className={styles.leftSection}>
				<Link to="/" className={styles.logoLink}>
					<img src="/logo.png" alt="BRASFI logo" className={styles.logo} />
				</Link>
				<nav className={styles.nav}>
					<a href="#">Sobre a BRASFI</a>
					<a href="#">Serviços</a>
					<Link to="/trabalhe-conosco">Faça parte</Link>
					<Link to="/evento">Eventos</Link>
					<a href="#">
						Notícias <span className={styles.dropdownArrow}>▼</span>
					</a>
				</nav>
			</div>
			<div className={styles.rightSection}>
				<button className={styles.langBtn}>Idioma</button>
				<button className={styles.contactBtn}>Contato</button>
			</div>
		</header>
	);
};

export default Header;
