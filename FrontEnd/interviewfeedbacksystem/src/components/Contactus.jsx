import React from 'react'
import Navbar from './Navbar'
import Footer from './Footer'
import { Avatar, Card, CardBody, CardFooter, CardHeader, Typography } from '@material-tailwind/react'

function Contactus() {
  return (
    <>
        <Navbar isLoginPage={false} isRegisterPage={false}/>
        <div className='mt-36 mx-24'>

            <span className='flex justify-center text-5xl'>Get In Touch</span>

            <Typography>
                <p className='text-center my-20 font-semibold text-xl'>
                Thank you for your interest in Feedback.com! We're here to assist you with any inquiries or concerns you may have. Below, you'll find various ways to get in touch with us:
                </p>
            </Typography>
<div className='flex flex-row space-x-8 my-10'>
    <Card className="mt-6 w-72 h-96 flex-1 hover:translate-y-6 duration-100">
      <CardHeader className='w-64 mx-auto'>
        <img
          src="/images/customer-support.png"
          alt="custom-support-image"
          />
      </CardHeader>
      <CardBody>
        <Typography variant="h5" color="blue-gray" className="mb-2 text-center">
          Customer Support
        </Typography>
        <Typography className='hover:text-blue-500'>
            <a href='https://mail.google.com/mail/?view=cm&fs=1&to=itteamifs@gmail.com'>
            <Avatar src="/images/mail-image.png" alt="mail-avatar" size='sm' /> &nbsp;&nbsp;itteamifs@gmail.com
            </a>
        </Typography>
      </CardBody>
    </Card>

    <Card className="mt-6 w-72 flex-1 hover:translate-y-6 duration-100">
      <CardHeader className='w-64 h-64 mx-auto '>
        <img
        className='object-cover object-center h-full w-full'
          src="/images/social-media.jpg"
          alt="social-media-image"
          />
      </CardHeader>
      <CardBody>
        <Typography variant="h5" color="blue-gray" className="mb-2 text-center">
          Social Media
        </Typography>
        <Typography className='hover:text-blue-500'>
            <a href='https://linkedIn.com/itteamifs-5632762'>
            <Avatar src="/images/linkedin.png" alt="linkedin-avatar" size='sm' /> &nbsp;&nbsp;linkedIn.com/itteamifs-5632762
            </a>
        </Typography>
      </CardBody>
    </Card>
    <Card className="mt-6 w-72 flex-1 hover:translate-y-6 duration-100">
      <CardHeader className='w-64 mx-auto h-64'>
        <img
        className='object-cover object-center h-full w-full'
          src="/images/onsite-visit.jpg"
          alt="onsite-visit-image"
          />
      </CardHeader>
      <CardBody>
        <Typography variant="h5" color="blue-gray" className="mb-2 text-center">
          Onsite Visit
        </Typography>
        <Typography className='hover:text-blue-500'>
            <a href='http://maps.google.com/?q=eInfochips Ltd Ognaj'>
            <Avatar src="/images/office-image.png" alt="office-avatar" size='sm' /> &nbsp;&nbsp;Ratana office,eInfochips Ognaj, Near Sola, Ahmedabad, Gujarat 380060
            </a>
        </Typography>
      </CardBody>
    </Card>
          </div>
        </div>
        <Footer/>
    </>
  )
}

export default Contactus