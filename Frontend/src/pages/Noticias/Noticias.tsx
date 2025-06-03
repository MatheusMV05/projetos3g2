import React from 'react';
import NoticiasIntro from '../../components/NoticiasIntro/NoticiasIntro';
import Blogwhite from '../../components/NoticiasIntro/Blogwhite';
import Recursos from '../../components/Recursos/Recursos';
import Conteudo from '../../components/Conteudo/Conteudo';

const Noticias: React.FC = () => {
	return (
		<div style={{ backgroundColor: '#ffffff', padding: '2rem' }}>
			
			<NoticiasIntro />
			<Blogwhite/>
			<Recursos />
			<Conteudo />
		</div>
	);
};

export default Noticias;
