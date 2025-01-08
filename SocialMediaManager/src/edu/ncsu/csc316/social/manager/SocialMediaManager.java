package edu.ncsu.csc316.social.manager;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Date;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.social.data.Connection;
import edu.ncsu.csc316.social.data.Person;
import edu.ncsu.csc316.social.dsa.Algorithm;
import edu.ncsu.csc316.social.dsa.DSAFactory;
import edu.ncsu.csc316.social.dsa.DataStructure;
import edu.ncsu.csc316.social.io.InputReader;

/**
 * SocialMediaManager accepts input files of lists of people
 * and the connections between them, and is then able to output
 * a list of the connections sorted by person or sorted by platform.
 * The people/platform are sorted alphabetically, while the connections
 * are sorted by either the other username in the connection, or date.
 * SocialMediaManager also has a getPeople method that returns a map of
 * username mapped to the Person.
 * @author Arnold Elamthuruthil
 */
public class SocialMediaManager {
	
	/** List of people in input file */
	private List<Person> people;
	/** List of connections in input file */
	private List<Connection> conn;
	/** Map of each person with their username as key */
	private Map<String, Person> pMap;
	/** Map of all connections by each person */
	private Map<String, List<Connection>> personMap;

	/**
	 * Creates a SocialMediaManager with the default map type, SkipList.
	 * @param peopleFile input people text file
	 * @param connectionFile input connections text file
	 * @throws FileNotFoundException if file is not found
	 */
    public SocialMediaManager(String peopleFile, String connectionFile) throws FileNotFoundException {
        this(peopleFile, connectionFile, DataStructure.SKIPLIST);
    }

    /**
	 * Creates a SocialMediaManager and sets the list type, comparison sorter
	 * type, nonComparison Sorter type, and map type. It creates
	 * a list of all connections and people found in the input files. A map
	 * that maps username to Person object is also created.
	 * @param peopleFile input people text file
	 * @param connectionFile input connections text file
	 * @param mapType type of map to implement
	 * @throws FileNotFoundException if file is not found
	 */
    public SocialMediaManager(String peopleFile, String connectionFile, DataStructure mapType)
            throws FileNotFoundException {
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.COUNTING_SORT);
        DSAFactory.setMapType(mapType);
        this.conn = InputReader.readConnectionData(connectionFile);
        this.people = InputReader.readPersonData(peopleFile);
        this.pMap = getPeople();
    }
    
    /**
     * Retrieves a map with the usernames of the people as 
     * keys and the Person with the username as the value
     * @return map of username and Person object
     */
    public Map<String, Person> getPeople() {
      	pMap = DSAFactory.getMap(null);
        for (Person p : people) {
        	pMap.put(p.getId(), p);
        }
        
        return pMap;
    }

    /**
     * Returns a map with the usernames of the people as keys and 
     * the list of their connections as the values. The people are sorted
     * alphabetically by last, then first name. The lists of connections
     * are sorted by the second name, then id, then date.
     * @return map of usernames and list of connections for the usernames
     */
	public Map<String, List<Connection>> getConnectionsByPerson() {
		personMap = DSAFactory.getMap(null);
		if (people.size() == 0 || conn.size() == 0) {
    		return personMap;
    	} 
    	for (Person p : people) {
    		personMap.put(p.getId(), DSAFactory.getIndexedList());
    	}
    	for (Connection c : conn) {
    		String[] p = c.getPeople();
    		//Find first id map
    		List<Connection> old1 = personMap.get(p[0]);
    		old1.addLast(c);
    		personMap.put(p[0], old1);
    		//Find second id map
    		List<Connection> old2 = personMap.get(p[1]);
    		old2.addLast(c);
    		personMap.put(p[1], old2);
    	}
    	for (List<Connection> list : personMap.values()) {
    		int size = list.size();
    		Connection[] connArr = new Connection[size];
    		for (int i = 0; i < size; i++) {
    			connArr[i] = list.get(i);
    		}
    		DSAFactory.getComparisonSorter(new SamePersonConnectionComparator()).sort(connArr);
          	
    		int i = 0;
         	for (Connection c : connArr) {
         		list.set(i, c);
         		i++;
         	}
    	}
    	
		return personMap;
    }

	/**
     * Returns a map with the platforms the connections are made on as keys and 
     * the list of their connections as the values. The platforms are sorted 
     * alphabetically, and their connections are sorted by date, then id.
     * @return map of platforms and list of connections for the platforms
     */
    public Map<String, List<Connection>> getConnectionsByPlatform() {
    	Map<String, List<Connection>> platformMap = DSAFactory.getMap(null);
    	if (people.size() == 0 || conn.size() == 0) {
    		return platformMap;
    	}
    	
        Connection[] dateArr = new Connection[conn.size()];
        for (int i = 0; i < conn.size(); i++) {
            dateArr[i] = conn.get(i);
        }
        DSAFactory.getComparisonSorter(new DateComparator()).sort(dateArr);
        
        List<String> platforms = DSAFactory.getIndexedList();
        boolean contains = false;
        for (int i = 0; i < dateArr.length; i++) {
            String platform = dateArr[i].getPlatform();
            
            for (String p : platforms) {
            	if (p.equals(platform)) {
            		contains = true;
            	}
            }
            if (!contains) {
                platformMap.put(platform, DSAFactory.getIndexedList());
                platforms.addLast(platform);
            } 
            
            platformMap.get(platform).addLast(dateArr[i]);
            contains = false;
        }
        
        return platformMap;
    }
    
    /**
     * Comparator that compares dates and returns an integer representing
     * their comparison
     * @author Arnold Elamthuruthil
     */
    private class DateComparator implements Comparator<Connection> {

    	@Override
    	public int compare(Connection o1, Connection o2) {
    		Date d1 = o1.getDate();
    		Date d2 = o2.getDate();
    		int date = d1.compareTo(d2);
    		if (date == 0) {
    			return o1.getId().compareTo(o2.getId());
    		}
    		return date;
    	}

    }
    
    /**
     * Comparator that compares Connections and returns an integer representing
     * their comparison. First finds which two usernames are the same, and then
     * compares the other two usernames. If there are two pairs of usernames, then
     * compare dates.
     * @author Arnold Elamthuruthil
     */
    private class SamePersonConnectionComparator implements Comparator<Connection> {

    	@Override
    	public int compare(Connection o1, Connection o2) {
    		String[] n1 = o1.getPeople();
            String[] n2 = o2.getPeople();

            Person p1 = pMap.get(n1[0]);
            Person p2 = pMap.get(n1[1]);
            Person p3 = pMap.get(n2[0]);
            Person p4 = pMap.get(n2[1]);
            
            Person compare1 = null;
            Person compare2 = null;
                    
            
            int date = o1.getDate().compareTo(o2.getDate());

            if (p1.equals(p3) && !p2.equals(p4)) {
            	compare1 = p2;
    			compare2 = p4;
    		} else if (p1.equals(p4) && !p2.equals(p3)) {
    			compare1 = p2;
    			compare2 = p3;
    		} else if (p2.equals(p3) && !p1.equals(p4)) {
    			compare1 = p1;
    			compare2 = p4;
    		} else if (p2.equals(p4) && !p1.equals(p3)) {
    			compare1 = p1;
    			compare2 = p3;
    		} else {
    			return date;
    		}
            int last = compare1.getLast().compareTo(compare2.getLast());
        	if (last == 0) {
        		int first = compare1.getFirst().compareTo(compare2.getFirst());
        		if (first == 0) {
        			int id = compare1.getId().compareTo(compare2.getId());
        			if (id == 0) {
        				return date;
        			}
        			return id;
        		}
        		return first;
        	}
			return last;
    	}
    }
}
