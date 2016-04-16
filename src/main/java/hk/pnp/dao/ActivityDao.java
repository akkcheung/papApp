package hk.pnp.dao;

import hk.pnp.HibernateUtil;
import hk.pnp.persistence.Activity;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.Event;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ActivityDao {

	public void save(Activity o) {
		Session session = HibernateUtil.getSession();

		session.beginTransaction();

		/*
		 * if (user.getId() == null) session.save(user); else
		 * session.update(user);
		 */

		session.saveOrUpdate(o);

		session.getTransaction().commit();

		session.close();

	}

	public List searchAll() {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		String hql = "FROM Activity order by id";
		Query query = session.createQuery(hql);

		return query.list();

	}

	public List getPageByPlanId(Long plan_id, 
			int page, int rows, String sort, String order,
			 String filterdatafield, String filtervalue) {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		// String hql = "FROM User order by id";

		StringBuilder sb = new StringBuilder();
		sb.append("FROM Activity ");

		sb.append(" where plan.id = " + plan_id.toString());	
		
		if (! filterdatafield.equals("")) {
			sb.append(" and ") ;
			
			if (! filterdatafield.equals("finComp") ) 
				sb.append(filterdatafield + " like '%" + filtervalue + "%'");			
			else 
				sb.append(filterdatafield + ".name" + " like '%" + filtervalue + "%'");
			
		}
		
		if (sort == null || sort.equals("")) 
			// sb.append(" order by id");			
			sb.append(" order by finComp_id, id ");			
		else
			sb.append(" order by " + sort + " " + order);
		
		Query query = session.createQuery(sb.toString());

		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);

		return query.list();

	}

	public Long getTotal() {

		String hql = "Select count (f.id) from Activity f";

		Session session = HibernateUtil.getSession();
		Query q = session.createQuery(hql);

		return (Long) q.uniqueResult();
	}

	public void remove(Activity o) {

		Session session = HibernateUtil.getSession();

		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();

	}

	public List findListByProperty(String field, String value) {

		/*
		 * http://stackoverflow.com/questions/14977018/jpa-how-to-get-entity-based
		 * -on-field-value-other-than-id
		 */

		Session session = HibernateUtil.getSession();
		Criteria c = session.createCriteria(Activity.class);
		List list = c.add(Restrictions.eq(field, value)).list();

		return list;
	}

	public Activity getById(Long id) {

		Session session = HibernateUtil.getSession();
		Activity obj = (Activity) session.get(Activity.class, id);

		return obj;

	}

	public List getByPlanIdFinCompId(Long plan_id, Long finComp_id) {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		String hql = "FROM Activity where "
				+ " and plan_id = " + plan_id.toString()
				+ " and finComp_id = " + finComp_id.toString()
				+ " order by id ";

		Query query = session.createQuery(hql);

		return query.list();

	}

	
	public List getByPlanId(Long plan_id) {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		String hql = "FROM Activity where plan_id = " + plan_id.toString()
				+ " order by finComp_id, id ";

		Query query = session.createQuery(hql);

		return query.list();

	}
	
	public List getByAlarmOn() {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		String hql = "FROM Activity E where alarm !=null and alarm < current_date() + 1 " + " order by alarm desc ";

		Query query = session.createQuery(hql);

		return query.list();
	}

}
