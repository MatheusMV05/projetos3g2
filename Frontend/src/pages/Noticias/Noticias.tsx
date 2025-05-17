import React from 'react';
import NoticiasIntro from '../../components/NoticiasIntro/NoticiasIntro';

const Noticias: React.FC = () => {
	return (
		<div style={{ backgroundColor: '#fff', padding: '2rem' }}>
			<h1>Notícias</h1>
			<p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
			<NoticiasIntro />
		</div>
	);
};

export default Noticias;
