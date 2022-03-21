import React, {useState} from 'react'
import Form from "../Components/LogEvent-Form/Form.js"
import LETable from '../Components/LogEvent-Form/LETable.js';
import Header from '../Components/LogEvent-Form/Header.js';
import { Typography } from '@mui/material';

export default function LogEvent() {
    {/** just some code to generate a big set of mock data */}
    const mockData  = [];
    for(let i = 0; i < 10000; i++){
      let severity = "N/A", date = `1/${i%30}/${2022 + (Math.floor(i / 2022))}`, ps = "Update Costumer", app = "CRM", activity = "Activity", priority = "N/A", category = "N/A", eai = "N/A";
      switch(i%4){
        case(0): category = "heartbeat"; break;
        case(1): category = "stop"; break;
        case(2): category = "status"; break;
        case(3): category = "security"; break;
        case(4): category = "start"; break;
      };
      switch(i%4){
        case(0): severity = "error"; eai = "EAI Domain 1"; break;
        case(1): severity = "warning"; eai = "EAI Domain 2"; break;
        case(2): severity = "info"; eai = "EAI Domain 3"; break;
        case(3): severity = "success"; eai = "EAI Domain 4"; break;
      };
      switch(i%3){
        case(0): priority = "high"; break;
        case(1): priority = "medium"; break;
        case(2): priority = "low"; break;
      };
      mockData.push({
        "severity": severity,
        "Created Date": date,
        "Process/Service": ps,
        "Application": app,
        "Activity": activity,
        "Log Event": "detail",
        "priority": priority,
        "category": category,
        "EAI Domain": eai
      });
    };
        {/*{"severity": "error", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "high", "category": "start", "EAI Domain": "EAI Domain 1" },
        {"severity": "warning", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "low", "category": "Status", "EAI Domain": "EAI Domain 3" },
        {"severity": "info", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "high" , "category": "Heartbeat", "EAI Domain": "EAI Domain 4"},
        {"severity": "success", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "medium", "category": "Security", "EAI Domain": "EAI Domain 2"},
        {"severity": "error", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "low" , "category": "Stop", "EAI Domain": "EAI Domain 3"},
        {"severity": "warning", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "medium", "category": "Status", "EAI Domain": "EAI Domain 1" },
        {"severity": "info", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "high", "category": "Security", "EAI Domain": "EAI Domain 1" },
        {"severity": "success", "Created Date": "3/2/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail" , "priority": "low", "category": "Start", "EAI Domain": "EAI Domain 2"},
        {"severity": "error", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "high", "category": "start", "EAI Domain": "EAI Domain 1" },
        {"severity": "warning", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "low", "category": "Status", "EAI Domain": "EAI Domain 3" },
        {"severity": "info", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "high" , "category": "Heartbeat", "EAI Domain": "EAI Domain 4"},
        {"severity": "success", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "medium", "category": "Security", "EAI Domain": "EAI Domain 2"},
        {"severity": "error", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "low" , "category": "Stop", "EAI Domain": "EAI Domain 3"},
        {"severity": "warning", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "medium", "category": "Status", "EAI Domain": "EAI Domain 1" },
        {"severity": "info", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail", "priority": "high", "category": "Security", "EAI Domain": "EAI Domain 1" },
        {"severity": "success", "Created Date": "3/10/2022", "Process/Service": "Update Costumer", "Application": "CRM", "Activity": "Activity", "Log Event": "Detail" , "priority": "low", "category": "Start", "EAI Domain": "EAI Domain 2"},*/}
      

    const [data, setData] = useState(mockData)

  return (
    <div>
        <Header /> 
        <Form mockData={mockData} setData={setData}/>
        <LETable data = {data}/>
    </div>
  )
}
