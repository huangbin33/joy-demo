package cn.joy.demo.external.spring;

import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class DynamicLocalSessionFactoryBean extends LocalSessionFactoryBean   
        implements ApplicationListener {   
        //实现ApplicationListener在新建表的同时通知该类更新SessionFactory   
  
    @Override  
    protected SessionFactory newSessionFactory(Configuration config)   
            throws HibernateException {   
        File xmlFile = new File("e:/Content_1.hbm.xml");   
        logger.info("添加动态映射文件*.hbm.xml到Configuration中...");   
        config.addFile(xmlFile);   
        logger.info("重建buildSessionFactory...");   
        return config.buildSessionFactory();   
    }   
  
    public void onApplicationEvent(ApplicationEvent event) {   
        /*if (event instanceof SessionFactoryChangeEvent) {   
            rebuildSessionFactory();   
        }   */
    }   
  
    /**  
     * 重新加载hibernate映射  
     */  
    protected synchronized void rebuildSessionFactory() {   
        destroy();   
        try {   
            afterPropertiesSet();   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
    }   
}  