import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from './AuthComponent/AuthProvider'
import { logoutUser } from '../services/AuthService';
import jwtDecode from 'jwt-decode';

function Navbar({ isLoginPage, isRegisterPage }) {
    let auth = useAuth();
    let navigate = useNavigate();
    const [userRole, setUserRole] = useState("");
    const handleLogout = async () => {
        try {
            const response = await logoutUser();
            console.log(response);
            auth.logout();
            navigate("/", { replace: false })
        }
        catch (error) {
            alert(error)
        }
    }
    useEffect(() => {

        if (auth.isLoggedIn != "") {
            let token = window.localStorage.getItem("loggedIn");
            const decoded = jwtDecode(token);
            console.log(decoded.role);
            setUserRole(decoded.role);
        }
    }, [])

    return (
        <>
            <nav class="bg-gray-100 dark:bg-gray-900 fixed w-full z-20 top-0 left-0 border-b border-gray-200 dark:border-gray-600">
                <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                    <Link to="/">
                        <span class="flex items-center">
                            <img src="/images/IFS-logo.jpg" class="h-12 mr-3" alt="Flowbite Logo" />
                            <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Feedback.com</span>
                        </span>
                    </Link>

                    <div class="flex md:order-2">
                        <button type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 text-center mr-3 md:mr-0 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            {
                                auth.isLoggedIn == "" ?
                                    isLoginPage ?
                                        <Link to="/user-login">Log In</Link> :
                                        isRegisterPage ?
                                            <Link to="/register-user">Register Now</Link> :
                                            <Link to="/user-login"> Get started </Link>
                                    :
                                    <span onClick={() => handleLogout()}>
                                        Logout
                                    </span>
                            }

                        </button>
                        <button data-collapse-toggle="navbar-sticky" type="button" class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-sticky" aria-expanded="false">
                            <span class="sr-only">Open main menu</span>
                            <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 1h15M1 7h15M1 13h15" />
                            </svg>
                        </button>
                    </div>
                    {auth.isLoggedIn == "" ?
                        <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-sticky">
                            <ul class="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 md:mt-0 md:border-0 md:bg-gray-100 dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700  ">
                                <li>
                                    <Link to="/">
                                        <span class="block py-2 pl-3 pr-4 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 md:dark:text-blue-500" aria-current="page">Home</span>
                                    </Link>
                                </li>
                                <li>
                                    <Link to="/about-us">
                                        <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-900 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">About</span>
                                    </Link>
                                </li>
                                <li>
                                    <Link to="/services">
                                        <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Services</span>
                                    </Link>
                                </li>
                                <li>
                                    <Link to="/contact-us">
                                        <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Contact</span>
                                    </Link>
                                </li>
                                {isLoginPage ?
                                    <li>
                                        <Link to="/register-user">
                                            <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Register</span>
                                        </Link>
                                    </li>
                                    :
                                    <li>
                                        <Link to="/user-login">
                                            <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Log In</span>
                                        </Link>
                                    </li>
                                }

                            </ul>
                        </div>
                        :
                        <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-sticky">
                            <ul class="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 md:mt-0 md:border-0 md:bg-gray-100 dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700  ">
                                <li>
                                    <Link to="/dash-board">
                                        <span class="block py-2 pl-3 pr-4 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 md:dark:text-blue-500" aria-current="page">Dashboard</span>
                                    </Link>
                                </li>
                                {userRole != "ROLE_HR" &&
                                    <>
                                        <li>
                                            <Link to="/new-feedback">
                                                <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-900 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Add Feedback</span>
                                            </Link>
                                        </li>
                                        <li>
                                            <Link to="/upload-feedbacks">
                                                <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Upload Feedbacks</span>
                                            </Link>
                                        </li>
                                    </>}
                                <li>
                                    <Link to="/view-all-feedbacks">
                                        <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">View all Feedback</span>
                                    </Link>
                                </li>
                                <li>
                                    <Link to="/generate-report">
                                        <span class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Reports</span>
                                    </Link>
                                </li>
                            </ul>
                        </div>
                    }
                </div>
            </nav>
        </>
    )
}

export default Navbar