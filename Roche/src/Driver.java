public class Driver {
	
	public static void main(String[] args) {
		LoginGUI l = new LoginGUI();
		GUI g = new GUI(l.getName(), l.getContact());
	}
}