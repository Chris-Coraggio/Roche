import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFileChooser;

public class Driver {
	private static String MASTER_FOLDER_PATH = "";
	private static int projectNumber;
	
	public static int getProjectNum(){
		return (projectNumber > 0 ? projectNumber : 0);
	}
	
	public static void incrProjectNum(){
		projectNumber++;
	}
	
	public static void setProjectNum(int num){
		projectNumber = num;
	}
	
	public static String getMasterFolder(){
		return MASTER_FOLDER_PATH;
	}
	
	public static String getSubFolder(){
		File irrelevant = new File(MASTER_FOLDER_PATH + "//" + projectNumber);
		if(! irrelevant.isDirectory()) irrelevant.mkdir();
		return (MASTER_FOLDER_PATH + "//" + projectNumber);
	}
	
	public static String getSystemFolder(){
		File irrelevant = new File(MASTER_FOLDER_PATH + "//system");
		if(! irrelevant.isDirectory()) irrelevant.mkdir();
		return (MASTER_FOLDER_PATH + "//system");
	}
	
	public static void main(String[] args) throws IOException {
		BufferedWriter pw = null;
		File logFile = new File("./log.txt");
		BufferedReader br = null;
		try{
		br = new BufferedReader(new FileReader(logFile));
		}catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		pw = new BufferedWriter(new FileWriter(logFile));
		System.out.println("Crisis averted");
		}
		try{
			MASTER_FOLDER_PATH = br.readLine();
			br.close();
		}catch(java.lang.NullPointerException err){
			//if the file contains no text, launch master folder picker and write path to file
			pw = new BufferedWriter(new FileWriter(logFile));
	        JFileChooser chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select your master folder");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false); 
		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
		        try {
					pw.write(chooser.getSelectedFile().getPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println(chooser.getSelectedFile().getPath());
		    }else {
		        System.out.println("No Selection");
		    }
		    pw.close();
		}
		
		while(true){
		LoginGUI l = new LoginGUI();
		new GUI(l.getName(), l.getPhoneNum(), l.getEmail());
		}
	}
}

/* TO DO LIST
 * 
 * William
 * 
 * ID Generator (in Document)
 * Pulldown for preestablished users (find that text file)
 * 
 * Chris
 * 
 * Fix closing down in login gui
 * 
 */