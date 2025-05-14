import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './Header.module.css';

const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [showDropdown, setShowDropdown] = useState(false);
  
  // Função para alternar o menu hamburguer
  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  // Função para alternar o submenu de notícias
  const toggleDropdown = (e: React.MouseEvent) => {
    e.preventDefault();
    setShowDropdown(!showDropdown);
  };

  // Fechar o menu quando a tela for redimensionada para desktop
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
    <header className={styles.header}>
      <div className={styles.leftSection}>
        <Link to="/" className={styles.logoLink}>
          <img src="/logo.png" alt="BRASFI logo" className={styles.logo} />
        </Link>
      </div>

      {/* Botão hamburguer visível apenas em telas pequenas */}
      <button 
        className={`${styles.hamburgerBtn} ${isMenuOpen ? styles.active : ''}`} 
        onClick={toggleMenu}
        aria-label="Menu"
      >
        <span className={styles.hamburgerIcon}></span>
      </button>

      {/* Menu de navegação com classe dinâmica */}
      <nav className={`${styles.nav} ${isMenuOpen ? styles.menuOpen : ''}`}>
        <a href="#">Sobre a BRASFI</a>
        <a href="#">Serviços</a>
        <Link to="/trabalhe-conosco">Faça parte</Link>
        <Link to="/evento">Eventos</Link>
        <div className={styles.dropdownContainer}>
          <a href="#" onClick={toggleDropdown}>
            Notícias <span className={styles.dropdownArrow}>▼</span>
          </a>
          {showDropdown && (
            <div className={styles.dropdownMenu}>
              <a href="#">Últimas notícias</a>
              <a href="#">Blog</a>
              <a href="#">Imprensa</a>
            </div>
          )}
        </div>
      </nav>

      <div className={styles.rightSection}>
        <button className={styles.langBtn}>Idioma</button>
        <button className={styles.contactBtn}>Contato</button>
      </div>
    </header>
  );
};

export default Header;