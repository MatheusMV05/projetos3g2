import React from 'react';
import { EventHeader } from '../../components/EventHeader/EventHeader';
import BlogPostHeader from '../../components/BlogPostHeader/BlogPostHeader';

const Evento: React.FC = () => {
	return (
		<div style={{ padding: '2rem' }}>
			<h1>Eventos</h1>
			<p>
				Participe dos nossos eventos e descubra como a sustentabilidade pode transformar o futuro financeiro.
			</p>

			<div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '2rem' }}>
				{/* Evento em destaque */}
				<div style={{ border: '1px solid #ccc', borderRadius: '4px', background: '#f9f9f9' }}>
					<div style={{ height: '200px', background: '#e0e0e0', position: 'relative', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
						<div style={{
							position: 'absolute',
							top: '1rem',
							right: '1rem',
							background: '#fff',
							padding: '0.5rem',
							textAlign: 'center',
							fontWeight: 'bold',
							fontSize: '0.85rem',
							borderRadius: '4px',
							border: '1px solid #ccc'
						}}>
							Qua<br />07<br />Fev 2024
						</div>
					</div>
					<div style={{ padding: '1rem' }}>
						<div style={{ fontSize: '0.75rem', fontWeight: 'bold', background: '#ddd', display: 'inline-block', padding: '0.2rem 0.4rem', borderRadius: '3px' }}>Categoria</div>
						<div style={{ fontWeight: 'bold', fontSize: '1.1rem', margin: '0.5rem 0' }}>Título do Evento</div>
						<div style={{ fontSize: '0.9rem' }}>Centro de Convenções Verde, Rua da Sustentabilidade, 123.</div>
						<a href="#" style={{ display: 'inline-block', marginTop: '0.5rem', color: '#000', textDecoration: 'none', fontWeight: 'bold', fontSize: '0.85rem' }}>Ver evento →</a>
					</div>
				</div>

				{/* Lista lateral de eventos */}
				<div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
					<EventHeader
						weekday="Sex"
						day="09"
						month="Fev"
						year="2024"
						title="Título do Evento"
						category="Categoria"
						location="Auditório Verde, Avenida da Ecologia, 456."
					/>
					<EventHeader
						weekday="Sáb"
						day="10"
						month="Fev"
						year="2024"
						title="Título do Evento"
						location="Sala Verde, Rua da Sustentabilidade, 789."
					/>
					<EventHeader
						weekday="Dom"
						day="11"
						month="Fev"
						year="2024"
						title="Título do Evento"
						location="Espaço Verde, Avenida da Sustentabilidade, 321."
					/>
				</div>
			</div>

			{/* BlogPostHeader */}
			<BlogPostHeader />
		</div>
	);
};

export default Evento;
