package com.uni.hs13.visupoll.datastructures.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.uni.hs13.visupoll.datastructures.Poll;

public class PollTest {
	//test
	Poll p;

	@Before
	public void setUp() throws Exception {
		p = new Poll();
		p.pollID = 5;
		p.pollName = "Sample_Name";
		p.description = "Sample_Description";
		p.deliveredVotes = 1000; 
		p.registVoters = 1100;
		p.yes = 800;
		p.no = 200;
		p.validVotes = 600;
		p.yesPercent = 80; 
		p.noPercent =20;
		p.turnout = 1;
		
	}


	@Test
	public void testGetPollID() {
		assertEquals(5, p.getPollID());
	}
	
	@Test
	public void testGetPollName() {
		assertEquals("Sample_Name", p.getPollName());
	}
	
	@Test
	public void testGetDescription() {
		assertEquals("Sample_Description", p.getDescription());
	}
	
	@Test
	public void testGetDeliveredVotes() {
		assertEquals(1000, p.getDeliveredVotes());
	}
	
	@Test
	public void testGetRegistVoters() {
		assertEquals(1100, p.getRegistVoters());
	}	
		
	@Test
	public void testGetYes() {
		assertEquals(800, p.getYes());
	}
	
	@Test
	public void testGetNo() {
		assertEquals(200, p.getNo());
	}
		
	@Test
	public void testGetValidVotes() {
		assertEquals(600, p.getValidVotes());
	}	
		
	@Test
	public void testYesPercent() {
		boolean result1 = false;
			if (80 == p.getYesPercent()) 
				{
					result1 = true;
				}
		assertTrue(result1);
	}	
		
	@Test
	public void testNoPercent() {
		boolean result2 = false;
			if (20 == p.getNoPercent()) 
				{
					result2 = true;
				}
		assertTrue(result2);
	}
	
	@Test
	public void testTurnout() {
		boolean result3 = false;
			if (1 == p.getTurnout()) 
				{
					result3 = true;
				}
		assertTrue(result3);
	}
		
}
