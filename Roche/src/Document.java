import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Document {
	
	private String sampleName;
	private String description;
	private int chargeNumber;
	private ArrayList<String> desiredTests;
	private ImageIcon picture;
	private int problemID;
	private String submitterName;
	private String submitterContact;
	
	public Document(){}
	
	public Document(String sampleName, String description, int chargeNumber, ArrayList<String> desiredTests,
			ImageIcon picture, String submitterName, String submitterContact){
		this.sampleName = sampleName;
		this.description = description;
		this.chargeNumber = chargeNumber;
		this.desiredTests = desiredTests;
		this.picture = picture;
		this.submitterName = submitterName;
		this.submitterContact = submitterContact;
		this.problemID = generateID();
	}
	
	public void makeLabel() throws java.io.IOException{
		FileWriter f = new FileWriter(new File("C://Users//Chris//Desktop//blah.csv"));
		f.write("Sample Name, Charge Number, Submitter Name, Submitter Contact, Problem ID\n");
		f.write("" + sampleName + ", " + chargeNumber + ", " + submitterName + ", " + submitterContact + "\n");
		f.close();
	}
	
	public int generateID(){ //WILL - override this method :)
		int genID = 0;
		File h = new File("C://Users//williamhahn//Desktop//willthiswork.csv");
		if (h.exists()){
			FileReader j = new FileReader(h);
			if (j.read() != -1){
				genID = j.read();	
			}
			j.close();
			genID += 1;
			System.out.println(genID);
		}
		
		FileWriter g = new FileWriter(h);
		g.write(Integer.toString(genID));
		g.close();
		return genID;
	}
	
}
