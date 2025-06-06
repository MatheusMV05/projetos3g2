import { useState } from 'react';
import { Link } from 'react-router-dom';
import './HeaderADM.css';
import Register from '../../pages/Register/Register';
import logo from '../../assets/Logo.svg';

const HeaderADM = () => {
  const [isRegisterOpen, setRegisterOpen] = useState(false);

  const handleOpenRegister = () => setRegisterOpen(true);
  const handleCloseRegister = () => setRegisterOpen(false);
  const handleSwitchToLogin = () => {
    setRegisterOpen(false);
    // Aqui você pode abrir o modal de login, se necessário
  };

  return (
    <>
      <header className="header-adm">
        <div className="header-left">
          <Link to="/">
            <img src={logo} alt="BRASFI logo" className="logo" />
          </Link>

          <nav className="nav-links">
            <a href="#">Seu Site</a>
            <a href="#">Recursos</a>
            <a href="#">Comunidade</a>
            <a href="#">Ajuda</a>
          </nav>
        </div>

        {/* Exemplo de botão que abre o modal de cadastro */}
        <div className="header-right">
          <button onClick={handleOpenRegister} className="register-btn">
            Cadastrar Usuário
          </button>
        </div>
      </header>

      <Register
        isOpen={isRegisterOpen}
        onClose={handleCloseRegister}
        onSwitchToLogin={handleSwitchToLogin}
      />
    </>
  );
};

export default HeaderADM;
