import React, { useRef } from 'react';
import './Sidebar.css';
import { NavLink } from 'react-router-dom';

interface SidebarProps {
  width: number;
  setWidth: (width: number) => void;
}

const Sidebar: React.FC<SidebarProps> = ({ width, setWidth }) => {
  const isResizing = useRef(false);

  const handleMouseDown = () => {
    isResizing.current = true;
    document.addEventListener('mousemove', handleMouseMove);
    document.addEventListener('mouseup', handleMouseUp);
  };

  const handleMouseMove = (e: MouseEvent) => {
    if (isResizing.current) {
      const newWidth = Math.max(150, Math.min(e.clientX, 400));
      setWidth(newWidth);
    }
  };

  const handleMouseUp = () => {
    isResizing.current = false;
    document.removeEventListener('mousemove', handleMouseMove);
    document.removeEventListener('mouseup', handleMouseUp);
  };

  return (
    <aside className="sidebar" style={{ width }}>
      <div className="sidebar-content">
        <div className="sidebar-title">Brasfi.com.br</div>
        <nav className="sidebar-nav">
          <NavLink to="/conteudo">Conteúdo</NavLink>
          <NavLink to="/acessos">Acessos</NavLink>
          <NavLink to="/recursos">Recursos</NavLink>
          <NavLink to="/equipe">Equipe</NavLink>
          <NavLink to="/experiencia">Experiência</NavLink>
          <NavLink to="/ajuda">Ajuda</NavLink>
          <NavLink to="/configuracoes">Configurações</NavLink>
        </nav>
      </div>
      <div className="resizer" onMouseDown={handleMouseDown}></div>
    </aside>
  );
};

export default Sidebar;
