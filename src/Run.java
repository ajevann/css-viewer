import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/*
 * Main run class.
 * 
 * Alexandre Vann, 2013
 */
public class Run {
	public static void main(String[] args)
	{
		//Changes the default look and feel for Mac so that JFileChooser will run correctly.
		try 
		{ 
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); 
		} 
		catch(Exception e) 
		{ 
			System.out.println("Error setting Java LAF: " + e); 
		}
		
		try
		{
			Parser p = new Parser();
			IOClass i = new IOClass();
			String filePath = "";
			
			JOptionPane.showMessageDialog(null, "Please select the file you wish to convert;\nChoosing a .css file will convert it to a .csv file and vice versa.");
			JFileChooser chooser = new JFileChooser();
		    int returnVal = chooser.showOpenDialog(null);
		    
		    
		    if (returnVal == JFileChooser.APPROVE_OPTION) 
		    {
				JOptionPane.showMessageDialog(null, "Opening file: " + chooser.getSelectedFile().getName());
				filePath = chooser.getSelectedFile().getAbsolutePath();
		    }
		    
		    if (chooser.getSelectedFile().getName().endsWith(".css"))
		    {
				p.parseCSS(i.importCSS(filePath));
				Table t = new Table(p.getItemsList());
				i.exportCSV(t.getTable());
				
				if (!t.getConflictsList().isEmpty())
					i.exportConflictsList(t.getConflictsList());
		    }
			
		    else if (chooser.getSelectedFile().getName().endsWith(".csv"))
		    {
				p.parseCSV(i.importCSV(filePath));
				Layout l = new Layout(p.getItemsList());
				i.exportCSS(l.getLayout());	
		    }
		    
//		    filePath = "/Users/Alex/Documents/workspace/CSS Viewer/Reset.css";
//			p.parseCSS(i.importCSS(filePath));
//			Table t = new Table(p.getItemsList());
//			i.exportCSV(t.getTable());

		    else 
		    {
		    	JOptionPane.showMessageDialog(null, "The file you have chosen cannot be converted.");
		    }
		    
		    JOptionPane.showMessageDialog(null, "Conversion complete. Your new file is located in the same directory as the original file.");
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}
}
