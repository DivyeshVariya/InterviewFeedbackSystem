import React, { useEffect, useState } from 'react'
import Navbar from './Navbar'
import { getUserDetails } from '../services/UserService';
import { fetchAllFeedbackForHr, fetchAllFeedbackForInterviewer, handleDeleteFeedbackById } from '../services/FeedbackService';
import { Button, Spinner, Typography } from '@material-tailwind/react';
import { Link } from 'react-router-dom';

function AllFeedbacks() {
  const [listOfFeedback, setListOfFeedback] = useState([]);
  const [userDetails, setUserDetails] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const TABLE_HEAD = ["Document No", "Candidate Name", "Date of interview", "Decision", "Action"]

  useEffect(() => {
    getAllFeedbacks();
  }, [])

  const getAllFeedbacks = async () => {
    setIsLoading(true);
    try {
      const userDetail = await getUserDetails(window.localStorage.getItem("userEmailId"));
      console.log("from use-effect")
      console.log(userDetail)
      setUserDetails(userDetail);
      setTimeout(async () => {
        let response;
        if(userDetail.userRole=="ROLE_HR")
        {
          response =await fetchAllFeedbackForHr();
        }
        else{
          response = await fetchAllFeedbackForInterviewer(userDetail.userFullName);
        }
        console.log(response)
        if (response.responseCode == 200) {
          console.log(response)
          setListOfFeedback(response.forms);
        }
        setIsLoading(false);
      }, 1000);
    }
    catch (error) {
      console.log(error);
      alert(error);
      setIsLoading(false);
    }
  }

  const deleteFeedback = async (documentNo) => {
    try {
      setIsLoading(true);
      console.log(documentNo);
      console.log("from delete feedback form-------------------");
      try {
        const response = await handleDeleteFeedbackById(documentNo);
        console.log(response);
        if (response.responseCode === 200) {
          alert(response.responseMessage);
          getAllFeedbacks();
          setIsLoading(false);
        }
        else {
          alert(response.responseMessage);
          setIsLoading(false);
        }
      }
      catch (error) {
        console.log(error);
        alert(error);
        setIsLoading(false);
      }

    }
    catch (error) {
      console.log(error);
      setIsLoading(false);
    }
  }

  return (
    <>
      <Navbar isLoginPage={false} isRegisterPage={false} />
      <div className='mt-36 mx-24'>
        <h2 className='flex justify-center text-2xl'> Feedback Information</h2>
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
              {listOfFeedback.length !== 0 ?
                Array.from(listOfFeedback).map(({ documentNo, candidateInfo, dateOfInterview, hiringDecision }, index) => {
                  const isLast = index === listOfFeedback?.length - 1;
                  const classes = isLast ? "p-4" : "p-4 border-b border-blue-gray-50";
                  return (
                    <tr key={index}>
                      <td className={classes}>
                        <Typography
                          variant="small"
                          color="blue-gray"
                          className="font-normal">
                          {documentNo}
                        </Typography>
                      </td>
                      <td className={classes}>
                        <Typography
                          variant="small"
                          color="blue-gray"
                          className="font-normal">
                          {candidateInfo.candidateFullName}
                        </Typography>
                      </td>
                      <td className={classes}>
                        <Typography
                          variant="small"
                          color="blue-gray"
                          className="font-normal">
                          {dateOfInterview.substring(0, 10)}
                        </Typography>
                      </td>
                      <td className={classes}>
                        <Typography
                          variant="small"
                          color="blue-gray"
                          className="font-normal">
                          {hiringDecision}
                        </Typography>
                      </td>
                      <td>
                        <div className='flex flex-row justify-center gap-4'>
                          <Link to={`/view-feedback-details?documentNo=${documentNo}`} > <Button className='bg-green-500' >View</Button></Link>
                          <Link to={`/update-feedback-details?documentNo=${documentNo}`} > <Button className='bg-amber-500' >Update</Button></Link>
                          <Button className='bg-red-500' onClick={() => { if (window.confirm('Are you sure to delete this record?')) { deleteFeedback(documentNo) } }}>Delete</Button>
                        </div>
                      </td>
                    </tr>
                  )
                })
                :
                <tr>
                  <td colSpan={5} className='text-center'>
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

export default AllFeedbacks