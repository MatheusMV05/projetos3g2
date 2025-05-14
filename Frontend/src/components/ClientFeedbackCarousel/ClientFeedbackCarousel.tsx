import React from 'react';
import Slider from 'react-slick';
import styles from './ClientFeedbackCarousel.module.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

interface Feedback {
	id: number;
	name: string;
	title: string;
	text: string;
}

const feedbacks: Feedback[] = [
	{
		id: 1,
		name: 'Lorem Ipsum',
		title: 'Dolor Sit, Amet Consectetur',
		text: '“Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean sit amet metus a nulla suscipit bibendum.”',
	},
	{
		id: 2,
		name: 'Sed Vitae',
		title: 'Vestibulum Non, Ligula In',
		text: '“Sed vitae nunc nec magna finibus pretium. Quisque convallis sapien ut mauris rhoncus, sed imperdiet nulla sodales.”',
	},
	{
		id: 3,
		name: 'Mauris Finibus',
		title: 'Nulla Facilisi',
		text: '“Mauris finibus justo vel nulla varius, non hendrerit justo scelerisque. In ut turpis felis.”',
	},
	{
		id: 4,
		name: 'Aliquam Erat',
		title: 'Donec Ultricies',
		text: '“Aliquam erat volutpat. Duis et sem sed purus sagittis ullamcorper. Donec ultricies lorem nec dignissim lacinia.”',
	},
];

const Arrow = ({
	className,
	onClick,
}: {
	className?: string;
	onClick?: () => void;
}) => {
	const isNext = className?.includes('slick-next');
	const isPrev = className?.includes('slick-prev');

	return (
		<div
			className={`${styles.arrow} ${isNext ? styles.next : ''} ${
				isPrev ? styles.prev : ''
			}`}
			onClick={onClick}
		>
			<span className={styles.arrowIcon}>{isNext ? '→' : '←'}</span>
		</div>
	);
};

const ClientFeedbackCarousel: React.FC = () => {
	const settings = {
		dots: true,
		infinite: true,
		slidesToShow: 3,
		slidesToScroll: 1,
		speed: 500,
		autoplay: true,
		autoplaySpeed: 5000,
		pauseOnHover: true,
		arrows: true,
		nextArrow: <Arrow className={`${styles.arrow} ${styles.next}`} />,
		prevArrow: <Arrow className={`${styles.arrow} ${styles.prev}`} />,
		responsive: [
			{
				breakpoint: 1024,
				settings: {
					slidesToShow: 2,
					slidesToScroll: 2,
				},
			},
			{
				breakpoint: 768,
				settings: {
					slidesToShow: 1,
					slidesToScroll: 1,
				},
			},
		],
	};

	return (
		<section className={styles.carouselSection}>
			<h2 className={styles.title}>Depoimentos de Clientes</h2>
			<p className={styles.subtitle}>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur at
				felis vel sem tincidunt suscipit.
			</p>
			<div className={styles.sliderContainer}>
				<Slider {...settings} className={styles.slider}>
					{feedbacks.map((feedback) => (
						<div key={feedback.id} className={styles.card}>
							<div className={styles.stars}>★★★★★</div>
							<p className={styles.text}>{feedback.text}</p>
							<div className={styles.profile}>
								<div className={styles.avatar}></div>
								<div>
									<p className={styles.name}>{feedback.name}</p>
									<p className={styles.role}>{feedback.title}</p>
								</div>
							</div>
						</div>
					))}
				</Slider>
			</div>
		</section>
	);
};

export default ClientFeedbackCarousel;
