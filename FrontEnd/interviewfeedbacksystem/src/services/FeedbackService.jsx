import axios from "axios"

const url1 = "http://localhost:8888/feedbackManager/handle-feedback-form";
const url2 = "http://localhost:8888/feedbackManager/get-all-feedback-files-by-interviewer";
const url3 = "http://localhost:8888/feedbackManager/handle-feedback-document";
const url4 = "http://localhost:8888/feedbackManager/download-document";
const url5 = "http://localhost:8888/feedbackManager/delete-feedback-file";
const url6 = "http://localhost:8888/feedbackManager/get-all-feedbacks-interviewer";
const url7 = "http://localhost:8888/feedbackManager/delete-feedback";
const url8 = "http://localhost:8888/feedbackManager/get-feedback-by-documentNo";
const url9 = "http://localhost:8888/feedbackManager/update-feedback-details";
const url10 = "http://localhost:8888/feedbackManager/get-hiring-data";
const url11 = "http://localhost:8888/feedbackManager/get-all-feedbacks-hr";

const handleFeedbackForm = async (feedbackFormData) => {

    const response = await axios.post(url1, feedbackFormData, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

const getAllFilesByInterviewerName = async (interviewerName) => {

    const response = await axios.get(`${url2}?interviewerName=${interviewerName}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

const handleFeedbackFile = async (file, interviewerName, interviewerDesignation) => {

    console.log(interviewerName + " " + interviewerDesignation)
    const formData = new FormData();
    formData.append('feedbackFile', file);
    const response = await axios.post(`${url3}?interviewerName=${interviewerName}&interviewerDesignation= ${interviewerDesignation}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

const handleFileDownload = async (fileId, fileName) => {
    console.log(fileId);
    const response = await axios.get(`${url4}?fileId=${fileId}`, {
        responseType: 'blob',
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    document.body.appendChild(a);
    a.click();

    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);

}

const handleDeleteFile = async (fileId) => {
    console.log(fileId);
    const response = await axios.delete(`${url5}?fileId=${fileId}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data)
    return response.data;
}

const fetchAllFeedbackForInterviewer = async (interviewerName) => {
    console.log(interviewerName);
    const response = await axios.get(`${url6}?interviewerName=${interviewerName}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

const fetchAllFeedbackForHr = async () => {

    const response = await axios.get(`${url11}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

const handleDeleteFeedbackById = async (documentNo) => {
    console.log(documentNo);
    const response = await axios.delete(`${url7}?documentNo=${documentNo}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data)
    return response.data;
}

const getFeedbackByDocumentNo = async (documentNo) => {
    console.log(documentNo);
    const response = await axios.get(`${url8}?documentNo=${documentNo}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

const handleFeedbackUpdate = async (data) => {
    console.log(data);
    const response = await axios.put(`${url9}`, data, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

const getHiringData = async (data) => {
    console.log(data);
    const response = await axios.get(`${url10}?to=${data.to}&from=${data.from}`, {
        headers: {
            "Content-Type": "application/json",
            "Accept": 'application/json',
            "Authorization": "Bearer " + window.localStorage.getItem("loggedIn")
        },
    })
    console.log(response.data);
    return response.data;
}

export { handleFeedbackForm, getAllFilesByInterviewerName, handleFeedbackFile, handleFileDownload, handleDeleteFile, fetchAllFeedbackForInterviewer, handleDeleteFeedbackById, getFeedbackByDocumentNo, handleFeedbackUpdate, getHiringData, fetchAllFeedbackForHr }