package cn.joy.demo.external.scheduler.quartz;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

public class QuartzTest {
	/*
	public static void testSimpleTrigger(String[] args) {

		// 通过schedulerFactory获取一个调度器
		SchedulerFactory schedulerfactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			// 通过schedulerFactory获取一个调度器
			scheduler = schedulerfactory.getScheduler();

			// 创建jobDetail实例，绑定Job实现类
			// 指明job的名称，所在组的名称，以及绑定job类
			JobDetail jobDetail = new JobDetail("job1", "jgroup1", MyJob.class);

			// 定义调度触发规则，比如每1秒运行一次，共运行8次
			SimpleTrigger simpleTrigger = new SimpleTrigger("simpleTrigger", "triggerGroup");
			// 马上启动
			simpleTrigger.setStartTime(new Date());
			// 间隔时间
			simpleTrigger.setRepeatInterval(1000);
			// 运行次数
			simpleTrigger.setRepeatCount(8);

			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, simpleTrigger);

			// 启动调度
			scheduler.start();
			
			System.out.println(scheduler);
			System.out.println(simpleTrigger.getStartTime());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}*/

	public static void main(String[] args) throws Exception {
		Scheduler sched = null;
		String schedulerName = "myJobScheduler";

		Properties quartzProp = new Properties();
		quartzProp.put("org.quartz.scheduler.instanceName", schedulerName);
		quartzProp.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		quartzProp.put("org.quartz.threadPool.threadCount", "10");
		quartzProp.put("org.quartz.threadPool.threadPriority", "5");
		quartzProp.put("org.quartz.jobStore.misfireThreshold", "60000");

		StdSchedulerFactory schedFact = new StdSchedulerFactory(quartzProp);
		sched = schedFact.getScheduler();

		String groupName = "test.scanner";
		String testCron = "0/5 * * * * ?";

		//JobDetail sendScannerJob = new JobDetail("sendScannerJob", groupName, MyJob.class);
		//CronTrigger trigger = new CronTrigger("sendScannerTrigger", groupName, "sendScannerJob", groupName, testCron);
		
		JobDetail sendScannerJob = JobBuilder.newJob(MyJob.class).withIdentity("sendScannerJob", groupName).build(); 
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("sendScannerTrigger", groupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(testCron)).build();
		
		//sched.addJob(sendScannerJob, true);
		sched.scheduleJob(sendScannerJob, trigger);

		sched.start();
		
		//while(true){
		Thread.sleep(8000);
			Date now = new Date();
			List<String> grpNames = sched.getJobGroupNames();
			for(String grp:grpNames){
				sendScannerJob = sched.getJobDetail(new JobKey("sendScannerJob", grp));
				System.out.println("\nJob："+sendScannerJob.getKey()+", "+sendScannerJob.getJobClass()+"\n");
				trigger = (CronTrigger)sched.getTrigger(new TriggerKey("sendScannerTrigger", grp));
				System.out.println("开始触发时间："+trigger.getStartTime()+"\n");
				System.out.println("上次触发时间："+trigger.getPreviousFireTime()+"\n");
				System.out.println("下次触发时间："+trigger.getNextFireTime()+"\n");
				Date nextFireTime = trigger.getNextFireTime();
				//if(nextFireTime.before(now)){
					//trigger.setStartTime(now);
					//sched.removeTrigger("sendScannerTrigger", grp);
					//sched.unscheduleJob("sendScannerTrigger", grp);
					//trigger = new CronTrigger("sendScannerTrigger", grp, "sendScannerJob", grp, "0/15 * * * * ?");
					//sched.scheduleJob(trigger);
					System.out.println("trigger sendScannerJob, now="+now+", nextFireTime="+trigger.getNextFireTime()+"\n");
				//}
			
				GroupMatcher<JobKey> groupMatcher = GroupMatcher.groupEquals(groupName);  
				sched.pauseJobs(groupMatcher); 
				Thread.sleep(8000);
				sched.resumeJobs(groupMatcher);
			}
		//}
		
		
	}

}
