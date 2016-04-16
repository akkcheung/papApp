package hk.pnp.action;

import hk.pnp.dao.CustDao;
import hk.pnp.dao.EventDao;
import hk.pnp.dao.PlanDao;
import hk.pnp.persistence.Event;
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

public class EventAction {
	
	Logger logger = LoggerFactory.getLogger(EventAction.class);

	
	private Event evt;
	
	private Long id;
	
	private Date alarm;
	private Date created;
	private String descr;
	private String remark;
	private String status;
	
	
	public void getEvents() throws Exception {

		// Data grid
		String page;
		String rows;
		Long total;
		
		String sort;
		String order;
		
		Long plan_id;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		page = request.getParameter("page");
		rows = request.getParameter("rows");

		sort = request.getParameter("sort");
		order = request.getParameter("order");
		
		EventDao dao = new EventDao();
		
		plan_id = Long.parseLong(request.getParameter("plan_id"));
		
		// ArrayList<Event> list = (ArrayList<Event>) dao.searchAll();
		
		
		/*ArrayList<Cust> list = (ArrayList<Cust>) dao.paginationUser(
				Integer.parseInt(page), Integer.parseInt(rows),
				sort,
				order
				);*/
		 
		ArrayList<Event> list = (ArrayList<Event>) dao.getByPlanId(plan_id);
		
		total = dao.getTotal();

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
	
	public void getOsEvents() throws Exception {
		
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
	

	public Event toEvent() {
		Event evt = new Event();
		
		evt.setId(id);
		evt.setCreated(created);
		evt.setAlarm(alarm);
		evt.setDescr(descr);
		evt.setRemark(remark);
		evt.setStatus(status);
		
		return evt;
	}
	
	
	public void add() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Long plan_id = Long.parseLong(request.getParameter("plan_id"));
		
		Gson gson = new Gson();

		EventDao eDao = new EventDao();
		PlanDao pDao = new PlanDao();
		
		evt = toEvent();
		Plan plan = pDao.getById(plan_id);
		
		evt.setPlan(plan);
		
		eDao.save(evt);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "以完成指示");
		
		// Map<String, Object> json = new HashMap<String, Object>();
		String result = gson.toJson(evt);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

	}
	
	public void edit() throws IOException{
		
	    EventDao dao = new EventDao();
		
	    evt = dao.getById(id);
	    
		// evt = toEvent();
        evt.setCreated(created);
        evt.setDescr(descr);
        evt.setRemark(remark);
        evt.setAlarm(alarm);
		
        Map<String, Object> map = new HashMap<String, Object>();
        
        logger.debug("evt.id" + evt.getId());
        
        try {
        	dao.save(evt);
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
		EventDao dao = new EventDao();

		/*
		 * List <User> userList=(List<User>) dao.findListByProperty("id", id);
		 */

		evt = dao.getById(id);
		
		// Logger logger = LoggerFactory.getLogger(userAction.class);		
		logger.info("event.getId() - " + evt.getId());
		
		dao.remove(evt);

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
