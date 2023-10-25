import React from 'react'
import Navbar from './Navbar'
import { Card, CardContent, CardMedia, Typography } from '@mui/material'

function Dashboard() {
    const cards = [
        {
            image: "/images/total-candidate.png",
            title: "Total Candidates",
            count: 100

        },
        {
            image: "/images/hired-candidate.png",
            title: "Hired Candidates",
            count: 225

        },
        {
            image: "/images/unhired-condidate.png",
            title: "UnHired Candidates",
            count: 25

        },
        {
            image: "/images/pending-interview.png",
            title: "Pending Interviews",
            count: 2500

        },




    ]
    return (
        <>
            <Navbar isLoginPage={false} isRegisterPage={false} />
            <div className='mt-36 mx-24'>
                <div className='flex flex-row pb-20'>
                    {
                        cards.map((card,index) => <>

                            <Card sx={{ width: 250, height:"350px",display:"flex",flexDirection:"column",justifyContent:"space-between", }} className='mx-10'>
                                <CardMedia
                                    sx={{ backgroundSize: "contain", width:"100%",height: "200px",scale:index==0?'0.9':"0.7" }}
                                    image={card.image}
                                    title="total-candidate-image"
                                />
                                <CardContent >
                                    <Typography gutterBottom variant="h5" component="div" className='text-center'>
                                       {card.count}
                                    </Typography>
                                    <Typography gutterBottom variant="h6" component="div" className='text-center'>
                                       <p style={{fontSize:"17px"}}>{card.title}</p>
                                    </Typography>
                                </CardContent>
                            </Card>


                        </>)
                    }

                    {/* ,display:'flex',flexDirection:"column",alignItems:"center" */}

                </div>
            </div>
        </>
    )
}

export default Dashboard