import React from 'react';
import { EventHeader } from '../../components/EventHeader/EventHeader';
import BlogPostHeader from '../../components/BlogPostHeader/BlogPostHeader';
import ProximosEventos from '../../components/ProximosEventos/ProximosEventos'; // <-- IMPORTADO AQUI

const Evento: React.FC = () => {
	return (
		<div style={{ padding: '2rem' }}>
			<h1>Eventos</h1>
			<p>
				Lorem ipsum dolor sit amet consectetur adipiscing elit.
				<br />
			</p>

			<div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '2rem' }}>
				{/* Evento em destaque */}
				<div style={{ border: '1px solid #ccc', borderRadius: '4px', background: '#f9f9f9' }}>
					<div
						style={{
							height: '200px',
							background: '#e0e0e0',
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
								padding: '0.5rem',
								textAlign: 'center',
								fontWeight: 'bold',
								fontSize: '0.85rem',
								borderRadius: '4px',
								border: '1px solid #ccc',
							}}
						>
							xxx<br />XX<br />xxx
						</div>
					</div>
					<div style={{ padding: '1rem' }}>
						<div
							style={{
								fontSize: '0.75rem',
								fontWeight: 'bold',
								background: '#ddd',
								display: 'inline-block',
								padding: '0.2rem 0.4rem',
								borderRadius: '3px',
							}}
						>
							Categoria
						</div>
						<div style={{ fontWeight: 'bold', fontSize: '1.1rem', margin: '0.5rem 0' }}>
							Título do Evento
						</div>
						<div style={{ fontSize: '0.9rem' }}>
							Lorem ipsum dolor sit amet consectetur adipiscing elit.
						</div>
						<a
							href="#"
							style={{
								display: 'inline-block',
								marginTop: '0.5rem',
								color: '#000',
								textDecoration: 'none',
								fontWeight: 'bold',
								fontSize: '0.85rem',
							}}
						>
							Ver evento →
						</a>
					</div>
				</div>

				{/* Lista lateral de eventos */}
				<div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
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
			<br></br>
			<br></br>
			<br></br>
			{/* BlogPostHeader */}
			<BlogPostHeader />

			{/* ProximosEventos */}
			<ProximosEventos />
		</div>
	);
};

export default Evento;
