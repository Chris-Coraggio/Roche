import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Document {
	
	private String sampleName;
	private String description;
	private int chargeNumber;
	private ArrayList<String> desiredTests;
	public static ImageIcon picture;
	private int problemID;
	private String submitterName;
	private String submitterPhone;
	private String submitterEmail;
	
	public Document(){}
	
	public Document(String sampleName, String description, int chargeNumber, ArrayList<String> desiredTests,
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
	}
	
	public void makeLabel() throws java.io.IOException{
		boolean fileExists = new File(Driver.getMasterFolder() + "//blah.csv").exists();
		FileWriter f = new FileWriter(new File(Driver.getMasterFolder() + "//blah.csv"), true); //appends
		if(! fileExists) f.write("Sample Name, Charge Number, Submitter Name, Submitter Phone, Submitter Email, Problem ID\n");
		f.write("" + sampleName + ", " + chargeNumber + ", " + submitterName + ", " + submitterPhone + ", " + submitterEmail + "\n");
		f.close();
		annotatePicture();
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
        BufferedImage image = ImageIO.read(new File(path));
        BufferedImage imageWithMargins = new BufferedImage(image.getWidth() * 4/3, image.getHeight(), image.getType());
        /*
        Graphics g = newImage.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,image.getWidth()+2*w,image.getHeight());
        g.drawImage(image, w, 0, null);
        g.dispose();
        */
        Graphics g = imageWithMargins.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.setColor(Color.WHITE);
        g.fillRect(image.getWidth(), 0, image.getWidth() /3, image.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        drawTheString(g, this.toString(), image.getWidth(), 0);
        g.dispose();
        
        ImageIO.write(imageWithMargins, "jpg", new File(path.substring(0, path.indexOf(".jpg")) + "-modified.jpg"));
	}
	
	void drawTheString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	public int generateID() throws IOException{ //Will - override
		int num = 0;
		File file = new File(Driver.getMasterFolder() + "//idCounter.txt");
		System.out.println("YO: " + file.exists());
		
		
		java.util.Scanner scan = new java.util.Scanner(file);
		try{
			num = Integer.valueOf(scan.nextLine());
			System.out.println("It was: " + num);
		}catch(NoSuchElementException err){
			FileWriter write1 = new FileWriter(file);
			write1.write("0");
			write1.close();
		}
		FileWriter writer = new FileWriter(file, false);

		num++;
		writer.write(Integer.toString(num));
		writer.close();
		scan.close();
		System.out.println("It is now: " + num);
		return num;
	}
	
	
	public String toString(){
		return( "\nID:\t" + problemID +
				"\nSample Name:\t" + sampleName +
				"\nDescription:\t" + description +
				"\nCharge Number:\t" + chargeNumber +
				"\nDesired Tests:\t" + desiredTests +
				"\nSubmitter Name:\t" + submitterName +
				"\nSubmitter Phone:\t" + submitterPhone +
				"\nSubmitter Email:\t" + submitterEmail
				);
	}
}
