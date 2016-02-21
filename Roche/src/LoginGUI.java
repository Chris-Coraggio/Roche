import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoginGUI extends javax.swing.JDialog{

	    private String name = "";
	    private String email = "";
	    private String phoneNum = "";
	    private ArrayList<String> usernames = new ArrayList<String>(); //already logged in usernames
	    private ArrayList<String> passwords = new ArrayList<String>(); //preestablished passwords
	    private ArrayList<String> phoneNums = new ArrayList<String>();
	    private ArrayList<String> names = new ArrayList<String>();
	    private ArrayList<String> emails = new ArrayList<String>();
	    private final java.io.File FILE;
	    private java.io.BufferedWriter write;
	    private java.io.BufferedReader read; 
	    private boolean isFinished;
	    
	    public LoginGUI() {
	    	FILE = makeFile();
	        initComponents();
	        isFinished = false;
	        this.setVisible(true);
	        while(true){
	        	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	System.out.println(isFinished);
	        	if(isFinished == true){
	        		System.out.println("MAde it");
	        		this.dispose();
	        		break;
	        	}
	        }
	        System.out.println("SHould have launched");
	    }
                          
	    private void initComponents() {
	    	
	    	try {
				write = new java.io.BufferedWriter(new java.io.FileWriter(FILE, true));
				read = new java.io.BufferedReader(new java.io.FileReader(FILE));
				readFromDatabase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        jLabel3 = new javax.swing.JLabel();
	        jTextField1 = new javax.swing.JTextField();
	        jPasswordField1 = new javax.swing.JPasswordField();
	        jLabel4 = new javax.swing.JLabel();
	        jButton1 = new javax.swing.JButton();
	        jLabel5 = new javax.swing.JLabel();
	        jTextField2 = new javax.swing.JTextField();
	        jLabel6 = new javax.swing.JLabel();
	        jTextField3 = new javax.swing.JTextField();
	        jLabel7 = new javax.swing.JLabel();
	        jTextField4 = new javax.swing.JTextField();
	        jLabel8 = new javax.swing.JLabel();
	        jPasswordField2 = new javax.swing.JPasswordField();
	        jLabel9 = new javax.swing.JLabel();
	        jPasswordField3 = new javax.swing.JPasswordField();
	        jButton2 = new javax.swing.JButton();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        warnings = new javax.swing.JTextArea();

	        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	        jLabel1.setText("Welcome to the Roche Sample Annotator! Please log in.");

	        jLabel2.setText("Username:");

	        jLabel3.setText("Password:");

	        jLabel4.setText("Don't have an account yet? Register here:");

	        jButton1.setText("Log In");
	        jButton1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jButton1ActionPerformed(evt);
	            }
	        });
	        
	        jButton2.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jButton2ActionPerformed(evt);
	            }
	        });

	        jLabel5.setText("Name:");

	        jLabel6.setText("Email:");

	        jLabel7.setText("Username:");

	        jLabel8.setText("Password:");

	        jLabel9.setText("Repeat Pw:");

	        jButton2.setText("Register");

	        warnings.setColumns(20);
	        warnings.setRows(5);
	        jScrollPane1.setViewportView(warnings);

	        jLabel10.setText("Phone:");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addContainerGap())
	                    .addComponent(jScrollPane1)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addGroup(layout.createSequentialGroup()
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
	                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
	                                    .addComponent(jPasswordField1)))
	                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(jButton1)
	                            .addComponent(jButton2)
	                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                    .addComponent(jLabel10)
	                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
	                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                    .addComponent(jLabel7)
	                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
	                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                    .addComponent(jLabel8)
	                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
	                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                    .addComponent(jLabel9)
	                                    .addGap(18, 18, 18)
	                                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
	                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                        .addComponent(jLabel6))
	                                    .addGap(9, 9, 9)
	                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
	                                        .addComponent(jTextField2)))))
	                        .addGap(0, 0, Short.MAX_VALUE))))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel1)
	                .addGap(18, 18, 18)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel2)
	                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel3)
	                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jButton1)
	                .addGap(12, 12, 12)
	                .addComponent(jLabel4)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel5)
	                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel6))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel10))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel7)
	                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(jLabel8)
	                            .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(jLabel9))
	                    .addComponent(jPasswordField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jButton2)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap())
	        );

	        pack();
	    }// </editor-fold>   
	    
	    public File makeFile(){
	    	File file = null;
	    	try{
	    	 file = new java.io.File(Driver.getSystemFolder() + "//file.txt");
	    	 return file;
	    	}catch(Exception e){
	    		try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		return file;
	    	}
	    }
                        

	    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
	        // TODO add your handling code here:
	    }                                           

	    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
	        //log in
	    	if(passwordsAreEquivalent(jPasswordField1.getPassword(), getPassword(jTextField1.getText()))){
	    		name = getName(jTextField1.getText());
	    		System.out.println("NAME:   " + name);
	    		email = getContact(jTextField1.getText());
	    		System.out.println("EMAIL:  " + email);
	    		isFinished = true;
	    		System.out.println("LOGGED IN: " + isFinished);
	    	}else warnings.setText("Username or password is incorrect. Please try again.");
	    }                                        

	    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt){
	    	//register
	    	String username, password;
	    	if(! passwordsAreEquivalent(jPasswordField2.getPassword(), jPasswordField3.getPassword())){
	    		warnings.setText("Your passwords do not match. Please try again.");
	    	}else if(isUsernameTaken(jTextField4.getText())){
	    		warnings.setText("Please choose a different username. That one is taken.");
	    	}else if(jTextField3.getText().indexOf("@") == -1){
	    		warnings.setText("Please use a full email address.");
	    	}else if(jTextField2.getText() == null){
	    		warnings.setText("Please include a name");
	    	}else{
	    	name = jTextField2.getText().replace(" ", "_");
	    	email = jTextField3.getText();
	    	phoneNum = jTextField5.getText();
	    	username = jTextField4.getText();
	    	password = charToString(jPasswordField3.getPassword());
	    	writeToDatabase(name, email, phoneNum, username, password);
	    	isFinished = true;
	    	}
	    }
	    
	    private void writeToDatabase(String name, String email, String phoneNum, String username, String password){
	    	System.out.println("Name:\t" + name + "\nEmail:\t" + email + "\nPhone:\t" + phoneNum + "\nUsername:\t" + username + "\nPassword:\t" + password);
	    	name.replace(" ", "_");
	    	try {
				write.write("" + name + " " + email + " " + phoneNum + " " + username + " " + password + "\n");
				write.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    private boolean passwordsAreEquivalent(char[] c, char[] d){
	    	boolean b = true;
	    	if(c.length != d.length) b = false;
	    	else{
	    	for(int i = 0; i < c.length - 1; i++){
	    		if(c[i] != d[i]) b = false;
	    	}
	    	}
	    	if(c.length < 1 || d.length < 1) b = false;
	    	return b;
	    }
	    
	    private void readFromDatabase() throws IOException{
	    	String s = "";
	    	while((s = read.readLine()) != null){
	    	System.out.println(s.substring(0, s.indexOf(" ")));
	    	names.add(s.substring(0, s.indexOf(" ")));
	    	s = s.substring(s.indexOf(" ") + 1);
	    	System.out.println(s.substring(0, s.indexOf(" ")));
	    	emails.add(s.substring(0, s.indexOf(" ")));
	    	s = s.substring(s.indexOf(" ") + 1);
	    	System.out.println(s.substring(0, s.indexOf(" ")));
	    	phoneNums.add(s.substring(0, s.indexOf(" ")));
	    	s = s.substring(s.indexOf(" ") + 1);
	    	System.out.println(s.substring(0, s.indexOf(" ")));
	    	usernames.add(s.substring(0, s.indexOf(" ")));
	    	s = s.substring(s.indexOf(" ") + 1);
	    	System.out.println(s);
	    	passwords.add(s);
	    	}
	    }
	    
	    private String getName(String username){
	    	for(String u : usernames) if(u.equals(username)) return names.get(usernames.indexOf(u));
	    	return null;
	    }
	    
	    private char[] getPassword(String username){
	    	String temp;
	    	for(String u : usernames) if(u.equals(username)){
	    		temp = passwords.get(usernames.indexOf(u));
	    	return toCharArray(temp);
	    	}
	    	return new char[]{'*'};
	    }
	    
	    private char[] toCharArray(String s){
	    	char[] c = new char[s.length()];
	    	for(int i = 0; i < s.length(); i++){
	    		c[i] = s.charAt(i);
	    	}
	    	return c;
	    }
	    
	    private String charToString(char [] c){
	    	String s = "";
	    	for(char a: c) s += a;
	    	return s;
	    }
	    
	    private String getContact(String username){
	    	for(String u : usernames) if(u.equals(username)) return emails.get(usernames.indexOf(u));
	    	return null;
	    }
	    
	    private boolean isUsernameTaken(String username){
	    	return false; //fix this later
	    }
	    
	    public String getName(){
	    	return name;
	    }

	    public String getEmail(){
	    	return email;
	    }
	    
	    public String getPhoneNum(){
	    	return this.phoneNum;
	    }
	    
	    // Variables declaration - do not modify                     
	    private javax.swing.JButton jButton1;
	    private javax.swing.JButton jButton2;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;
	    private javax.swing.JLabel jLabel5;
	    private javax.swing.JLabel jLabel6;
	    private javax.swing.JLabel jLabel7;
	    private javax.swing.JLabel jLabel8;
	    private javax.swing.JLabel jLabel9;
	    private javax.swing.JLabel jLabel10 = new javax.swing.JLabel(); //phone number
	    private javax.swing.JPasswordField jPasswordField1;
	    private javax.swing.JPasswordField jPasswordField2;
	    private javax.swing.JPasswordField jPasswordField3;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JTextArea warnings;
	    private javax.swing.JTextField jTextField1;
	    private javax.swing.JTextField jTextField2;
	    private javax.swing.JTextField jTextField3;
	    private javax.swing.JTextField jTextField4;
	    private javax.swing.JTextField jTextField5 = new javax.swing.JTextField(); //phone number
	    // End of variables declaration                   
	}
