import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './Header.module.css';
import Login from '../../pages/Login/Login'; // apenas Login
import logo from '../../assets/Logo.svg';

const Header: React.FC = () => {
	const [isMenuOpen, setIsMenuOpen] = useState(false);
	const [showDropdown, setShowDropdown] = useState(false);
	const [isLoginOpen, setIsLoginOpen] = useState(false); // controle do login

	const toggleMenu = () => {
		setIsMenuOpen(!isMenuOpen);
	};

	const toggleDropdown = (e: React.MouseEvent) => {
		e.preventDefault();
		setShowDropdown(!showDropdown);
	};

	useEffect(() => {
		const handleResize = () => {
			if (window.innerWidth > 768 && isMenuOpen) {
				setIsMenuOpen(false);
			}
		};

		window.addEventListener('resize', handleResize);
		return () => {
			window.removeEventListener('resize', handleResize);
		};
	}, [isMenuOpen]);

	return (
		<>
			<header className={styles.header}>
				<div className={styles.leftSection}>
					<Link to="/" className={styles.logoLink}>
						<img src={logo} alt="BRASFI logo" className={styles.logo} />
					</Link>
				</div>

				<button
					className={`${styles.hamburgerBtn} ${isMenuOpen ? styles.active : ''}`}
					onClick={toggleMenu}
					aria-label="Menu"
				>
					<span className={styles.hamburgerIcon}></span>
				</button>

				<nav className={`${styles.nav} ${isMenuOpen ? styles.menuOpen : ''}`}>
					<Link to="/sobre">Sobre a BRASFI</Link>
					<Link to="/servicos">Serviços</Link>
					<Link to="/trabalhe-conosco">Faça parte</Link>
					<Link to="/evento">Eventos</Link>

					<div className={styles.dropdownContainer}>
						<a href="#" onClick={toggleDropdown}>
							Notícias <span className={styles.dropdownArrow}>▼</span>
						</a>
						{showDropdown && (
							<div className={styles.dropdownMenu}>
								<Link to="/noticias">Últimas notícias</Link>
								<a href="#">Blog</a>
								<a href="#">Imprensa</a>
							</div>
						)}
					</div>

					{/* Botão de Login */}
					<a
						href="#"
						onClick={(e) => {
							e.preventDefault();
							setIsLoginOpen(true);
						}}
					>
						Entrar
					</a>
				</nav>

				<div className={styles.rightSection}>
					<button className={styles.langBtn}>Idioma</button>
					<button className={styles.contactBtn}>Contato</button>
				</div>
			</header>

			{/* Modal de Login */}
			<Login
				isOpen={isLoginOpen}
				onClose={() => setIsLoginOpen(false)}
			/>
		</>
	);
};

export default Header;
