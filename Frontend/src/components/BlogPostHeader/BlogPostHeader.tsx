import React from 'react';
import styles from './BlogPostHeader.module.css';
import { FaFacebookF, FaLinkedinIn, FaTwitter } from 'react-icons/fa';
import { LuLink } from 'react-icons/lu';

const BlogPostHeader: React.FC = () => {
	return (
		<section className={styles.blogPostHeader}>
			<div className={styles.left}>
				<div className={styles.backLink}>Eventos &gt;</div>
				<h1 className={styles.title}>
					Como Finanças
					<br />
					Sustentáveis
					<br />
					Transforma
					<br />
					Negócios
				</h1>

				<p className={styles.details}>11 Junho • 19h • Porto Digital</p>

				<div className={styles.share}>
					<p className={styles.shareLabel}>Compartilhe este post</p>
					<div className={styles.icons}>
						<a href="#">
							<LuLink />
						</a>
						<a href="#">
							<FaLinkedinIn />
						</a>
						<a href="#">
							<FaTwitter />
						</a>
						<a href="#">
							<FaFacebookF />
						</a>
					</div>
				</div>
			</div>

			<div className={styles.right}>
				<div className={styles.imagePlaceholder}>📷</div>
			</div>
		</section>
	);
};

export default BlogPostHeader;
