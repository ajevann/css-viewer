import java.util.ArrayList;

/*
 * Class in charge of creating the .css layout.
 * 
 * Alexandre Vann, 2013
 */
public class Layout {
	
	ArrayList<Item> itemsList = new ArrayList<Item>();
	ArrayList<String> layout = new ArrayList<String>();
	
	public Layout()
	{
		//
	}
	
	public Layout(ArrayList<Item> itemsList)
	{
		this.itemsList = itemsList;
		setup(itemsList);
	}
	
	public void setup(ArrayList<Item> itemsList)
	{
		String tempS = "";
		boolean first = true;
		
		for (Item i : itemsList)
		{
			if (first)
			{
				tempS = i.selector;
				layout.add(i.selector + " {");
				first = false;
			}
			
			if (!tempS.equals(i.selector))
			{
				layout.add("}");
				layout.add("");
				layout.add(i.selector + " {");
				
				tempS = i.selector;
			}
			
			if (tempS.equals(i.selector))
			{
				layout.add("\t" + i.property + ":" + i.value + ";");
			}
		}
		
		layout.add("}");
	}
	
	public ArrayList<String> getLayout()
	{
		return layout;
	}
	
	public void print()
	{
		for (String s : layout)
		{
			System.out.println(s);
		}
	}
	
}
