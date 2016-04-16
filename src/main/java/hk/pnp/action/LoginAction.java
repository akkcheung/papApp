package hk.pnp.action;

import hk.pnp.dao.UserDao;
import hk.pnp.persistence.User;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;

	private Map<String, Object> session;

	private UserDao dao;
	private User user;

	public String home() {
		return SUCCESS;
	}

	// Log Out user
	public String logOut() {
		session.remove("loginId");
		addActionMessage("You have been Successfully Logged Out");
		return SUCCESS;
	}

	// Login user
	public String login() {

		boolean cont = false;

		if (userName.isEmpty()) {
			addActionError("Username can't be blanked");
		} else {

			// System.out.println(userName + "-" + password) ;

			dao = new UserDao();

			List list = dao.findListByProperty("email", userName);
			// List list = dao.getByEmail(userName);

			if (list.size() > 0) {
				user = (User) list.get(0);

				if (user.getPassword().equals(password)) {
					cont = true;
				}

			}

		}

		if (cont) {
			session.put("loginId", userName);
			return SUCCESS;
		}

		return LOGIN;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> map) {
		this.session = map;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
