package hk.pnp.dao;


import hk.pnp.HibernateUtil;
import hk.pnp.persistence.Plan;




import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class PlanDao {
	
	Session session = HibernateUtil.getSession();
	
	public void save(Plan plan) {
		// Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		
		/*
		if (user.getId() == null)
			session.save(user);
		else
			session.update(user);
		*/
		
		session.saveOrUpdate(plan);
		
		session.getTransaction().commit();
		
		session.close();
		
	}
	
	public List searchAll() {
	
		ArrayList list = null;
		
		// Session session = HibernateUtil.getSession();
		
		String hql = "FROM Plan order by id";
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
	// public List paginationUser(int page, int rows) {
	public List paginationPlan(int page, int rows, String sort, String order) {
	
		ArrayList list = null;
		
		// Session session = HibernateUtil.getSession();
		
		// String hql = "FROM User order by id";
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Plan P");

		if (sort == null) {
			sb.append(" order by P.id");
		
		} else {
			/*if (sort.equals("sales")) 
				sort = "P.Cust.Sales.name";*/
			
			sb.append(" order by " + sort + " " + order);
		
		}
		
		// Query query = session.createQuery(hql);
		Query query = session.createQuery(sb.toString());
		
		query.setFirstResult( (page-1) * rows);
		query.setMaxResults(rows);
		
		return query.list();
		
	}	
	
	public Long getTotal() {
		
		String hql = "Select count (c.id) from Plan c";
		
		// Session session = HibernateUtil.getSession();
		Query q = session.createQuery(hql);
		
		return  (Long) q.uniqueResult();
	}
	
	public void remove(Plan plan) {
		
		// Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		session.delete(plan);	
		session.getTransaction().commit();
		
	}
	
	public List findListByProperty(String field, String value){
		
		/*
		 * http://stackoverflow.com/questions/14977018/jpa-how-to-get-entity-based-on-field-value-other-than-id
		*/
		
		// Session session = HibernateUtil.getSession();
		Criteria c = session.createCriteria(Plan.class);
		List list = c.add(Restrictions.eq(field, value)).list();
		
		return list;
	}
	
	public Plan getById(Long plan_id) {
		
		// Session session = HibernateUtil.getSession();
		Plan plan =  (Plan) session.get(Plan.class, plan_id);
		
		return plan;
		
	}
	
	public List<?> getByCustId(Long id) {
		
		ArrayList list = null;
		
		// Session session = HibernateUtil.getSession();
		
		String hql = "FROM Plan P where P.cust.id =" + id.toString();
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}

	public List paginationPlan2(int page, int rows,
			String sort, String order, String filterdatafield,
			String filtervalue) {

		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		// String hql = "FROM User order by id";
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Plan");
		
		if (! filterdatafield.equals("")) {
			sb.append(" where ") ;
			
			if (! filterdatafield.equals("cust") && ! filterdatafield.equals("sales")) 
				sb.append(filterdatafield + " like '%" + filtervalue + "%'");			
			else 
				sb.append(filterdatafield + ".name" + " like '%" + filtervalue + "%'");
			
		}
		
		if (sort == null || sort.equals("")) 
			sb.append(" order by id");
		else
			sb.append(" order by " + sort + " " + order);
		
		// Query query = session.createQuery(hql);
		
		System.out.println(sb.toString());
		
		Query query = session.createQuery(sb.toString());
		
		// query.setFirstResult( (page-1) * rows);
		query.setFirstResult( (page) * rows);
		
		query.setMaxResults(rows);
		
		return query.list();
		
	}
	
	
}
