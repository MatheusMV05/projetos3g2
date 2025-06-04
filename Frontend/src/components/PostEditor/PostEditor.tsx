import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useEditor, EditorContent } from '@tiptap/react';
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
	FaArrowLeft,
} from 'react-icons/fa';

export default function PostEditor() {
	const location = useLocation();
	const [title, setTitle] = useState(location.state?.titulo || '');
	const [showPreview, setShowPreview] = useState(false);
	const [draftSaved, setDraftSaved] = useState(false);
	const [imageUrl, setImageUrl] = useState('');
	const [lastSaved, setLastSaved] = useState({ title: '', content: '' });

	const navigate = useNavigate();

	const editor = useEditor({
		extensions: [
			StarterKit,
			Link,
			Highlight,
			TextAlign.configure({ types: ['heading', 'paragraph'] }),
			Image,
		],
		content: location.state?.conteudo || '',
	});

	const addImage = () => {
		if (imageUrl && editor) {
			editor.chain().focus().setImage({ src: imageUrl }).run();
			setImageUrl('');
		}
	};

	const editorContent = editor ? JSON.stringify(editor.getJSON()) : '';

	useEffect(() => {
		const handler = setTimeout(() => {
			if (!editor) return;
			const currentContent = editorContent;
			const shouldSave =
				title !== lastSaved.title || currentContent !== lastSaved.content;

			if (shouldSave) {
				console.log('Rascunho salvo:', { title, content: currentContent });
				setLastSaved({ title, content: currentContent });
				setDraftSaved(true);
				setTimeout(() => setDraftSaved(false), 2000);
			}
		}, 2000);
		return () => clearTimeout(handler);
	}, [title, editor, editorContent, lastSaved]);

	return (
		<motion.div
			className={styles.container}
			initial={{ opacity: 0, y: 30 }}
			animate={{ opacity: 1, y: 0 }}
			exit={{ opacity: 0, y: -30 }}
			transition={{ duration: 0.3 }}
		>
			{/* Header */}
			<div className={styles.header}>
				<button className={styles.back} onClick={() => navigate('/conteudo')}>
					<FaArrowLeft /> Voltar
				</button>

				<div className={styles.actions}>
					<button className={styles.save}>
						<FaSave /> Salvar
					</button>
					<button
						className={styles.preview}
						onClick={() => setShowPreview(true)}
					>
						<FaEye /> Visualizar
					</button>
					<button className={styles.publish}>
						<FaPaperPlane /> Publicar
					</button>
				</div>
			</div>

			<input
				className={styles.titleInput}
				placeholder="Adicionar título"
				value={title}
				onChange={(e) => setTitle(e.target.value)}
			/>

			{/* Toolbar */}
			<div className={styles.toolbar}>
				<button
					onClick={() => editor?.chain().focus().toggleBold().run()}
					title="Negrito"
				>
					<FaBold />
				</button>
				<button
					onClick={() => editor?.chain().focus().toggleItalic().run()}
					title="Itálico"
				>
					<FaItalic />
				</button>
				<button
					onClick={() => editor?.chain().focus().toggleBulletList().run()}
					title="Lista não ordenada"
				>
					<FaListUl />
				</button>
				<button
					onClick={() => editor?.chain().focus().toggleOrderedList().run()}
					title="Lista ordenada"
				>
					<FaListOl />
				</button>
				<button
					onClick={() => editor?.chain().focus().setTextAlign('left').run()}
					title="Alinhar à esquerda"
				>
					<FaAlignLeft />
				</button>
				<button
					onClick={() => editor?.chain().focus().setTextAlign('center').run()}
					title="Centralizar"
				>
					<FaAlignCenter />
				</button>
				<button
					onClick={() => editor?.chain().focus().setTextAlign('right').run()}
					title="Alinhar à direita"
				>
					<FaAlignRight />
				</button>
				<input
					type="text"
					placeholder="URL da imagem"
					value={imageUrl}
					onChange={(e) => setImageUrl(e.target.value)}
					className={styles.imageInput}
				/>
				<button onClick={addImage} title="Inserir imagem">
					<FaImage />
				</button>
			</div>

			<EditorContent editor={editor} className={styles.editor} />

			{draftSaved && (
				<div className={styles.draftSaved}>
					💾 Rascunho salvo automaticamente
				</div>
			)}

			{showPreview && (
				<div className={styles.previewModal}>
					<div className={styles.previewContent}>
						<h1>{title}</h1>
						<div
							dangerouslySetInnerHTML={{ __html: editor?.getHTML() || '' }}
						/>
						<button onClick={() => setShowPreview(false)}>Fechar</button>
					</div>
				</div>
			)}
		</motion.div>
	);
}
