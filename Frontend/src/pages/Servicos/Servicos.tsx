import React from 'react';
import SustainableProjectsSection from '../../components/Projects/SustainableProjectsSection';
import Metrics from '../../components/Metrics/Metrics';
import SustainableFinanceCourses from '../../components/SustainableFinanceCourses/SustainableFinanceCourses'
import ClientFeedbackCarousel from '../../components/ClientFeedbackCarousel/ClientFeedbackCarousel'

const Servicos: React.FC = () => {
	return (
		<main>
			<SustainableProjectsSection />
			<Metrics />
			<SustainableFinanceCourses />
			<ClientFeedbackCarousel />
		</main>
	);
};

export default Servicos;
