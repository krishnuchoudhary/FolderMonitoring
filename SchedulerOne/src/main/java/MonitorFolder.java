import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MonitorFolder implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("This is Scheduler for Monitor Secured Folder");
		Path securedFolder = Paths.get("C:/FolderMonitor/secured");

		Path archiveFolder = Paths.get("C:/FolderMonitor/archive");
		
		   startMonitor(securedFolder,archiveFolder);
	}
	
	    public void startMonitor(Path src,Path arch) {
			
			long folderSize = 0;

			try{				
				folderSize = findSecuredFolderSize(src.toString());

				System.out.println("Size of Secured Folder is :" + folderSize);

				if (folderSize > 900000){

					archiveOldFiles(src.toString(), arch.toString());
				}
				
			} catch (Exception e){
				e.printStackTrace();
			}		
	    }
	    private static void archiveOldFiles(String securedFolderPath, 
				String archiveFolderPath) {

			System.out.println("Archiving Older Files Started.....");

			long folderSize = 0;

			File folder = new File(securedFolderPath);

			File[] listOfFiles = folder.listFiles();

			List<File> listOfArchiveFiles = new ArrayList<File>();	
			Arrays.sort(listOfFiles, new Comparator<File>(){

				public int compare(File file1, File file2) {

					return Long.valueOf(file2.lastModified()).compareTo(file1.lastModified());
				}			
			});			
			if (listOfFiles.length > 0) {	
			
				for (File file : listOfFiles){		
			
					folderSize += file.length();	
				
					if (folderSize > 900000){	
					
						listOfArchiveFiles.add(file);
					}
				}
				System.out.println("No. of files to be archived : " + listOfArchiveFiles.size());

				moveFilesToArchiveFolder(listOfArchiveFiles.toArray(), securedFolderPath, archiveFolderPath);
			}		
		}
	    private static void moveFilesToArchiveFolder(
				Object[] listOfArchiveFiles, String securedFolderPath, 
				String archiveFolderPath) {

			System.out.println("Moving older files to archive folder...");
		
			int archivedFilesCount = 0;

			try{
				for(int i=0; i<listOfArchiveFiles.length; i++){	
				
					File sharedFolderFile = new File(listOfArchiveFiles[i].toString());	
				
					File source = new File(securedFolderPath + "\\" + sharedFolderFile.getName());

					File destination = new File(archiveFolderPath + "\\" + sharedFolderFile.getName());
					Files.move(source.toPath(), destination.toPath(), 
							StandardCopyOption.ATOMIC_MOVE);

					archivedFilesCount += 1;

					System.out.println("Number of files archived: " + archivedFilesCount);
				}
				System.out.println("Total count of archived files : " + archivedFilesCount);
			}
			catch (IOException e) {			
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}		
		}
	    
		private static long findSecuredFolderSize(String securedFolderPath) {	
			
			System.out.println("Get current secured folder size--------");

			long size = 0;

			File folder = new File(securedFolderPath);

			File[] listOfFiles = folder.listFiles();

			if (listOfFiles.length > 0) {

				for (File file : listOfFiles){

					if (file.toString().contains(".bat") || file.toString().contains(".sh")){

						file.delete();

						System.out.println("File deleted :" + file.getName());

						continue;
					}					
					if(file.isFile()){	
							
						size += file.length();
					}				
				}			
			}		
			return size;
			
		}
		
}
