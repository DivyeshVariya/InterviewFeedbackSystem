import React, { useEffect, useState } from 'react'
import Navbar from './Navbar'
import { Button, Card, Input, Option, Select, Step, Stepper, Typography } from '@material-tailwind/react';
import { UserIcon, WrenchScrewdriverIcon, QuestionMarkCircleIcon } from "@heroicons/react/24/outline";
import StarRating from './StarRating';
import { getUserDetails } from '../services/UserService';
import { handleFeedbackForm } from '../services/FeedbackService';
import { useNavigate } from 'react-router-dom';

function FeedbackForm() {
    let navigate = useNavigate();

    const [activeStep, setActiveStep] = React.useState(0);
    const [isLastStep, setIsLastStep] = React.useState(false);
    const [isFirstStep, setIsFirstStep] = React.useState(false);

    const [interviewerInfo, setInterviewerInfo] = useState({})
    const [interviewDecision, setInterviewDecision] = useState({})
    const [technicalSkillData, setTechnicalSkillData] = useState([]);
    const [softSkillData, setSoftSkillData] = useState([]);
    const [candidateInfo, setCandidateInfo] = useState({});

    const handleNext = () => !isLastStep && setActiveStep((cur) => cur + 1);
    const handlePrev = () => !isFirstStep && setActiveStep((cur) => cur - 1);

    useEffect(() => {

        const getInterviewerInfo = async () => {
            const userEmailId = window.localStorage.getItem("userEmailId");
            console.log(userEmailId);
            const response = await getUserDetails(userEmailId);
            console.log(response);
            setInterviewerInfo({ interviewerName: response.userFullName, interviewerDesignation: response.userDesignation })
        }
        getInterviewerInfo();
    }, [])

    const submitFeedback = async () => {

        try {
            if (candidateInfo !== null && interviewerInfo !== null && technicalSkillData !== null && softSkillData !== null && interviewDecision !== null) {
                const feedbackFormData = {
                    candidateInfo: candidateInfo,
                    interviewerInfo: interviewerInfo,
                    technicalEvaluation: technicalSkillData,
                    softSkillsEvaluation: softSkillData,
                    hiringDecision: interviewDecision.hiringDecision,
                    dateOfInterview: interviewDecision.dateOfInterview
                }
                console.log(feedbackFormData);
                const response = await handleFeedbackForm(feedbackFormData);
                console.log(response)
                alert("Feedback submited successfully !!");
                navigate("/dash-board", { replace: false });
            }
            else {
                alert("Fill the required fields !!");
            }
        }
        catch (error) {
            alert(error);
            activeStep(0);
            interviewerInfo({});
            interviewDecision({});
            technicalSkillData([]);
            softSkillData([]);
            candidateInfo({});
        }
    }

    return (
        <>
            <Navbar />
            <div className='mt-32 mx-24'>
                <div className='my-5'>
                    <h1 className='text-center font-bold text-2xl'>Candidate Evaluation Form</h1>
                </div>
                <div className="w-full py-4 px-8">
                    <Stepper
                        activeStep={activeStep}
                        isLastStep={(value) => setIsLastStep(value)}
                        isFirstStep={(value) => setIsFirstStep(value)}
                    >
                        {/* onClick={() => setActiveStep(0)} */}
                        <Step >
                            <UserIcon className="h-5 w-5" />
                        </Step>
                        {/* onClick={() => setActiveStep(1)} */}
                        <Step >
                            <WrenchScrewdriverIcon className="h-5 w-5" />
                        </Step>
                        {/* onClick={() => setActiveStep(2)} */}
                        <Step >
                            <QuestionMarkCircleIcon className="h-5 w-5" />
                        </Step>
                    </Stepper>
                    <div>
                        {
                            activeStep === 0 ?
                                <Step1 candidateInfo={candidateInfo} setCandidateInfo={setCandidateInfo} fn={""} te={0} re={0} ap={""} ad={""} ps={""} ss={""} /> :
                                activeStep === 1 ?
                                    <Step2 technicalSkillData={technicalSkillData} setTechnicalSkillData={setTechnicalSkillData} softSkillData={softSkillData}
                                        setSoftSkillData={setSoftSkillData} sr={0} ssn={""} tr={0} tsn={""} tc={""} />
                                    :
                                    <>
                                        <Step3 interviewDecision={interviewDecision} setInterviewDecision={setInterviewDecision} doi={new Date()} hd={""} />

                                        <div className='flex justify-center'>
                                            <Button className='bg-green-500 text-center' onClick={() => { submitFeedback() }}>Submit Feedback</Button>
                                        </div>
                                    </>
                        }
                    </div>
                    <div className="mt-16 flex justify-between">
                        <Button onClick={handlePrev} disabled={isFirstStep}>
                            Prev
                        </Button>
                        <Button onClick={handleNext} disabled={isLastStep}>
                            Next
                        </Button>
                    </div>
                </div>
            </div>
        </>
    )
}

const Step1 = ({ candidateInfo, setCandidateInfo, fn, te, re, ap, ad, ps, ss }) => {
    const [fullName, setFullName] = useState(fn);
    const [totalExp, setTotalExp] = useState(te);
    const [relevantExp, setRelevantExp] = useState(re);
    const [applyingPosition, setApplyingPosition] = useState(ap);
    const [applyingDivision, setApplyingDivision] = useState(ad);
    const [primarySkill, setPrimarySkill] = useState(ps);
    const [secondarySkill, setSecondarySkill] = useState(ss);

    const saveData = () => {
        if (fullName !== "" && totalExp >= 0 && relevantExp >= 0 && applyingDivision !== "" && applyingPosition !== "" && primarySkill !== "" && secondarySkill !== "") {
            setCandidateInfo({ candidateFullName: fullName, candidatePosition: applyingPosition, candidateDivision: applyingDivision, candidateTotalExperience: totalExp, candidateRelevantExperience: relevantExp, candidatePrimarySkills: primarySkill.toUpperCase().split(","), candidateSecondarySkills: secondarySkill.toUpperCase().split(",") });
            alert("data saved...")
        }
        else {
            alert("Enter valid input !!!")
        }
    }
    return (
        <Card className='h-full w-full'>
            <Typography variant="h4" color="blue-gray" className='text-center my-10'>
                Candidate Information
            </Typography>
            <div className="mb-8 flex flex-col gap-6">
                <div className='w-full px-20'>
                    <Input label="Full Name" type='text' name='candidateFullName' onChange={(e) => { setFullName(e.target.value); console.log(e.target.value) }} value={fullName} required />
                </div>
                <div className='flex flex-row gap-20 mx-auto'>
                    <div className='w-96'>
                        <Input type="number" size="lg" label="Total Experience" name='candidateTotalExperience' onChange={(e) => { setTotalExp(e.target.value); console.log(e.target.value) }} value={totalExp} required />
                    </div>
                    <div className='w-96'>
                        <Input type="number" size="lg" label="Relevant Experience" name='candidateRelevantExperience' onChange={(e) => { setRelevantExp(e.target.value); console.log(e.target.value) }} value={relevantExp} required />
                    </div>
                </div>
                <div className='flex flex-row gap-20 mx-auto'>
                    <div className='w-96'>
                        <Select label="Applying Position" name='candidatePosition'
                            value={applyingPosition}
                            selected={() => applyingPosition}
                            onChange={(data) => { setApplyingPosition(data); console.log(data) }}>
                            <Option value='INTERN' >INTERN</Option>
                            <Option value="ENGINEER" >ENGINEER</Option>
                            <Option value='SENIOR ENGINEER'> SENIOR ENGINEER</Option>
                        </Select>
                    </div>
                    <div className='w-96'>
                        <Select label="Applying Division" name='candidateDivision' value={applyingDivision}
                            selected={() => applyingDivision}
                            onChange={(data) => { setApplyingDivision(data); console.log(data) }}>
                            <Option value='PES'>PES</Option>
                            <Option value="ASIC">ASIC</Option>
                        </Select>
                    </div>
                </div>
                <div className='flex flex-row gap-20 mx-auto'>
                    <div className='w-96'>
                        <Input type='text' label='Primary Skills' style={{ textTransform: "uppercase" }} name='candidatePrimarySkills' value={primarySkill} onChange={(e) => { setPrimarySkill(e.target.value); console.log(e.target.value.split(",")) }} required />
                        <Typography
                            variant="small"
                            color="gray"
                            className="mt-2 flex items-center gap-1 text-xs"
                        >Primary skill must be comma separated. Ex : C,JAVA</Typography>
                    </div>
                    <div className='w-96'>
                        <Input type='text' label='Secondary Skills' style={{ textTransform: "uppercase" }} name='candidateSecondarySkills' value={secondarySkill} onChange={(e) => { setSecondarySkill(e.target.value); console.log(e.target.value.split(",")) }} required />
                        <Typography
                            variant="small"
                            color="gray"
                            className="mt-2 flex items-center gap-1 text-xs"
                        >Secondary skill must be comma separated. Ex : PYTHON,AWS</Typography>
                    </div>
                </div>
                <div className='mx-auto '>
                    <Button className='bg-blue-gray-500 text-center' onClick={() => saveData()}>Save Data</Button>
                </div>
            </div>
        </Card>
    )
}

const Step2 = ({ technicalSkillData, setTechnicalSkillData, softSkillData, setSoftSkillData, tr, tsn, tc, sr, ssn }) => {


    const [techRated, setTechRated] = useState(tr);
    const [techSkillName, setTechSkillName] = useState(tsn);
    const [techComment, setTechComment] = useState(tc);

    const [softRated, setSoftRated] = useState(sr);
    const [softSkillName, setSoftSkillName] = useState(ssn);

    const TECHNICAL_SKILL_HEAD = ["Skill", "Comment", "Rate", "Actions"];
    const SOFT_SKILL_HEAD = ["Skill", "Rate", "Actions"];

    const addTechinalSkill = () => {
        console.log(technicalSkillData)
        let updateTechRated="";
        if (techComment !== "" && techSkillName !== "" && techRated !== 0) {
            if (techRated == 5) {
                updateTechRated="A";
            }
            else if (techRated == 4) {
                updateTechRated="B";
            }
            else if (techRated == 3) {
                updateTechRated="C";
            }
            else if (techRated == 2) {
                updateTechRated="D";
            }
            else {
                updateTechRated="E";
            }
            setTechnicalSkillData((oldData) => [...oldData, { skill: techSkillName.toUpperCase(), rating: updateTechRated, comments: techComment.toUpperCase() }]);
            setTechComment("");
            setTechRated(0);
            setTechSkillName("");
            alert("Skill added...");
        }
        else {
            alert("Enter a valid input !!");
        }
    }

    const deleteTechinalSkill = (index) => {
        console.log(technicalSkillData);
        console.log(index)

        setTechnicalSkillData(technicalSkillData.filter((skill, i) => i != index))
    }

    const deleteSoftSkill = (index) => {
        console.log(softSkillData);
        console.log(index)

        setSoftSkillData(softSkillData.filter((skill, i) => i != index))
    }

    const addSoftSkill = () => {
        console.log(softSkillData);
        let updateSoftRated="";
        if (softSkillName !== "" && softRated !== 0) {
            if (softRated == 5) {
                updateSoftRated="A";
            }
            else if (softRated == 4) {
                updateSoftRated="B";
            }
            else if (softRated == 3) {
                updateSoftRated="C";
            }
            else if (softRated == 2) {
                updateSoftRated="D";
            }
            else {
                updateSoftRated="E";
            }
            console.log({ skill: softSkillName.toUpperCase(), rating: updateSoftRated })
            setSoftSkillData((oldData) => [...oldData, { skill: softSkillName.toUpperCase(), rating: updateSoftRated }]);
            setSoftRated(0);
            setSoftSkillName("");
            alert("Skill added...");
        }
        else {
            alert("Enter a valid input !!");
        }
    }

    return (
        <Card className='h-full w-full overflow-hidden'>
            <Typography variant="h4" color="blue-gray" className='text-center my-10'>
                Skill Evaluation
            </Typography>
            <div className='p-5'>
                <Typography variant="h5" color="blue-gray" className='text-left my-10 ml-5'>
                    Technical Skills
                </Typography>
                <div className='flex flex-row gap-6 px-6' >
                    <div className='w-96'>
                        <Input type='text' name="skillName" label='Skill' style={{ textTransform: "uppercase" }} value={techSkillName} onChange={(e) => setTechSkillName(e.target.value)} />

                    </div>
                    <div className='w-96'>
                        <Input type='text' name="comment" label='Comment' value={techComment} onChange={(e) => setTechComment(e.target.value)} />
                    </div>
                    <div className='mt-1'>
                        <StarRating onSetRating={setTechRated} clearRating={techRated == 0 ? true : false} storkeColor='#808080' fillColor="#fde047" size={30} />
                    </div>
                </div>
                <div className='justify-center flex my-10'>
                    <Button className='bg-blue-500 text-white' onClick={() => addTechinalSkill()} >Add </Button>
                </div>
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
                        {technicalSkillData.length != 0 &&
                            technicalSkillData.map(({ skill, comments, rating }, index) => {
                                const isLast = index === technicalSkillData.length - 1;
                                const classes = isLast ? "p-4" : "p-4 border-b border-blue-gray-50";
                                console.log(technicalSkillData)
                                return (

                                    <tr key={skill}>
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
                                        <td className={classes}>
                                            <Button className="font-medium bg-red-400 w-24" onClick={() => deleteTechinalSkill(index)}>
                                                Delete
                                            </Button>
                                        </td>
                                    </tr>


                                );
                            })}
                        {
                            technicalSkillData.length == 0 && <tr>
                                <td colSpan={4} className='text-center'>
                                    No Skill added
                                </td>
                            </tr>
                        }
                    </tbody>
                </table>
            </div>
            <div className='p-5'>
                <Typography variant="h5" color="blue-gray" className='text-left my-10 ml-5'>
                    Soft Skills
                </Typography>
                <div className='flex flex-row gap-6 px-6' >
                    <div className='w-96'>
                        <Input type='text' style={{ textTransform: "uppercase" }} name="skillName" label='Skill' value={softSkillName} onChange={(e) => setSoftSkillName(e.target.value)} />
                    </div>
                    <div className='mt-1'>
                        <StarRating onSetRating={setSoftRated} clearRating={softRated == 0 ? true : false} storkeColor='#808080' fillColor="#fde047" size={30} />
                    </div>
                    <div className='ml-10'>
                        <Button className='bg-blue-500 text-white' onClick={() => addSoftSkill()} >Add </Button>
                    </div>
                </div>
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
                        {softSkillData.length != 0 &&
                            softSkillData.map(({ skill, rating }, index) => {
                                const isLast = index === softSkillData.length - 1;
                                const classes = isLast ? "p-4" : "p-4 border-b border-blue-gray-50";
                                console.log("length :" + softSkillData.length)
                                return (
                                    <tr key={skill}>
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
                                        <td className={classes}>
                                            <Button className="font-medium bg-red-400 w-24" onClick={() => deleteSoftSkill(index)}>
                                                Delete
                                            </Button>
                                        </td>
                                    </tr>
                                );
                            })}
                        {
                            softSkillData.length == 0 && <tr>
                                <td colSpan={4} className='text-center'>
                                    No Skill added
                                </td>
                            </tr>
                        }
                    </tbody>
                </table>
            </div>
        </Card>
    )
}

const Step3 = ({ interviewDecision, setInterviewDecision, doi, hd }) => {
    const [dateOfInterview, setDateOfInterview] = useState(doi);
    const [hiringDecision, setHiringDecision] = useState(hd);

    const saveData = () => {
        if (dateOfInterview !== "" && hiringDecision !== "") {
            setInterviewDecision({ hiringDecision: hiringDecision, dateOfInterview: dateOfInterview });
            alert("Data Saved !!!");
        }
        else {
            alert("Enter valid data !!");
        }
    }

    return (
        <Card className='h-full w-full overflow-hidden'>
            <Typography variant="h4" color="blue-gray" className='text-center my-10'>
                Interview Decision
            </Typography>
            <div className='flex flex-row gap-10 mx-auto mb-10'>
                <div className='w-96'>
                    <Input type="date" name="dateOfInterview" label='Date of Interview' value={dateOfInterview.substring(0, 10)} onChange={(e) => { setDateOfInterview(e.target.value); console.log(e.target.value) }} />
                </div>
                <div className='w-96'>
                    <Select name="hiringDecision" label='Hiring Decision' value={hiringDecision} selected={() => hiringDecision} onChange={(data) => { setHiringDecision(data); console.log(data) }}>
                        <Option value="HIRE">HIRE</Option>
                        <Option value='PENDING'>PENDING</Option>
                        <Option value='NO_HIRE'>NO_HIRE</Option>
                    </Select>
                </div>
            </div>
            <div className='mx-auto mb-10'>
                <Button className='bg-blue-gray-500 text-center' onClick={() => { saveData() }}>Save Data</Button>
            </div>
        </Card>
    )
}

export default FeedbackForm
export { Step1, Step2, Step3 }