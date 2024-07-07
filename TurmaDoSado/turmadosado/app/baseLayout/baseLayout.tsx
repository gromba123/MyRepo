import styles from "./baseLayout.module.css";
import Header from "../header/header"

export default function BaseLayout(
    props: { children: React.ReactNode }
  ) {
  return (
    <div className={styles.base}>
      <Header/>
      <div className={styles.grid}>
        <div className={styles.background}/>
        <div className={styles.content}>
          {props.children}
        </div>
      </div>
    </div>
  );
}