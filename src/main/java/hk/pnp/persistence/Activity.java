package hk.pnp.persistence;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="tb_activity")
@Entity
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	Date created;
	
	String status; // e.g. 已簽約, 未錄音, 已批, 補料
	
	String descr;
	
	String remark;
	
	Date alarm;

	@ManyToOne
	private FinComp finComp;
	
	@ManyToOne
	private Plan plan;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getAlarm() {
		return alarm;
	}

	public void setAlarm(Date alarm) {
		this.alarm = alarm;
	}

	public FinComp getFinComp() {
		return finComp;
	}

	public void setFinComp(FinComp finComp) {
		this.finComp = finComp;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	
}
