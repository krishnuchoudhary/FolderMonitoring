import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FolderMonitorService implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		fileCopyInto();
	}

	       public void fileCopyInto() {
	    	   Path temp = Paths.get("C:/FolderMonitor/temp");
	    	   
	    		Path secured = Paths.get("C:/work/secured");
	    		Date d=new Date();
	    		System.out.println("The Scheduler run in every 5 Min -------:"+d);
	    		
	    	 System.out.println("In Copying all files from temp to secured folder");
	    			File folder = new File(temp.toString());
	    	 
	    			File[] listOfFiles = folder.listFiles();
	    	 
	    			try {
	    				if (listOfFiles.length > 0) {
	    	 
	    					for (File file : listOfFiles){	
	    					
	    						File source = new File(temp + "\\" + file.getName());
	    	 
	    						File destination = new File(secured + "\\" + file.getName());
	    	 
	    						Files.copy(source.toPath(), destination.toPath(), 
	    								StandardCopyOption.REPLACE_EXISTING);
	    					}
	    				}
	    				
	    			} catch (IOException e) {			
	    				e.printStackTrace();
	    			} catch (Exception ex){
	    				ex.printStackTrace();
	    			}
	    		
	       }
}
