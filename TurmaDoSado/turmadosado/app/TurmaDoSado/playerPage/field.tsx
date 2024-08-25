import { TableElement } from "../common/DomainTypes";
import styles from "./field.module.css";


export default function Field(
    props: { header: Array<String>,  elements: Array<TableElement>}
) {
    const headers = props.header.map( String => 
        // eslint-disable-next-line react/jsx-key
        <th>{String}</th>
    )
    // eslint-disable-next-line react/jsx-key
    const elements = <div className={styles.content}>
        <div className={styles.column}>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
        </div>
        <div className={styles.column}>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
        </div>
        <div className={styles.column}>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
        </div>
        <div className={styles.column}>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
            <div className={styles.square}>
                <div className={styles.dot}></div>
            </div>
        </div>
    </div>
    return (
        <div className={styles.tableWrapper}>
            <table className={styles.table}>
                <tr className={styles.header}>
                    {headers}
                </tr>
            </table>
            {elements}    
        </div>
    )
}