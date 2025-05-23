import React from 'react';
import NoticiasIntro from '../../components/NoticiasIntro/NoticiasIntro';
import Recursos from '../../components/Recursos/Recursos'; // <-- importado aqui
import Blogwhite from '../../components/NoticiasIntro/blogwhite';

const Noticias: React.FC = () => {
	return (
		<div style={{ backgroundColor: '#ffffff', padding: '2rem' }}>
			
			<NoticiasIntro />
			<Blogwhite/>
			<Recursos />
		</div>
	);
};

export default Noticias;
