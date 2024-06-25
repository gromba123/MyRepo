import './App.css'
import AboutScreen from './components/AboutScreen'
import Header from './components/Header'
import HomeScreen from './components/HomeScreen'
import Sidebar from './components/Sidebar'

function App() {

  return (
    <div className='container'>
      <Header/>
      <AboutScreen/>
    </div>
  )
}

export default App
