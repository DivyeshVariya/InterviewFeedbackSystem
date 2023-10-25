import React, { useEffect, useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { getFeedbackByDocumentNo, handleFeedbackUpdate } from '../services/FeedbackService';
import { Button, Input, Option, Select, Spinner, Typography } from '@material-tailwind/react';
import Navbar from './Navbar';
import { Step1, Step2, Step3 } from './FeedbackForm';

function UpdateFeedback() {
    const location = useLocation();
    let navigate=useNavigate();
    const documentNo = new URLSearchParams(location.search).get('documentNo');
    console.log(documentNo);
    const [feedbackDetails, setFeedbackDetails] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const [candidateInfo, setCandidateInfo] = useState({});
    const [interviewerInfo, setInterviewerInfo] = useState({})
    const [interviewDecision, setInterviewDecision] = useState({})
    const [technicalSkillData, setTechnicalSkillData] = useState([]);
    const [softSkillData, setSoftSkillData] = useState([]);

    // const [interviewerName, setInterviewerName] = useState("");
    // const [interviewerDesignation, setInterviewerDesignation] = useState("");

    useEffect(() => {
        fetchData(documentNo)
    }, [])

    const handleSubmit =async(e)=>{
        e.preventDefault();
        try{

            console.log(interviewerInfo);
            console.log(candidateInfo);
            console.log(technicalSkillData);
            console.log(softSkillData);
            console.log(interviewDecision);
            const response =await handleFeedbackUpdate({documentNo:documentNo,candidateInfo:candidateInfo,interviewerInfo:interviewerInfo,technicalSkillData:technicalSkillData,softSkillsEvaluation:softSkillData,hiringDecision:interviewDecision.hiringDecision,dateOfInterview:interviewDecision.dateOfInterview})
            console.log(response);
            if(response.responseCode===200)
            {
                alert(response.responseMessage);
                navigate("/view-all-feedbacks");
            }
            else{
                alert("Something went wrong !!");
            }
            
        }
        catch(error)
        {
            alert(error);
        }
    } 

    const fetchData = async (docNo) => {
        try {
            setIsLoading(true);
            console.log(docNo);
            const response = await getFeedbackByDocumentNo(docNo);
            console.log(response);
            if (response.responseCode === 200) {
                setFeedbackDetails(response.form);
                setCandidateInfo(response.form.candidateInfo);
                setTechnicalSkillData(response.form.technicalEvaluation);
                setSoftSkillData(response.form.softSkillsEvaluation);
                setInterviewDecision({ dateOfInterview: response.form.dataOfInterview, hiringDecision: response.form.hiringDecision })
                setInterviewerInfo(response.form.interviewerInfo);
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
    return (
        <>
            <Navbar isLoginPage={false} isRegisterPage={false} />
            <div className='my-36 mx-auto' >
                <h2 className='text-2xl text-center my-10'>Update Feedback Details</h2>
                {isLoading ?

                    <div className='flex justify-center'>
                        <Spinner />
                    </div>
                    :
                    <>
                        <span className='text-red-500 text-md flex justify-center'>Note : Make sure whichever data you are updating that already double check.</span>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 py-6 px-32'>
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
                            <div className=' rounded-md border-2 border-gray-600 p-6'>

                                <Step1 setCandidateInfo={setCandidateInfo} candidateInfo={candidateInfo}
                                    fn={feedbackDetails.candidateInfo.candidateFullName}
                                    te={feedbackDetails.candidateInfo.candidateTotalExperience}
                                    re={feedbackDetails.candidateInfo.candidateRelevantExperience}
                                    ap={feedbackDetails.candidateInfo.candidatePosition}
                                    ad={feedbackDetails.candidateInfo.candidateDivision}
                                    ps={feedbackDetails.candidateInfo.candidatePrimarySkills}
                                    ss={feedbackDetails.candidateInfo.candidateSecondarySkills}
                                />
                            </div>
                        </div>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <Step2 technicalSkillData={technicalSkillData}
                                    setTechnicalSkillData={setTechnicalSkillData}
                                    softSkillData={softSkillData}
                                    setSoftSkillData={setSoftSkillData}
                                    sr={feedbackDetails.softSkillsEvaluation.rating}
                                    ssn={feedbackDetails.softSkillsEvaluation.skill}
                                    tr={feedbackDetails.technicalEvaluation.rating}
                                    tsn={feedbackDetails.technicalEvaluation.skill}
                                    tc={feedbackDetails.technicalEvaluation.comments}
                                />
                            </div>
                        </div>
                        <form onSubmit={handleSubmit}>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <h3 className='absolute flex top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
                                    <span className='bg-gray-300 px-2 text-md font-medium"'>Interviewer Information</span>
                                </h3>
                                <div className='flex flex-row justify-center gap-20 mt-4'>
                                    <div className='w-96'>
                                        <Input label='Interviewer Name' type='text' name="interviewerName" value={interviewerInfo.interviewerName} onChange={(e) => { setInterviewerInfo({...interviewerInfo,interviewerName:e.target.value}) }} required />
                                    </div>
                                    <div className='w-96'>
                                        <Input label='Interviewer Designation' type='text' name='interviewerDesignation' value={interviewerInfo.interviewerDesignation} onChange={(e) => { setInterviewerInfo({...interviewerInfo,interviewerDesignation:e.target.value}) }} required />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className='container mx-auto p-5'>
                            <div className='relative rounded-md border-2 border-gray-600 p-6'>
                                <Step3
                                    interviewDecision={interviewDecision}
                                    setInterviewDecision={setInterviewDecision}
                                    doi={feedbackDetails.dateOfInterview}
                                    hd={feedbackDetails.hiringDecision} />
                            </div>
                        </div>
                        <div className='flex flex-row justify-center gap-10'>
                            <div className=''>
                                <Button className='bg-blue-500' type='submit'  >Update Data</Button>
                            </div>
                            <div className=''>
                                <Link to="/view-all-feedbacks">
                                    <Button className='bg-gray-500'>Back</Button>
                                </Link>
                            </div>
                        </div>
                        </form>
                    </>
                }
            </div>
        </>
    )
}

export default UpdateFeedback








{/* <div className='flex flex-row justify-center gap-8'>
    <div className='w-96'>
        <Input  label='Candidate Full Name' type='text' name='fullName' value={fullName} onChange={(e) => { setFullName(e.target.value) }} required />
    </div>
    <div className='w-96'>
        <Select label="Applying Position" name='candidatePosition' value={applyingPosition}
            onChange={(data) => { setApplyingPosition(data); console.log(data) }}>
            <Option value='INTERN'>INTERN</Option>
            <Option value="ENGINEER">ENGINEER</Option>
            <Option value='SENIOR ENGINEER'> SENIOR ENGINEER</Option>
        </Select>
    </div>
    <div className='w-96'>
        <Select label="Applying Division" name='candidateDivision' value={applyingDivision}
            onChange={(data) => { setApplyingDivision(data); console.log(data) }}>
            <Option value='PES'>PES</Option>
            <Option value="ASIC">ASIC</Option>
        </Select>
    </div>
</div>
<div className='flex flex-row justify-center gap-20 mt-4'>
    <div className='w-96'>
        <Input  label='Total Experience' type='number' name="totalExp" value={totalExp} onChange={(e) => { setTotalExp(e.target.value) }} required />
    </div>
    <div className='w-96'>
        <Input  label='Relevant Experience' type='number' name='relevantExp' value={relevantExp} onChange={(e) => { setRelevantExp(e.target.value) }} required />
    </div>
</div>
<div className='flex flex-row justify-center gap-24 my-4'>
<div className='w-96'>
<Input type='text' label='Primary Skills' style={{ textTransform: "uppercase" }} name='candidatePrimarySkills' value={primarySkill} onChange={(e) => { setPrimarySkill(e.target.value.split(",")); console.log(e.target.value.split(",")) }} required />
<Typography
variant="small"
color="gray"
className="mt-2 flex items-center gap-1 text-xs"
>Primary skill must be comma separated. Ex : C,JAVA</Typography>
</div>
<div className='w-96'>
<Input type='text' label='Secondary Skills' style={{ textTransform: "uppercase" }} name='candidateSecondarySkills' value={secondarySkill} onChange={(e) => { setSecondarySkill(e.target.value.split(",")); console.log(e.target.value.split(",")) }} required />
<Typography
variant="small"
color="gray"
className="mt-2 flex items-center gap-1 text-xs"
>Secondary skill must be comma separated. Ex : PYTHON,AWS</Typography>
</div>
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
</div>*/}