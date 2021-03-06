import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Document {
	
	private String sampleName;
	private String description;
	private String chargeNumber;
	private ArrayList<String> desiredTests;
	public static ImageIcon picture;
	private int problemID;
	private String submitterName;
	private String submitterPhone;
	private String submitterEmail;
	private String concatenatedDesiredTests;
	public Document(){}
	
	public Document(String sampleName, String description, String chargeNumber, ArrayList<String> desiredTests,
			ImageIcon picture, String submitterName, String submitterPhone, String submitterEmail) throws IOException{
		this.sampleName = sampleName;
		this.description = description;
		this.chargeNumber = chargeNumber;
		this.desiredTests = desiredTests;
		this.picture = picture;
		this.submitterName = submitterName;
		this.submitterPhone = submitterPhone;
		this.submitterEmail = submitterEmail;
		this.problemID = generateID();
		this.concatenatedDesiredTests = concatenatedTests();
		
	}
	public String getID(){
		return "" + problemID;
	}
	
	public String getName(){
		return sampleName;
	}
	
	public String getProjectNumber(){
		return "" + Driver.getProjectNum();
	}
	
	public String getSubmitterName(){
		return submitterName;
	}
	
	public ArrayList<String> getTests(){
		return desiredTests;
	}
	
	public void makeLabel() throws java.io.IOException{
		boolean fileExists = new File(Driver.getMasterFolder() + "//Master_Spreadsheet.csv").exists();
		FileWriter f = new FileWriter(new File(Driver.getMasterFolder() + "//Master_Spreadsheet.csv"), true); //appends
		if(! fileExists) f.write("Project Number, Problem ID, Sample Name, Sample Description, Charge Number, Submitter Name, Submitter Phone, Submitter Email, Desired Tests, Desired Tests, Desired Tests, Desired Tests, Desired Tests, Desired Tests, Desired Tests, Desired Tests, Desired Tests, Desired Tests\n");
		f.write("" + Driver.getProjectNum() + ", " + problemID + ", " + sampleName + ", " + description + ", " + chargeNumber + ", "  + submitterName + ", " + submitterPhone + ", " + submitterEmail + ", " + concatenatedDesiredTests + "\n");
		f.close();
		annotatePicture();
	}
	public String concatenatedTests(){
		try {
			String tests = desiredTests.get(0);
			for (int x = 1; x < desiredTests.size(); x++){
				tests = tests + ", " + desiredTests.get(x);
			}
			return tests;
		} catch (IndexOutOfBoundsException e){
			System.out.println("None Are Selected");
			return "No Tests Selected";
		}
		
		
	}
	
	public String savePicture(String name) throws IOException{ //returns path of file made
		BufferedImage bi = new BufferedImage(
			    picture.getIconWidth(),
			    picture.getIconHeight(),
			    BufferedImage.TYPE_INT_RGB);
			java.awt.Graphics g = bi.createGraphics();
			// paint the picture to the BufferedImage
			picture.paintIcon(null, g, 0,0);
			g.dispose();
		ImageIO.write(bi, "jpg", new File(Driver.getSubFolder() + "/" + name + ".jpg"));
		return(Driver.getSubFolder() + "/" + name + ".jpg");
	}
	
	public void annotatePicture() throws IOException{
		
		ArrayList<String> descriptionSplitIntoLines = new ArrayList<String>();
		JButton label = new JButton(this.toString(), picture);
		label.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		String path = "";
		try {
			path = savePicture(sampleName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(path);
		BufferedImage imagedummy = ImageIO.read(new File(path));
        Font fdummy = new Font("TimesRoman", Font.PLAIN, 18);
        
        BufferedImage imageWithMarginsdummy = new BufferedImage(imagedummy.getWidth(), imagedummy.getHeight() * 4/3, imagedummy.getType());
        Graphics gdummy = imageWithMarginsdummy.getGraphics();
        FontMetrics fmdummy = gdummy.getFontMetrics(fdummy);
        BufferedImage image = ImageIO.read(new File(path));
        Font f = new Font("TimesRoman", Font.PLAIN, 18);
        
        BufferedImage imageWithMargins = new BufferedImage(image.getWidth(), image.getHeight() + (fmdummy.getAscent() * 20), image.getType());
        Graphics2D g = imageWithMargins.createGraphics();
        FontMetrics fm = g.getFontMetrics(f);
        /*
        Graphics g = newImage.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,image.getWidth()+2*w,image.getHeight());
        g.drawImage(image, w, 0, null);
        g.dispose();
        */
       
        g.drawImage(image, 0, 0, null);
        g.setColor(Color.WHITE);
        //g.fillRect(image.getWidth(), 0, image.getWidth() /3, image.getHeight());
        g.setColor(Color.WHITE);
        
        
        g.setFont(f);
        
        
        g.fillRect(0, image.getHeight(), image.getWidth(), fm.getAscent() * 20);
        g.setColor(Color.BLACK);
        g.drawString("Sample Name: " + sampleName, 0, image.getHeight() + fm.getAscent());
        g.drawString("Sample ID: " + problemID, 0, image.getHeight() + (fm.getAscent() * 2));
        g.drawString("Project Number: " + Driver.getProjectNum(), 0, image.getHeight() + (fm.getAscent() * 3));
        g.drawString("Tests: " + concatenatedDesiredTests, 0, image.getHeight() + (fm.getAscent() * 4));
        g.drawString("Charge Number: " + chargeNumber, 0, image.getHeight() + (fm.getAscent() * 5));
        g.drawString("Submitter Name: " + submitterName, 0, image.getHeight() + (fm.getAscent() * 6));
        g.drawString("Submitter Phone Number: " + submitterPhone, 0, image.getHeight() + (fm.getAscent() * 7));
        g.drawString("Submitter Email Adress: " + submitterEmail, 0, image.getHeight() + (fm.getAscent() * 8));
        
        String concatenate = "";
        String[] split2;
        //int indexSplit1 = 0;
        int indexSplit2 = 0;
        split2 = description.split(" ");
        	for (String y : split2){
        		concatenate = concatenate + split2[indexSplit2] + " "; 
        		
        		if (concatenate.length() <= 80){
        			indexSplit2++;
        		}
        		else{
        			descriptionSplitIntoLines.add(concatenate);
        			indexSplit2++;
                	
                	concatenate = "";
                	
        		}
        	}
        	
        	
        	
        
        int index = 0;
        int multiplier = 10;
        for (String x : descriptionSplitIntoLines){
        	g.drawString(descriptionSplitIntoLines.get(index) , 0, image.getHeight() + (fm.getAscent() * multiplier));
        	multiplier++;
        	index++;
        }
        g.drawString("Description: ", 0, image.getHeight() + (fm.getAscent() * 9));
        /*drawTheString(g, this.toString().substring(0, this.toString().indexOf("Desired")), 0, image.getHeight());
        drawTheString(g, this.toString().substring(this.toString().indexOf("Desired"), this.toString().indexOf("Submitter")), image.getWidth() / 3, image.getHeight());
        drawTheString(g, this.toString().substring(this.toString().indexOf("Submitter")), image.getWidth() * 2 /3, image.getHeight());*/
        g.dispose();
        
        ImageIO.write(imageWithMargins, "jpg", new File(path.substring(0, path.indexOf(".jpg")) + "-modified.jpg"));
	}
	
	/*void drawTheString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}*/
	
	public int generateID() throws IOException{ //Will - override
		int num = 0;
  		File file = new File(Driver.getSystemFolder() + "//idCounter.txt");
  		java.util.Scanner scan;
  		try{
  			scan = new java.util.Scanner(file);
  		}catch(FileNotFoundException err){
  			file.createNewFile();
  			scan = new java.util.Scanner(file);
  			System.out.println("The situation is under control");
  		}
  		try{
 		num = Integer.parseInt(scan.nextLine());
 			num = Integer.valueOf(scan.nextLine());
 			System.out.println("It was: " + num);
  		}catch(NoSuchElementException err){
 			FileWriter write1 = new FileWriter(file);
			write1.write("0");
 			write1.close();
  		}
  		FileWriter writer = new FileWriter(file, false);
  		num++;
 		file.delete();
  		writer.write(Integer.toString(num));
  		writer.close();
  		scan.close();
 		System.out.println("It is now: " + num);
  		return num;
  	}
	
	public String toString(){
		final int MAX_LENGTH = 10;
		String tests = "";
		//for(String s: desiredTests) tests += (s + "\n");
		int row = 1;
		for (int x = 0; x < desiredTests.size(); x++){
			if (row == 1){
				tests += desiredTests.get(x) + ",      ";
				
			}
			else {
				tests += desiredTests.get(x) + "\n";
				row = 0;
			}
			row ++;
			
		}
		return( "ID:  " + problemID +
				"\nSample Name:  " + (sampleName.length() > MAX_LENGTH ? "\n" : "") + sampleName +
				"\nDescription:  " + (description.length() > MAX_LENGTH ? "\n" : "") + description + 
				"\nCharge Number:  " + chargeNumber +
				"\nDesired Tests:\n" + tests +
				"Submitter Name:  " + (submitterName.length() > MAX_LENGTH ? "\n" : "") + submitterName + 
				"\nSubmitter Phone:  " + (submitterPhone.length() > MAX_LENGTH ? "\n" : "") + submitterPhone + 
				"\nSubmitter Email:  " + (submitterEmail.length() > MAX_LENGTH ? "\n" : "") + submitterEmail
				);
	}
}
