import { Link } from 'react-router-dom';
import '../css/header.css'

const Header = () => (
    <>
        <div className='header'>
            <ul>
                <li><Link to="/">Home</Link></li>
                <li><Link to="/news">Play</Link></li>
                <li><Link to="/contact">Puzzles</Link></li>
            </ul>
        </div>
    </>
)

export default Header;