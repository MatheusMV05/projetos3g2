import React from 'react';
import AppRoutes from './routes/AppRoutes';
import AnalyticsTracker from './components/Analytics/AnalyticsTracker'; // 1. Importar

const App: React.FC = () => {
    return (
        <div style={{fontFamily: 'Unageo, sans-serif'}}>
            <AnalyticsTracker/> {/* 2. Adicionar o tracker */}
            <AppRoutes/>
        </div>
    );
};

export default App;