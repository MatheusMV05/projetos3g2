import React, { useState, useEffect } from 'react';
import styles from './SobreSection.module.css';
import { Box, ChevronRight } from 'lucide-react';
import { motion } from 'framer-motion';
import { InformacaoInstitucionalService } from '../../services/informacaoService'; // 1. Importar o serviço

const SobreSection: React.FC = () => {
	// 2. Criar estado para armazenar o mapa de informações
	const [info, setInfo] = useState<Record<string, string>>({});
	const [loading, setLoading] = useState(true);

	// 3. Buscar os dados da API
	useEffect(() => {
		const fetchSobreData = async () => {
			try {
				// Buscamos todos os itens do tipo 'SOBRE_SECAO' de uma vez
				const dataMap = await InformacaoInstitucionalService.buscarMapaPorTipo('SOBRE_SECAO');
				setInfo(dataMap);
			} catch (error) {
				console.error("Erro ao buscar dados da seção Sobre:", error);
			} finally {
				setLoading(false);
			}
		};

		fetchSobreData();
	}, []);

	// Função auxiliar para evitar a repetição de (info.chave || 'Texto padrão')
	const getText = (key: string, fallback: string) => loading ? '...' : (info[key] || fallback);

	return (
		<section className={styles.sobreSection}>
			<div className={styles.content}>
				<motion.h2
					className={styles.title}
					initial={{ opacity: 0, y: 50 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.6, ease: 'easeOut' }}
				>
					{/* 4. Usar os dados do estado */}
					<span dangerouslySetInnerHTML={{ __html: getText('sobre_secao_titulo', 'Lorem ipsum dolor <br /> sit amet') }} />
				</motion.h2>

				<motion.p
					className={styles.subtitle}
					initial={{ opacity: 0, y: 50 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.6, ease: 'easeOut', delay: 0.1 }}
				>
					{getText('sobre_secao_subtitulo', 'Lorem ipsum dolor sit amet consectetur adipiscing elit. Aenean sit amet metus a nulla suscipit bibendum.')}
				</motion.p>

				<div className={styles.grid}>
					{/* Card 1 */}
					<motion.div className={styles.card} initial={{ opacity: 0, y: 50 }} whileInView={{ opacity: 1, y: 0 }} viewport={{ once: true, amount: 0.4 }} transition={{ duration: 0.6, ease: 'easeOut', delay: 0.2 }}>
						<div className={styles.icon}><Box size={40} strokeWidth={1.5} /></div>
						<h3>{getText('sobre_card1_titulo', 'Lorem ipsum dolor')}</h3>
						<p>{getText('sobre_card1_texto', 'Lorem ipsum dolor sit amet consectetur adipiscing elit.')}</p>
					</motion.div>

					{/* Card 2 */}
					<motion.div className={styles.card} initial={{ opacity: 0, y: 50 }} whileInView={{ opacity: 1, y: 0 }} viewport={{ once: true, amount: 0.4 }} transition={{ duration: 0.6, ease: 'easeOut', delay: 0.3 }}>
						<div className={styles.icon}><Box size={40} strokeWidth={1.5} /></div>
						<h3>{getText('sobre_card2_titulo', 'Lorem ipsum dolor')}</h3>
						<p>{getText('sobre_card2_texto', 'Lorem ipsum dolor sit amet consectetur adipiscing elit.')}</p>
					</motion.div>

					{/* Card 3 */}
					<motion.div className={styles.card} initial={{ opacity: 0, y: 50 }} whileInView={{ opacity: 1, y: 0 }} viewport={{ once: true, amount: 0.4 }} transition={{ duration: 0.6, ease: 'easeOut', delay: 0.4 }}>
						<div className={styles.icon}><Box size={40} strokeWidth={1.5} /></div>
						<h3>{getText('sobre_card3_titulo', 'Lorem ipsum dolor')}</h3>
						<p>{getText('sobre_card3_texto', 'Lorem ipsum dolor sit amet consectetur adipiscing elit.')}</p>
					</motion.div>
				</div>

				<motion.div
					className={styles.buttons}
					initial={{ opacity: 0, y: 50 }}
					whileInView={{ opacity: 1, y: 0 }}
					viewport={{ once: true, amount: 0.4 }}
					transition={{ duration: 0.6, ease: 'easeOut', delay: 0.5 }}
				>
					<button className={styles.primary}>{getText('sobre_botao_primario', 'Lorem Ipsum')}</button>
					<button className={styles.link}>
						{getText('sobre_botao_secundario', 'Lorem ipsum')} <ChevronRight size={16} />
					</button>
				</motion.div>
			</div>
		</section>
	);
};

export default SobreSection;