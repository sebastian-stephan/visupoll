package com.uni.hs13.visupoll.datastructures.Test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.uni.hs13.visupoll.datastructures.TownshipData;

public class TownshipDataTest {
	//test
	TownshipData td;
	@Before
	public void setUp() throws Exception {
		
		td = new TownshipData();
		td.townshipID = 3;
		td.districtID = 5;
		td.townshipName = "Sample_Name";
		td.deliveredVotes = 500;
		td.registVoters = 600;
		td.yes = 400;
		td.no = 100;
		td.validVotes = 499;
		td.yesPercent = 80; 
		td.noPercent = 20;
		td.turnout = 2;
	}

	@Test
	public void tesGetTownshipID() {
		assertEquals(3, td.getTownshipID());		
	}
	
	@Test
	public void tesGetDistrictID() {
		assertEquals(5, td.getDistrictID());		
	}

	@Test
	public void tesGetTownshipName() {
		assertEquals("Sample_Name", td.getTownshipName());		
	}
	
	@Test
	public void tesGetDeliveredVotes() {
		assertEquals(500, td.getDeliveredVotes());		
	}
	
	@Test
	public void tesGetRegisteredVoters() {
		assertEquals(600, td.getRegistVoters());		
	}
	
	@Test
	public void tesGetYes() {
		assertEquals(400, td.getYes());		
	}
	
	@Test
	public void tesGetNo() {
		assertEquals(100, td.getNo());		
	}
	
	@Test
	public void tesGetValidVotes() {
		assertEquals(499, td.getValidVotes());		
	}
	
	@Test
	public void testYesPercent() {
		boolean result1 = false;
			if (80 == td.getYesPercent()) 
				{
					result1 = true;
				}
		assertTrue(result1);
	}
	
	@Test
	public void testNoPercent() {
		boolean result2 = false;
			if (20 == td.getNoPercent()) 
				{
					result2 = true;
				}
		assertTrue(result2);
	}
	
	@Test
	public void testTurnout() {
		boolean result3 = false;
			if (2 == td.getTurnout()) 
				{
					result3 = true;
				}
		assertTrue(result3);
	}

}
