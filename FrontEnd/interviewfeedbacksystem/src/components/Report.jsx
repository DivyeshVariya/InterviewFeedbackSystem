import React, { useEffect, useState } from 'react'
import Navbar from './Navbar'
import { Button, Input } from '@material-tailwind/react'
import { getHiringData } from '../services/FeedbackService';
import {  PieChart } from 'recharts';
import Pie from './Pie';

function Report() {
  const [dateRange, setDateRange] = useState({ "to": "", "from": "" });
  const [hiringData, setHiringData] = useState(null);
  const [loading, setLoading] = useState(false)


  const handleSubmit = async (e) => {
    setLoading(true)
    e.preventDefault();
    try {
      console.log(dateRange);
      const response = await getHiringData(dateRange);
      console.log(response);
      if (response.responseCode) {
        alert(response.responseMessage);
        setHiringData(
          [
            { name: "Pending Applications", count: response.pendingApplications },
            { name: "Hired Applications", count: response.hiredApplications },
            { name: "Not Hired Applications", count: response.notHiredApplications }
          ]
        );
      }
      else {
        setLoading(false)
        alert("Something went to wrong !!");
      }
    }
    catch (error) {
      setLoading(false)
      alert(error);
    }
  }
  console.log("hiring data", hiringData)
  
  return (
    <>
      <Navbar isLoginPage={false} isRegisterPage={false} />
      <div className='mt-36 flex justify-center text-2xl '>Generate Report</div>

      <form onSubmit={handleSubmit}>
        <div className='flex flex-row gap-10 justify-center m-10'>
          <div className='w-80'>
            <Input type='date' name='toDate' label='To' onChange={(e) => { setDateRange({ ...dateRange, to: e.target.value }) }} required />
          </div>
          <div className='w-80'>
            <Input type='date' name='fromDate' label='From' onChange={(e) => { setDateRange({ ...dateRange, from: e.target.value }) }} required />
          </div>
        </div>
        <div className='flex justify-center m-5'>
          <Button className='bg-blue-500 ' type='submit'>Submit</Button>
        </div>
      </form>
      <div className='flex justify-center my-10'>
      {
        hiringData? <Pie data={hiringData}  dateRange={dateRange}/>: "No data"
      }
    </div>
    </>
  )
}

export default Report