package hk.pnp.action;

// import hk.pnp.dao.CaseDao;

import hk.pnp.dao.CustDao;
import hk.pnp.dao.PlanDao;
import hk.pnp.dao.SalesDao;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.Plan;
import hk.pnp.persistence.Sales;

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

public class PlanAction {
	
	Logger logger = LoggerFactory.getLogger(PlanAction.class);

	
	private Plan plan;
	
	private Long id;
	
	private Date createdDt;
	
	private String code;  // e.g MP0100, PA2571, IC1322  optional , assigned, not in sequence
	
	private String source; //e.g JF7
	
	private double loanAmt;  //貸款金額
	
	private String loanUsage; // 貸款用途
	
	// 入金 - Todo : Ask for details
	
	private Date loanRcvDt ;// 收錢日
	
	private String status ; // 已約 , 已簽, 取消, 催收中
	
	private float commissionPercent;
	
	private Date signDt; 
	
	private Long custId;
	
	private Long salesId;
	
	public void getPlan() throws Exception {

		// Data grid
		String page;
		String rows;
		Long total;
		
		String sort;
		String order;
		
		String filterdatafield , filtervalue ;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		page = request.getParameter("pagenum");
		rows = request.getParameter("pagesize");

		sort = request.getParameter("sortdatafield");
		order = request.getParameter("sortorder");	
		
		filterdatafield = request.getParameter("filterdatafield0") != null ? request
				.getParameter("filterdatafield0") : "";
		
		filtervalue	= request.getParameter("filtervalue0") != null ? request
				.getParameter("filtervalue0") : "";
		
		PlanDao dao = new PlanDao();
		
		
		// ArrayList<Plan> list = (ArrayList<Plan>) dao.searchAll();
		
		/*
		ArrayList<Plan> list = (ArrayList<Plan>) dao.paginationPlan(
				Integer.parseInt(page), Integer.parseInt(rows),
				sort,
				order
				);
		*/
		
		ArrayList<Plan> list = (ArrayList<Plan>) dao.paginationPlan2(
				Integer.parseInt(page), Integer.parseInt(rows), sort, order,
				filterdatafield, filtervalue);

		
		total = dao.getTotal();

		//Gson gson = new Gson();
		
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

	public Plan toPlan() {
		Plan plan = new Plan();
		
		plan.setId(id);
		plan.setCode(code);
		plan.setCreatedDt(createdDt);
		
		//plan.setSource(source);
		plan.setStatus(status);
		
		plan.setLoanAmt(loanAmt);
		plan.setLoanRcvDt(loanRcvDt);
		plan.setLoanUsage(loanUsage);
		
		plan.setSignDt(signDt);
		
		
		logger.info("plan.getId() - " + plan.getId());
		
		return plan;
	}
	
	
	public void add() throws Exception {
		plan = toPlan();

		Gson gson = new Gson();

		/*CustDao cDao = new CustDao();
		
		Cust cust = cDao.getById(custId);
		*/
		// Plan plan = new Plan();
		
		// plan.setCreatedDt(new Date());
		// plan.setCust(cust);
		
		 CustDao cDao = new CustDao();
		   
		 //  System.out.println("custId :" + custId );
		   
		   if (custId != null ) {
			   Cust cust = cDao.getById(custId);
				
			   plan.setCust(cust);
		   } else {
			   plan.setCust(null);
		   }
		   
		   SalesDao sDao = new SalesDao();
		   
		   if (salesId != null ) {
			   Sales o = sDao.getById(salesId);
				
			   plan.setSales(o);
		   } else {
			   plan.setSales(null);
		   }
		   
		
		PlanDao dao = new PlanDao();
		dao.save(plan);

		// Map<String, Object> json = new HashMap<String, Object>();
		String result = gson.toJson(plan);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json; charset=utf-8");

		response.setHeader("cache-control", "no-cache");

		response.getWriter().write(result);

	}
	
	public void edit() throws IOException{
        
		
		// plan = toPlan();
        
	   PlanDao dao = new PlanDao();
	
	   plan = dao.getById(id);
	   
	   plan.setCode(code);
	   plan.setCreatedDt(createdDt);
	   plan.setLoanAmt(loanAmt);
	   plan.setLoanRcvDt(loanRcvDt);
	   plan.setLoanUsage(loanUsage);
	   plan.setStatus(status);
	   
	   plan.setCommissionPercent(commissionPercent);
	   plan.setSignDt(signDt);
	   
	   CustDao cDao = new CustDao();
	   
	   System.out.println("custId :" + custId );
	   
	   if (custId != null ) {
		   Cust cust = cDao.getById(custId);
			
		   plan.setCust(cust);
	   } else {
		   plan.setCust(null);
	   }
	   
	   SalesDao sDao = new SalesDao();
	   
	   if (salesId != null ) {
		   Sales o = sDao.getById(salesId);
			
		   plan.setSales(o);
	   } else {
		   plan.setSales(null);
	   }
	   
       Map<String, Object> map = new HashMap<String, Object>();
        
       //  logger.debug("plan.id" + plan.getId());
        
       // System.out.println("plan.status : " + plan.getStatus());
       
       try {
        	dao.save(plan);
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
		PlanDao dao = new PlanDao();

		/*
		 * List <User> userList=(List<User>) dao.findListByProperty("id", id);
		 */

		plan = dao.getById(id);
		
		// Logger logger = LoggerFactory.getLogger(userAction.class);		
		logger.info("plan.getId() - " + plan.getId());
		
		dao.remove(plan);

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

	public void newPlan() throws IOException{
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long id =  Long.valueOf(request.getParameter("id"));
		// Long plan_id =  Long.valueOf(request.getParameter("plan_id"));
		
		Gson gson = new Gson();
		
		CustDao cDao = new CustDao();
		PlanDao pDao = new PlanDao();
		
		if (pDao.getByCustId(id).size() > 0) {
			map.put("errorMsg", "客人有單, 無須開單");
		} else {
			Cust cust = cDao.getById(id);
			Plan plan = new Plan();
			
			plan.setCreatedDt(new Date());
			plan.setCust(cust);
			
			pDao.save(plan);
			
			map.put("success", "以完成指示");
		}
	
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

	/*	public Plan getplan() {
			return plan;
		}
	*/
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getLoanUsage() {
		return loanUsage;
	}

	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage;
	}

	public Date getLoanRcvDt() {
		return loanRcvDt;
	}

	public void setLoanRcvDt(Date loanRcvDt) {
		this.loanRcvDt = loanRcvDt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getCode() {
		return code;
	}

	public float getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(float commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

	public Date getSignDt() {
		return signDt;
	}

	public void setSignDt(Date signDt) {
		this.signDt = signDt;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getSalesId() {
		return salesId;
	}

	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}

		
}
