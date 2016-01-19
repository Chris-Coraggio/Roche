import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	private String submitterContact;
	
	//Fix annotation of image
	
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
		FileWriter f = new FileWriter(new File(System.getProperty("user.home") + "//Desktop//Roche//blah.csv"));
		f.write("Sample Name, Charge Number, Submitter Name, Submitter Contact, Problem ID\n");
		f.write("" + sampleName + ", " + chargeNumber + ", " + submitterName + ", " + submitterContact + "\n");
		f.close();
	}
	
	public void savePicture(String name) throws IOException{
		BufferedImage bi = new BufferedImage(
			    picture.getIconWidth(),
			    picture.getIconHeight(),
			    BufferedImage.TYPE_INT_RGB);
			java.awt.Graphics g = bi.createGraphics();
			// paint the picture to the BufferedImage
			picture.paintIcon(null, g, 0,0);
			g.dispose();
		ImageIO.write(bi, "jpg", new File(System.getProperty("user.home") + "\\Desktop\\Roche\\" + name + ".jpg"));
	}
	
	public void annotatePicture(){
		JButton label = new JButton(this.toString(), picture);
		label.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		try {
			savePicture("test2");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int generateID() throws IOException{ //WILL - override this method :)
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
	
	
	public String toString(){
		return( "Sample Name:\t" + sampleName +
				"\nDescription:\t" + description +
				"\nCharge Number:\t" + chargeNumber +
				"\nDesired Tests:\t" + desiredTests +
				"\nSubmitter Name:\t" + submitterName +
				"\nSubmitter Contact:\t" + submitterContact +
				"\n ID:\t" + problemID);
	}
	
	public class annotatedImage extends javax.swing.JPanel{
		@Override
		protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage((java.awt.Image)Document.picture, 0, 0, this);
            
	}
	}
}
