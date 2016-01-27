import java.awt.Button;
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
			ImageIcon picture, String submitterName, String submitterContact) throws IOException{
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
		ImageIO.write(bi, "jpg", new File(System.getProperty("user.home") + "\\Desktop\\Roche\\" + name + ".jpg"));
		return(System.getProperty("user.home") + "\\Desktop\\Roche\\" + name + ".jpg");
	}
	
	public void annotatePicture(){
		// http://research.cs.queensu.ca/~blostein/java.html
		JButton label = new JButton(this.toString(), picture);
		label.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		String path = "";
		try {
			path = savePicture("test2");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[][][] testImage = loadImage(path);
		final int MAXROWS = testImage.length;
        final int MAXCOLS = testImage[0].length;
        for (int row=MAXROWS * 2 / 3; row<MAXROWS; row++) {
            for (int col=MAXCOLS * 2 /3; col<MAXCOLS; col++) {
                testImage[row][col][1] = 255;
            }  // for col
        }  // for row
        System.out.println("The top half of the image is green.");
        saveImage(testImage, path + "-modified.bmp");
	}
	
	public int generateID() throws IOException{ //WILL - override this method :)
		/*int genID = 0;
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
		*/
		return 0;
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
	
	public static int[][][] loadImage(String path) {
        Image img = (Image)null;  // create a null Image

        
        try{

                img = Toolkit.getDefaultToolkit().getImage(path);


                if (img != null) {
                     // Make sure the entire image is loaded before continuing 
                     Button b = new Button();  // Create a button to use as a paraemter
                                              // to the constructor for MediaTracker.
                    MediaTracker tracker = new MediaTracker(b);
                    tracker.addImage(img,0);
                    tracker.waitForID(0);

                    // Create "observer", an object that allows us to
                      // use getWidth and getHeight.
                    iObserver observer = new iObserver();
                    int width = img.getWidth(observer);
                    int height = img.getHeight(observer);
        
                    if(width==-1 || height==-1){
                        // the image has not loaded.
                        img = (Image)null;
                    }
                }  // if img != null
        }

        // Catch InterruptedException for tracker.waitfor(), and catch
        // IOException for the console operations.
        catch(InterruptedException e) {
            System.out.println(e);
            System.exit(1);
        }

        // Translate from Image img to a 3D array "imagePixels".
        // Using this 3D array, imagePixels[r][c][w] gives the value
        // of row r, column c, colour w.
        int[][][] imagePixels = getImagePixels(img);
        return imagePixels;
    } // end of method loadImage
	
	public static void saveImage(int[][][] imagePixels, String fileName){
        int height = imagePixels.length;
        int width = imagePixels[0].length;
        int[][] flat = new int[width*height][4];

        // Flatten the image into a 2D array.
        int index=0;
        for(int row=0; row<height; row++) {
            for(int col=0; col<width; col++) {
                for(int rgbo=0; rgbo<4; rgbo++) {
                    flat[index][rgbo]=imagePixels[row][col][rgbo];
                }
                index++;
            }  // for col
        }  // for row

        // Combine the 8-bit red, green, blue, offset values into 32-bit words.
        int[] outPixels = new int[flat.length];
        for(int j=0; j<flat.length; j++) {
            outPixels[j] = ((flat[j][0]&0xff)<<16) | ((flat[j][1]&0xff)<<8)
                            | (flat[j][2]&0xff) | ((flat[j][3]&0xff)<<24);
        } // for j

        // Write the data out to file with the name given by string saveName.
        BMPFile bmpf = new BMPFile();
        bmpf.saveBitmap(fileName, outPixels, width, height);
            System.out.println("Saved " + fileName);
    }  // end of method saveImage
	
	private static int[][][] getImagePixels(Image img) {

        // Get the raw pixel data 
        iObserver observer = new iObserver();
        int width1 = img.getWidth(observer);
        int height1 = img.getHeight(observer);
        int[] rawPixels = utils.getPixels(img,width1,height1);

        // Each pixel is represented by 32 bits.  Separate the tH32 bits into
        // four 8-bit values (red, green, blue, offset).
        int[][] rgbPixels = new int[rawPixels.length][4];
        for(int j=0; j<rawPixels.length; j++) {
            rgbPixels[j][0] = ((rawPixels[j]>>16)&0xff);
            rgbPixels[j][1] = ((rawPixels[j]>>8)&0xff);
            rgbPixels[j][2] = (rawPixels[j]&0xff);
            rgbPixels[j][3] =((rawPixels[j]>>24)&0xff);
        }  // for j

        // Arrange the data by rows and columns
        int[][][] imagePixels = new int[height1][width1][4];
        int index=0;
        for(int row=0; row<imagePixels.length; row++) {
            for(int col=0; col<imagePixels[0].length; col++) {
                for(int rgbo=0; rgbo<4; rgbo++) {
                    imagePixels[row][col][rgbo]=rgbPixels[index][rgbo];
                } // for rgbo
                index++;
            } // for col
        }  // for row
        return imagePixels;
    } // end of method getImagePixels

}
