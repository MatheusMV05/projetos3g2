import React, {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import styles from './Header.module.css';
import Login from '../../pages/Login/Login';
import ContatoModal from '../ContatoModal/ContatoModal';
import logo from '../../assets/Logo.svg';
import {AnalyticsService} from '../../services/anayticsService.ts'; // 1. Garanta que o AnalyticsService está importado

const Header: React.FC = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [showDropdown, setShowDropdown] = useState(false);
    const [isLoginOpen, setIsLoginOpen] = useState(false);
    const [isContatoOpen, setIsContatoOpen] = useState(false);

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

    // 2. Definir a função handleContatoClick para realizar ambas as ações
    const handleContatoClick = () => {
        // Ação 1: Registrar o evento de analytics
        AnalyticsService.registrarEvento({
            tipo: 'EVENT',
            categoria: 'Header',
            acao: 'Clique',
            rotulo: 'Botao Contato',
        });

        // Ação 2: Abrir o modal de contato
        setIsContatoOpen(true);
    };

    return (
        <>
            <header className={styles.header}>
                <div className={styles.leftSection}>
                    <Link to="/" className={styles.logoLink}>
                        <img src={logo} alt="BRASFI logo" className={styles.logo}/>
                    </Link>
                </div>

                <button
                    className={`${styles.hamburgerBtn} ${isMenuOpen ? styles.active : ''}`}
                    onClick={() => setIsMenuOpen(!isMenuOpen)}
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
                        <a href="#" onClick={(e) => {
                            e.preventDefault();
                            setShowDropdown(!showDropdown);
                        }}>
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
                    {/* 3. Chamar a função handleContatoClick no onClick */}
                    <button className={styles.contactBtn} onClick={handleContatoClick}>
                        Contato
                    </button>
                </div>
            </header>

            {/* Modal de Login */}
            <Login
                isOpen={isLoginOpen}
                onClose={() => setIsLoginOpen(false)}
            />

            {/* Modal de Contato */}
            <ContatoModal
                isOpen={isContatoOpen}
                onClose={() => setIsContatoOpen(false)}
            />
        </>
    );
};

export default Header;