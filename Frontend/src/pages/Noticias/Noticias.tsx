import React from 'react';
import NoticiasIntro from '../../components/NoticiasIntro/NoticiasIntro';
import Blog from '../../components/Blog/Blog';
import Recursos from '../../components/Recursos/Recursos'; // <-- importado aqui

const Noticias: React.FC = () => {
	return (
		<div style={{ backgroundColor: '#fff', padding: '2rem' }}>
			<h1>Notícias</h1>
			<p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
			
			<NoticiasIntro />
			<Blog />
			<Recursos /> {/* <-- adicionado aqui */}
		</div>
	);
};

export default Noticias;
