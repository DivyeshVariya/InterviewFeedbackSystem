import { Accordion, AccordionBody, AccordionHeader, Avatar, Button, Card, CardBody, CardFooter, CardHeader, List, ListItem, ListItemPrefix, Typography } from '@material-tailwind/react';
import React from 'react';
import Footer from './Footer';
import Navbar from './Navbar';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChartLine, faDatabase, faFileCircleCheck } from '@fortawesome/free-solid-svg-icons';

function Aboutus() {
    const [open, setOpen] = React.useState(1);
 
  const handleOpen = (value) => setOpen(open === value ? 0 : value);
 
  return (
    <>
        <Navbar isLoginPage={false} isRegisterPage={false}/>
        <div className='mt-36 mx-24 backdrop-blur-3xl bg-white/30'>
            <div className='flex justify-center font-bold text-5xl'>
            <span>About</span>
            </div>
        <Accordion open={open === 1}>
        <AccordionHeader onClick={() => handleOpen(1)}>What is Feedback.com ?</AccordionHeader>
        <AccordionBody>
          <p className='text-base text-center'>
          Are you tired of sifting through stacks of interview feedback forms and struggling to make data-driven hiring decisions? Look no further! Feedback.com is your all-in-one solution for efficiently managing post-interview data and gaining valuable insights into your hiring process.
            At Feedback.com, we understand the importance of making informed hiring decisions. Our platform is designed to simplify the process of collecting, organizing, and analyzing interview feedback, empowering your team to make the best hiring choices. Whether you're a recruiter, HR professional, or a hiring manager, Feedback.com has you covered.
          </p>
        </AccordionBody>
      </Accordion>
      <h3 className='text-3xl font-semibold flex justify-center m-10'>Key Features</h3>
      <Card className="w-100 mx-auto shadow-lg shadow-indigo-400 border mb-10">
      <List>
        <ListItem>
          <ListItemPrefix>
          <FontAwesomeIcon icon={faDatabase} beat style={{color: "#dfc311",height:"30px"}} />
          </ListItemPrefix>
          <div>
            <Typography variant="h6" color="blue-gray" className='hover:text-2xl'>
            Effortless Data Collection
            </Typography>
            <Typography variant="small" color="gray" className="font-normal">
            Say goodbye to paper forms and scattered spreadsheets. Our user-friendly interface makes it easy to collect feedback from interviewers and candidates.
            </Typography>
          </div>
        </ListItem>
        <ListItem>
          <ListItemPrefix>
          <FontAwesomeIcon icon={faChartLine} fade size="2xl" style={{color:"#2c2a79",}}/>
          </ListItemPrefix>
          <div>
            <Typography variant="h6" color="blue-gray" className='hover:text-2xl'>
            Real-time Analytics
            </Typography>
            <Typography variant="small" color="gray" className="font-normal">
            Gain instant access to comprehensive data analytics. Identify patterns, strengths, and areas for improvement in your interview process.
            </Typography>
          </div>
        </ListItem>
        <ListItem>
          <ListItemPrefix>
          <FontAwesomeIcon icon={faFileCircleCheck} shake size="2xl" style={{color: "#46e83b",}} />
          </ListItemPrefix>
          <div>
            <Typography variant="h6" color="blue-gray" className='hover:text-2xl'>
            Customizable Reports
            </Typography>
            <Typography variant="small" color="gray" className="font-normal">
            Create tailored reports and summaries that suit your organization's unique needs. Share insights seamlessly with your team.
            </Typography>
          </div>
        </ListItem>
      </List>
    </Card>

    <h3 className='text-3xl font-semibold flex justify-center m-20'>Developer</h3>

    <Card className="mt-6 w-96 mx-auto border-dotted border-4 mb-10">
      <CardHeader color="blue-gray" className="h-72 object-cover object-center">
        <img
          src="images/new pp.jpg"
          alt="developer-image"
        />
      </CardHeader>
      <CardBody>
        <Typography variant="h5" color="blue-gray" className="mb-2 text-center">
          Divyesh Variya - Java Full Stack Intern
        </Typography>
      </CardBody>
      <CardFooter className="pt-0 mx-auto">
        <Button variant="outlined" color='light-blue' className="rounded-full">contact on : variyad81@gmail.com</Button>
      </CardFooter>
    </Card>
        </div>
        <Footer/>
    </>
      )
}

export default Aboutus