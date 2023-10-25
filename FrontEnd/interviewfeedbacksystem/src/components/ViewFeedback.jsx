import { Button, Spinner, Typography } from '@material-tailwind/react';
import jsPDF from 'jspdf';
import React, { useEffect, useRef, useState } from 'react'
import { Link, useLocation } from 'react-router-dom'
import { getFeedbackByDocumentNo } from '../services/FeedbackService';

function ViewFeedback() {
    const location = useLocation();
    const documentNo = new URLSearchParams(location.search).get('documentNo');
    console.log(documentNo);
    const [feedbackDetails, setFeedbackDetails] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    useEffect(() => {
        fetchData(documentNo)
    }, [])

    const fetchData = async (docNo) => {
        try {
            setIsLoading(true);
            console.log(docNo);
            const response = await getFeedbackByDocumentNo(docNo);
            console.log(response);
            if (response.responseCode === 200) {
                setFeedbackDetails(response.form);
            }
            setTimeout(() => {
                setIsLoading(false);
            }, 2000);
        }
        catch (error) {
            alert(error);
            setIsLoading(false);
        }
    }

    const TECHNICAL_SKILL_HEAD = ["#", "Skill", "Comment", "Rating"];
    const SOFT_SKILL_HEAD = ["#", "Skill", "Rating"];

    const reportTemplateRef = useRef(null);

    const printFeedback = async (documentNo) => {
        const doc = new jsPDF({
            format: 'a1',
            unit: 'px',
            orientation: 'portrait',

        });

        // Adding the fonts.
        doc.setFont('Inter-Regular', 'normal');

        doc.html(reportTemplateRef.current, {
            async callback(doc) {
                doc.save(documentNo);
            },
        });
    }

    return (
        <>
            <div className='my-10 mx-auto' ref={reportTemplateRef}>
                <h2 className='text-2xl text-center my-10'>Feedback Information</h2>
                {isLoading ?

                    <div className='flex justify-center'>
                        <Spinner />
                    </div> 
                    :
                    <>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <h3 className='absolute flex top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
                                    <span className='bg-gray-300 px-2 text-md font-medium"'>General Information</span>
                                </h3>
                                <div className='flex flex-row justify-center gap-8'>
                                    <label className='text-gray-700'>Document Number :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.documentNo}</span>
                                </div>
                            </div>
                        </div>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <h3 className='absolute flex top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
                                    <span className='bg-gray-300 px-2 text-md font-medium"'>Candidate Information</span>
                                </h3>
                                <div className='flex flex-row justify-center gap-8'>
                                    <label className='text-gray-700'>Candidate Full Name :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.candidateInfo.candidateFullName}</span>
                                    <label className='text-gray-700'>Applying Division :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.candidateInfo.candidateDivision}</span>
                                    <label className='text-gray-700'>Applying Position :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.candidateInfo.candidatePosition}</span>
                                </div>
                                <div className='flex flex-row justify-center gap-20 mt-4'>
                                    <label className='text-gray-700'>Total Experience :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.candidateInfo.candidateTotalExperience} years</span>
                                    <label className='text-gray-700'>Relevant Experience :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.candidateInfo.candidateRelevantExperience} years</span>
                                </div>
                                <div className='flex flex-row justify-center gap-24 my-4'>
                                    <label className='text-gray-700'>Primary Skills :</label>
                                    {(feedbackDetails.candidateInfo.candidatePrimarySkills).map((skill, index) => {
                                        return (
                                            <>
                                                {feedbackDetails.candidateInfo.candidatePrimarySkills.length - 1 === index ?
                                                    <span className=''>{skill}</span>
                                                    :
                                                    <span className=''>{skill} {" ,"}</span>
                                                }
                                            </>
                                        )
                                    })}
                                    <label className='text-gray-700'>Secondary Skills :</label>
                                    {(feedbackDetails.candidateInfo.candidateSecondarySkills).map((skill, index) => {
                                        return (
                                            <>
                                                {feedbackDetails.candidateInfo.candidateSecondarySkills.length - 1 === index ?
                                                    <span className=''>{skill}</span>
                                                    :
                                                    <span className=''>{skill} {" ,"}</span>
                                                }
                                            </>
                                        )
                                    })}
                                </div> 
                            </div>
                        </div>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <h3 className='absolute flex top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
                                    <span className='bg-gray-300 px-2 text-md font-medium"'>Interviewer Information</span>
                                </h3>
                                <div className='flex flex-row justify-center gap-8'>
                                    <label className='text-gray-700'>Interviewer Full Name :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.interviewerInfo.interviewerName}</span>
                                    <label className='text-gray-700'>Interviewer Designation :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.interviewerInfo.interviewerDesignation}</span>
                                </div>
                            </div>
                        </div>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <h3 className='absolute flex top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
                                    <span className='bg-gray-300 px-2 text-md font-medium"'>Technical Skill Evaluation</span>
                                </h3>
                                <table className="w-full min-w-max table-auto text-center my-6">
                                    <thead>
                                        <tr>
                                            {TECHNICAL_SKILL_HEAD.map((head) => (
                                                <th
                                                    key={head}
                                                    className="border-b border-blue-gray-100 bg-blue-gray-50 p-4"
                                                >
                                                    <Typography
                                                        variant="small"
                                                        color="blue-gray"
                                                        className="font-normal leading-none opacity-70"
                                                    >
                                                        {head}
                                                    </Typography>
                                                </th>
                                            ))}
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {feedbackDetails.technicalEvaluation.length != 0 &&
                                            feedbackDetails.technicalEvaluation.map(({ skill, comments, rating }, index) => {
                                                const isLast = index === feedbackDetails.technicalEvaluation.length - 1;
                                                const classes = isLast ? "p-4" : "p-4 border-b border-blue-gray-50";

                                                return (

                                                    <tr key={skill}>
                                                        <td className={classes}>
                                                            <Typography
                                                                variant="small"
                                                                color="blue-gray"
                                                                className="font-normal"
                                                            >
                                                                {index + 1}
                                                            </Typography>
                                                        </td>
                                                        <td className={classes}>
                                                            <Typography
                                                                variant="small"
                                                                color="blue-gray"
                                                                className="font-normal"
                                                            >
                                                                {skill}
                                                            </Typography>
                                                        </td>
                                                        <td className={classes}>
                                                            <Typography
                                                                variant="small"
                                                                color="blue-gray"
                                                                className="font-normal"
                                                            >
                                                                {comments}
                                                            </Typography>
                                                        </td>
                                                        <td className={classes}>
                                                            <Typography
                                                                variant="small"
                                                                color="blue-gray"
                                                                className="font-normal"
                                                            >
                                                                {rating}
                                                            </Typography>
                                                        </td>
                                                    </tr>
                                                );
                                            })}
                                        {
                                            feedbackDetails.technicalEvaluation.length == 0 && <tr>
                                                <td colSpan={4} className='text-center'>
                                                    No Skill added
                                                </td>
                                            </tr>
                                        }
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <h3 className='absolute flex top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
                                    <span className='bg-gray-300 px-2 text-md font-medium"'>Soft Skill Evaluation</span>
                                </h3>
                                <table className="w-full min-w-max table-auto text-center my-6">
                                    <thead>
                                        <tr>
                                            {SOFT_SKILL_HEAD.map((head) => (
                                                <th
                                                    key={head}
                                                    className="border-b border-blue-gray-100 bg-blue-gray-50 p-4"
                                                >
                                                    <Typography
                                                        variant="small"
                                                        color="blue-gray"
                                                        className="font-normal leading-none opacity-70"
                                                    >
                                                        {head}
                                                    </Typography>
                                                </th>
                                            ))}
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {feedbackDetails.softSkillsEvaluation.length != 0 &&
                                            feedbackDetails.softSkillsEvaluation.map(({ skill, rating }, index) => {
                                                const isLast = index === feedbackDetails.softSkillsEvaluation.length - 1;
                                                const classes = isLast ? "p-4" : "p-4 border-b border-blue-gray-50";

                                                return (

                                                    <tr key={skill}>
                                                        <td className={classes}>
                                                            <Typography
                                                                variant="small"
                                                                color="blue-gray"
                                                                className="font-normal"
                                                            >
                                                                {index + 1}
                                                            </Typography>
                                                        </td>
                                                        <td className={classes}>
                                                            <Typography
                                                                variant="small"
                                                                color="blue-gray"
                                                                className="font-normal"
                                                            >
                                                                {skill}
                                                            </Typography>
                                                        </td>
                                                        <td className={classes}>
                                                            <Typography
                                                                variant="small"
                                                                color="blue-gray"
                                                                className="font-normal"
                                                            >
                                                                {rating}
                                                            </Typography>
                                                        </td>
                                                    </tr>
                                                );
                                            })}
                                        {
                                            feedbackDetails.softSkillsEvaluation.length == 0 && <tr>
                                                <td colSpan={3} className='text-center'>
                                                    No Skill added
                                                </td>
                                            </tr>
                                        }
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <h3 className='absolute flex top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
                                    <span className='bg-gray-300 px-2 text-md font-medium"'>Hiring Decision</span>
                                </h3>
                                <div className='flex flex-row justify-center gap-8'>
                                    <label className='text-gray-700'>Decision :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.hiringDecision}</span>
                                    <label className='text-gray-700'>Date Of Interview :</label>
                                    <span className='mx-4 text-center'>{feedbackDetails.dateOfInterview.substring(0, 10)}</span>
                                </div>
                            </div>
                        </div>
                        <div className='flex flex-row justify-center gap-10'>
                            <div className=''>
                                <Button className='bg-blue-500' onClick={(e) => { printFeedback(feedbackDetails.documentNo) }}>Print</Button>
                            </div>
                            <div className=''>
                                <Link to="/view-all-feedbacks">
                                    <Button className='bg-gray-500'>Back</Button>
                                </Link>
                            </div>
                        </div>
                    </>
                }
            </div>
        </>
    )
}

export default ViewFeedback