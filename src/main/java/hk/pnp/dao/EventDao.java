package hk.pnp.dao;

import hk.pnp.HibernateUtil;
import hk.pnp.persistence.Cust;
import hk.pnp.persistence.Event;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class EventDao {

	public void save(Event event) {
		Session session = HibernateUtil.getSession();

		session.beginTransaction();

		/*
		 * if (user.getId() == null) session.save(user); else
		 * session.update(user);
		 */

		session.saveOrUpdate(event);

		session.getTransaction().commit();

		session.close();

	}

	public List searchAll() {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		String hql = "FROM Event order by id";
		Query query = session.createQuery(hql);

		return query.list();

	}

	// public List paginationUser(int page, int rows) {
	public List paginationUser(int page, int rows, String sort, String order) {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		// String hql = "FROM User order by id";

		StringBuilder sb = new StringBuilder();
		sb.append("FROM Event");

		if (sort == null)
			sb.append(" order by id");
		else
			sb.append(" order by " + sort + " " + order);

		// Query query = session.createQuery(hql);
		Query query = session.createQuery(sb.toString());

		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);

		return query.list();

	}

	public Long getTotal() {

		String hql = "Select count (f.id) from Event f";

		Session session = HibernateUtil.getSession();
		Query q = session.createQuery(hql);

		return (Long) q.uniqueResult();
	}

	public void remove(Event event) {

		Session session = HibernateUtil.getSession();

		session.beginTransaction();
		session.delete(event);
		session.getTransaction().commit();

	}

	public List findListByProperty(String field, String value) {

		/*
		 * http://stackoverflow.com/questions/14977018/jpa-how-to-get-entity-based
		 * -on-field-value-other-than-id
		 */

		Session session = HibernateUtil.getSession();
		Criteria c = session.createCriteria(Event.class);
		List list = c.add(Restrictions.eq(field, value)).list();

		return list;
	}

	public Event getById(Long event_id) {

		Session session = HibernateUtil.getSession();
		Event event = (Event) session.get(Event.class, event_id);

		return event;

	}

	public List getByPlanId(Long plan_id) {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		String hql = "FROM Event where plan_id = " + plan_id.toString()
				+ " order by id ";

		Query query = session.createQuery(hql);

		return query.list();

	}

	public List getByAlarmOn() {

		ArrayList list = null;

		Session session = HibernateUtil.getSession();

		String hql = "FROM Event E where alarm !=null and alarm < current_date() + 1 " + " order by alarm desc ";

		Query query = session.createQuery(hql);

		return query.list();
	}

}
