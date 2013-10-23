import java.util.ArrayList;

/*
 * Class in charge of creating the .csv table layout.
 * 
 * Alexandre Vann, 2013
 */
public class Table 
{
	int rSize = 0;
	int cSize = 0;
	
	ArrayList<Item> itemsList;
	
	String[][] table;
	ArrayList<String> conflictsList = new ArrayList<String>();
	
	public Table()
	{
		//Default Constructor
	}
	
	public Table(ArrayList<Item> itemsList)
	{
		this.itemsList = itemsList;
		setup(itemsList);
	}
	
	public void setup(ArrayList<Item> itemsList)
	{
		ArrayList<String> sList = new ArrayList<String>();
		ArrayList<String> pList = new ArrayList<String>();
		
		for (Item i : itemsList)
		{
			if (!sList.contains(i.selector)) sList.add(i.selector);
			if (!pList.contains(i.property)) pList.add(i.property);
		}
		
		rSize = sList.size() + 1;
		cSize = pList.size() + 1;
		
		table = new String[rSize][cSize];
		
		
		for (int i = 1; i <= sList.size(); i++)
			table[i][0] = sList.get(i - 1);
		
		for (int i = 1; i <= pList.size(); i++)
			table[0][i] = pList.get(i - 1);
		
		for (Item i : itemsList)
		{	
			int iR = sList.indexOf(i.selector) + 1;
			int iC = pList.indexOf(i.property) + 1;

			if (table[iR][iC] == null)
				table[iR][iC] = i.value;
			else
			{
				String s = (conflictsList.size() + 1) + ") CF : " + i + "\tCurrent Value : " + table[iR][iC] + "\tNew Value : " + i.value;
				System.out.println(s);
				conflictsList.add(s);
			}
		}
		
		for (int i = 0; i < rSize; i++)
			for (int j = 0; j < cSize; j++)
				if (table[i][j] == null)
					table[i][j] = "";
	}
	
	public String[][] getTable()
	{
		return table;
	}
	
	public void print()
	{
		for (int i = 0; i < rSize; i++)
		{
			for (int j = 0; j < cSize; j++)
				System.out.print(table[i][j] + "\t");
			System.out.println();
		}
	}

	public ArrayList<String> getConflictsList() 
	{
		return conflictsList;
	}
}
	
