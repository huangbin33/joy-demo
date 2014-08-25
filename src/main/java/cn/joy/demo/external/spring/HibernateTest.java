package cn.joy.demo.external.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateTest {
	private static Logger log = Logger.getLogger(HibernateTest.class);

	private static SessionFactory sessionFactory;

	private static ThreadLocal threadLocal = new ThreadLocal();

	public static void main(String[] args) throws Exception {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/springapp.xml");
		HibernateTest bean = (HibernateTest) ctx.getBean("hibernateTest");

		// HibernateTemplate hibernateTemplate = hibernateBean.getHt();
		HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		session.beginTransaction();

		/*
		 * List<TestObj> objs = session.createQuery("from TestObj").list();
		 * System.out.println(objs.get(0).getCcc().getBinaryStream());
		 * 
		 * TestObj o = new TestObj(); o.setBbb("aaa"); File file = new
		 * File("D:/bbs.png");
		 * o.setCcc(Hibernate.getLobCreator(session).createBlob(new
		 * FileInputStream(file), file.length())); session.save(o);
		 */

		// o = (Atzalltype)session.get(Atzalltype.class, o.getId());

		/*
		 * List<Atzsubtype> ss =
		 * (List)session.createQuery("from Atzsubtype where atzalltypeid=35"
		 * ).list(); System.out.println(ss); ss.get(0).setSstr("aaaabb");
		 */

		// o.setAtzsubtypeSet(new HashSet());
		// session.merge(o);
		// System.out.println(s.getAtzalltypeid());
		/*
		 * o.setCstr("abcdddeeff");
		 * 
		 * //o = (Atzalltype)session.get(Atzalltype.class, 71L);
		 * //System.out.println(o.getCstr()); session.flush(); Connection conn =
		 * session.connection(); Statement sta = conn.createStatement();
		 * ResultSet rs =
		 * sta.executeQuery("select cstr from atzalltype where id=71");
		 * if(rs.next()){ System.out.println("jdbc:"+rs.getString(1)); }
		 */

		/*
		 * String hql = "from Atzalltype where id=71"; Query query =
		 * session.createQuery(hql); List<Atzalltype> list = query.list();
		 * System.out.println(list.size()); Atzalltype a = new Atzalltype();
		 * a.setId(711L); a.setCstr("abcde");
		 * a.setActizversion(list.get(0).getActizversion()); session.merge(a);
		 */
		/*
		 * for(Atzalltype o:list){
		 * o.setCstr("abcdef"+Math.floor(Math.random()*1000)); }
		 * session.flush(); session.clear();
		 */

		/*
		 * String sql =
		 * "select atzwuliaojcxx_wuliaobm from VFCCA523FB00000001Z where atzwuliaojcxx_wuliaosjxz = '0' and atzwuliaojcxx_shenhezt = '3' and atzwuliaojcxx_wuliaoxz = '0' and atzwuliaojcxx_shifouqy = '1' and upper(atzwuliaojcxx_wuliaobm) like upper('%54%') and 1 = 1   and ( 1 = 1 ) order by atzwuliaojcxx_zuihouxgrq desc"
		 * ; SQLQuery query = session.createSQLQuery(sql); List list =
		 * query.list(); for(Object o:list){ System.out.println(o); }
		 */
		/*
		 * Atzkehu kehu = new Atzkehu(); session.save(kehu);
		 * System.out.println(kehu.getId()); SessionFactory factory =
		 * session.getSessionFactory(); if(factory instanceof
		 * SessionFactoryImpl&&session instanceof SessionImpl){
		 * SessionFactoryImpl sfi = (SessionFactoryImpl)factory;
		 * System.out.println("Generate Next ID:");
		 * System.out.println(sfi.getEntityPersister
		 * ("spring.domain.Atzkehu").getIdentifierGenerator
		 * ().generate((SessionImpl)session, new Atzkehu()));
		 * System.out.println(
		 * sfi.getEntityPersister("spring.domain.Atzkehu").getIdentifierGenerator
		 * ().generate((SessionImpl)session, new Atzkehu()));
		 * System.out.println(
		 * sfi.getEntityPersister("spring.domain.Atzkehu").getIdentifierGenerator
		 * ().generate((SessionImpl)session, new Atzkehu())); } kehu = new
		 * Atzkehu(); session.save(kehu); System.out.println(kehu.getId());
		 */
		// Atzkehu kehu = (Atzkehu)hibernateTemplate.get(Atzkehu.class, 1111L);
		// Atzalltype old = (Atzalltype)session.get(Atzalltype.class, 1L);
		// old.setCstr("aaabbbbccc");
		// session.flush();
		// session.update(old);
		/*
		 * String hql = "from Atzalltype";
		 * 
		 * Query query = session.createQuery(hql);
		 * //session.setFlushMode(FlushMode.NEVER); //session.flush(); List list
		 * = query.list(); Atzalltype o = (Atzalltype)list.get(0);
		 * System.out.println(list.size());
		 */

		// session.setFlushMode(FlushMode.AUTO);
		// o.setRefall(1L);;
		/*
		 * Atzalltype o = new Atzalltype(); o.setCstr("员工_"+j); session.save(o);
		 * }
		 */
		/*
		 * for(int i=2;i<16;i++){ double maxJ = Math.random()*100+50; for(int
		 * j=1;j<maxJ;j++){ Atzalltype o = new Atzalltype();
		 * o.setCstr("员工_"+i+"_"+j); o.setPalltype((long)i); session.save(o); }
		 * }
		 */

		/*
		 * //ObjectNotFound，session.get也可能出现，先获取对象id为27的对象A，其引用了id为5的对象B，会为B生成一个代理
		 * ，当再去get id为5的对象B时，会使用前面生成的代理，如果id为5的对象B不存在，就会报ObjectNotFound
		 * Atzalltype o = (Atzalltype)session.get(Atzalltype.class, 27L);
		 * System.out.println(o); Atzreftype so =
		 * (Atzreftype)session.get(Atzreftype.class, 5L);
		 * System.out.println(so);
		 */
		/*
		 * //START:模拟getActizEventInstance后，再updateActizEventInstance会报Illegal
		 * attempt to associate a collection with two open sessions
		 * //event相对于session3是游离态，event中的Set在Session2中为持久态，在session3中是游离态
		 * Session session =
		 * hibernateTemplate.getSessionFactory().openSession();
		 * session.beginTransaction();
		 * 
		 * Session session2 =
		 * hibernateTemplate.getSessionFactory().openSession();
		 * session2.beginTransaction(); ActizEventInstance event =
		 * (ActizEventInstance)session2.get(ActizEventInstance.class, 5201L);
		 * 
		 * Session session3 =
		 * hibernateTemplate.getSessionFactory().openSession();
		 * session3.beginTransaction(); //session3.get(ActizEventInstance.class,
		 * 5201L); //ActizEventInstance event =
		 * (ActizEventInstance)hibernateTemplate.get(ActizEventInstance.class,
		 * 5201L); //event.setStatus("已处理1"); session3.update(event);
		 * session3.getTransaction().commit(); session3.close();
		 * 
		 * //event.setAsyn(true); //session2.getTransaction().commit();
		 * //session2.close();
		 * //END:模拟getActizEventInstance后，再updateActizEventInstance会报Illegal
		 * attempt to associate a collection with two open sessions
		 */

		/*
		 * Atzalltype obj = (Atzalltype)session.get(Atzalltype.class, 1L);
		 * Hibernate.initialize(obj.getRef1Ref()); session.close();
		 * System.out.println(obj.getCstr()); System.out.println(obj.getRef1());
		 * System.out.println(obj.getRef1Ref());
		 */

		// START:模拟treeupdate提交后置规则对一个list循环update，中间有flush操作
		/*
		 * List<Atzchandi> list = new ArrayList(); Atzchandi obj =
		 * (Atzchandi)session.get(Atzchandi.class, 1L); list.add(obj); Atzchandi
		 * obj2 = (Atzchandi)session.get(Atzchandi.class, 2L); list.add(obj2);
		 * session.close(); session =
		 * hibernateTemplate.getSessionFactory().openSession();
		 * session.beginTransaction(); for(Atzchandi o:list){ o.setBianhao(1);
		 * session.merge(o); } for(Atzchandi o:list){ o.setName("abcdef");
		 * session.merge(o); session.flush(); }
		 */
		// END:模拟treeupdate提交后置规则对一个list循环update，中间有flush操作

		// START:模拟list4edit updateobjects 在后置规则中修改
		/*
		 * List list = new ArrayList(); Atzchandi obj =
		 * (Atzchandi)session.get(Atzchandi.class, 1L); Atzchandi o = new
		 * Atzchandi(); o.setId(obj.getId()); o.setBianhao(1); o.setName("上海1");
		 * o.setActizversion(obj.getActizversion()); list.add(o); Atzchandi obj2
		 * = (Atzchandi)session.get(Atzchandi.class, 2L); Atzchandi o2 = new
		 * Atzchandi(); o2.setId(obj2.getId()); o2.setName("北京1");
		 * o2.setBianhao(2); o2.setActizversion(obj2.getActizversion());
		 * list.add(o2); for(int i=0;i<list.size();i++){
		 * session.merge(list.get(i)); } //merge是把o拷给obj，o不会变成持久态
		 * o.setBianhao(13); session.merge(o);
		 */
		// END:模拟list4edit updateobjects 在后置规则中修改

		// query.setInteger("age", 18);
		// int count = query.executeUpdate();
		// System.out.println(query.list().size());

		// START:模拟批量add已持久化的entity，数据由n变成2n-1条
		/*
		 * List list = query.list(); session.setCacheMode(CacheMode.IGNORE);
		 * for(int i=0;i<list.size();i++){ Atzchandi obj =
		 * (Atzchandi)list.get(i); obj.setMiaoshu("haha"); session.save(obj);
		 * if(i==0){ session.flush(); session.clear(); } }
		 * session.setCacheMode(CacheMode.NORMAL); session.flush();
		 */
		// END:模拟批量add已持久化的entity，数据由n变成2n-1条
		if (session != null && session.isOpen()) {
			session.getTransaction().commit();
			session.close();
		}
	}

	private static Session getSessionFromThreadLocal() {
		Session session = null;
		Map sessionMap = (Map) (Map) threadLocal.get();
		if (sessionMap != null) {
			session = (Session) sessionMap.get("ss");
		}
		return session;
	}

	private static void setSessionToThreadLocal(Session session) {
		Map sessionMap = (Map) (Map) threadLocal.get();
		if (sessionMap == null) {
			sessionMap = new HashMap();
			threadLocal.set(sessionMap);
		}
		sessionMap.put("ss", session);
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		HibernateTest.sessionFactory = sessionFactory;
	}

}
