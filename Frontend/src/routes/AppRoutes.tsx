import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import Home from '../pages/Home/Home';
import TrabalheConosco from '../pages/TrabalheConosco/TrabalheConosco';
import Evento from '../pages/Evento/Evento'; 

const AppRoutes: React.FC = () => {
	return (
		<Routes>
			<Route element={<MainLayout />}>
				<Route path="/" element={<Home />} />
				<Route path="/trabalhe-conosco" element={<TrabalheConosco />} />
				<Route path="/evento" element={<Evento />} />
			</Route>
		</Routes>
	);
};

export default AppRoutes;
