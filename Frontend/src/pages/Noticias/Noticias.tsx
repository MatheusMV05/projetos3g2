import React from 'react';
import NoticiasIntro from '../../components/NoticiasIntro/NoticiasIntro';
import Blogwhite from '../../components/NoticiasIntro/Blogwhite';
import Recursos from '../../components/Recursos/Recursos';

const Noticias: React.FC = () => {
	return (
		<div >
			
			<NoticiasIntro />
			<Blogwhite/>
			<Recursos />
		</div>
	);
};

export default Noticias;
