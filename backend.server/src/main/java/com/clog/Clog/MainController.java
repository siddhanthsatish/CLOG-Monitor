package com.clog.Clog;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.clog.Clog.BusinessProcess.BusinessGridFilter;
import com.clog.Clog.BusinessProcess.BusinessGridSpecification;
import com.clog.Clog.BusinessProcess.BusinessProcessTreeMap;
import com.clog.Clog.BusinessProcess.BusinessProcessTreeNode;
import com.clog.Clog.BusinessProcess.BusinessTreeRepository;
import com.clog.Clog.BusinessProcess.EAIdomain;

import com.clog.Clog.BusinessProcess.businessTreeFilter;
import com.clog.Clog.BusinessProcess.businessTreeSpecification;
import com.clog.Clog.DashboardView.DashBoardLineGraphFilter;
import com.clog.Clog.DashboardView.RecentEventsSpecification;
import com.clog.Clog.LogDetailFiles.LogDetail;
import com.clog.Clog.LogDetailFiles.LogDetailRepository;
import com.clog.Clog.LogEventFiles.LogEvent;
import com.clog.Clog.LogEventFiles.LogEventFilterSpecification;
import com.clog.Clog.LogEventFiles.LogEventRepository;
import com.clog.Clog.LogEventFiles.LogEventsSearchCriteria;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/clog")
public class MainController {

    @Autowired
    private LogDetailRepository logRepo;
    @Autowired
    private LogEventRepository logEventRepo;
    @Autowired
    private BusinessTreeRepository busTree;

    @GetMapping(path="/logDetail")
    public @ResponseBody Optional<LogDetail> getLogDetails(@RequestParam String id) {
        return logRepo.findById(id);
    }

    @GetMapping(path="/logEvents")
    public @ResponseBody List<LogEvent> getLogEvents(@RequestParam(required = false) String[] businessDomain, @RequestParam(required = false) String[] eaiDomain, @RequestParam String startTime,
    @RequestParam String endTime,@RequestParam(required = false) String[] businessSubDomain,@RequestParam(required = false) String[] process, @RequestParam(required = false) String[] priorities, @RequestParam(required = false) String[] categories,
    @RequestParam(required =  false) String[] severities, @RequestParam(required = false) String[] application) {
        LogEventsSearchCriteria filt = new LogEventsSearchCriteria();
        filt.setBusinessDomain(businessDomain);
        filt.setEaiDomain(eaiDomain);
        filt.setStartTime(Timestamp.valueOf(startTime));
        filt.setEndTime(Timestamp.valueOf(endTime));
        filt.setBusinessSubDomain(businessSubDomain);
        filt.setProcess(process);
        filt.setPriorities(priorities);
        filt.setCategories(categories);
        filt.setSeverities(severities);
        filt.setApplication(application);
        LogEventFilterSpecification test = new LogEventFilterSpecification(filt);
        return logEventRepo.findAll(test);
    }

    @GetMapping(path="/businessProcessTree")
    public @ResponseBody Map<String, Map<String, Map<String, List<BusinessProcessTreeNode>>>> getBusinessTree(
    @RequestParam String startTime, 
    @RequestParam String endTime, 
    @RequestParam(required = false) String[] eaiDomain, 
    @RequestParam(required = false) String[] publishingBusinessDomain) {

        businessTreeFilter filt = new businessTreeFilter();

        filt.setStartTime(Timestamp.valueOf(startTime));
        filt.setEndTime(Timestamp.valueOf(endTime));
        filt.setEaiDomain(eaiDomain);
        filt.setPublishingBusinessDomain(publishingBusinessDomain);

        businessTreeSpecification spec = new businessTreeSpecification(filt);

        List<EAIdomain> test =  busTree.findAll(spec);

        BusinessProcessTreeMap returnMap = new BusinessProcessTreeMap();
        
        for(EAIdomain x : test) {
            returnMap.addObj(x);
        }
        return returnMap.getMap();
    }

    @GetMapping(path="/businessProcessGrid")
    public @ResponseBody List<LogEvent> getBusinessProcessGrid(
    @RequestParam String eaiTransactionId, 
    @RequestParam(required = false) String[] severities, 
    @RequestParam(required = false) String[] businessDomain)
    {
        BusinessGridFilter businessFilter = new BusinessGridFilter();
        businessFilter.setEai_transaction_id(eaiTransactionId);
        businessFilter.setBusinessDomainList(businessDomain);
        businessFilter.setSeverities(severities);

        BusinessGridSpecification businessGridSpec = new BusinessGridSpecification(businessFilter);

        return logEventRepo.findAll(businessGridSpec);

    }

    @GetMapping(path="/countByType")
    public @ResponseBody Map<Timestamp,Long> test(@RequestParam String severity, @RequestParam Double intervals, @RequestParam int timeBack) {
        SortedMap<Timestamp,Long> testObj = new TreeMap<Timestamp,Long>();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp startTime = new Timestamp(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(timeBack));
        Calendar cal = Calendar.getInstance();
        Double intervalLength = timeBack / intervals;
        cal.setTime(startTime);       
        while(cal.getTime().before(currentTime)) {
            System.out.println(intervalLength);
            DashBoardLineGraphFilter filter = new DashBoardLineGraphFilter(severity,new Timestamp(cal.getTimeInMillis()),currentTime);
            System.out.println(cal.getTimeInMillis());
            cal.add(Calendar.MILLISECOND, (int) Math.round(intervalLength*60*1000));
            System.out.println(cal.getTimeInMillis());
            filter.setEndTime(new Timestamp(cal.getTimeInMillis()));
            RecentEventsSpecification test2 = new RecentEventsSpecification(filter);          
            testObj.put(filter.getStartTime(), logEventRepo.count(test2));
            break;
        }
        return testObj;
    }
    @GetMapping(path="/businessProcessPieGraph")
    public @ResponseBody Map<String, Integer> getPieGraph(@RequestParam int timeBack) {
        Map<String, Integer> returnMap = new HashMap<String,Integer>();
        LogEventsSearchCriteria filt = new LogEventsSearchCriteria();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp startTime = new Timestamp(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(timeBack));
        filt.setStartTime(startTime);
        filt.setEndTime(currentTime);

        return returnMap;
    }
    @GetMapping(path="/businessDomains")
    public @ResponseBody List<String> getBusinessDomains() {
        return logEventRepo.findDistinctBusinessDomains();
    }

    @GetMapping(path="/businessSubDomains")
    public @ResponseBody List<String> getBusinessSubDomains() {
        return logEventRepo.findDistinctBusinessSubDomains();
    }

    @GetMapping(path="/applications")
    public @ResponseBody List<String> getApplications() {
        return logEventRepo.findDistinctApplications();
    }

    @GetMapping(path="/services")
    public @ResponseBody List<String> getServices() {
        return logEventRepo.findDistinctServices();
    }

    @GetMapping(path="/publishingBusinessDomains")
    public @ResponseBody List<String> getPublishingBusinessDomains() {
        return busTree.findDistinctPublishingBusinessDomains();
    }

    @GetMapping(path="/eaiDomains")
    public @ResponseBody List<String> getEAIDomains(){
        return logEventRepo.findDistinctEAI_Domains();
    }
    
}
