package edu.ncsu.csc316.social.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * Tests the ReportManager class
 * @author Arnold Elamthuruthil
 */
public class ReportManagerTest {

	/**
	 * Tests the getConectionsByPerson() method for 1 connection
	 */
	@Test
	public void testGetConnectionsByPerson1() {
		ReportManager r = null;
		try {
			r = new ReportManager("input/people1.txt", "input/connections1.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String out = r.getConnectionsByPerson();
		String exp = "Connections for Justin Boyer (boyerj706) {\n"
				+ "   Napoleon Kertzmann (kertzmannn329) on Telegram since Wed Nov 16 03:42:00 EST 2016\n"
				+ "}\n"
				+ "Connections for Napoleon Kertzmann (kertzmannn329) {\n"
				+ "   Justin Boyer (boyerj706) on Telegram since Wed Nov 16 03:42:00 EST 2016\n"
				+ "}\n";
		assertEquals(exp, out);
	}
	
	/**
	 * Tests the getConectionsByPerson() method for 2+ connections
	 */
	@Test
	public void testGetConnectionsByPerson2() {
		ReportManager r = null;
		try {
			r = new ReportManager("input/people2.txt", "input/connections2.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String out = r.getConnectionsByPerson();
		String exp = "Connections for Justin Boyer (boyerj706) {\n"
				+ "   Napoleon Kertzmann (kertzmannn329) on Telegram since Wed Nov 16 03:42:00 EST 2016\n"
				+ "   Royce Simonis (simonisr882) on Twitch since Mon Aug 03 02:54:39 EDT 2020\n"
				+ "}\n"
				+ "Connections for Napoleon Kertzmann (kertzmannn329) {\n"
				+ "   Justin Boyer (boyerj706) on Telegram since Wed Nov 16 03:42:00 EST 2016\n"
				+ "}\n"
				+ "Connections for Royce Simonis (simonisr882) {\n"
				+ "   Justin Boyer (boyerj706) on Twitch since Mon Aug 03 02:54:39 EDT 2020\n"
				+ "}\n";
		assertEquals(exp, out);
	}
	
	/**
	 * Tests the getConectionsByPlatform() method for 1 platform
	 */
	@Test
	public void testGetConnectionsByPlatform1() {
		ReportManager r = null;
		try {
			r = new ReportManager("input/people1.txt", "input/connections1.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String out = r.getConnectionsByPlatform();
		String exp = "Connections on Telegram {\n"
				+ "   Wed Nov 16 03:42:00 EST 2016: Justin Boyer (boyerj706) <--> Napoleon Kertzmann (kertzmannn329)\n"
				+ "}\n";
		assertEquals(exp, out);
	}
	
	/**
	 * Tests the getConectionsByPlatform() method for 2 platforms
	 */
	@Test
	public void testGetConnectionsByPlatform2() {
		ReportManager r = null;
		try {
			r = new ReportManager("input/people2.txt", "input/connections2.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String out = r.getConnectionsByPlatform();
		String exp = "Connections on Telegram {\n"
				+ "   Wed Nov 16 03:42:00 EST 2016: Justin Boyer (boyerj706) <--> Napoleon Kertzmann (kertzmannn329)\n"
				+ "}\n"
				+ "Connections on Twitch {\n"
				+ "   Mon Aug 03 02:54:39 EDT 2020: Justin Boyer (boyerj706) <--> Royce Simonis (simonisr882)\n"
				+ "}\n";
		assertEquals(exp, out);
	}
	
	/**
	 * Tests the getConectionsByPerson() method for 0 people
	 */
	@Test
	public void testGetConnectionsByPersonEmpty() {
		ReportManager r = null;
		try {
			r = new ReportManager("input/emptyPeople.txt", "input/connectionsFull.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String people = r.getConnectionsByPerson();

		String exp = "No people information was provided.";
		
		assertEquals(exp, people);
		
	}
	
	/**
	 * Tests the getConectionsByPlatform() method for 0 connections
	 */
	@Test
	public void testGetConnectionsByPlatformEmpty() {
		ReportManager r = null;
		try {
			r = new ReportManager("input/peopleFull.txt", "input/emptyConn.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String platform = r.getConnectionsByPlatform();

		String exp = "No connections exist in the social media network.";
		
		assertEquals(exp, platform);
		
	}
	
	
	

}
