import React from 'react';
import styles from './JoinUs.module.css';

const JoinUs: React.FC = () => {
    return (
        <section className={styles.joinUsSection}>
            <div className={styles.container}>
                <div className={styles.left}>
                    <h2>Quer fazer parte?</h2>
                </div>
                <div className={styles.right}>
                    <div className={styles.contentBox}>
                        <p>
                            Venha fazer parte da transformação rumo a uma economia mais verde.
                            Entre em contato conosco para saber como você pode contribuir!
                        </p>
                        <button className={styles.cta}>Clique aqui</button>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default JoinUs;
