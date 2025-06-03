import { useState } from 'react';
import HeaderADM from '../components/HeaderADM/HeaderADM';
import Sidebar from '../components/Sidebar/Sidebar';
import { Outlet } from 'react-router-dom';
import './LayoutADM.css';

const LayoutADM = () => {
  const [sidebarWidth, setSidebarWidth] = useState(240);

  return (
    <div className="layout-adm">
      <HeaderADM />
      <div className="layout-body">
        <Sidebar width={sidebarWidth} setWidth={setSidebarWidth} />
        <main className="main-content">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default LayoutADM;
