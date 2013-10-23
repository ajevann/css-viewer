import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Class in charge of parsing through raw data and formatting into legible information.
 * 
 * Alexandre Vann, 2013
 */
public class Parser 
{
	ArrayList<Item> itemsList = new ArrayList<Item>();
	
	ArrayList<String> propertiesList;
	ArrayList<String> selectorsList;
	ArrayList<String> valuesList;
	
	Map<String, String> propertiesMap;
	Map<String, String> selectorsMap;
	
	public Parser()
	{
	 	propertiesList = new ArrayList<String>();
		selectorsList = new ArrayList<String>();
		valuesList = new ArrayList<String>();
		
		propertiesMap = new HashMap<String, String>();
		selectorsMap = new HashMap<String, String>();
	}
	
	public void parseCSV(ArrayList<ArrayList<String>> main)
	{	
		int id = 1;
		for (int i = 1; i < main.size(); i++)
		{
			for (int j = 1; j < main.get(i).size(); j++)
			{
				if (!main.get(i).get(j).isEmpty())
				{
					Item item = new Item(id, main.get(i).get(0), main.get(0).get(j), main.get(i).get(j));
					id++;
					itemsList.add(item);
				}
			}
		}
	}
	
	public void parseCSS(ArrayList<String> main)
	{
		String mainString = "";
		for (String line : main)
		{
			line = line.trim();
			mainString += line;
		}
		
		int start = 0;
		int index = 0;
		int numofob = 0;
		
		int itemCount = 0;
		
		ArrayList<String> multipleSelectors = new ArrayList<String>();
		
		boolean multipleSelector = true;
		boolean withinSelector = false;
		boolean skipComment = false;
		
		String selector = "";
		
		for (char c : mainString.toCharArray())
		{	
			if (skipComment)
			{
				//Do nothing, simply skip the commented section.
			}
			
			//Picks out the selectors
			else if (c == '{' && numofob == 0)
			{
				withinSelector = true;
				selector = mainString.substring(start, index - 1);
				selector = selector.trim();
				
				//Single selector.
				
				//Multiple selectors on a line.
				if (selector.contains(","))
				{
					multipleSelectors.clear();
					multipleSelector = true;
					int subIndex = 0;
					selector += " ";

					for (int i = 0; i < selector.length(); i++)
					{
						if (selector.charAt(i) == ',' || i == selector.length() - 1)
						{
							String tempS = selector.substring(subIndex, i).trim();
							//System.out.println(tempS);
							selectorsList.add(tempS);
							multipleSelectors.add(tempS);
							subIndex = i + 1;
						}
					}
				}
				
				else
				{
					selectorsList.add(selector);

					//System.out.println(selector);
				}

				start = index + 1;
				
				numofob++;
			}
			
			//Grabs the properties and their values
			else if (c == ';' && withinSelector)
			{
				String s = mainString.substring(start, index);
				s = s.trim();

				int subIndex = 0;
				s += ";";
				
				System.out.println(s);
				
				String p = s.substring(subIndex, s.indexOf(':')).trim();
				String v = s.substring(s.indexOf(':') + 1, (s.length() - 1)).trim();

				//System.out.println("\t" + p + "\t" + v);
				
				//Multiple selectors.
				if (multipleSelector)
				{
					for (String ms : multipleSelectors)
					{
						itemCount++;
						Item i = new Item(itemCount, ms, p, v);
						itemsList.add(i);
					}
				}
				
				//Single selector.
				else
				{
					itemCount++;
					Item i = new Item(itemCount, selector, p, v);
					itemsList.add(i);
				}
				
				start = index + 1;
			}
			
			//Closes out the selector
			else if (c == '}' && numofob == 1)
			{
				//System.out.println("end selector");
				start = index + 1;
				withinSelector = false;
				multipleSelector = false;
				numofob--;
			}
			
			//Picks out meta data
			else if (c == '{' && numofob >= 1)
			{
				//System.out.println(" new meta");
				numofob++;
			}
			
			//Closes out the meta data
			else if (c == '}' && numofob > 1)
			{
				//System.out.println(" end meta");
				numofob--;
			}
			
			//Dealing with comments is difficult at this time because .css files 
			//seem to deal with comments through a mixture of /*...*/ and 
			//line-based commenting.
			
			index++;
		}
		
	}
	
	public void oldParseCSS(ArrayList<String> main)
	{
		boolean isSelector = false;

		Item item = new Item();
		String s = "";
		String p = "";
		String v = "";
				
		for (String line : main)
		{
			line = line.trim();
				
			//Ignore lines that have been commented out.
			if (!line.startsWith("//")) 
			{	
				//This boolean signifies that the parser has entered inside the bracket region of a selector.
				if (isSelector && !line.isEmpty() && !line.contains("}"))
				{
					String property = line.substring(0, line.indexOf(':'));
					String value = line.substring(line.indexOf(':') + 1, line.indexOf(';'));
					
					p = property;
					v = value;
					
					if (!propertiesList.contains(property))
						propertiesList.add(property);
					
					valuesList.add(value);
					
					int id = itemsList.size();
					item = new Item(id, s, p, v);
					
					itemsList.add(item);
					
					item = new Item();
					p = "";
					v = "";
				}

				if (line.contains("}") && isSelector)
				{
					isSelector = false;
					s = "";
				}
				
				if (line.contains("{") && !isSelector)
				{
					String selector = line.substring(0, line.indexOf('{') - 1);
					s = selector;
					selectorsList.add(selector);
				
					isSelector = true;
				}
			}
		}
	}
	
	public ArrayList<String> cleanList(ArrayList<String> oldList)
	{
		ArrayList<String> newList = new ArrayList<String>();
		for (String a : oldList)
			if (!newList.contains(a))
				newList.add(a);
		
		return newList;
	}
	
	public ArrayList<Item> getItemsList()
	{
		return itemsList;
	}
}
