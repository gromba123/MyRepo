import '../css/HomeScreen.css'

const HomeScreen = () => {
  return (
    <div className='content'>
        <div className='title'>
            <Card/>
        </div>
    </div>
  )
}

export default HomeScreen

const Card = () => {
    return (
      <div className='card'>
        <div>Hello, <br/> My name is Gonçalo Romba<br/>I'm a Software Engineer</div>
      </div>
    )
  }