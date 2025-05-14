import React from 'react';
import SustainableProjectsSection from '../../components/Projects/SustainableProjectsSection';
import Metrics from '../../components/Metrics/Metrics';
import SustainableFinanceCourses from '../../components/SustainableFinanceCourses/SustainableFinanceCourses'

const Servicos: React.FC = () => {
	return (
		<main>
			<SustainableProjectsSection />
			<Metrics />
			<SustainableFinanceCourses />
		</main>
	);
};

export default Servicos;
