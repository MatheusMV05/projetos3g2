import React from 'react';
import HeroSection from '../../components/HeroSection/HeroSection';
import SobreSection from '../../components/SobreSection/SobreSection';
import Impacto from '../../components/Impacto/Impacto';
import Relato from '../../components/Relato/Relato';
import Partners from '../../components/Partners/Partners';
import Faq from '../../components/Faq/Faq';
import JoinUs from '../../components/JoinUs/JoinUs';

const Home: React.FC = () => {
	return (
		<>
			<HeroSection />
			<SobreSection />
			<Impacto />
			<Relato />
			<Partners />
			<Faq />
			<JoinUs />
		</>
	);
};

export default Home;
