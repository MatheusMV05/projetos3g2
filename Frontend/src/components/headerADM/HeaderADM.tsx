import { useState } from 'react';
import './HeaderADM.css';
import Register from '../../pages/Register/Register';

const HeaderADM = () => {
  const [isRegisterOpen, setRegisterOpen] = useState(false);

  const handleOpenRegister = () => setRegisterOpen(true);
  const handleCloseRegister = () => setRegisterOpen(false);
  const handleSwitchToLogin = () => {
    setRegisterOpen(false);
    // Aqui você pode abrir o modal de login se quiser
  };

  return (
    <>
      <header className="header-adm">
        <div className="header-left">
          <img src="/logo.png" alt="Logo" className="logo" />
          <nav className="nav-links">
            <a href="#">Seu Site</a>
            <a href="#">Recursos</a>
            <a href="#">Comunidade</a>
            <a href="#">Ajuda</a>
          </nav>
        </div>
        <div className="header-right">
          <button className="register-btn" onClick={handleOpenRegister}>Cadastro</button>
          <img src="/user.png" alt="Avatar" className="avatar" />
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
