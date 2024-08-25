import { TableElement } from "../common/DomainTypes";
import styles from "./table.module.css";


export default function Table(
    props: { header: Array<String>,  elements: Array<TableElement>}
) {
    const headers = props.header.map( String => 
        // eslint-disable-next-line react/jsx-key
        <th>{String}</th>
    )
    // eslint-disable-next-line react/jsx-key
    const elements = props.elements.map(elem => <tr>
        {elem.elements.map( String => 
        // eslint-disable-next-line react/jsx-key
        <td>{String}</td>
    )}
    </tr>)
    return (
        <div className={styles.tableWrapper}>
            <table className={styles.table}>
                <tr className={styles.header}>
                    {headers}
                </tr>
                {elements}
            </table>     
        </div>
    )
}