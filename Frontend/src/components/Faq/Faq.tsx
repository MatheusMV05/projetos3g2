import React, { useState, useEffect } from 'react';
import styles from './Faq.module.css';
import { X, Plus } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';
import { InformacaoInstitucionalService, InformacaoInstitucional } from '../../services/informacaoService'; // 1. Importar

const Faq: React.FC = () => {
	const [openIndex, setOpenIndex] = useState<number | null>(0);
	// 2. Criar estado para os FAQs, loading e erros
	const [faqs, setFaqs] = useState<InformacaoInstitucional[]>([]);
	const [loading, setLoading] = useState(true);

	// 3. Buscar os dados da API
	useEffect(() => {
		const fetchFaqs = async () => {
			try {
				const data = await InformacaoInstitucionalService.buscarPorTipo('FAQ');
				setFaqs(data);
			} catch (error) {
				console.error("Erro ao buscar FAQs:", error);
				// Opcional: Adicionar um item de erro ao FAQ para feedback visual
				setFaqs([{
					id: 0, chave: "Erro ao carregar perguntas", valor: "Não foi possível buscar os dados do servidor. Tente novamente mais tarde.",
					tipo: '', descricao: '', ativo: false, dataCriacao: '', dataAtualizacao: ''
				}]);
			} finally {
				setLoading(false);
			}
		};

		fetchFaqs();
	}, []);

	const toggle = (index: number) => {
		setOpenIndex(openIndex === index ? null : index);
	};

	if (loading) {
		return (
			<section className={styles.faqSection}>
				<div className={styles.container}>
					<h2>Carregando Perguntas Frequentes...</h2>
				</div>
			</section>
		);
	}

	return (
		<section className={styles.faqSection}>
			<div className={styles.container}>
				<div className={styles.header}>
					{/* Estes títulos também podem ser buscados da API, se desejar */}
					<h2 className={styles.title}>Perguntas Frequentes</h2>
					<p className={styles.subtitle}>
						Encontre respostas para as dúvidas mais comuns sobre nossos
						<br />
						serviços e iniciativas.
					</p>
				</div>

				<div className={styles.list}>
					{/* 4. Mapear sobre o estado 'faqs' */}
					{faqs.map((item, index) => (
						<div
							key={item.id} // Usar um ID único do banco de dados
							className={`${styles.item} ${
								openIndex === index ? styles.open : ''
							}`}
							onClick={() => toggle(index)}
						>
							<div className={styles.questionRow}>
								{/* Usar 'chave' para a pergunta */}
								<h3 className={styles.question}>{item.chave}</h3>
								{openIndex === index ? (
									<X size={18} strokeWidth={2} />
								) : (
									<Plus size={18} strokeWidth={2} />
								)}
							</div>
							<AnimatePresence>
								{openIndex === index && (
									<motion.div
										key="content"
										initial={{ opacity: 0, height: 0 }}
										animate={{ opacity: 1, height: 'auto' }}
										exit={{ opacity: 0, height: 0 }}
										transition={{ duration: 0.3, ease: 'easeInOut' }}
									>
										{/* Usar 'valor' para a resposta */}
										<p className={styles.answer}>{item.valor}</p>
									</motion.div>
								)}
							</AnimatePresence>
						</div>
					))}
				</div>
			</div>
		</section>
	);
};

export default Faq;