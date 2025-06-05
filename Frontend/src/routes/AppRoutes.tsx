import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import PaginaDinamica from '../pages/PaginaDinamica/PaginaDinamica.tsx'; // 1. Import the new component
import LayoutADM from '../layouts/LayoutADM'; // novo layout
import Home from '../pages/Home/Home';
import TrabalheConosco from '../pages/TrabalheConosco/TrabalheConosco';
import Evento from '../pages/Evento/Evento';
import Servicos from '../pages/Servicos/Servicos';
import Sobre from '../pages/Sobre/Sobre';
import Noticias from '../pages/Noticias/Noticias';
import Equipe from '../pages/Equipe/Equipe';
import Conteudo from '../pages/Conteudo/Conteudo'
import PostEditor from '../pages/PostEditor/PostEditor'

const AppRoutes: React.FC = () => {
	return (
		<Routes>
			{/* Rotas públicas */}
			<Route element={<MainLayout />}>
				<Route path="/" element={<Home />} />
				<Route path="/pagina/:slug" element={<PaginaDinamica />} /> {/* 2. Add this new route */}
				<Route path="/trabalhe-conosco" element={<TrabalheConosco />} />
				<Route path="/evento" element={<Evento />} />
				<Route path="/servicos" element={<Servicos />} />
				<Route path="/sobre" element={<Sobre />} />
				<Route path="/noticias" element={<Noticias />} />
			</Route>

			{/* Rotas administrativas */}
			<Route element={<LayoutADM />}>
				<Route path="/equipe" element={<Equipe />} />
				<Route path="/conteudo" element={<Conteudo />} />
			</Route>

			<Route path="/post-editor" element={<PostEditor />} />

		</Routes>
	);
};

export default AppRoutes;
