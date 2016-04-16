package hk.pnp.action;

import hk.pnp.dao.ActivityDao;
import hk.pnp.dao.CustDao;
import hk.pnp.dao.EventDao;
import hk.pnp.dao.FinCompDao;
import hk.pnp.dao.PlanDao;
import hk.pnp.persistence.Activity;
import hk.pnp.persistence.Event;
import hk.pnp.persistence.FinComp;
import hk.pnp.persistence.Plan;
import hk.pnp.persistence.Cust;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActivityAction {
	
	Logger logger = LoggerFactory.getLogger(ActivityAction.class);

	
	private Activity act;
	
	private Long id;
	
	private Date alarm;
	private Date created;
	private String descr;
	private String remark;
	private String status;
	
	
	public void getActivities() throws Exception {

		// Data grid
		String page;
		String rows;
		Long total;
		
		String sort;
		String order;
		
		Long plan_id;
		
		String filterdatafield , filtervalue ;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		/*
		page = request.getParameter("page");
		rows = request.getParameter("rows");

		sort = request.getParameter("sort");
		order = request.getParameter("order");
		*/
		
		page = request.getParameter("pagenum");
		rows = request.getParameter("pagesize");

		sort = request.getParameter("sortdatafield");
		order = request.getParameter("sortorder");	
		
		filterdatafield = request.getParameter("filterdatafield0") != null ? request
				.getParameter("filterdatafield0") : "";
		
		filtervalue	= request.getParameter("filtervalue0") != null ? request
				.getParameter("filtervalue0") : "";
		
		
		ActivityDao dao = new ActivityDao();
		
		plan_id = Long.parseLong(request.getParameter("plan_id"));
		
		// ArrayList<Event> list = (ArrayList<Event>) dao.searchAll();
		
		
		/*ArrayList<Cust> list = (ArrayList<Cust>) dao.paginationUser(
				Integer.parseInt(page), Integer.parseInt(rows),
				sort,
				order
				);*/
		 
		// ArrayList list = (ArrayList) dao.getByPlanId(plan_id);
		
		total = dao.getTotal();
		
		ArrayList<Activity> list = (ArrayList<Activity>) dao.getPageByPlanId(plan_id 
				,Integer.parseInt(page), Integer.parseInt(rows)
				,sort,	order
				,filterdatafield, filtervalue
				);

		// Gson gson = new Gson();
		Gson gson = new GsonBuilder()
		   // .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
			.setDateFormat("dd-MM-yyyy").create();
		 
		
		// String result = gson.toJson(list);

		// logger.debug(gson.toJson(list));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);

		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

		// return SUCCESS;
	}
	
	public void getOsActivities() throws Exception {
		
		EventDao dao = new EventDao();
		
		ArrayList<Event> list = (ArrayList<Event>) dao.getByAlarmOn();
		
		Gson gson = new GsonBuilder()
		   // .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
			.setDateFormat("dd-MM-yyyy").create();
		 
		String result = gson.toJson(list);
		
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

	}	
	

	public Activity toActivity() {
		
		Activity act ;
		
		ActivityDao dao = new ActivityDao();	
		
		if (id != null)
			act = dao.getById(id);
		else
			act = new Activity();
		
		// act.setId(id);	
		act.setCreated(created);
		act.setAlarm(alarm);
		act.setDescr(descr);
		act.setRemark(remark);
		act.setStatus(status);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Long plan_id, finComp_id ;
		
		/*
		System.out.println("plan_id :" + request.getParameter("plan_id"));
		System.out.println("finComp_id :" + request.getParameter("finComp_id"));
		*/
		
		if (! request.getParameter("plan_id").equals("") ){
			plan_id = Long.parseLong(request.getParameter("plan_id"));
		
			PlanDao pDao = new PlanDao();
			Plan plan = pDao.getById(plan_id);
			act.setPlan(plan);
		}
		
		if (request.getParameter("finComp_id") != null
				|| request.getParameter("finComp_id").length() > 0 ) {
			finComp_id = Long.parseLong(request.getParameter("finComp_id"));
		
			FinCompDao fDao = new FinCompDao();		
			FinComp finComp = fDao.getById(finComp_id);
			act.setFinComp(finComp);
		}
			
		return act;
	}
	
	
	public void add() throws Exception {
		
		
		Gson gson = new Gson();

		ActivityDao aDao = new ActivityDao();
		
		
		act = toActivity();
		
		
		
		aDao.save(act);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "以完成指示");
		
		// Map<String, Object> json = new HashMap<String, Object>();
		String result = gson.toJson(act);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

	}
	
	public void edit() throws IOException{
	
	    ActivityDao dao = new ActivityDao();
		
	    // act = dao.getById(id);
	  
		act = toActivity();
        
	    /*
	    act.setCreated(created);
        act.setDescr(descr);
        act.setRemark(remark);
        act.setAlarm(alarm);
		*/
		
        Map<String, Object> map = new HashMap<String, Object>();
        
        logger.debug("act.id" + act.getId());
        
        try {
        	dao.save(act);
        	map.put("success", "success");
        } catch (Exception e) {	
        	map.put("errorMsg", "错误444");
        }
        
        Gson gson = new Gson();

		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");
		
		response.getWriter().write(result);
      
    }
	

	public void remove() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		Long id =  Long.valueOf(request.getParameter("id"));
		
		Gson gson = new Gson();
		ActivityDao dao = new ActivityDao();

		/*
		 * List <User> userList=(List<User>) dao.findListByProperty("id", id);
		 */

		act = dao.getById(id);
		
		// Logger logger = LoggerFactory.getLogger(userAction.class);		
		logger.info("act.getId() - " + act.getId());
		
		dao.remove(act);

		// Map<String, Object> json = new HashMap<String, Object>();

		// return SUCCESS;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("success", "successs");
	
		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");
		
		response.getWriter().write(result);
	}

	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAlarm() {
		return alarm;
	}

	public void setAlarm(Date alarm) {
		this.alarm = alarm;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	
	
}
