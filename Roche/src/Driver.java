import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

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
		File irrelevant = new File(MASTER_FOLDER_PATH);
		if(! irrelevant.isDirectory()) irrelevant.mkdir();
		return MASTER_FOLDER_PATH;
	}
	
	public static String getSubFolder(){
		File irrelevant = new File(getMasterFolder() + "//" + projectNumber);
		if(! irrelevant.isDirectory()) irrelevant.mkdir();
		return (MASTER_FOLDER_PATH + "//" + projectNumber);
	}
	
	public static String getSystemFolder(){
		File irrelevant = new File(getMasterFolder() + "//system");
		if(! irrelevant.isDirectory()) irrelevant.mkdir();
		return (MASTER_FOLDER_PATH + "/system");
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("java.library.path"));
		System.setProperty("java.library.path", "C://Users//Chris//Desktop");
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
		        MASTER_FOLDER_PATH = chooser.getSelectedFile().getPath();
		        System.out.println(chooser.getSelectedFile().getPath());
		    }else {
		        System.out.println("No Selection");
		    }
		    pw.close();
			/*pw = new BufferedWriter(new FileWriter(logFile));
			boolean fileChosen = false;
			javax.swing.JFrame fileChooser = new javax.swing.JFrame();
			while(fileChosen == false){
				fileChooser.setSize(300, 700);
		        FileDialog chooser = new FileDialog(fileChooser, "Select your master folder"); 
			    chooser.setDirectory(".");
			    chooser.setMultipleMode(false);
			    fileChooser.setVisible(true);
			    chooser.setVisible(true);
			    if (chooser.getFile() != null && new File(chooser.getFile()).isDirectory()) { 
			        try {
						pw.write(chooser.getFile());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        MASTER_FOLDER_PATH = chooser.getFile();
			        System.out.println(chooser.getFile());
			        pw.close();
			        fileChosen = true;
			    }
			}
			*/
		}
		File f = new File(getSystemFolder() + "//chargeNumbers.csv");
		if(! f.exists()) f.createNewFile();
		f = new File(getSystemFolder() + "//desiredTests.csv");
		if(! f.exists()) f.createNewFile();
		//if(! new File(getSystemFolder() + "//chargeNumbers.csv").exists()) new File(getSystemFolder() + "//chargeNumbers.csv").createNewFile();
        //if(! new File(getSystemFolder() + "//desiredTests.csv").exists()) new File(getSystemFolder() + "//desiredTests.csv").createNewFile();
		while(true){
		LoginGUI l = new LoginGUI();
		new GUI(l.getName(), l.getPhoneNum(), l.getEmail());
		System.out.println("Check");
		}
	}
}

/* TO DO LIST
 * 
 * Will
 * 
 * Make screen bigger (100% height, 80% width)
 * Expand picture window to include full picture
 * 
 * Chris
 * 
 * Print label
 * 
 * For Later
 * 
 * Pulldown for preeestablished users
 * Roche Logo
 */