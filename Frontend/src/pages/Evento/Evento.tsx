import React from 'react';
import { EventHeader } from '../../components/EventHeader/EventHeader';
import BlogPostHeader from '../../components/BlogPostHeader/BlogPostHeader';
import ProximosEventos from '../../components/ProximosEventos/ProximosEventos';
import Blog from '../../components/Blog/Blog';

const Evento: React.FC = () => {
	return (
		<div style={{ padding: '2rem' }}>
			<h1>Eventos</h1>
			<p>
				Lorem ipsum dolor sit amet consectetur adipiscing elit.
				<br />
			</p>

			{/* Layout com grid ajustado */}
			<div
				style={{
					display: 'grid',
					gridTemplateColumns: '700px 1fr', // largura fixa para o card principal
					columnGap: '2rem', // distância ajustada entre colunas
					alignItems: 'start',
				}}
			>
				{/* Evento em destaque */}
				<div
					style={{
						border: '1px solid #000000',
						borderRadius: '4px',
						background: '#f9f9f9',
						overflow: 'hidden',
					}}
				>
					<div
						style={{
							height: '300px',
							background: '#ddd',
							position: 'relative',
							display: 'flex',
							justifyContent: 'center',
							alignItems: 'center',
						}}
					>
						<div
							style={{
								position: 'absolute',
								top: '1rem',
								right: '1rem',
								background: '#fff',
								padding: '0.5rem 2.2rem',
								textAlign: 'center',
								fontWeight: 'bold',
								fontSize: '1rem',
							}}
						>
							xxx<br />XX<br />xxx
						</div>
					</div>
					<div style={{ padding: '1.2rem' }}>
						<div
							style={{
								fontSize: '0.75rem',
								fontWeight: 'bold',
								background: '#ddd',
								display: 'inline-block',
								padding: '0.2rem 0.6rem',
								borderRadius: '3px',
							}}
						>
							Categoria
						</div>
						<div style={{ fontWeight: 'bold', fontSize: '1.1rem', margin: '1rem 0' }}>
							Título do Evento
						</div>
						<div style={{ fontSize: '0.9rem', fontWeight: 'bold' }}>
							Lorem ipsum dolor sit amet consectetur adipiscing elit.
						</div>
						<a
							href="#"
							style={{
								display: 'inline-block',
								marginTop: '1rem',
								color: '#161616',
								textDecoration: 'none',
								fontWeight: 'normal',
								fontSize: '0.85rem',
							}}
						>
							Ver evento &gt;
						</a>
					</div>
				</div>

				{/* Lista lateral de eventos */}
				<div style={{ display: 'flex', flexDirection: 'column', gap: '1.2rem', fontWeight: 'bold' }}>
					<EventHeader
						weekday="xxx"
						day="XX"
						month="xxx"
						title="Título do Evento"
						category="Categoria"
						location="Lorem ipsum dolor sit amet consectetur adipiscing elit."
					/>
					<EventHeader
						weekday="xxx"
						day="XX"
						month="xxx"
						title="Título do Evento"
						location="Lorem ipsum dolor sit amet consectetur adipiscing elit."
					/>
					<EventHeader
						weekday="xxx"
						day="XX"
						month="xxx"
						title="Título do Evento"
						location="Lorem ipsum dolor sit amet consectetur adipiscing elit."
					/>
				</div>
			</div>

			<br />
			<br />
			<br />

			<BlogPostHeader />
			<ProximosEventos />
			<Blog />
		</div>
	);
};

export default Evento;
