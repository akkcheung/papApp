package hk.pnp.action;

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

import hk.pnp.dao.CustDao;
import hk.pnp.dao.SalesDao;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.Sales;

public class SalesAction {

	Logger logger = LoggerFactory.getLogger(CustAction.class);
	
	private Sales sales;
	
	private Long id;

	private String name;
	private String team;
	
	public void getAll() throws Exception {

		SalesDao dao = new SalesDao();
			
		ArrayList<Sales> list = (ArrayList<Sales>) dao.getAll();
	
		Gson gson = new Gson();

		
		String result = gson.toJson(list);

		
		
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

		
	}
	
	public void getSales() throws Exception {

		// Data grid
		String page;
		String rows;
		Long total;

		String sort;
		String order;

		String name_search;
		String team_search;

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
		
		/*
		name_search = request.getParameter("name_search") != null ? request
				.getParameter("name_search") : "";

		team_search = request.getParameter("team_search") != null ? request
				.getParameter("team_search") : "";
				
		 */
		
		String filterdatafield , filtervalue ; 
		
		filterdatafield = request.getParameter("filterdatafield0") != null ? request
						.getParameter("filterdatafield0") : "";
				
		filtervalue	= request.getParameter("filtervalue0") != null ? request
						.getParameter("filtervalue0") : "";

		SalesDao dao = new SalesDao();

		/*
		 * ArrayList<Cust> list = (ArrayList<Cust>) dao.searchAll();
		 */

		ArrayList<Sales> list = (ArrayList<Sales>) dao.pagination(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				// name_search, team_search
				filterdatafield, filtervalue
				);

		total = dao.getTotal();

		Gson gson = new Gson();

		// String result = gson.toJson(list);

		// logger.debug(gson.toJson(list));

		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("total", total);
		
		map.put("pagenum", page);
		map.put("pagesize", rows);
		
		map.put("rows", list);

		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

		// return SUCCESS;
	}

	public void getLov() throws Exception {
		
		SalesDao dao = new SalesDao();
		ArrayList<Sales> list = (ArrayList<Sales>) dao.getAll();
		
		Gson gson = new Gson();		
		String result = gson.toJson(list);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);		
	}
	
	
	public Sales toSales() {
		Sales sales = new Sales();

		sales.setId(id);
		sales.setName(name);
		sales.setTeam(team);
		
		return sales;
	}
	
	public void add() throws Exception {
		sales = toSales();

		Gson gson = new Gson();

		SalesDao dao = new SalesDao();
		dao.save(sales);

		// Map<String, Object> json = new HashMap<String, Object>();
		String result = gson.toJson(sales);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

	}
	
	public void edit() throws IOException {
		sales = toSales();

		SalesDao dao = new SalesDao();

		Map<String, Object> map = new HashMap<String, Object>();

		logger.debug("sales.id" + sales.getId());

		try {
			dao.save(sales);
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
		SalesDao dao = new SalesDao();

		/*
		 * List <User> userList=(List<User>) dao.findListByProperty("id", id);
		 */

		sales = dao.getById(id);

		// Logger logger = LoggerFactory.getLogger(userAction.class);
		logger.info("sales.getId() - " + sales.getId());

		Map<String, Object> map = new HashMap<String, Object>();
		
		if (! dao.hasPlan(id)) {
			dao.remove(sales);
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

	
	public void searchByName() throws Exception {

		String name_search;
		// String tel_search;

		HttpServletRequest request = ServletActionContext.getRequest();
	
		name_search = request.getParameter("name_startsWith") != null ? request
				.getParameter("name_startsWith") : "";

		/*		
		tel_search = request.getParameter("tel_search") != null ? request
				.getParameter("tel_search") : "";
		*/
				
		SalesDao dao = new SalesDao();
		
		 ArrayList<Sales> list = (ArrayList<Sales>) dao.getByName(name_search);
		

		/*
		ArrayList<Cust> list = (ArrayList<Cust>) dao.paginationUser(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				name_search, tel_search);
		*/
		
		// total = dao.getTotal();

		// Gson gson = new Gson();
		Gson gson = new GsonBuilder()
		   // .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
			.setDateFormat("dd-MM-yyyy").create();
		 

		// String result = gson.toJson(list);

		// logger.debug(gson.toJson(list));

		Map<String, Object> map = new HashMap<String, Object>();
		
		// map.put("total", total);
		map.put("rows", list);

		String result = gson.toJson(map);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

		// return SUCCESS;
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

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	
}
