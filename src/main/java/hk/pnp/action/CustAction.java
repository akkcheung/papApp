package hk.pnp.action;

import hk.pnp.dao.CustDao;
import hk.pnp.dao.PlanDao;
import hk.pnp.dao.SalesDao;
import hk.pnp.persistence.Plan;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.Sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CustAction {

	Logger logger = LoggerFactory.getLogger(CustAction.class);

	private Cust cust;

	private Long id;

	private String source;
	private Date createdt;
	// private String sales;
	private String name;
	private String identity;

	private String tel;
	private String occupation;
	private double salary;
	private double debt;
	private String ppty;

	private String addr;
	private String status;

	private Long sales_id;

	private String sex;

	public void getCust() throws Exception {

		// Data grid
		String page;
		String rows;
		Long total;

		String sort;
		String order;

		String name_search;
		String tel_search;

		HttpServletRequest request = ServletActionContext.getRequest();
		page = request.getParameter("page");
		rows = request.getParameter("rows");

		sort = request.getParameter("sort");
		order = request.getParameter("order");

		name_search = request.getParameter("name_search") != null ? request
				.getParameter("name_search") : "";

		tel_search = request.getParameter("tel_search") != null ? request
				.getParameter("tel_search") : "";

		CustDao dao = new CustDao();

		/*
		 * ArrayList<Cust> list = (ArrayList<Cust>) dao.searchAll();
		 */

		ArrayList<Cust> list = (ArrayList<Cust>) dao.paginationUser(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				name_search, tel_search);

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

	public void getCust2() throws Exception {

		// Data grid
		String page;
		String rows;
		Long total;

		String sort;
		String order;

		String name_search;
		String tel_search;

		String filterdatafield , filtervalue ; 
		
		// String sortdatafield, sortorder ; 
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		page = request.getParameter("pagenum");
		rows = request.getParameter("pagesize");

		sort = request.getParameter("sortdatafield");
		order = request.getParameter("sortorder");	
		
		
		/*
		name_search = request.getParameter("name_search") != null ? request
				.getParameter("name_search") : "";

		tel_search = request.getParameter("tel_search") != null ? request
				.getParameter("tel_search") : "";

		 */
		
		filterdatafield = request.getParameter("filterdatafield0") != null ? request
				.getParameter("filterdatafield0") : "";
		
		filtervalue	= request.getParameter("filtervalue0") != null ? request
				.getParameter("filtervalue0") : "";

		CustDao dao = new CustDao();

		/*
		 * ArrayList<Cust> list = (ArrayList<Cust>) dao.searchAll();
		 */
/*
		ArrayList<Cust> list = (ArrayList<Cust>) dao.paginationUser(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				name_search, tel_search);

*/		
		ArrayList<Cust> list = (ArrayList<Cust>) dao.paginationUser2(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				filterdatafield, filtervalue);

		
		total = dao.getTotal();

		// Gson gson = new Gson();
		Gson gson = new GsonBuilder()
		   // .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
			.setDateFormat("dd-MM-yyyy").create();
		 

		// String result = gson.toJson(list);

		// logger.debug(gson.toJson(list));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		
		// map.put("totalRecords", total);
		
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

	
	
	public Cust toCust() {
		Cust cust = new Cust();

		cust.setId(id);
		cust.setCreatedt(createdt);

		// sales

		cust.setName(name);

		cust.setIdentity(identity);

		cust.setTel(tel);
		cust.setOccupation(occupation);

		cust.setSource(source);
		cust.setSalary(salary);
		cust.setPpty(ppty);
		cust.setDebt(debt);

		cust.setPpty(ppty);
		cust.setAddr(addr);
		cust.setStatus(status);
		cust.setSex(sex);

		if (sales_id != null)
		{
			SalesDao dao = new SalesDao();
			
			Sales sales = dao.getById(sales_id);
			cust.setSales(sales);
		}
		return cust;
	}

	public void add() throws Exception {
		cust = toCust();

		//logger.debug("id" + id);
		//System.out.println("id :" + id + " name : " + name);
		cust.setId(null);
		
		System.out.println("cust.id" + cust.getId() + " name : " + name);
		
		
		
		Gson gson = new Gson();

		CustDao dao = new CustDao();
		
		List custList = dao.getbyField("tel", cust.getTel());
		
		Map<String, String> map = new HashMap<String, String>();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String result;
		
		if (custList.size() == 0) {
		
			dao.save(cust);

		// Map<String, Object> json = new HashMap<String, Object>();
		// String result = gson.toJson(cust);
		
			map.put("success", "");			
			result = gson.toJson(map);
			
			/*
			response.setStatus(200) ; 
			result = "success";
			*/
			
		} else {
			// map.put("error", "");
			
			response.setStatus(400) ; 
            result = "Duplicate tel";
		}
		
		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

	}

	public void edit() throws IOException {
		cust = toCust();

		CustDao dao = new CustDao();

		Map<String, Object> map = new HashMap<String, Object>();

		logger.debug("cust.id" + cust.getId());

		try {
			dao.save(cust);
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
		CustDao dao = new CustDao();

		/*
		 * List <User> userList=(List<User>) dao.findListByProperty("id", id);
		 */

		cust = dao.getById(id);

		// Logger logger = LoggerFactory.getLogger(userAction.class);
		logger.info("cust.getId() - " + cust.getId());

		Map<String, Object> map = new HashMap<String, Object>();
		
		if (! dao.hasPlan(id)) {
			dao.remove(cust);
			
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
	
	public void getCustomersByName() throws Exception {

		String name_search;
		String tel_search;

		HttpServletRequest request = ServletActionContext.getRequest();
	
		name_search = request.getParameter("name_startsWith") != null ? request
				.getParameter("name_startsWith") : "";

		tel_search = request.getParameter("tel_search") != null ? request
				.getParameter("tel_search") : "";

		CustDao dao = new CustDao();
		
		 ArrayList<Cust> list = (ArrayList<Cust>) dao.searchAll(name_search, tel_search);
		

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreatedt() {
		return createdt;
	}

	public void setCreatedt(Date createdt) {
		this.createdt = createdt;
	}

	/*
	 * public String getSales() { return sales; }
	 * 
	 * 
	 * public void setSales(String sales) { this.sales = sales; }
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public String getPpty() {
		return ppty;
	}

	public void setPpty(String ppty) {
		this.ppty = ppty;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCust(Cust cust) {
		this.cust = cust;
	}

	public Long getSales_id() {
		return sales_id;
	}

	public void setSales_id(Long sales_id) {
		this.sales_id = sales_id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
