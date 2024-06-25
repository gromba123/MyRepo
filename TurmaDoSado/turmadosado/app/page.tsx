import Image from "next/image";
import styles from "./page.module.css";
import Header from "./header/header"
import BaseLayout from "./baseLayout/baseLayout";
import PlayerPage from "./playerPage/playerPage";

export default function Home() {
  return (
    <main className={styles.main}>
      <PlayerPage/>
    </main>
  );
}