package hk.pnp.action;

import hk.pnp.DBConnection;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class JasperAction {

	/*
	 * private Integer pYr; private Integer pMon;
	 */

	private Connection conn;

	private Map<String, Integer> reportParameter = null;

	// private Map<String, String>reportParameter = null;

	// public String execute() throws Exception {
	public String genSalesDailyWsRpt() throws Exception {

		setConn(DBConnection.getInstance());

		reportParameter = new HashMap<String, Integer>();
		// reportParameter = new HashMap<String, String>();

		HttpServletRequest request = ServletActionContext.getRequest();
		String pYr = request.getParameter("pYr");
		String pMon = request.getParameter("pMon");

		reportParameter.put("pYr", Integer.parseInt(pYr));
		reportParameter.put("pMon", Integer.parseInt(pMon));
		/*
		 * reportParameter.put("pYr", "2015"); reportParameter.put("pMon",
		 * "10");
		 */

		System.out.println(reportParameter.get("pYr"));
		System.out.println(reportParameter.get("pMon"));

		return "success";
	}

	public String genPerformanceRpt() throws Exception {

		setConn(DBConnection.getInstance());

		reportParameter = new HashMap<String, Integer>();

		HttpServletRequest request = ServletActionContext.getRequest();
		String pYr = request.getParameter("pYr");
		String pMon = request.getParameter("pMon");

		reportParameter.put("pYr", Integer.parseInt(pYr));
		reportParameter.put("pMon", Integer.parseInt(pMon));

		System.out.println(reportParameter.get("pYr"));
		System.out.println(reportParameter.get("pMon"));

		return "success";
	}

	public Map<String, Integer> getReportParameter() {
		return reportParameter;
	}

	public void setReportParameter(Map<String, Integer> reportParameter) {
		this.reportParameter = reportParameter;
	}

	/*
	 * public Integer getpYr() { return pYr; }
	 * 
	 * 
	 * 
	 * 
	 * public void setpYr(Integer pYr) { this.pYr = pYr; }
	 * 
	 * 
	 * 
	 * 
	 * public Integer getpMon() { return pMon; }
	 * 
	 * 
	 * 
	 * 
	 * public void setpMon(Integer pMon) { this.pMon = pMon; }
	 */

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
