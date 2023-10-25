import axios from "axios"

const url1="http://localhost:8888/userManager/get-user-details"

const getUserDetails=async(userEmailId)=>{

    const response=await axios.get(`${url1}?userEmailId=${userEmailId}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization":"Bearer "+window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

export {getUserDetails}