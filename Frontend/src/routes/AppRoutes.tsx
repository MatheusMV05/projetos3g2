import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import Home from '../pages/Home/Home';
import TrabalheConosco from '../pages/TrabalheConosco/TrabalheConosco';
import Evento from '../pages/Evento/Evento';
import Servicos from '../pages/Servicos/Servicos';

const AppRoutes: React.FC = () => {
	return (
		<Routes>
			<Route element={<MainLayout />}>
				<Route path="/" element={<Home />} />
				<Route path="/trabalhe-conosco" element={<TrabalheConosco />} />
				<Route path="/evento" element={<Evento />} />
				<Route path="/servicos" element={<Servicos />} />
			</Route>
		</Routes>
	);
};

export default AppRoutes;
