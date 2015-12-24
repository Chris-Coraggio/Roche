public class Driver {
	
	public static void main(String[] args) {
		while(true){
		LoginGUI l = new LoginGUI();
		new GUI(l.getName(), l.getContact());
		}
	}
}