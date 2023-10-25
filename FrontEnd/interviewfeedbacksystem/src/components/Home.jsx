import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowRight, faCoffee } from '@fortawesome/free-solid-svg-icons'
import { Button } from '@material-tailwind/react'
import React from 'react'
import TypewriterComponent from 'typewriter-effect';
import '../App.css'
import Navbar from './Navbar';
import Footer from './Footer';
import { Link } from 'react-router-dom';
function Home() {
    return (
        <>
        <Navbar/>
            <div class="grid grid-cols-2 gap-4 content-center mx-20 my-[120px] overflow-y-scroll" >
                <div class="text-ellipsis overflow-hidden mt-10 mr-[35px] text-center">
                    <h1 class="text-7xl font-semibold ">
                    <TypewriterComponent
 
 onInit={(typewriter) => {
     typewriter
         .typeString("Welcome You To")
         .pauseFor(1000)
         .deleteAll()
         .typeString("Feedback.com")
         .start();
 }
 }
 options={{loop:true}}
/>
                        </h1>
                    <p class="text-gray-500	 text-base my-10 mx-10 mr-10 justify-self-auto">
                        It is very powerful interview feedback system that handles post interview data. Use at bigger organization for managing hiring records and get meaningful insights.
                    </p>
                    <Button color='blue' className='hover:scale-125 hover:bg-blue-900 transition ease-in-out  duration-200' >
                        <Link to="/user-login">
                         <span className='mr-2 hover:scale-150'>
                        Get's Started
                    </span>
                        </Link>
                        <FontAwesomeIcon icon={faArrowRight} size='xl' />
                    </Button>
                </div>
                <img src='/images/home-img.jpg' alt="Interview-home-image" variant='rounded' class="h-96 w-full rounded-lg object-cover object-center shadow-xl shadow-blue-gray-900/50 hover:rotate-6 transition ease-in-out  duration-200 hover:scale-75" />
            </div>
            <Footer/>
        </>

    )
}

export default Home