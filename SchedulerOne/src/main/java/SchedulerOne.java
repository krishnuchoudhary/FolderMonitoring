import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerOne {
     public static void main(String args[]) {
    	 JobDetail job = JobBuilder.newJob(FolderMonitorService.class)
 				.withIdentity("anyJobName", "group1").build();
 		System.out.println("This is test for scheduleing");
 		//ScheduleJob sb =new ScheduleJob();
 		try {     Trigger trigger = TriggerBuilder.newTrigger().withIdentity("anyTriggerName", "group1")
 				  .withSchedule(
 				     CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *"))
 				  .build();

 				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
 				scheduler.start();
 				scheduler.scheduleJob(job, trigger);

 			} catch (SchedulerException e) {
 				e.printStackTrace();
 			}
 		
 		   SchedulerTwo st=new SchedulerTwo();
 		   st.runSchedulerForMin();
     }
}
