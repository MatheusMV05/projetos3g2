import React from 'react';
import styles from './EventHeader.module.css';

interface EventHeaderProps {
  day: string;
  weekday: string;
  month: string;
  year: string;
  title: string;
  category?: string;
  location: string;
}

export const EventHeader: React.FC<EventHeaderProps> = ({
  day,
  weekday,
  month,
  year,
  title,
  category,
  location,
}) => {
  return (
    <div className={styles.eventCard}>
      <div className={styles.left}>
        <div className={styles.weekday}>{weekday}</div>
        <div className={styles.day}>{day}</div>
        <div className={styles.month}>{month} {year}</div>
      </div>
      <div className={styles.right}>
        <div>
          <span className={styles.title}>{title}</span>
          {category && <span className={styles.category}>{category}</span>}
        </div>
        <div className={styles.location}>{location}</div>
        <a href="#">Ver evento →</a>
      </div>
    </div>
  );
};
