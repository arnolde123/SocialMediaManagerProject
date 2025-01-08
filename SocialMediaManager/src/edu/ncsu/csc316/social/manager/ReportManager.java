package edu.ncsu.csc316.social.manager;

import java.io.FileNotFoundException;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.social.data.Connection;
import edu.ncsu.csc316.social.data.Person;
import edu.ncsu.csc316.social.dsa.Algorithm;
import edu.ncsu.csc316.social.dsa.DSAFactory;
import edu.ncsu.csc316.social.dsa.DataStructure;

/**
 * ReportManager uses SocialMediaManager to take a person and connections
 * file and output a list of the connections sorted by person or sorted by platform.
 * The people/platform are sorted alphabetically, while the connections
 * are sorted by either the other username in the connection, or date.
 * These lists are output as strings, and have error messages if no people or connections
 * are input.
 * @author Arnold Elamthuruthil
 */
public class ReportManager {
	/** Single instance of the SocialMediaManager */
	private SocialMediaManager manager;

    /** Map of usernames mapped to a Person object */
    private Map<String, Person> persons;
    
    /**
	 * Creates a ReportManager with the default map type, SkipList
	 * @param peopleFile input people text file
	 * @param connectionFile input connections text file
	 * @throws FileNotFoundException if file is not found
	 */
    public ReportManager(String peopleFile, String connectionFile) throws FileNotFoundException {
        this(peopleFile, connectionFile, DataStructure.SKIPLIST);
    }

    /**
	 * Creates a ReportManager with the custom map type. Also creates a persons object
	 * that is a map of all people's usernames as strings, mapped to the corresponding
	 * Person object.
	 * @param peopleFile input people text file
	 * @param connectionFile input connections text file
	 * @param mapType type of map to implement
	 * @throws FileNotFoundException if file is not found
	 */
	public ReportManager(String peopleFile, String connectionFile, DataStructure mapType) throws FileNotFoundException {
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.COUNTING_SORT);
        DSAFactory.setMapType(mapType);
        this.manager = new SocialMediaManager(peopleFile, connectionFile, mapType);
        this.persons = manager.getPeople();
        
    }

    /**
     * Prints a list with the usernames of the people and 
     * the list of their connections. The people are in alphabetic order and the 
     * list of their connections are also sorted by name, then id, then date.
     * @return String version list of usernames and list of connections for the usernames
     */
	public String getConnectionsByPerson() {
		Map<String, List<Connection>> map = manager.getConnectionsByPerson();
		if (map.size() == 0) {
			if (persons.size() == 0) {
				return "No people information was provided.";
			} else {
				return "No connections exist in the social media network.";
			}
		}
    	StringBuilder out = new StringBuilder();

    	for (String id : map) {
    		Person p = persons.get(id);
    		List<Connection> connections = map.get(id);
    		out.append("Connections for ").append(p.getFirst()).append(" ");
    		out.append(p.getLast()).append(" (").append(id).append(") {\n");
    		if (connections.size() == 0) {
    			out.append("   No connections exist\n");
    		} else {
    			for (Connection c : connections) {
        			String p1Id = c.getPeople()[0];
        			if (p1Id.equals(id)) {
        				p1Id = c.getPeople()[1];
        			}
        			Person p1 = persons.get((String) p1Id);
        			out.append("   ").append(p1.getFirst()).append(" ");
        			out.append(p1.getLast()).append(" (").append(p1Id);
        			out.append(") on ").append(c.getPlatform()).append(" since ");
        			out.append(c.getDate().toString()).append("\n");
        		}
    		}
    		out.append("}\n");
    	}
    	return out.toString();	
    }

	/**
     * Prints a list with the platforms the connections and the list of their connections
     * associated with each platform. The platforms are sorted alphabetically, and their
     * connections are sorted by date, then id.
     * @return String version of list of platforms and list of connections for each platform
     */
	public String getConnectionsByPlatform() {
		Map<String, List<Connection>> map = manager.getConnectionsByPlatform();
		if (map.size() == 0) {
			if (persons.size() == 0) {
				return "No people information was provided."; 
			} else {
				return "No connections exist in the social media network.";
			}
		}
    	StringBuilder out = new StringBuilder();
    	
		for (String platform : map) {
	        out.append("Connections on ").append(platform).append(" {\n");
			List<Connection> platConns = map.get(platform);
			for (Connection c : platConns) {
				//Orders the two people in the connection
				String[] peoples = c.getPeople();
				Person u1 = persons.get(peoples[0]);
				Person u2 = persons.get(peoples[1]);
				String u1Str = u1.getFirst() + " " + u1.getLast() + " (" +
						u1.getId() + ")";
				String u2Str = u2.getFirst() + " " + u2.getLast() + " (" +
						u2.getId() + ")";
				String date = c.getDate().toString();
				out.append("   ").append(date).append(": ");
				if (u1.compareTo(u2) < 0) {
	                out.append(u1Str).append(" <--> ").append(u2Str).append("\n");
	            } else {
	                out.append(u2Str).append(" <--> ").append(u1Str).append("\n");
	            }
 			}
	        out.append("}\n");
		}
    	return out.toString();
    }
    

}
