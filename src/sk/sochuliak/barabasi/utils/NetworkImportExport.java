package sk.sochuliak.barabasi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class NetworkImportExport {
	
	public static String DEFAULT_DIRECTORY = null;
	
	public static boolean export(File file, List<int[]> neighboringPairNodes) {
		boolean result = false;
		PrintWriter pt = null;
		try {
			pt = new PrintWriter(file);
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
	
	public static List<int[]> importFromFile(File file) {
		List<int[]> result = new ArrayList<int[]>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileReader(file));
			while (scanner.hasNextLine()) {
				String row = scanner.nextLine();
				String[] splitted = row.split("\t");
				int[] neighbooringPair = new int[]{Integer.valueOf(splitted[0]), Integer.valueOf(splitted[1])};
				result.add(neighbooringPair);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File " + file.getName() + " not found!");
			result = new ArrayList<int[]>(0);
		} catch (NumberFormatException nfe) {
			System.err.println("File " + file.getName() + " is not in correct format!");
			result = new ArrayList<int[]>(0);
		} finally {
			scanner.close();
		}
		return result;
	}

}
