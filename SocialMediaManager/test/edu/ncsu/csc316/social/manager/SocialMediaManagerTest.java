package edu.ncsu.csc316.social.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.social.data.Connection;
import edu.ncsu.csc316.social.data.Person;

/**
 * Tests the SocialMediaManager class
 * @author Arnold Elamthuruthil
 */
public class SocialMediaManagerTest {

	/**
	 * Tests the getConnectionsByPerson() method with 1 connection
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetConnectionsByPerson1() {
        SocialMediaManager c = null;
		try {
			c = new SocialMediaManager("input/people1.txt", "input/connections1.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        Map<String, List<Connection>> result = c.getConnectionsByPerson();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        
        List<Connection> connectionsForPerson1 = result.get("kertzmannn329");
        assertEquals(1, connectionsForPerson1.size()); 
        
        List<Connection> connectionsForPerson2 = result.get("boyerj706");
        assertEquals(1, connectionsForPerson2.size()); 
    }
	
	/**
	 * Tests the getConnectionsByPerson() method with 2 connections
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetConnectionsByPerson2() {
        SocialMediaManager c = null;
		try {
			c = new SocialMediaManager("input/peopleFull.txt", "input/connectionsFull.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        Map<String, List<Connection>> result = c.getConnectionsByPerson();
        
        assertNotNull(result);
        assertEquals(8, result.size()); 
        
        List<Connection> connectionsForPerson1 = result.get("kertzmannn329");
        assertEquals(4, connectionsForPerson1.size());
        
        List<Connection> connectionsForPerson2 = result.get("boyerj706");
        assertEquals(7, connectionsForPerson2.size()); 
    }
	
	/**
	 * Tests the getConectionsByPlatform() method for 1 platform
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetConnectionsByPlatform1() {
		  SocialMediaManager c = null;
			try {
				c = new SocialMediaManager("input/people1.txt", "input/connections1.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        
	        Map<String, List<Connection>> result = c.getConnectionsByPlatform();
	        
	        assertNotNull(result);
	        assertEquals(1, result.size()); 
	        
	        List<Connection> connectionsForPlatform1 = result.get("Telegram");
	        assertEquals(1, connectionsForPlatform1.size()); 
	        
	}
	
	/**
	 * Tests the getConectionsByPlatform() method for 11 platforms
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetConnectionsByPlatform2() {
		SocialMediaManager c = null;
		try {
			c = new SocialMediaManager("input/peopleFull.txt", "input/connectionsFull.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	       
	    Map<String, List<Connection>> result = c.getConnectionsByPlatform();
	       
	    assertNotNull(result);
	    assertEquals(11, result.size()); 
	    
	    List<Connection> discord = result.get("Discord");
	    assertEquals(2, discord.size()); 
	    assertEquals("XAFL8903", discord.get(0).getId());
	    assertEquals(3, result.get("Facebook").size()); 
	    assertEquals(2, result.get("Instagram").size()); 
	    assertEquals(3, result.get("LinkedIn").size()); 
	    assertEquals(1, result.get("Reddit").size()); 
	    assertEquals(2, result.get("Telegram").size()); 
	    assertEquals(1, result.get("TikTok").size()); 
	    assertEquals(2, result.get("Twitch").size()); 
	    assertEquals(1, result.get("Twitter").size()); 
	    assertEquals(1, result.get("WeChat").size()); 
	    assertEquals(2, result.get("WhatsApp").size()); 

	}
	
	/**
	 * Tests the getPeople() method.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test 
	public void testGetPeople() {
		SocialMediaManager c = null;
		try {
			c = new SocialMediaManager("input/peopleFull.txt", "input/connectionsFull.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	       
	    Map<String, Person> result = c.getPeople();
	    assertEquals(8, result.size());
	      
	}
	
	/**
	 * Tests the getConnectionsByPerson() method with 1 connection
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetConnectionsByPersonSame() {
        SocialMediaManager c = null;
		try {
			c = new SocialMediaManager("input/peopleSame.txt", "input/connectionsSame.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        Map<String, List<Connection>> result = c.getConnectionsByPerson();
        
        assertNotNull(result);
        assertEquals(6, result.size()); 
        
        List<Connection> connectionsForPerson1 = result.get("kertzmannn329");
        assertEquals(5, connectionsForPerson1.size()); 
        
        List<Connection> connectionsForPerson2 = result.get("boyerj706");
        assertEquals(1, connectionsForPerson2.size());
        
        List<Connection> connectionsForPerson3 = result.get("boyesdfrb706");
        assertEquals(1, connectionsForPerson3.size());
    }

}
