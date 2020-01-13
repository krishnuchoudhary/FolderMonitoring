import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerTwo  {
	 public void runSchedulerForMin() {
    	 JobDetail job = JobBuilder.newJob(MonitorFolder.class)
 				.withIdentity("anyJobName11", "group2").build();
 		System.out.println("This is test for scheduleing");
 		//ScheduleJob sb =new ScheduleJob();
 		try {     Trigger trigger = TriggerBuilder.newTrigger().withIdentity("anyTriggerName1", "group2")
 				  .withSchedule(
 				     CronScheduleBuilder.cronSchedule("0 0/2 * 1/1 * ? *"))
 				  .build();

 				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
 				scheduler.start();
 				scheduler.scheduleJob(job, trigger);

 			} catch (SchedulerException e) {
 				e.printStackTrace();
 			}
     }

}
