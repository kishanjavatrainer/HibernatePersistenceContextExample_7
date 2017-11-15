package com.infotech.client;

import org.hibernate.LockMode;
import org.hibernate.Session;

import com.infotech.entities.Person;
import com.infotech.util.HibernateUtil;

public class ReattachAndMergeEntityClientTest {

	public static void main(String[] args) {

		// reattachEntityUsingLock();
		// reattachEntityUsingSaveOrUpdate();
		mergeDetachedEntityUsingMerge();
	}

	private static void reattachEntityUsingLock() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			long personId = 1L;
			Person person = session.byId(Person.class).load(personId);
			
			//Clear the Session so the person entity becomes detached
			session.clear();
			
			person.setName("Mr. Gavin King");
			
			session.beginTransaction();
			session.lock(person,LockMode.NONE);
			
			session.getTransaction().commit();
			
			System.out.println(person.getName());
			
			person = session.byId(Person.class).load(personId);
			System.out.println(person.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void reattachEntityUsingSaveOrUpdate() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			long personId = 1L;
			Person person = session.byId( Person.class ).load( personId );
			
			//Clear the Session so the person entity becomes detached
			session.clear();
			
			person.setName("Dr. Gavin King");

			session.beginTransaction();
			session.saveOrUpdate( person );
			
			session.getTransaction().commit();
			
			System.out.println(person.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	private static void mergeDetachedEntityUsingMerge() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			long personId = 1L;
			Person person = session.byId( Person.class ).load( personId );
			
			//Clear the Session so the person entity becomes detached
			session.clear();
			person.setName("Mr. Gavin King2");

			session.beginTransaction();
			person = (Person) session.merge( person );
			
			session.getTransaction().commit();
			System.out.println(person.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
