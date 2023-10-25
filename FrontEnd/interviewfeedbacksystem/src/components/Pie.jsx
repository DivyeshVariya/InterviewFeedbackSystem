
import { Card, DonutChart, Title } from '@tremor/react'
import React from 'react'

function Pie({ data,dateRange }) {
    let sum=0;
    data.forEach((c)=>{
        sum += c.count
    })

    const valueFormatter = (number) => {
        return ((number/sum)*100).toFixed(2) + "%";
    };
    console.log("from pie chart :"+data);
    return (
        <Card className="max-w-lg ">
            <Title className='text-center'>Hiring Data : {dateRange.from} to {dateRange.to}</Title>
            <DonutChart
            variant='pie'
                className="mt-6"
                data={data}
                category="count"
                index="name"
                valueFormatter={valueFormatter}
                colors={["red", "red", "red"]}
            />
            <div className='flex flex-row'>
            <div className='w-3 h-3 bg-pink-500 mt-2 mr-2'></div>
            <spna className=''> Pending Applications</spna>
            </div>
            <div className='flex flex-row'>
            <div className='w-3 h-3 bg-gray-500 mt-2 mr-2'></div>
            <spna className=''> Hired Applications</spna>
            </div>
            <div className='flex flex-row'>
            <div className='w-3 h-3 bg-indigo-500 mt-2 mr-2'></div>
            <spna className=''> Un-Hired Applications</spna>
            </div>
        </Card>
    )
}

export default Pie