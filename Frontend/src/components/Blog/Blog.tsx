import React from 'react';
import styles from './Blog.module.css';

const Blog: React.FC = () => {
	return (
		<section className={styles.blogSection}>
			{/* Destaque principal */}
			<div className={styles.mainHighlight}>
				<div className={styles.mainImage}>
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
				<div className={styles.mainContent}>
					<span className={styles.mainCategory}>Lorem ipsum</span>
					<div className={styles.mainTitle}>
						Lorem ipsum <br></br> dolor sit amet
					</div>
					<p className={styles.mainDescription}>
						Lorem ipsum dolor sit amet consectetur adipiscing elit.
					</p>
					<div className={styles.mainAuthor}>
						<span>
							👤 Lorem ipsum dolor sit amet consectetur adipiscing elit.
						</span>
					</div>
				</div>
			</div>
			<br></br>
			<br></br>
			{/* Filtros simulados */}
			<div className={styles.filters}>
				<button>Lorem ipsum</button>
				<a>
					<strong>Lorem ipsum</strong>
				</a>
				<a>
					<strong>Lorem ipsum</strong>
				</a>
				<a>
					<strong>Lorem ipsum</strong>
				</a>
				<a>
					<strong>Lorem ipsum</strong>
				</a>
			</div>
			<br></br>
			<br></br>
			{/* Cards */}
			<div className={styles.grid}>
				{[...Array(6)].map((_, i) => (
					<div className={styles.card} key={i}>
						<div className={styles.cardImage}>📷</div>
						<div className={styles.cardContent}>
							<div className={styles.cardCategory}>Lorem</div>
							<div className={styles.cardTitle}>
								Lorem ipsum dolor sit amet consectetur adipiscing elit.
							</div>
							<div className={styles.cardAuthor}>
								<span>
									👤 Lorem ipsum dolor sit amet consectetur adipiscing elit.
									<br></br>
								</span>
							</div>
						</div>
					</div>
				))}
			</div>
		</section>
	);
};

export default Blog;
