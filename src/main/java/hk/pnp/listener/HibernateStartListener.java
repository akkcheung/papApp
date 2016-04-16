package hk.pnp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;

public class HibernateStartListener  implements ServletContextListener {

	Logger logger = LoggerFactory.getLogger(HibernateStartListener.class);

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
		logger.info("inside contextDestroyed()");
		
		System.out.println("Destroy Context");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
		System.out.println("Initialized Context");
		
		logger.info("inside contextInitialized()");
		
		// String hql = "Select count (f.id) from User f";
		
		Session session = hk.pnp.HibernateUtil.getSession();
		// Query q = session.createQuery(hql);
		
		
		session.close();
		
	}

	
	
}
