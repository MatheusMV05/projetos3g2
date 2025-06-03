import './HeaderADM.css';

const HeaderADM = () => {
  return (
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
        <img src="/user.png" alt="Avatar" className="avatar" />
      </div>
    </header>
  );
};

export default HeaderADM;