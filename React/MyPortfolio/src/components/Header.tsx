import React from 'react'
import '../css/Header.css'

interface ItemProps {
    label: string;
    onClick: () => void;
}

const Header = () => {
  return (
    <div className='test'>
      <header className='header'>
          <div className='icon'>
              <Item label='About' onClick={() => {return}}/>
          </div>
          <div className='items'>
            <Item label='About' onClick={() => {return}}/>
            <Item label='Skills' onClick={() => {return}}/>
            <Item label='Portfolio' onClick={() => {return}}/>
            <Item label='Contact' onClick={() => {return}}/>
          </div>
          <div className='hide'>
              <Item label='About' onClick={() => {return}}/>
          </div>
    </header>
    </div>
  )
}

export default Header

const Item = ({label} : ItemProps) => {
    return (
      <div>{label}</div>
    )
}