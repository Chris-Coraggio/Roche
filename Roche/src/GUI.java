import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class GUI extends javax.swing.JDialog{

	private static final long serialVersionUID = 1L;
	private Icon picture = null; //instance of picture after most recent picture taken
    private String name, phone, email; //name and contact of whoever logs in
    private boolean isFinished = false;
    private ArrayList<String> chargeNumbers = new ArrayList<String>();
    private ArrayList<String> desiredTests = new ArrayList<String>();
    private List<javax.swing.JCheckBox> testsCheckboxes;
    private Document doc; //store document saved for printing
    private final String DEFAULT_FONT = "Sylfaen";
    private final int DEFAULT_FONT_SIZE = 24;
    private final int GAP_SIZE = sizeScreen() / 23;
    
    public GUI(){
    	new GUI("Name", "3171234567", "junkemail@gmail.com");
    }
    
    public GUI(String name, String phone, String email) {
    	this.name = name;
    	this.phone = phone;
    	this.email = email;
    	try {
    		System.out.println("-------------------------------------------------------------");
			initChargeNumbers();
			initDesiredTests();
			System.out.println("-------------------------------------------------------------");
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        initComponents();
        try {
			initProjectNumber();
			assignStuffToButtons();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        this.setVisible(true);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exitSystem();
                System.out.println("End of windowClosing()");
            }
        });
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	VideoCapture camera = new VideoCapture(0);
    	while(true){
    		//System.out.println(isFinished);
    		if(isFinished == true){
        		System.out.println("Swaggin it out");
        		this.dispose();
        		break;
        	}
    	if(!camera.isOpened()){
    		System.out.println("Error");
    	}
    	else {
    		Mat frame = new Mat();
    	    while(picture == null){
    	    	camera.read(frame);
    	    	try {
					if(frame.width() > 0 && frame.height() > 0)GUI.pictureLabel.setIcon(new ImageIcon(createAwtImage(frame)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	//System.out.println("In second while loop");
    	    	if(isFinished == true){
            		System.out.println("Swaggin it out");
            		this.dispose();
            		break;
            	}
    	    }	
    	    pictureLabel.setIcon(picture);
    	}
    	}
    }
    
    public void initProjectNumber() throws IOException{
    	BufferedWriter pw = null;
		File logFile = new File(Driver.getSystemFolder() + "/projectNumber.txt");
		BufferedReader br = null;
		try{
		br = new BufferedReader(new FileReader(logFile));
		}catch (FileNotFoundException e){
		// TODO Auto-generated catch block
		//e.printStackTrace();
		pw = new BufferedWriter(new FileWriter(logFile));
		System.out.println("Crisis averted");
		}
		try{
			Driver.setProjectNum(Integer.parseInt(br.readLine()));
			jLabel8.setText("Project Number:  " + Driver.getProjectNum());
			br.close();
		}catch(java.lang.NullPointerException err){
			//if the file contains no text, set first project number
			pw = new BufferedWriter(new FileWriter(logFile));
	        pw.write("1");
	        Driver.setProjectNum(1);
	        jLabel8.setText("Project Number:  " + Driver.getProjectNum());
		    pw.close();
		}
    }
    
    public void writeProjectNumber() throws IOException{
    	BufferedWriter pw = null;
		File logFile = new File(Driver.getSystemFolder() + "/projectNumber.txt");
		BufferedReader br = null;
		try{
		br = new BufferedReader(new FileReader(logFile));
		pw = new BufferedWriter(new FileWriter(logFile));
		}catch (FileNotFoundException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
		pw = new BufferedWriter(new FileWriter(logFile));
		System.out.println("Crisis averted");
		}
		pw.write("" + Driver.getProjectNum());
		pw.close();
    }
    
	public static BufferedImage createAwtImage(Mat mat) {

	    int type = 0;
	    if (mat.channels() == 1) {
	        type = BufferedImage.TYPE_BYTE_GRAY;
	    } else if (mat.channels() == 3) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    } else {
	        return null;
	    }

	    BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    mat.get(0, 0, data);
	    return image;
	}
	
	public void initChargeNumbers() throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(Driver.getSystemFolder() + "/chargeNumbers.csv"));
		String string;
		while(br.ready()){
			string = br.readLine();
			string = string.replace(",", " ");
			System.out.println(string);
			while(string.length() >= 1){
				if(string.indexOf(" ") != -1){
					chargeNumbers.add(string.substring(0, string.indexOf(" ")));
					string = string.substring(string.indexOf(" ") + 1);
				}else{
					chargeNumbers.add(string);
					string = "";
				}
			}
		}
		System.out.println(chargeNumbers);
	}
	
	public void initDesiredTests() throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(Driver.getSystemFolder() + "/desiredTests.csv"));
		String string;
		while(br.ready()){
			string = br.readLine();
			System.out.println(string);
			while(string.length() >= 1){
				if(string.indexOf(",") != -1){
					desiredTests.add(string.substring(0, string.indexOf(",")));
					string = string.substring(string.indexOf(",") + 1);
				}else{
					desiredTests.add(string);
					string = "";
				}
			}
		}
		System.out.println(desiredTests);
	}

	  
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
    	
    	this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);

        jScrollPane1 = new javax.swing.JScrollPane();
        pictureLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        jScrollPane1.setViewportView(pictureLabel);

        jLabel1.setText("Sample Name:");

        jLabel2.setText("Description:");

        jLabel3.setText("Project Charge #:");

        jLabel4.setText("Desired Tests:");
        
        jLabel1.setFont(new java.awt.Font(DEFAULT_FONT, 3, DEFAULT_FONT_SIZE));
        jLabel2.setFont(new java.awt.Font(DEFAULT_FONT, 3, DEFAULT_FONT_SIZE));
        jLabel3.setFont(new java.awt.Font(DEFAULT_FONT, 3, DEFAULT_FONT_SIZE));
        jLabel4.setFont(new java.awt.Font(DEFAULT_FONT, 3, DEFAULT_FONT_SIZE));
        jLabel10.setFont(new java.awt.Font(DEFAULT_FONT, 3, (DEFAULT_FONT_SIZE * 2))); //doubled
        jLabel7.setFont(new java.awt.Font(DEFAULT_FONT, 3, DEFAULT_FONT_SIZE));
        jLabel8.setFont(new java.awt.Font(DEFAULT_FONT, 3, DEFAULT_FONT_SIZE));
        

        jButton1.setText("Submit Sample");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					jButton1ActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton2.setText("Take Picture");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        jButton3.setText("Reset Camera");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        
        jButton4.setText("Log Out");
        jButton4.addActionListener(new java.awt.event.ActionListener(){
        	public void actionPerformed(java.awt.event.ActionEvent evt){
        		jButton4ActionPerformed(evt);
        	}
        });
        
        jButton5.setText("New Project");
        jButton5.addActionListener(new java.awt.event.ActionListener(){
        	public void actionPerformed(java.awt.event.ActionEvent e){
        		jButton5ActionPerformed(e);
        	}
        });
        
        jButton6.setText("Print Sample");
        jButton6.addActionListener(new java.awt.event.ActionListener(){
        	public void actionPerformed(java.awt.event.ActionEvent e){
        		jButton6ActionPerformed(e);
        	}
        });
        
        jLabel7.setText("Logged in as: " + name);
        jLabel8.setText("Project Number:  " + Driver.getProjectNum());

        jLabel10.setText("jLabel10");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(332, 332, 332)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pictureLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox7)
                                    .addComponent(jCheckBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox8)
                                    .addComponent(jCheckBox9)
                                    .addComponent(jCheckBox10))
                                .addGap(159, 159, 159))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jCheckBox1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox10)))
                        .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                        .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                    		.addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(GAP_SIZE, GAP_SIZE, GAP_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        //sizeScreen();
    }// </editor-fold>    
    
    public int sizeScreen(){ //returns vertical height of screen
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)(screenSize.width * .8), screenSize.height);
        return screenSize.height;
    }

    public void exitSystem() {
    	try {
			writeProjectNumber();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("TERMINATED");
    	isFinished = true;
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws IOException{ //generate label
		jLabel10.setText("Submitting Sample...");
    	ArrayList<String> tests = compileTests();
        Document d = new Document(
        		(jTextField1.getText() != null ? jTextField1.getText() : ""),
        		(jTextArea1.getText() != null ? jTextArea1.getText() : ""),
        		(jComboBox1.getSelectedItem() != null ? jComboBox1.getSelectedItem().toString() : new String("0")),
        		tests,
        		(javax.swing.ImageIcon)picture,
        		this.name, this.phone, this.email);
        d.makeLabel();
        doc = d;
        jLabel10.setText("Sample Submitted at " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }        
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt){ //take picture
    	jLabel10.setText("Picture Taken at " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
    	picture = pictureLabel.getIcon();
    	jButton2.setEnabled(false);
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt){ //reset picture
    	picture = null;
    	jButton2.setEnabled(true);
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt){ //log out
    	try {
			writeProjectNumber();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	isFinished = true;
    }
    
    private void jButton5ActionPerformed(ActionEvent e) { //new project
		Driver.incrProjectNum();
		jLabel8.setText("Project Number:  " + Driver.getProjectNum());
	}
    
    private void jButton6ActionPerformed(ActionEvent e){ //print sample
    	printString(doc.toString());
    	jLabel10.setText("Label Printed at " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
    
    public void printString(String s){
    	char[] printdata = s.toCharArray();
    	DocFlavor flavor = DocFlavor.CHAR_ARRAY.TEXT_PLAIN;
    	PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
    	DocPrintJob pjob = pservice.createPrintJob();
    	Doc doc = new SimpleDoc(printdata, flavor, null);
    	try {
			pjob.print(doc, null);
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public ArrayList<String> compileTests(){
    	ArrayList<String> tests = new ArrayList<String>();
    	for(javax.swing.JCheckBox j : testsCheckboxes){
    		if(j.isSelected()) tests.add(j.getText());
    	}
    	return tests;
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }      
    
    private void assignStuffToButtons(){
    	 testsCheckboxes = Arrays.asList(jCheckBox1, jCheckBox2, jCheckBox3,
						    			 jCheckBox4, jCheckBox5, jCheckBox6,
						    			 jCheckBox7, jCheckBox8, jCheckBox9, jCheckBox10);
    	 for(int x = 0; x < testsCheckboxes.size() && x < desiredTests.size(); x++){
    		 testsCheckboxes.get(x).setText(desiredTests.get(x));
    	 }
    	 jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(chargeNumbers.toArray())); 
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1; //sample Name
    private javax.swing.JLabel jLabel2; //description
    private javax.swing.JLabel jLabel3; //chargeNumber
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel10; //notifications
    private javax.swing.JLabel jLabel7; //logged in as:
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    public static javax.swing.JLabel pictureLabel;
    // End of variables declaration                   
}