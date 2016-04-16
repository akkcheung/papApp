package hk.pnp.persistence;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="tb_sales")
@Entity
public class Sales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private boolean isActive;
	
	private String team;
	
	
	/*@OneToMany
	@JoinColumn(name="salesId")
	private List <Case> cases;
	*/
	
	/*@OneToMany
	@JoinColumn(name="salesId")
	private List <Cust> customers;
	 */
	
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


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	/*public List<Case> getCases() {
		return cases;
	}


	public void setCases(List<Case> cases) {
		this.cases = cases;
	}*/


	/*public List<Cust> getCustomers() {
		return customers;
	}


	public void setCustomers(List<Cust> customers) {
		this.customers = customers;
	}*/
	
	
}
