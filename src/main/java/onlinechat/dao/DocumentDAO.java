package onlinechat.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import onlinechat.entity.Document;
import onlinechat.util.Hibernateutil;

public class DocumentDAO {
	
	
	public static void save(Document document) {
		Session session=Hibernateutil.createSession();
		Transaction t = session.beginTransaction();    
		session.save(document);    
		  
		t.commit();    
		session.close();
	}
	
	public static List<Document> list() {
		Session session = Hibernateutil.createSession();
		List<Document> documents = null;
		try {
			documents = (List<Document>)session.createQuery("from Document").list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return documents;
	}
	
	public static Document get(Integer id) {
		Session session = Hibernateutil.createSession();
		return (Document)session.get(Document.class, id);
	}


	public static void remove(Integer id) {
		Session session = Hibernateutil.createSession();
		Transaction t = session.beginTransaction();    
		Document document = (Document)session.get(Document.class, id);
		
		session.delete(document);
		t.commit();    
		session.close();
		
	}
}