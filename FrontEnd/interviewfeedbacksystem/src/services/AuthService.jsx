import axios from "axios";

const url1 = "http://localhost:8888/userAuth/register-user";
const url2 = "http://localhost:8888/userAuth/user-login";
const url3 = "http://localhost:8888/userAuth/forget-password";
const url4 = "http://localhost:8888/userAuth/logout"

const registerUser = async (data) => {

    const response = await axios.post(url1, data, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
        },
    });
    console.log(response.data);
    return response.data;
}

const userLogin = async (data) => {

    const response = await axios.post(url2, data, {
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
            "Accept": 'application/json',
        },
    });
    console.log(response.data);
    return response.data;
}

const forgetPassword = async (userEmailId) => {

    const response = await axios.post(`${url3}?userEmailId=${userEmailId}`, {
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
            "Accept": 'application/json',
        },
    });
    console.log(response.data);
    return response.data;
}

const logoutUser = async () => {
    console.log(window.localStorage.getItem("loggedIn"))
    const response = await axios.get(url4, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

export { registerUser, userLogin, forgetPassword, logoutUser }