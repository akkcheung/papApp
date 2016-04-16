package hk.pnp.action;

import hk.pnp.dao.EventDao;
import hk.pnp.dao.FinCompDao;
import hk.pnp.dao.SalesDao;
import hk.pnp.persistence.Event;
import hk.pnp.persistence.FinComp;
import hk.pnp.persistence.Sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FinCompAction {
	
	Logger logger = LoggerFactory.getLogger(FinCompAction.class);
	
	private FinComp finComp;
	
	private Long id;
	private String name;
	
	FinCompDao dao;

	public void getAll_o() throws Exception {
		
		dao = new FinCompDao();
		
		ArrayList list = (ArrayList) dao.getAll();
		
		Gson gson = new GsonBuilder()
		   // .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
			.setDateFormat("dd-MM-yyyy").create();
		 
		String result = gson.toJson(list);
		
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);
	}
	
	public void getAll() throws Exception {

		/*
	    
	    // Data grid
		String page;
		String rows;
		Long total;

		String sort;
		String order;

		String name_search;
		String team_search;

		HttpServletRequest request = ServletActionContext.getRequest();
		
		page = request.getParameter("pagenum");
		rows = request.getParameter("pagesize");

		sort = request.getParameter("sortdatafield");
		order = request.getParameter("sortorder");	
		
		String filterdatafield , filtervalue ; 
		
		filterdatafield = request.getParameter("filterdatafield0") != null ? request
						.getParameter("filterdatafield0") : "";
				
		filtervalue	= request.getParameter("filtervalue0") != null ? request
						.getParameter("filtervalue0") : "";
		*/

		dao = new FinCompDao();
		
		/*
		ArrayList<FinComp> list = (ArrayList<FinComp>) dao.pagination(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				// name_search, team_search
				filterdatafield, filtervalue
				);

		total = dao.getTotal();
 		*/
		
		Gson gson = new Gson();

		Map<String, Object> map = new HashMap<String, Object>();
		
		/*		
		map.put("pagenum", page);
		map.put("pagesize", rows);
		 */	
		
		
		ArrayList<FinComp> list = (ArrayList<FinComp>) dao.getAll();
		 		
		map.put("rows", list);

		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);
	}
	
	public void getPage() throws Exception {

		// Data grid
		String page;
		String rows;
		Long total;

		String sort;
		String order;

		String name_search;
		String team_search;

		HttpServletRequest request = ServletActionContext.getRequest();
		
		page = request.getParameter("pagenum");
		rows = request.getParameter("pagesize");

		sort = request.getParameter("sortdatafield");
		order = request.getParameter("sortorder");	
		
		String filterdatafield , filtervalue ; 
		
		filterdatafield = request.getParameter("filterdatafield0") != null ? request
						.getParameter("filterdatafield0") : "";
				
		filtervalue	= request.getParameter("filtervalue0") != null ? request
						.getParameter("filtervalue0") : "";

		dao = new FinCompDao();

		ArrayList<FinComp> list = (ArrayList<FinComp>) dao.pagination(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				// name_search, team_search
				filterdatafield, filtervalue
				);

		total = dao.getTotal();

		Gson gson = new Gson();

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pagenum", page);
		map.put("pagesize", rows);
		
		map.put("rows", list);

		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);
	}
	
	public void add() throws Exception {

		finComp = toFinComp();

		Gson gson = new Gson();

		dao = new FinCompDao();
		dao.save(finComp);

		// Map<String, Object> json = new HashMap<String, Object>();
		String result = gson.toJson(finComp);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

	}
	
	public void edit() throws IOException {
		finComp = toFinComp();

		dao = new FinCompDao();

		Map<String, Object> map = new HashMap<String, Object>();

		logger.debug("sales.id" + finComp.getId());

		try {
			dao.save(finComp);
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
		Long id = Long.valueOf(request.getParameter("id"));

		Gson gson = new Gson();
		dao = new FinCompDao();

		/*
		 * List <User> userList=(List<User>) dao.findListByProperty("id", id);
		 */

		finComp = dao.getById(id);

		// Logger logger = LoggerFactory.getLogger(userAction.class);
		logger.info("finComp.getId() - " + finComp.getId());

		

		// Map<String, Object> json = new HashMap<String, Object>();

		// return SUCCESS;

		Map<String, Object> map = new HashMap<String, Object>();

		if (!dao.hasActivity(id)) {
			dao.remove(finComp);
		
			map.put("success", "true");
		} else {
			map.put("fail", "true");
		}
		
		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);
	}

	
	public FinComp toFinComp() {
		FinComp o = new FinComp();

		o.setId(id);
		o.setName(name);
				
		return o;
	}
	
	public void getByPlanId() throws Exception {
	
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Long id =  Long.valueOf(request.getParameter("planId"));
		
		dao = new FinCompDao();
		
		ArrayList list = (ArrayList) dao.getByPlanId(id);
		
		Gson gson = new GsonBuilder()
		   // .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
			.setDateFormat("dd-MM-yyyy").create();
		 
		String result = gson.toJson(list);
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
