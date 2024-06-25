import Image from "next/image";
import styles from "./header.module.css";

export default function Header() {
    return(
        <div className={styles.base}>
            <div className={styles.background}>
                <p>Galeria</p>
            </div>
            <div className={styles.bar}>
                <p>Jornadas</p>
                <p>Jogadores</p>
                <Image
                    className={styles.logo}
                    src="/turma_do_sado_negative.png"
                    alt="Turma do Sado Logo"
                    width={180}
                    height={180}
                    priority
                />
                <p>Clube</p>
                <p>Galeria</p>
            </div>
        </div>
    )
}