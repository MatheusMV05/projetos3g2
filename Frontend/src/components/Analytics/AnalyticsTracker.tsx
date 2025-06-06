import {useEffect} from 'react';
import {useLocation} from 'react-router-dom';
import {AnalyticsService} from '../../services/anayticsService.ts';

const AnalyticsTracker: React.FC = () => {
    const location = useLocation();

    useEffect(() => {
        // Envia um evento de PAGEVIEW sempre que a rota (location) mudar
        AnalyticsService.registrarEvento({
            tipo: 'PAGEVIEW',
            categoria: 'Navegação',
            acao: 'Visualização de Página',
            rotulo: location.pathname + location.search,
        });
    }, [location]); // O efeito é re-executado a cada mudança de 'location'

    return null; // Este componente não renderiza nada na tela
};

export default AnalyticsTracker;