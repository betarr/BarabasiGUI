package sk.sochuliak.giraphe.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class NetworkImportExport {
	
	public static String DEFAULT_DIRECTORY = null;
	
	public static boolean export(File file, String networkName, List<int[]> neighboringPairNodes) {
		boolean result = false;
		PrintWriter pt = null;
		try {
			pt = new PrintWriter(file);
			pt.println(networkName);
			for (int[] neighboringPair : neighboringPairNodes) {
				String row = neighboringPair[0] + "\t" + neighboringPair[1];
				pt.println(row);
			}
			result = true;
		} catch (FileNotFoundException e) {
			System.err.println("File " + file.getName() + " not found!");
		} finally {
			pt.close();
		}
		return result;
	}
	
	public static NetworkImportObject importFromFile(File file) {
		NetworkImportObject result = new NetworkImportObject();
		List<int[]> nodePairs = new ArrayList<int[]>();
		Scanner scanner = null;
		boolean firstRow = true;
		try {
			scanner = new Scanner(new FileReader(file));
			while (scanner.hasNextLine()) {
				String row = scanner.nextLine();
				row = row.trim();
				if (NetworkImportExport.ignoreRow(row)) {
					continue;
				}
				if (firstRow) {
					result.setName(row);
					firstRow = false;
					continue;
				}
				String[] splitted = row.split("\t");
				if (splitted.length == 1) {
					splitted = row.split(" ");
				}
				String xValue = splitted[0].trim();
				String yValue = splitted[1].trim();
				int[] neighbooringPair = new int[]{Integer.valueOf(xValue), Integer.valueOf(yValue)};
				nodePairs.add(neighbooringPair);
			}
			result.setNeighboringPairs(nodePairs);
		} catch (FileNotFoundException e) {
			System.err.println("File " + file.getName() + " not found!");
			result = null;
		} catch (NumberFormatException nfe) {
			System.err.println("File " + file.getName() + " is not in correct format!");
			result = null;
		} finally {
			scanner.close();
		}
		return result;
	}
	
	public static boolean ignoreRow(String row) {
		return row == null || row.equals("") || row.startsWith("#");
	}

}
