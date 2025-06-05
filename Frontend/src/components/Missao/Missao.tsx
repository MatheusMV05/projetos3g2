import React, { useState, useEffect } from 'react';
import styles from './Missao.module.css';
import { InformacaoInstitucionalService } from '../../services/informacaoService';

const Missao: React.FC = () => {
	// Estados para armazenar o título e o texto da missão
	const [titulo, setTitulo] = useState('Carregando...');
	const [texto, setTexto] = useState('Por favor, aguarde enquanto buscamos nossos dados.');
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const fetchMissaoData = async () => {
			try {
				// Supondo que você tenha chaves como 'missao_titulo' e 'missao_texto' no banco
				const tituloData = await InformacaoInstitucionalService.buscarPorChave('missao_titulo');
				const textoData = await InformacaoInstitucionalService.buscarPorChave('missao_texto');

				setTitulo(tituloData.valor);
				setTexto(textoData.valor);
			} catch (error) {
				console.error("Erro ao buscar dados da missão:", error);
				setTitulo("Nossa Missão"); // Fallback
				setTexto("Não foi possível carregar os dados da missão. Por favor, tente novamente mais tarde."); // Fallback
			} finally {
				setLoading(false);
			}
		};

		fetchMissaoData();
	}, []); // O array vazio assegura que o useEffect rode apenas uma vez

	return (
		<section className={styles.container}>
			<div className={styles.left}>
				<h2>{loading ? 'Carregando...' : titulo}</h2>
				<p>{loading ? '...' : texto}</p>
			</div>
			<div className={styles.right}>
				{/* Você pode tornar a imagem dinâmica também, buscando por uma chave 'missao_imagem_url' */}
				<div className={styles.image}></div>
			</div>
		</section>
	);
};

export default Missao;