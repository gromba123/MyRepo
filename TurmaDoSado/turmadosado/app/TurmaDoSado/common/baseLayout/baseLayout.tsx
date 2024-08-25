import styles from "./baseLayout.module.css";

export default function BaseLayout(
    props: { children: React.ReactNode }
  ) {
  return (
    <div className={styles.base}>
      <div className={styles.sidebar}>

      </div>
      <div className={styles.content}>
        {props.children}
      </div>
    </div>
  );
}