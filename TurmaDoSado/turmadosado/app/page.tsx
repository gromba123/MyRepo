import Image from "next/image";
import styles from "./page.module.css";
import PlayerPage from "./TurmaDoSado/playerPage/playerPage";

export default function Home() {
  return (
    <main className={styles.main}>
      <PlayerPage/>
    </main>
  );
}