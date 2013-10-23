import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/*
 * Class in charge of reading in and writing out necessary files.
 * 
 * Alexandre Vann, 2013
 */
public class IOClass {

	String filePath = "";
	String fileName = "";
	
	public IOClass()
	{
		//
	}
	
	public ArrayList<String> importCSS(String filepath)
	{
		this.filePath = filepath.substring(0, filepath.lastIndexOf('/') + 1);
		this.fileName = filepath.substring(filepath.lastIndexOf('/') + 1, filepath.length());
		
		ArrayList<String> fileContents = new ArrayList<String>();
		
		BufferedReader br = null;

		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader(filepath));
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				fileContents.add(sCurrentLine);
			}

		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
		finally 
		{
			try 
			{
				if (br != null)
					br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		
		return fileContents;
	}
	
	public ArrayList<ArrayList<String>> importCSV(String filepath)
	{
		this.filePath = filepath.substring(0, filepath.lastIndexOf('/') + 1);
		this.fileName = filepath.substring(filepath.lastIndexOf('/') + 1, filepath.length());
		
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		
		CSVReader r = null;
		try {
			r = new CSVReader(new FileReader(filepath));

			String[] row;
			while ((row = r.readNext()) != null) {

				ArrayList<String> temp = new ArrayList<String>();
			
				for (int i = 0; i < row.length; i++) 
				{
					temp.add(row[i]);
				}
				
				table.add(temp);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if (r != null)
					r.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		
		return table;
	}
	
	public void exportCSS(ArrayList<String> input)
	{
		fileName = fileName.substring(0, fileName.indexOf('.'));
		fileName = fileName + " Converted.css";
		
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
			
			for (String s : input)
			{
				bw.write(s + "\n");
			}
			
			bw.close();
 
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void exportCSV(String[][] input)
	{
		CSVWriter w = null;
		fileName = fileName.substring(0, fileName.indexOf('.'));
		fileName = fileName + " Converted.csv";

		try {
			w = new CSVWriter(new FileWriter(new File(filePath, fileName)), ',');
		     
		    ArrayList<String[]> data = new ArrayList<String[]>();
			
			for (int i = 0; i < input.length; i++)
		    {
				data.add(input[i]);
		    }
		     
		    w.writeAll(data);
		     
			w.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportConflictsList(ArrayList<String> conflictsList)
	{
		
		fileName = fileName.substring(0, fileName.indexOf('.'));
		fileName = fileName + " Conflicts List.txt";
		
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
			
			bw.write("Conflicts Report\nTotal conflicts found... " + conflictsList.size() + "\n");
			
			for (String s : conflictsList)
			{
				bw.write(s + "\n");
			}
			
			bw.close();
 
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
