package hk.pnp.dao;


import hk.pnp.HibernateUtil;
import hk.pnp.persistence.Cust;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class CustDao {
	
	public void save(Cust cust) {
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		
		/*
		if (user.getId() == null)
			session.save(user);
		else
			session.update(user);
		*/
		
		session.saveOrUpdate(cust);
		
		session.getTransaction().commit();
		
		session.close();
		
	}
	
	public List searchAll() {
	
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		String hql = "FROM Cust order by id";
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
	public List searchAll(String name, String tel) {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Cust");
		
		sb.append(" where") ;
			sb.append(" name like '%" + name + "%'");
			// sb.append(" and tel like '%" + tel + "%'");			
			sb.append(" order by id");
		
		// Query query = session.createQuery(hql);
		
		System.out.println(sb.toString());
		
		Query query = session.createQuery(sb.toString());
		
		
		// String hql = "FROM Cust order by id";
		// Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
	// public List paginationUser(int page, int rows) {
	public List paginationUser(int page, int rows, String sort, String order
			, String name, String tel) {
	
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		// String hql = "FROM User order by id";
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Cust");
		
		sb.append(" where") ;
		
			sb.append(" name like '%" + name + "%'");
		
			sb.append(" and tel like '%" + tel + "%'");		
		
		
		if (sort == null) 
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
	
	
	public List paginationUser2(int page, int rows, String sort, String order
			,String filterdatafield, String filtervalue) {
	
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		// String hql = "FROM User order by id";
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Cust");
		
		if (! filterdatafield.equals("")) {
			sb.append(" where ") ;
			
			sb.append(filterdatafield + " like '%" + filtervalue + "%'");
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
	
	public Long getTotal() {
		
		String hql = "Select count (f.id) from Cust f";
		
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery(hql);
		
		return  (Long) q.uniqueResult();
	}
	
	public void remove(Cust cust) {
		
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		session.delete(cust);	
		session.getTransaction().commit();
		
	}
	
	public List findListByProperty(String field, String value){
		
		/*
		 * http://stackoverflow.com/questions/14977018/jpa-how-to-get-entity-based-on-field-value-other-than-id
		*/
		
		Session session = HibernateUtil.getSession();
		Criteria c = session.createCriteria(Cust.class);
		List list = c.add(Restrictions.eq(field, value)).list();
		
		return list;
	}
	
	public Cust getById(Long cust_id) {
		
		Session session = HibernateUtil.getSession();
		Cust cust =  (Cust) session.get(Cust.class, cust_id);
		
		return cust;
		
	}
	
	public List getbyField(String field, String fieldVal) {
		
		Session session = HibernateUtil.getSession();
		
		Criteria criteria = session.createCriteria(Cust.class);
		
		// Cust cust = (Cust) criteria.add(Restrictions.eq(field, fieldVal)).uniqueResult();
		List custList = criteria.add(Restrictions.eq(field, fieldVal)).list();
		
		return custList;
		
	}
	
	public boolean hasPlan(Long id) {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Plan");
		
		sb.append(" where") ;
			sb.append(" cust.id = " + id);
					
		System.out.print(sb.toString());
		
		Query query = session.createQuery(sb.toString());
			
		if (query.list().size()> 0 )		
			return true ;				
	
	    return false;
				
	}

}
