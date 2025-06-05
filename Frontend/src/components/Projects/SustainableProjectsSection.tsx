import React, {useState, useEffect} from 'react';
import {motion} from 'framer-motion';
import ProjectsGrid from './ProjectsGrid';
import styles from './SustainableProjectsSection.module.css';
import {CategoryService} from '../../services/categoryService'; // 1. Importar
import {CategoryDTO} from '../../services/publicationService';

// Os projetos ainda são mockados, mas as categorias serão dinâmicas
const allProjects = [
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Sustentabilidade'], // Usar nomes de categorias reais
        image: 'https://via.placeholder.com/300x200',
    },
    {
        title: 'Lorem Ipsum',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        tags: ['Finanças Verdes'],
        image: 'https://via.placeholder.com/300x200',
    },
    // ... mais projetos
];

const SustainableProjectsSection: React.FC = () => {
    const [selectedCategory, setSelectedCategory] = useState('Todos');
    // 2. Adicionar estado para as categorias
    const [categories, setCategories] = useState<CategoryDTO[]>([]);
    const [loading, setLoading] = useState(true);

    // 3. Buscar as categorias da API
    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const data = await CategoryService.listarTodas();
                setCategories(data);
            } catch (error) {
                console.error("Erro ao buscar categorias:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchCategories();
    }, []);

    const filteredProjects =
        selectedCategory === 'Todos'
            ? allProjects
            : allProjects.filter((p) => p.tags.includes(selectedCategory));

    return (
        <section className={styles.section}>
            <motion.div
                className={styles.header}
                initial={{opacity: 0, y: 50}}
                whileInView={{opacity: 1, y: 0}}
                transition={{duration: 0.6}}
                viewport={{once: true}}
            >
                <h2>Lorem ipsum dolor sit amet consectetur</h2>
                <p>Lorem ipsum dolor sit amet consectetur adipiscing elit.</p>
            </motion.div>

            <div className={styles.filters}>
                {/* 4. Renderizar os botões dinamicamente */}
                <button
                    className={`${styles.filterBtn} ${selectedCategory === 'Todos' ? styles.active : ''}`}
                    onClick={() => setSelectedCategory('Todos')}
                >
                    Todos
                </button>
                {loading ? <p>Carregando filtros...</p> : categories.map((cat) => (
                    <button
                        key={cat.id}
                        className={`${styles.filterBtn} ${
                            selectedCategory === cat.name ? styles.active : ''
                        }`}
                        onClick={() => setSelectedCategory(cat.name)}
                    >
                        {cat.name}
                    </button>
                ))}
            </div>

            <ProjectsGrid projects={filteredProjects}/>

            <motion.div
                className={styles.buttonWrapper}
                initial={{opacity: 0, y: 50}}
                whileInView={{opacity: 1, y: 0}}
                transition={{delay: 0.2}}
                viewport={{once: true}}
            >
                <button className={styles.viewAllButton}>Lorem ipsum</button>
            </motion.div>
        </section>
    );
};

export default SustainableProjectsSection;