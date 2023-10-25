import React, { useState } from 'react'
import {
    Card,
    Input,
    Checkbox,
    Button,
    Typography,
    Select,
    Option
} from "@material-tailwind/react";
import { registerUser } from '../services/AuthService';
import SelectOption from '@material-tailwind/react/components/Select/SelectOption';
import { Link, useNavigate } from 'react-router-dom';
import Navbar from './Navbar';

function Register() {
    let navigate = useNavigate();
    const [userRole, setUserRole] = useState("");
    const [userDepartment, setUserDepartment] = useState("");
    const [userDesignation, setUserDesignation] = useState("");
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (e.target[6].value === e.target[7].value) {
                const userData = {
                    userRole: userRole,
                    userEmailId: e.target[1].value,
                    userFullName: e.target[2].value,
                    userContactNo: e.target[3].value,
                    userDesignation: userDesignation,
                    userDepartment: userDepartment,
                    userPassword: e.target[6].value
                }
                console.log(userData);
                const response = await registerUser(userData);
                console.log(response)
                if (response.responseCode == 200) {
                    navigate("/user-login");
                }
                alert(response.message);
            }
            else {
                throw Error("Password and Confirmed Password both must be same !!!")
            }
        }
        catch (error) {
            alert(error);
            e.target[1].value = "";
            e.target[2].value = "";
            e.target[3].value = "";
            e.target[6].value = "";
            e.target[7].value = "";
            setUserDepartment("");
            setUserDesignation("");
            setUserRole("");
        }
    }
    return (
        <>
            <Navbar isLoginPage={false} isRegisterPage={true} />
            <div className='w-1/2 mx-auto mt-24  mb-20' >

                <Card color="transparent" shadow={true} className='border p-6 ring-2 ring-yellow-300'>
                    <Typography variant="h4" color="blue-gray" className="text-center">
                        Sign Up
                    </Typography>
                    <Typography color="gray" className="mt-1 font-normal text-center">
                        Enter your details to register.
                    </Typography>
                    <div className='mx-auto'>

                        <form onSubmit={handleSubmit} className="mt-8 mb-2 w-1/2 max-w-screen-lg sm:w-96">
                            <div className="mb-4 flex flex-col justify-center gap-6">
                                <Select label='Select Role' name="userRole" onChange={(e) => { setUserRole(e) }} value={userRole}>
                                    <Option value="INTERVIEWER">INTERVIEWER</Option>
                                    <Option value="HR">HR</Option>
                                </Select>
                                <Input size="lg" type='email' name='userEmailId' label="Email Id" required />
                                <Input size="lg" type='text' name="userFullName" label="Full Name" required />
                                <Input size='lg' type='text' name="userContactNo" label='Contact Number' required />
                                {userRole === "HR" ?
                                    <Select label='Select Designation' name="userDesignation" onChange={(e) => { setUserDesignation(e) }} value={userDesignation} >
                                        <Option value="EXECUTIVE">EXECUTIVE</Option>
                                        <Option value="ASSISTANT MANAGER">ASSISTANT MANAGER</Option>
                                        <Option value="SENIOR MANAGER">SENIOR MANAGER</Option>
                                        <Option value="MANAGER">MANAGER</Option>
                                    </Select>
                                    :
                                    <Select label='Select Designation' name="userDesignation" onChange={(e) => { setUserDesignation(e) }} value={userDesignation}>
                                        <Option value="ENGINEER">ENGINEER</Option>
                                        <Option value="SENIOR ENGINEER">SENIOR ENGINEER</Option>
                                        <Option value="TECH LEAD">TECH LEAD</Option>
                                        <Option value="TECHNICAL MANAGER">TECHNICAL MANAGER</Option>
                                    </Select>
                                }
                                {userRole === 'HR' ?
                                    <Select label='Select Department' name="userDepartment" onChange={(e) => { setUserDepartment(e) }} value={userDepartment} >
                                        <Option value="HR GENERAL">HR GENERAL</Option>
                                    </Select>
                                    :
                                    <Select label='Select Department' name="userDepartment" onChange={(e) => { setUserDepartment(e) }} value={userDepartment}>
                                        <Option value="PES">PES</Option>
                                        <Option value="ASIC">ASIC</Option>
                                        <Option value="HARDWARE">HARDWARE</Option>
                                    </Select>

                                }
                                <Input type="password" size="lg" label="Password" name='userPassword' required />
                                <Input type="password" size="lg" label="Confirmed Password" name='confirmedPassword' required />
                            </div>
                            <Checkbox
                                label={
                                    <Typography
                                        variant="small"
                                        color="gray"
                                        className="flex items-center font-normal"
                                    >
                                        I agree the
                                        <a
                                            href="#"
                                            className="font-medium transition-colors hover:text-gray-900 text-blue-300"
                                        >
                                            &nbsp;Terms and Conditions
                                        </a>
                                    </Typography>
                                }
                                containerProps={{ className: "-ml-2.5" }}
                                required
                            />
                            <Button type='submit' className="mt-6" fullWidth>
                                Register
                            </Button>
                            <Typography color="gray" className="mt-4 text-center font-normal">
                                Already have an account?{" "}
                                <Link to="/user-login">
                                    <span className="font-medium text-blue-900 font-semibold">
                                        Log In
                                    </span>
                                </Link>
                            </Typography>
                        </form>
                    </div>
                </Card>
            </div>
        </>
    )
}

export default Register