package com.uni.hs13.visupoll.datastructures.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.uni.hs13.visupoll.datastructures.CantonData;

public class CantonDataTest {
	
	CantonData cd;

	@Before
	public void setUp() throws Exception {
		
		cd = new CantonData();
		cd.cantonID = 3;
		cd.pollID = 4; //test
		cd.cantonNameShort = "UR";
		cd.cantonNameLong = "Uri";
		cd.deliveredVotes = 500;
		cd.registVoters = 600;
		cd.yes = 400;
		cd.no = 100;
		cd.validVotes = 499;
		cd.yesPercent = 80; 
		cd.noPercent = 20;
		cd.turnout = 2;
	}

	
	@Test
	public void tesGetCantonID() {
		assertEquals(3, cd.getCantonID());		
	}
	
	@Test
	public void tesGetPollID() {
		assertEquals(4, cd.getPollID());		
	}

	@Test
	public void tesGetDCantonNameShort() {
		assertEquals("UR", cd.getCantonNameShort());		
	}
	
	@Test
	public void tesGetDCantonNameLong() {
		assertEquals("Uri", cd.getCantonNameLong());		
	}
	
	@Test
	public void tesGetDeliveredVotes() {
		assertEquals(500, cd.getDeliveredVotes());		
	}
	
	@Test
	public void tesGetRegisteredVoters() {
		assertEquals(600, cd.getRegistVoters());		
	}
	
	@Test
	public void tesGetYes() {
		assertEquals(400, cd.getYes());		
	}
	
	@Test
	public void tesGetNo() {
		assertEquals(100, cd.getNo());		
	}
	
	@Test
	public void tesGetValidVotes() {
		assertEquals(499, cd.getValidVotes());		
	}
	
	@Test
	public void testYesPercent() {
		boolean result1 = false;
			if (80 == cd.getYesPercent()) 
				{
					result1 = true;
				}
		assertTrue(result1);
	}
	
	@Test
	public void testNoPercent() {
		boolean result2 = false;
			if (20 == cd.getNoPercent()) 
				{
					result2 = true;
				}
		assertTrue(result2);
	}
	
	@Test
	public void testTurnout() {
		boolean result3 = false;
			if (2 == cd.getTurnout()) 
				{
					result3 = true;
				}
		assertTrue(result3);
	}

}
