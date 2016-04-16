package hk.pnp.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="tb_cust")
@Entity
public class Cust {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date createdt;
	
	private String name;
	
	private String sex;  // M, F
	
	private String identity ;
	
	private String tel;
	
	private String occupation;
	
	private double salary;
	
	private String payrollInfo;// 出糧方式
	
	private double debt;
	
	private String ppty;
	
	private String status;
	
	private String addr;
	
	private String source;
	
	@ManyToOne
	private Sales sales;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Date getCreatedt() {
		return createdt;
	}

	public void setCreatedt(Date createdt) {
		this.createdt = createdt;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	/*@Enumerated(EnumType.STRING)
	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}
*/
	
	
	public String getIdentity() {
		return identity;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getPayrollInfo() {
		return payrollInfo;
	}

	public void setPayrollInfo(String payrollInfo) {
		this.payrollInfo = payrollInfo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	/*public List<Case> getCases() {
	return cases;
}

public void setCases(List<Case> cases) {
	this.cases = cases;
}*/
	
	public void setSource(String source) {
		this.source = source;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}
	
	public Sales getSales() {
		return sales;
	}

	public String getSource() {
		return source;
	}

	

	


	
}
