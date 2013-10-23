
/*
 * Class used to facilitate item manipulation.
 * 
 * Alexandre Vann, 2013
 */
public class Item 
{
	int id;
	String selector;
	String property;
	String value;
	
	public Item()
	{
		
	}
	
	public Item(int id, String selector, String property, String value)
	{
		this.id = id;
		this.selector = selector;
		this.property = property;
		this.value = value;
	}
	
	public String toString()
	{
		return id + "\t" + selector + "\t" + property + "\t" + value;
	}
}
