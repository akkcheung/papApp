package hk.pnp.dao;


import hk.pnp.HibernateUtil;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.User;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class UserDao {
	
	public void save(User user) {
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		
		/*
		if (user.getId() == null)
			session.save(user);
		else
			session.update(user);
		*/
		
		session.saveOrUpdate(user);
		
		session.getTransaction().commit();
		
		session.close();
		
	}
	
	public List searchAll() {
	
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		String hql = "FROM User order by id";
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
	public List searchAll(String name, String tel) {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM User");
		
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
		sb.append( "FROM User");
		
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
		sb.append( "FROM User");
		
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
		
		String hql = "Select count (u.id) from User u";
		
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery(hql);
		
		return  (Long) q.uniqueResult();
	}
	
	public void remove(User user) {
		
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		session.delete(user);	
		session.getTransaction().commit();
		
	}
	
	public List findListByProperty(String field, String value){
		
		/*
		 * http://stackoverflow.com/questions/14977018/jpa-how-to-get-entity-based-on-field-value-other-than-id
		*/
		
		Session session = HibernateUtil.getSession();
		Criteria c = session.createCriteria(User.class);
		List list = c.add(Restrictions.eq(field, value)).list();
		
		return list;
	}
	
	public User getById(Long id) {
		
		Session session = HibernateUtil.getSession();
		User u =  (User) session.get(User.class, id);
		
		return u;
		
	}
	
	
	public List<?> getByEmail(String email) {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		String hql = "FROM User P where P.emal ='" + email + "'";
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
}
