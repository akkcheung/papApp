package hk.pnp.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="tb_plan")
@Entity
public class Plan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	Date createdDt;

	String code;  // e.g MP0100, PA2571, IC1322  optional , assigned, not in sequence
	
	// String source; //e.g JF7
	
	double loanAmt;  //貸款金額
	
	String loanUsage; // 貸款用途
	
	// 入金 - Todo : Ask for details
	
	Date signDt;
	
	Date loanRcvDt ;// 收錢日
	
	String status ; // 已約 , 已簽, 取消, 催收中
	
	double commissionPercent ;
	
	/*
	// Event //  has many
	@OneToMany
	@JoinColumn(name="caseId")
	private List <Event> events;
	*/

	@ManyToOne
	private Cust cust;
	
	@ManyToOne
	private Sales sales;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/*public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}*/

	/*
	 public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	*/

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

	public Cust getCust() {
		return cust;
	}

	public void setCust(Cust cust) {
		this.cust = cust;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Date getSignDt() {
		return signDt;
	}

	public void setSignDt(Date signDt) {
		this.signDt = signDt;
	}

	public double getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(double commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	
}
