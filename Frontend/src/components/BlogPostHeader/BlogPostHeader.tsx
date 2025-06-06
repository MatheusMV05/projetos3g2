import React from 'react';
import styles from './BlogPostHeader.module.css';
import {FaFacebookF, FaLinkedinIn, FaTwitter} from 'react-icons/fa';
import {LuLink} from 'react-icons/lu';
import {LuCalendarDays} from 'react-icons/lu';

const BlogPostHeader: React.FC = () => {
    return (
        <section className={styles.blogPostHeader}>
            <div className={styles.left}>
                <div className={styles.backLink}>Eventos &gt;</div>

                <h1 className={styles.title}>
                    Lorem
                    <br/>
                    Ipsum
                    <br/>
                    Dolor
                    <br/>
                    Amet
                </h1>

                <div className={styles.details}>
                    <LuCalendarDays className={styles.icon}/>
                    <span>11 Junho</span>
                    <span className={styles.dot}>•</span>
                    <span>19h</span>
                    <span className={styles.dot}>•</span>
                    <span>Porto Digital</span>
                </div>

                <div className={styles.share}>
                    <p className={styles.shareLabel}>Compartilhe este post</p>
                    <div className={styles.icons}>
                        <a href="#"><LuLink/></a>
                        <a href="#"><FaLinkedinIn/></a>
                        <a href="#"><FaTwitter/></a>
                        <a href="#"><FaFacebookF/></a>
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
                        <img src="/PlaceholderImage.svg" alt="Placeholder"/>
                    </a>
                </div>
            </div>
        </section>
    );
};

export default BlogPostHeader;
