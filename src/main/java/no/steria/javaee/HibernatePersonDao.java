package no.steria.javaee;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.context.ThreadLocalSessionContext;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class HibernatePersonDao implements PersonDao {

	private SessionFactory sessionFactory;

	public HibernatePersonDao(String dsJndiName) {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.setProperty(Environment.DATASOURCE, dsJndiName);
		cfg.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, ThreadLocalSessionContext.class.getName());
		cfg.addAnnotatedClass(Person.class);
		sessionFactory = cfg.buildSessionFactory();
	}

	@Override
	public void beginTransaction() {
		getSession().beginTransaction();
	}

	@Override
	public void createPerson(Person person) {
		getSession().save(person);
	}

	@Override
	public void endTransaction(boolean commit) {
		if (commit) {
			getSession().getTransaction().commit();
		} else {
			getSession().getTransaction().rollback();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findPeople(String nameQuery) {
		Criteria criteria = getSession().createCriteria(Person.class);
		if (nameQuery != null) {
			criteria.add(Restrictions.ilike("name", nameQuery, MatchMode.ANYWHERE));
		}
		return criteria.list();
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}