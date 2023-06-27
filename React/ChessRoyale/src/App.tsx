import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import Header from './components/Header'


function App() {

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path='/home' element={<Header/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
