import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import LayoutADM from '../layouts/LayoutADM'; // novo layout
import Home from '../pages/Home/Home';
import TrabalheConosco from '../pages/TrabalheConosco/TrabalheConosco';
import Evento from '../pages/Evento/Evento';
import Servicos from '../pages/Servicos/Servicos';
import Sobre from '../pages/Sobre/Sobre';
import Noticias from '../pages/Noticias/Noticias';
import Equipe from '../pages/Equipe/Equipe';
const AppRoutes: React.FC = () => {
	return (
		<Routes>
			{/* Rotas públicas */}
			<Route element={<MainLayout />}>
				<Route path="/" element={<Home />} />
				<Route path="/trabalhe-conosco" element={<TrabalheConosco />} />
				<Route path="/evento" element={<Evento />} />
				<Route path="/servicos" element={<Servicos />} />
				<Route path="/sobre" element={<Sobre />} />
				<Route path="/noticias" element={<Noticias />} />
			</Route>

			{/* Rotas administrativas */}
			<Route element={<LayoutADM />}>
				<Route path="/equipe" element={<Equipe />} />
				
			</Route>
		</Routes>
	);
};

export default AppRoutes;
