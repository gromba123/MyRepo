import Image from "next/image";
import BaseLayout from "../baseLayout/baseLayout";
import styles from "./playerPage.module.css"

export default function PlayerPage() {
    return (
        <BaseLayout>
            <Content/>
        </BaseLayout>
    )
}

function Content() {
    return(
        <div className={styles.base}>
            <div className={styles.player}>
                <Image
                    className={styles.frame}
                    src="/turma_do_sado_negative.png"
                    alt="Turma do Sado Logo"
                    width={250}
                    height={250}
                    priority
                />
                <div className={styles.frame}>
                    <p>Altura: 1.83 m</p>
                    <p>Nascimento: 11/03/2001</p>
                    <p>Naturalidade: Setúbal, Portugal</p>
                    <p>Posição: Ponta de Lança</p>
                    <p>Nacionalidade: Portugal</p>
                </div>
            </div>
            <div></div>
        </div>
    )
}