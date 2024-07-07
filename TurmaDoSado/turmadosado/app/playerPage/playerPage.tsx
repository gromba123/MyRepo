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
            <div className={styles.name}>Gonçalo Romba</div>
            <div className={styles.player}>
                <div className={styles.gridImage1}>
                    <div className={styles.gridImage2}></div>
                    <Image
                        className={styles.gridImage3}
                        src="/vini.jpg"
                        alt="Vini"
                        width={250}
                        height={300}
                        priority
                    />
                </div>
                <div className={styles.spacer}/>
                <div className={styles.gridText1}>
                    <div className={styles.gridText2}></div>
                    <div className={styles.gridText3}>
                        <p>Altura: 1.83 m</p>
                        <p>Nascimento: 11/03/2001</p>
                        <p>Naturalidade: Setúbal, Portugal</p>
                        <p>Posição: Ponta de Lança</p>
                        <p>Nacionalidade: Portugal</p>
                </div>
                </div>
            </div>
            <div></div>
        </div>
    )
}