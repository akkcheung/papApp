package hk.pnp.dao;

import hk.pnp.HibernateUtil;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.FinComp;
import hk.pnp.persistence.Sales;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class FinCompDao {
	
	Session session = HibernateUtil.getSession();
	
	public List getAll() {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		String hql = "from FinComp order by id";
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
	public FinComp getById(Long id) {
		
		Session session = HibernateUtil.getSession();
		FinComp obj =  (FinComp) session.get(FinComp.class, id);
		
		return obj;
		
	}
	
	public void save(FinComp obj) {
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		
		/*
		if (user.getId() == null)
			session.save(user);
		else
			session.update(user);
		*/
		
		session.saveOrUpdate(obj);
		
		session.getTransaction().commit();
		
		session.close();
		
	}
	
	
	public Long getTotal() {
		
		String hql = "Select count (t.id) from FinComp t";
		
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery(hql);
		
		return  (Long) q.uniqueResult();
	}
	
	public List findListByProperty(String field, String value){
		
		/*
		 * http://stackoverflow.com/questions/14977018/jpa-how-to-get-entity-based-on-field-value-other-than-id
		*/
		
		Session session = HibernateUtil.getSession();
		Criteria c = session.createCriteria(FinComp.class);
		List list = c.add(Restrictions.eq(field, value)).list();
		
		return list;
	}
	
	public List<?> getByPlanId(Long id) {
		
		ArrayList list = null;
		
		// Session session = HibernateUtil.getSession();
		
		String hql = "FROM FinComp c where c.plan.id =" + id.toString();
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}

	public void remove(FinComp finComp) {
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		session.delete(finComp);	
		session.getTransaction().commit();
		
	}
	
	public List pagination(int page, int rows, String sort, String order
			// , String name, String team
			,String filterdatafield, String filtervalue
			) {
	
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM FinComp");
		
		if (! filterdatafield.equals("")) {
			sb.append(" where ") ;
			
			sb.append(filterdatafield + " like '%" + filtervalue + "%'");
		}
		
		if (sort == null  || sort.equals("")) 
			sb.append(" order by id");
		else
			sb.append(" order by " + sort + " " + order);
		
		// Query query = session.createQuery(hql);
		
		System.out.println(sb.toString());
		
		Query query = session.createQuery(sb.toString());
		
		query.setFirstResult( (page-1) * rows);
		query.setMaxResults(rows);
		
		return query.list();
		
	}	
	
	public boolean hasActivity(Long id) {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Activity");
		
		sb.append(" where") ;
			sb.append(" finComp.id = " + id);
					
		System.out.print(sb.toString());
		
		Query query = session.createQuery(sb.toString());
			
		if (query.list().size()> 0 )		
			return true ;				
	
	    return false;
				
	}
}
