import React from 'react';
import { Outlet, useLocation } from 'react-router-dom'; // Importando useLocation
import Header from '../components/Header/Header';
import Footer from '../components/Footer/Footer';

const MainLayout: React.FC = () => {
  const location = useLocation(); // Obtém a rota atual

  // Condicional para verificar se a rota atual é a de registro
  const isRegisterPage = location.pathname === '/cadastro';

  return (
    <>
     <Header />
      <main>
        <Outlet />
      </main>
      {!isRegisterPage && <Footer />} {/* Não renderiza Footer se for a página de registro */}
    </>
  );
};

export default MainLayout;
