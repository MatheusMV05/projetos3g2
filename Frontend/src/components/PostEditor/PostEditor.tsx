import React, {useState, useEffect} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import {motion} from 'framer-motion';
import {useEditor, EditorContent} from '@tiptap/react';
import StarterKit from '@tiptap/starter-kit';
import Link from '@tiptap/extension-link';
import TextAlign from '@tiptap/extension-text-align';
import Highlight from '@tiptap/extension-highlight';
import Image from '@tiptap/extension-image';
import styles from './PostEditor.module.css';
import {
    FaBold,
    FaItalic,
    FaListUl,
    FaListOl,
    FaAlignLeft,
    FaAlignCenter,
    FaAlignRight,
    FaImage,
    FaSave,
    FaEye,
    FaPaperPlane,
    FaArrowLeft
} from 'react-icons/fa';

import {PaginaService} from '../../services/paginaService';
import {generateSlug} from '../../utils/slug';

export default function PostEditor() {
    const location = useLocation();
    const navigate = useNavigate();

    const pageId = location.state?.id;

    const [title, setTitle] = useState(location.state?.titulo || '');
    const [statusMessage, setStatusMessage] = useState({text: '', type: ''});
    const [showPreview, setShowPreview] = useState(false); // Estado do modal de preview
    const [imageUrl, setImageUrl] = useState('');

    const editor = useEditor({
        extensions: [StarterKit, Link, Highlight, TextAlign.configure({types: ['heading', 'paragraph']}), Image],
        content: location.state?.conteudo || '',
        autofocus: 'end',
    });

    const addImage = () => {
        if (imageUrl && editor) {
            editor.chain().focus().setImage({src: imageUrl}).run();
            setImageUrl('');
        }
    };

    const handleSave = async () => {
        if (!editor || !title) {
            setStatusMessage({text: 'O título é obrigatório.', type: 'error'});
            return;
        }
        setStatusMessage({text: 'Salvando...', type: 'info'});
        const payload = {
            titulo: title,
            slug: generateSlug(title),
            conteudo: editor.getHTML(),
        };
        try {
            if (pageId) {
                await PaginaService.atualizar(pageId, payload);
                setStatusMessage({text: 'Página atualizada com sucesso!', type: 'success'});
            } else {
                await PaginaService.criar(payload);
                setStatusMessage({text: 'Página criada com sucesso!', type: 'success'});
            }
            setTimeout(() => navigate('/conteudo'), 1500);
        } catch (err) {
            console.error("Erro ao salvar página:", err);
            setStatusMessage({text: 'Erro ao salvar. Tente novamente.', type: 'error'});
        }
    };

    if (!editor) {
        return <div className={styles.container}><p>Carregando editor...</p></div>;
    }

    return (
        <motion.div
            className={styles.container}
            initial={{opacity: 0, y: 30}}
            animate={{opacity: 1, y: 0}}
            exit={{opacity: 0, y: -30}}
            transition={{duration: 0.3}}
        >
            <div className={styles.header}>
                <button className={styles.back} onClick={() => navigate('/conteudo')}>
                    <FaArrowLeft/> Voltar
                </button>
                {statusMessage.text &&
                    <div className={`${styles.statusMessage} ${styles[statusMessage.type]}`}>{statusMessage.text}</div>}
                <div className={styles.actions}>
                    {/* Botão de salvar funcional */}
                    <button className={styles.save} onClick={handleSave}>
                        <FaSave/> Salvar
                    </button>
                    {/* Botão de visualizar funcional */}
                    <button className={styles.preview} onClick={() => setShowPreview(true)}>
                        <FaEye/> Visualizar
                    </button>
                    <button className={styles.publish}>
                        <FaPaperPlane/> Publicar
                    </button>
                </div>
            </div>

            <input
                className={styles.titleInput}
                placeholder="Adicionar título"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <div className={styles.toolbar}>
                <button onClick={() => editor.chain().focus().toggleBold().run()} title="Negrito"
                        className={editor.isActive('bold') ? styles.isActive : ''}>
                    <FaBold/>
                </button>
                <button onClick={() => editor.chain().focus().toggleItalic().run()} title="Itálico"
                        className={editor.isActive('italic') ? styles.isActive : ''}>
                    <FaItalic/>
                </button>
                {/* ... (outros botões da toolbar) ... */}
                <button onClick={() => editor.chain().focus().toggleBulletList().run()} title="Lista não ordenada"
                        className={editor.isActive('bulletList') ? styles.isActive : ''}>
                    <FaListUl/>
                </button>
                <button onClick={() => editor.chain().focus().toggleOrderedList().run()} title="Lista ordenada"
                        className={editor.isActive('orderedList') ? styles.isActive : ''}>
                    <FaListOl/>
                </button>
                <button onClick={() => editor.chain().focus().setTextAlign('left').run()} title="Alinhar à esquerda"
                        className={editor.isActive({textAlign: 'left'}) ? styles.isActive : ''}>
                    <FaAlignLeft/>
                </button>
                <button onClick={() => editor.chain().focus().setTextAlign('center').run()} title="Centralizar"
                        className={editor.isActive({textAlign: 'center'}) ? styles.isActive : ''}>
                    <FaAlignCenter/>
                </button>
                <button onClick={() => editor.chain().focus().setTextAlign('right').run()} title="Alinhar à direita"
                        className={editor.isActive({textAlign: 'right'}) ? styles.isActive : ''}>
                    <FaAlignRight/>
                </button>
                <input
                    type="text"
                    placeholder="URL da imagem"
                    value={imageUrl}
                    onChange={(e) => setImageUrl(e.target.value)}
                    className={styles.imageInput}
                />
                <button onClick={addImage} title="Inserir imagem">
                    <FaImage/>
                </button>
            </div>

            <EditorContent editor={editor} className={styles.editor}/>

            {/* ================================================================== */}
            {/* CÓDIGO DO MODAL DE PREVIEW RESTAURADO */}
            {/* ================================================================== */}
            {showPreview && (
                <div className={styles.previewModal}>
                    <div className={styles.previewContent}>
                        <h1>{title}</h1>
                        <div
                            dangerouslySetInnerHTML={{__html: editor.getHTML()}}
                        />
                        <button onClick={() => setShowPreview(false)}>Fechar</button>
                    </div>
                </div>
            )}
        </motion.div>
    );
}