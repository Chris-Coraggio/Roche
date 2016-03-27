import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

public class PrintLabel {
	public static ArrayList<String> desiredTests = new ArrayList<String>();
	public static String id = new String();
	public static String name = new String();
	public static String submitter = new String();
	public static String project = new String();

	public PrintLabel(String id, String name, String project, String submitter, ArrayList<String> tests) {
		this.id = id;
		this.name = name;
		this.submitter = submitter;
		this.project = project;
		this.desiredTests = tests;
		this.desiredTests.add("");
		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj.printDialog()) {
			PageFormat pf = pj.defaultPage();
			Paper paper = pf.getPaper();
			double width = fromCMToPPI(3.5);
			double height = fromCMToPPI(8.8);
			paper.setSize(width, height);
			paper.setImageableArea(fromCMToPPI(0.25), fromCMToPPI(0.5), width - fromCMToPPI(0.35),
					height - fromCMToPPI(1));
			System.out.println("Before- " + dump(paper));
			pf.setOrientation(PageFormat.PORTRAIT);
			pf.setPaper(paper);
			System.out.println("After- " + dump(paper));
			System.out.println("After- " + dump(pf));
			dump(pf);
			PageFormat validatePage = pj.validatePage(pf);
			System.out.println("Valid- " + dump(validatePage));
			// Book book = new Book();
			// book.append(new MyPrintable(), pf);
			// pj.setPageable(book);
			pj.setPrintable(new MyPrintable(), pf);
			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}

	

	protected static double fromCMToPPI(double cm) {
		return toPPI(cm * 0.393700787);
	}

	protected static double toPPI(double inch) {
		return inch * 72d;
	}

	protected static String dump(Paper paper) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(paper.getWidth()).append("x").append(paper.getHeight()).append("/").append(paper.getImageableX())
				.append("x").append(paper.getImageableY()).append(" - ").append(paper.getImageableWidth()).append("x")
				.append(paper.getImageableHeight());
		return sb.toString();
	}

	protected static String dump(PageFormat pf) {
		Paper paper = pf.getPaper();
		return dump(paper);
	}

	public static class MyPrintable implements Printable {

		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			System.out.println(pageIndex);
			int result = NO_SUCH_PAGE;
			if (pageIndex < 2) {
				Graphics2D g2d = (Graphics2D) graphics;
				System.out.println("[Print] " + dump(pageFormat));
				double width = pageFormat.getImageableWidth();
				double height = pageFormat.getImageableHeight();
				g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
				g2d.draw(new Rectangle2D.Double(1, 1, width - 1, height - 1));
				FontMetrics fm = g2d.getFontMetrics();
				Font font = new Font(null, Font.PLAIN, 10);
				AffineTransform affineTransform = new AffineTransform();
				affineTransform.rotate(Math.toRadians(90), 0, 0);
				Font rotatedFont = font.deriveFont(affineTransform);
				g2d.setFont(rotatedFont);
				int index = 0;
				int widthMult = 1;
				int heightMult = 1;
				double labelHeight = width / 5;
				double labelWidth = height / 3;
				for (String x : desiredTests) {
					g2d.drawString(desiredTests.get(index), (int) (labelHeight * heightMult) - 8,
							(int) (height - (labelWidth * widthMult)));
					if (heightMult == 5) {
						heightMult = 0;
						widthMult = 2;
					}
					index++;
					heightMult++;
				}
				g2d.drawString(name, (int) (width - 8), fm.getLeading() + 2);
				g2d.drawString("ID: " + id, (int) (width - 8 - (width / 6)), fm.getLeading() + 2);
				g2d.drawString("Project #:", (int) (width - 8 - ((width / 6)*2)), fm.getLeading() + 2);
				g2d.drawString(project, (int) (width - 8 - ((width / 6)* 3)), fm.getLeading() + 2);
				g2d.drawString("Submitter:", (int) (width - 8 - ((width / 6)*4)), fm.getLeading() + 2);
				g2d.drawString(submitter, (int) (width - 8 - ((width / 6)*5)), fm.getLeading() + 2);
				result = PAGE_EXISTS;
			}
			return result;
		}
	}
}
