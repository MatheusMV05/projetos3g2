import React, { useEffect } from 'react';
import AppRoutes from './routes/AppRoutes';
import AnalyticsTracker from './components/Analytics/AnalyticsTracker';
import { AuthService } from './services/authService';

const App: React.FC = () => {
    // Inicializa o serviço de autenticação quando a aplicação carregar
    useEffect(() => {
        AuthService.initialize();

        // Debug: mostra informações de autenticação no console (apenas em desenvolvimento)
        if (process.env.NODE_ENV === 'development') {
            console.log('🔐 Auth Status:', {
                isAuthenticated: AuthService.isAuthenticated(),
                userEmail: AuthService.getUserEmail(),
                hasToken: !!AuthService.getToken()
            });
        }
    }, []);

    return (
        <div style={{fontFamily: 'Unageo, sans-serif'}}>
            <AnalyticsTracker/>
            <AppRoutes/>
        </div>
    );
};

export default App;