import Image from "next/image";
import BaseLayout from "../common/baseLayout/baseLayout";
import styles from "./playerPage.module.css"
import Table from "./table";
import Field from "./field";
import "/node_modules/flag-icons/css/flag-icons.min.css";

export default function PlayerPage() {
    return (
        <BaseLayout>
            <Content/>
        </BaseLayout>
    )
}

function Content() {
    const headersPosition = ["Position"]
    const headersCareer = ["Shortened Career Story", "Games", "Goals", "Assists"]
    const tableElementsCareer = [
        {elements: ["2023/24", "19", "1", "0"]},
        {elements: ["2022/23", "23", "1", "0"]},
        {elements: ["2021/22", "17", "1", "0"]},
        {elements: ["2020/21", "16", "1", "0"]}
    ]
    const headers = ["Matchday", "Wins", "Team", "Goals", "Assists", "TOTW"]
    const tableElements = [
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]},
        {elements: ["1", "5", "Marretas FC", "1", "0", "Sim"]}
    ]
    return(
        <div className={styles.base}>
            <div className={styles.headerRow}>
                <Image
                    src="/vini.jpg"
                    alt="Vini"
                    width={250}
                    height={300}
                />
                <div className={styles.personal}>
                    <div className={styles.name}>
                        Miguel
                        <div>Vinagre</div>
                        <div>#7</div>
                    </div>
                    <div className={styles.details}>
                        <Image
                            src="/logo.png"
                            alt="Logo"
                            width={150}
                            height={150}
                        />
                        <div className={styles.container}>
                            <div className={styles.marker}>Nascimento</div>
                            <div className={styles.border}>19.06.2000(23)</div>
                        </div>
                        <div className={styles.container}>
                            <div className={styles.marker}>País</div>
                            <div className={styles.border}>Portugal <span className="fi fi-pt"></span></div>
                        </div>
                    </div>
                </div>
            </div>
            <div className={styles.row}>
                <div className={styles.paddingRight}>
                    <Field
                        header={headersPosition}
                        elements={tableElementsCareer}
                    />
                </div>
                <div className={styles.paddingLeft}>
                    <Table
                        header={headersCareer}
                        elements={tableElementsCareer}
                    />
                </div>
            </div>
            <Table
                header={headers}
                elements={tableElements}
            />
        </div>
    )
}