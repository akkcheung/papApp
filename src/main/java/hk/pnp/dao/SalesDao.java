package hk.pnp.dao;

import hk.pnp.HibernateUtil;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.Sales;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class SalesDao {
	
	public List getAll() {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		String hql = "from Sales order by id";
		Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
	public Sales getById(Long sales_id) {
		
		Session session = HibernateUtil.getSession();
		Sales sales =  (Sales) session.get(Sales.class, sales_id);
		
		return sales;
		
	}
	
	public void save(Sales sales) {
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		
		/*
		if (user.getId() == null)
			session.save(user);
		else
			session.update(user);
		*/
		
		session.saveOrUpdate(sales);
		
		session.getTransaction().commit();
		
		session.close();
		
	}
	
	public void remove(Sales sales) {
		
		Session session = HibernateUtil.getSession();
		
		session.beginTransaction();
		session.delete(sales);	
		session.getTransaction().commit();
		
	}
	
	public List pagination(int page, int rows, String sort, String order
			// , String name, String team
			,String filterdatafield, String filtervalue
			) {
	
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		// String hql = "FROM User order by id";
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Sales");
	
		/*
		sb.append(" where") ;
		sb.append(" name like '%" + name + "%'");
		
			sb.append(" and team like '%" + team + "%'");		
		*/
		
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
	
	public Long getTotal() {
		
		String hql = "Select count (t.id) from Sales t";
		
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery(hql);
		
		return  (Long) q.uniqueResult();
	}
	
	public List getByName(String name) {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Sales");
		
		sb.append(" where") ;
			sb.append(" name like '%" + name + "%'");
			// sb.append(" and tel like '%" + tel + "%'");			
			sb.append(" order by id");
		
		// Query query = session.createQuery(hql);
		
		System.out.print(sb.toString());
		
		Query query = session.createQuery(sb.toString());
		
		
		// String hql = "FROM Cust order by id";
		// Query query = session.createQuery(hql);
		
		return query.list();
				
	}
	
	public boolean hasPlan(Long id) {
		
		ArrayList list = null;
		
		Session session = HibernateUtil.getSession();
		
		StringBuilder sb = new StringBuilder();		
		sb.append( "FROM Plan");
		
		sb.append(" where") ;
			sb.append(" sales.id = " + id);
					
		System.out.print(sb.toString());
		
		Query query = session.createQuery(sb.toString());
			
		if (query.list().size()> 0 )		
			return true ;				
	
	    return false;
				
	}
}
