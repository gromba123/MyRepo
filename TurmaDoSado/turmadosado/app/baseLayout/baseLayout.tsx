import styles from "./baseLayout.module.css";
import Header from "../header/header"

export default function BaseLayout(
    props: { children: React.ReactNode }
  ) {
  return (
    <div className={styles.base}>
      <Header/>
      {props.children}
    </div>
  );
}