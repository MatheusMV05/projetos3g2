import React from 'react';
import {useParams} from 'react-router-dom';

// Placeholder para a página de detalhe da publicação
const PublicationDetailPage: React.FC = () => {
    const {slug} = useParams();
    return (
        <div style={{padding: '2rem'}}>
            <h1>Página de Detalhe da Publicação</h1>
            <p>O conteúdo para o slug "<strong>{slug}</strong>" será carregado aqui.</p>
        </div>
    );
};

export default PublicationDetailPage;