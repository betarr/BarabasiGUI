package sk.sochuliak.giraphe.utils;

import java.util.List;

public class NetworkImportObject {

	private String name = null;
	private List<int[]> neighboringPairs = null;

	public String getName() {
		return name;
	}
	
	public NetworkImportObject setName(String name) {
		this.name = name;
		return this;
	}
	
	public List<int[]> getNeighboringPairs() {
		return neighboringPairs;
	}
	
	public NetworkImportObject setNeighboringPairs(List<int[]> neighboringPairs) {
		this.neighboringPairs = neighboringPairs;
		return this;
	}
	
	public static boolean isInstanceValid(NetworkImportObject importObject) {
		return importObject.getName() != null 
				&& !importObject.getName().equals("")
				&& importObject.getNeighboringPairs() != null
				&& importObject.getNeighboringPairs().size() != 0;
	}
}
