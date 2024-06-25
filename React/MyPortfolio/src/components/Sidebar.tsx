import '../css/Sidebar.css'
import '../css/all.min.css'

interface ItemProps {
    label: string;
    onClick: () => void;
}

interface IconProps {
    url: string;
    src: string;
}

const Sidebar = () => {
  return (
    <aside className='aside'>
        <div className='items'>
          <Item label='Home' onClick={() => {return}}/>
          <Item label='About Me' onClick={() => {return}}/>
          <Item label='Skills' onClick={() => {return}}/>
          <Item label='Portfolio' onClick={() => {return}}/>
          <Item label='Contact' onClick={() => {return}}/>
        </div>
        <div className='icons'>
            <Icon 
                url="https://www.linkedin.com/in/goncaloromba/"
                src="../assets/github.svg"
            />
            <Icon 
                url="https://github.com/gromba123"
                src="../assets/linkedin.svg"
            />
        </div>
    </aside>
  )
}

const Item = ({label} : ItemProps) => {
    return (
      <div>{label}</div>
    )
}

const Icon = ({url, src} : IconProps) => {
  return (
    <div>
        <a href={url}>
            <img src={src}/>
        </a>
    </div>
  )
}

export default Sidebar