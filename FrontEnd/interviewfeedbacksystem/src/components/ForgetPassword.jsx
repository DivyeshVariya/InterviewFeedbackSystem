import { Button, Card, CardBody, CardHeader, Input, Typography } from '@material-tailwind/react'
import React, { useState } from 'react'
import { forgetPassword } from '../services/AuthService';
import { Link, useNavigate } from 'react-router-dom';

function ForgetPassword() {
    let navigate = useNavigate();
    const [userEmailId, setUserEmailId] = useState("")
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(userEmailId);
            const response = await forgetPassword(userEmailId);
            console.log(response);
            if (response.responseCode == 200) {
                navigate("/");
            }
            alert(response.message);
        }
        catch (error) {
            alert(error)
        }
    }
    return (
        <>
            <Card className="mt-6 w-96 mx-auto mt-[80px] ring-2 ring-blue-500">
                <CardHeader color="blue-gray" className="relative h-56 shadow-lg shadow-blue-500/50">
                    <img
                        className='h-full w-full object-cover object-center'
                        src="/images/reset-password.jpg"
                        alt="reset-password-image"
                    />
                </CardHeader>
                <CardBody>
                    <form onSubmit={handleSubmit}>
                        <div className="relative flex w-full max-w-[24rem] my-5">
                            <Input
                                type="email"
                                label="Email Address"
                                onChange={(e) => { setUserEmailId(e.target.value) }}
                                className="pr-20"
                            />
                            <Button
                                size="sm"
                                type='submit'
                                color={userEmailId ? "green" : "blue-gray"}
                                disabled={!userEmailId}
                                className="!absolute right-1 top-1 rounded"
                            >
                                Submit
                            </Button>
                        </div>
                    </form>
                    <Typography className='font-semibold text-center mt-10'>
                        Enter your registerd email address. System automatically reset your password and share with via registerd email address.
                    </Typography>
                </CardBody>
                {/* <CardFooter className="pt-0 flex justify-center">
        <Button type='submit'>Submit</Button>
      </CardFooter> */}
            </Card>
            <div className='mt-5 mx-auto text-center'>
                <Link to="/">
                    <span className='text-red-500 font-bold'>Back to Home ?</span>
                </Link>
            </div>
        </>
    )
}

export default ForgetPassword