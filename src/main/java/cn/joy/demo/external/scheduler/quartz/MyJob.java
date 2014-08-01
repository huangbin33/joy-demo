package cn.joy.demo.external.scheduler.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {  
    //把要执行的操作，写在execute方法中  
    public void execute(JobExecutionContext arg0) throws JobExecutionException {  
        System.out.println("测试Quartz"+new Date());  
    }  
    
}  
