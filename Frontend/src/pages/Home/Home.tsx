import React from 'react';
import HeroSection from '../../components/HeroSection/HeroSection';
import SobreSection from '../../components/SobreSection/SobreSection';
import Impacto from '../../components/Impacto/Impacto';
import Relato from '../../components/Relato/Relato';

const Home: React.FC = () => {
	return (
		<>
			<HeroSection />
			<SobreSection />
			<Impacto />
			<Relato />
		</>
	);
};

export default Home;
