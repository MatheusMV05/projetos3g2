import React from 'react';
import styles from './BlogPostHeader.module.css';
import { FaFacebookF, FaLinkedinIn, FaTwitter } from 'react-icons/fa';
import { LuLink } from 'react-icons/lu';

const BlogPostHeader: React.FC = () => {
	return (
		<section className={styles.blogPostHeader}>
			<div className={styles.left}>
				<div className={styles.backLink}>Lorem ipsum &gt;</div>
				<h1 className={styles.title}>
					Lorem ipsum
					<br />
					dolor sit
					<br />
					amet
					<br />
					consectetur
				</h1>

				<p className={styles.details}>
					Lorem ipsum dolor sit amet consectetur.
				</p>

				<div className={styles.share}>
					<p className={styles.shareLabel}>Lorem ipsum dolor.</p>
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
				<div className={styles.imagePlaceholder}>
					<a
						href="/PlaceholderImage.svg"
						target="_blank"
						rel="noopener noreferrer"
					>
						<img src="/PlaceholderImage.svg" alt="PlaceHolder" />
					</a>
				</div>
			</div>
		</section>
	);
};

export default BlogPostHeader;
