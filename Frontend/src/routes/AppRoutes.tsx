import React from 'react';
import {Routes, Route, useParams} from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import PaginaDinamica from '../pages/PaginaDinamica/PaginaDinamica.tsx';
import LayoutADM from '../layouts/LayoutADM';
import Home from '../pages/Home/Home';
import TrabalheConosco from '../pages/TrabalheConosco/TrabalheConosco';
import Evento from '../pages/Evento/Evento';
import Servicos from '../pages/Servicos/Servicos';
import Sobre from '../pages/Sobre/Sobre';
import Noticias from '../pages/Noticias/Noticias';
import Equipe from '../pages/Equipe/Equipe';
import Conteudo from '../pages/Conteudo/Conteudo'
import PostEditor from '../pages/PostEditor/PostEditor'
import EventoEditor from '../components/EventoEditor/EventoEditor'; // Import do novo editor
import CancelarInscricaoPage from '../pages/CancelarInscricao/CancelarInscricaoPage';
import BuscaPage from '../pages/Busca/BuscaPage';
import PaginaViewPage from '../pages/PaginaView/PaginaViewPage';
import PublicationDetailPage from '../pages/PublicationDetail/PublicationDetailPage';
import TagsListPage from '../pages/Tags/TagsListPage';

// Crie um placeholder para a página de detalhes da tag
const TagDetailPage: React.FC = () => {
    const {slug} = useParams();
    return <div style={{padding: '2rem'}}><h1>Posts com a tag: {slug}</h1></div>
}

const AppRoutes: React.FC = () => {
    return (
        <Routes>
            {/* Rotas públicas */}
            <Route element={<MainLayout/>}>
                <Route path="/" element={<Home/>}/>
                <Route path="/pagina/:slug" element={<PaginaDinamica/>}/>
                <Route path="/trabalhe-conosco" element={<TrabalheConosco/>}/>
                <Route path="/evento" element={<Evento/>}/>
                <Route path="/servicos" element={<Servicos/>}/>
                <Route path="/sobre" element={<Sobre/>}/>
                <Route path="/noticias" element={<Noticias/>}/>
                <Route path="/newsletter/cancelar" element={<CancelarInscricaoPage/>}/>
                <Route path="/search" element={<BuscaPage/>}/>
                <Route path="/pagina/view/:id" element={<PaginaViewPage/>}/>
                <Route path="/publicacao/:slug" element={<PublicationDetailPage/>}/>
                <Route path="/tags" element={<TagsListPage/>}/>
                <Route path="/tag/:slug" element={<TagDetailPage/>}/>
            </Route>

            {/* Rotas administrativas */}
            <Route element={<LayoutADM/>}>
                <Route path="/equipe" element={<Equipe/>}/>
                <Route path="/conteudo" element={<Conteudo/>}/>
            </Route>

            {/* Rotas de editores (fora do layout principal para máxima flexibilidade) */}
            <Route path="/post-editor" element={<PostEditor/>}/>
            <Route path="/evento-editor" element={<EventoEditor/>}/> {/* Nova rota para eventos */}

        </Routes>
    );
};

export default AppRoutes;