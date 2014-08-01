package cn.joy.demo.external.spring;

import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

/**
 * @author Gavin King
 */
public class DynamicClassTest {


	protected void configure(Configuration cfg) {
		cfg.setProperty(Environment.DEFAULT_ENTITY_MODE, EntityMode.MAP.toString());
	}
	
	public Session openSession(){
		return null;
	}
/*
	public void testLazyDynamicClass() {
		Session s = openSession();
		assertTrue("Incorrectly handled default_entity_mode", s.getEntityMode() == EntityMode.MAP);
		Session other = s.getSession(EntityMode.MAP);
		assertEquals("openSession() using same entity-mode returned new session", s, other);

		other = s.getSession(EntityMode.POJO);
		other.close();
		assertTrue(!other.isOpen());
		assertTrue(other.isConnected()); // because it is linked to the "root"
											// session's connection

		s.close();

		s = openSession();
		Transaction t = s.beginTransaction();

		Map cars = new HashMap();
		cars.put("description", "Cars");
		Map monaro = new HashMap();
		monaro.put("productLine", cars);
		monaro.put("name", "monaro");
		monaro.put("description", "Holden Monaro");
		Map hsv = new HashMap();
		hsv.put("productLine", cars);
		hsv.put("name", "hsv");
		hsv.put("description", "Holden Commodore HSV");
		List models = new ArrayList();
		cars.put("models", models);
		models.add(hsv);
		models.add(monaro);
		s.save("ProductLine", cars);
		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();

		cars = (Map) s.createQuery("from ProductLine pl order by pl.description").uniqueResult();
		models = (List) cars.get("models");
		assertFalse(Hibernate.isInitialized(models));
		assertEquals(models.size(), 2);
		assertTrue(Hibernate.isInitialized(models));

		s.clear();

		List list = s.createQuery("from Model m").list();
		for (Iterator i = list.iterator(); i.hasNext();) {
			assertFalse(Hibernate.isInitialized(((Map) i.next()).get("productLine")));
		}
		Map model = (Map) list.get(0);
		assertTrue(((List) ((Map) model.get("productLine")).get("models")).contains(model));
		s.clear();

		t.commit();
		s.close();

		s = openSession();
		t = s.beginTransaction();
		cars = (Map) s.createQuery("from ProductLine pl order by pl.description").uniqueResult();
		s.delete(cars);
		t.commit();
		s.close();
	}
*/
	protected String[] getMappings() {
		return new String[] { "dynamic/ProductLine.hbm.xml" };
	}


}
