import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styles from './Header.module.css';
import Login from '../../pages/Login/Login';
import ContatoModal from '../ContatoModal/ContatoModal';
import logo from '../../assets/Logo.svg';
import { AnalyticsService } from '../../services/anayticsService.ts';

// Declaração necessária para uso do Google Tradutor
declare global {
  interface Window {
    googleTranslateElementInit: () => void;
    google: any;
  }
}

const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [showDropdown, setShowDropdown] = useState(false);
  const [isLoginOpen, setIsLoginOpen] = useState(false);
  const [isContatoOpen, setIsContatoOpen] = useState(false);
  const [termoBusca, setTermoBusca] = useState('');
  const navigate = useNavigate();

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (termoBusca.trim()) {
      navigate(`/search?q=${encodeURIComponent(termoBusca.trim())}`);
    }
  };

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth > 768 && isMenuOpen) {
        setIsMenuOpen(false);
      }
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, [isMenuOpen]);

  const handleContatoClick = () => {
    AnalyticsService.registrarEvento({
      tipo: 'EVENT',
      categoria: 'Header',
      acao: 'Clique',
      rotulo: 'Botao Contato',
    });
    setIsContatoOpen(true);
  };

  // Função para carregar o tradutor do Google
  const handleIdiomaClick = () => {
    if (!document.getElementById('google-translate-script')) {
      const script = document.createElement('script');
      script.id = 'google-translate-script';
      script.src = '//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit';
      document.body.appendChild(script);
    }
    const el = document.getElementById('google_translate_element');
    if (el) el.style.display = 'block';
  };

  // Função chamada pelo script externo
  useEffect(() => {
    window.googleTranslateElementInit = () => {
      new window.google.translate.TranslateElement(
        {
          pageLanguage: 'pt',
          includedLanguages: 'en,es,fr,de,it',
          layout: window.google.translate.TranslateElement.InlineLayout.SIMPLE,
        },
        'google_translate_element'
      );
    };
  }, []);

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
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                setShowDropdown(!showDropdown);
              }}
            >
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

        <form onSubmit={handleSearchSubmit} className={styles.searchForm}>
          <input
            type="search"
            placeholder="Buscar no site..."
            value={termoBusca}
            onChange={(e) => setTermoBusca(e.target.value)}
            className={styles.searchInput}
          />
        </form>

        <div className={styles.rightSection}>
          <button className={styles.langBtn} onClick={handleIdiomaClick}>
            Idioma
          </button>
          <button className={styles.contactBtn} onClick={handleContatoClick}>
            Contato
          </button>
        </div>
      </header>

      {/* Seletor do Google Tradutor (aparece após clique) */}
      <div id="google_translate_element" style={{ display: 'none', position: 'absolute', top: '70px', right: '20px', zIndex: 9999 }}></div>

      {/* Modal de Login */}
      <Login isOpen={isLoginOpen} onClose={() => setIsLoginOpen(false)} />

      {/* Modal de Contato */}
      <ContatoModal isOpen={isContatoOpen} onClose={() => setIsContatoOpen(false)} />
    </>
  );
};

export default Header;
