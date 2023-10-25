import React, { useEffect, useState } from 'react'
import Navbar from './Navbar'
import { Button, Input, Spinner, Typography } from '@material-tailwind/react'
import { getAllFilesByInterviewerName, handleDeleteFile, handleFeedbackFile, handleFileDownload } from '../services/FeedbackService'
import { getUserDetails } from '../services/UserService'

function UploadFeedback() {

    const TABLE_HEAD = ['#', 'File name', 'Uploaded by', 'Actions'];
    const [tableData, setTableData] = useState([]);
    const [file, setFile] = useState("");
    const [userDetails, setUserDetails] = useState({});
    const [isLoading, setIsLoading] = useState(false);

    const listOfFilesData = async () => {
        setIsLoading(true);
        const userDetail = await getUserDetails(window.localStorage.getItem("userEmailId"));
        console.log("from use-effect")
        console.log(userDetail)
        setUserDetails(userDetail);
        setTimeout(async () => {
            const response = await getAllFilesByInterviewerName(userDetail.userFullName);
            console.log(response)
            if (response.responseCode == 200) {
                console.log(response)
                setTableData(response.listOfFiles);
            }
            setIsLoading(false);
        }, 1000);
    }
    useEffect(() => {

        listOfFilesData();
    }, [])

    const submitFile = async (e) => {
        e.preventDefault();
        try {
            console.log(file);
            console.log("from submit file-----------------")
            console.log(userDetails)
            const response = await handleFeedbackFile(file, userDetails.userFullName, userDetails.userDesignation);
            console.log(response);
            if (response.responseCode === 200) {
                alert(response.responseMessage);
                setFile("");
                e.target[0].value = "";
                listOfFilesData();
            }
        }
        catch (error) {
            alert(error);
            e.target[0].value = "";
        }
    }

    const downloadFile = async (fileId, fileName) => {
        try {
            console.log(fileId);
            console.log("from download file----------------");
            await handleFileDownload(fileId, fileName);

        }
        catch (error) {
            alert(error);
        }
    }

    const deleteFile = async (fileId) => {
        setIsLoading(true);
        try {
            console.log(fileId);
            console.log("from delete File-------------------");
            const response = await handleDeleteFile(fileId);
            console.log(response);
            if (response.responseCode === 200) {
                alert(response.responseMessage);
                listOfFilesData();
            }
            else {
                alert(response.responseMessage);
            }
            setIsLoading(false);
        }
        catch (error) {
            alert(error);
            setIsLoading(false);
        }

    }

    return (
        <>
            <Navbar isLoginPage={false} isRegisterPage={false} />
            <div className='mt-36 mx-24'>
                <div className='text-center'>
                    <Typography className='text-2xl'>Upload Feedbacks</Typography>
                    <p className='my-5'>
                        Effortless Feedback Upload in Excel Format. Experience the simplicity of managing feedback with Feedback.com. Our platform empowers you to effortlessly upload feedback files in Excel format, making data entry a thing of the past.
                    </p>
                </div>
                <form onSubmit={submitFile}>
                    <div className='flex flex-row gap-10 justify-center my-10'>
                        <div className='w-96'>
                            <Input label='Upload Excel file' multiple type='file' name="fileUpload" accept='.xlsx' onChange={(e) => { setFile(e.target.files[0]) }} required />
                        </div>
                        <div>
                            <Button className='bg-green-500' type='submit' >
                                Submit
                            </Button>
                        </div>
                    </div>
                </form>
                {console.log(tableData.length)}
                <table className="w-full min-w-max table-auto text-center my-6">
                    <thead>
                        <tr>
                            {
                                TABLE_HEAD.map((head) => (
                                    <td key={head} className="border-b border-blue-gray-100 bg-blue-gray-50 p-4">
                                        <Typography
                                            variant="small"
                                            color="blue-gray"
                                            className="font-normal leading-none opacity-70">
                                            {head}
                                        </Typography>
                                    </td>
                                ))
                            }
                        </tr>
                    </thead>
                    {isLoading ?
                        <tbody>
                            <tr>
                                <td colSpan={5} className='text-center'>
                                    <Spinner className='mx-auto' />
                                </td>
                            </tr>
                        </tbody> :
                        <tbody>
                            {
                                tableData.length !== 0 ?
                                    Array.from(tableData).map(({ fileId, fileName, creationDate }, index) => {
                                        const isLast = index === tableData?.length - 1;
                                        const classes = isLast ? "p-4" : "p-4 border-b border-blue-gray-50";
                                        return (
                                            <tr key={index}>
                                                <td className={classes}>
                                                    <Typography
                                                        variant="small"
                                                        color="blue-gray"
                                                        className="font-normal">
                                                        {index + 1}
                                                    </Typography>
                                                </td>
                                                <td className={classes}>
                                                    <Typography
                                                        variant="small"
                                                        color="blue-gray"
                                                        className="font-normal">
                                                        {fileName}
                                                    </Typography>
                                                </td>
                                                <td className={classes}>
                                                    <Typography
                                                        variant="small"
                                                        color="blue-gray"
                                                        className="font-normal">
                                                        {creationDate}
                                                    </Typography>
                                                </td>
                                                <td>
                                                    <div className='flex flex-row justify-center gap-4'>
                                                        <Button className='bg-green-500' value="download" onClick={() => { downloadFile(fileId, fileName) }}>Download</Button>
                                                        <Button className='bg-red-500'
                                                            onClick={() => {
                                                                if(window.confirm('Are you sure to delete this record?')){ deleteFile(fileId)}
                                                            }}>Delete</Button>
                                                    </div>
                                                </td>
                                            </tr>
                                        )
                                    })
                                    :
                                    <tr>
                                        <td colSpan={4} className='text-center'>
                                            No Data Available
                                        </td>
                                    </tr>
                            }
                        </tbody>
                    }
                </table>
            </div>
        </>
    )
}

export default UploadFeedback