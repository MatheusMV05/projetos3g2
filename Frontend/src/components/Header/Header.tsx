import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './Header.module.css';
import Register from '../../pages/Register/Register';
import Login from '../../pages/Login/Login'; // <- novo componente a ser usado

const Header: React.FC = () => {
	const [isMenuOpen, setIsMenuOpen] = useState(false);
	const [showDropdown, setShowDropdown] = useState(false);
	const [isRegisterOpen, setIsRegisterOpen] = useState(false);
	const [isLoginOpen, setIsLoginOpen] = useState(false); // <- controle do login

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
						<img src="/logo.png" alt="BRASFI logo" className={styles.logo} />
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
					<a href="/sobre">Sobre a BRASFI</a>
					<a href="/servicos">Serviços</a>
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

					{/* Agora é Login */}
					<a href="#" onClick={(e) => {
						e.preventDefault();
						setIsLoginOpen(true);
					}}>
						Entrar
					</a>
				</nav>

				<div className={styles.rightSection}>
					<button className={styles.langBtn}>Idioma</button>
					<button className={styles.contactBtn}>Contato</button>
				</div>
			</header>

			{/* Modais */}
			<Login
				isOpen={isLoginOpen}
				onClose={() => setIsLoginOpen(false)}
				onSwitchToRegister={() => {
					setIsLoginOpen(false);
					setIsRegisterOpen(true);
				}}
			/>

			<Register
				isOpen={isRegisterOpen}
				onClose={() => setIsRegisterOpen(false)}
				onSwitchToLogin={() => {
					setIsRegisterOpen(false);
					setIsLoginOpen(true);
				}}
			/>
		</>
	);
};

export default Header;
