import '../css/AboutScreen.css'

const AboutScreen = () => {
  return (
    <div className='content'>
        <div className='title'>
          <Card/>
          <div className='pseudo-partial-border'>
            <img src="../../public/photo.jpg" alt="My photo" className='image'/>
          </div>
        </div>
    </div>
  )
}

const Card = () => {
  return (
    <div className='card'>
      <div>Hello, my name is Gonçalo Romba</div>
      <div>I'm a Software Engineer</div>
      <div>Fluent in Web and Mobile Development with over 10 years of experience</div>
    </div>
  )
}

export default AboutScreen