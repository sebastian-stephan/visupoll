package com.uni.hs13.visupoll.datastructures.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.uni.hs13.visupoll.datastructures.DistrictData;

public class DistrictDataTest {
	//test
	DistrictData dt;
	
	@Before
	public void setUp() throws Exception {
		dt = new DistrictData();
		dt.districtID = 3;
		dt.cantonID = 5;
		dt.districtName = "Sample_Name";
		dt.deliveredVotes = 500;
		dt.registVoters = 600;
		dt.yes = 400;
		dt.no = 100;
		dt.validVotes = 499;
		dt.yesPercent = 80; 
		dt.noPercent = 20;
		dt.turnout = 2;
	}

	@Test
	public void tesGetDistrictID() {
		assertEquals(3, dt.getDistrictID());		
	}
	
	@Test
	public void tesGetCantonID() {
		assertEquals(5, dt.getCantonID());		
	}

	@Test
	public void tesGetDistrictName() {
		assertEquals("Sample_Name", dt.getDistrictName());		
	}
	
	@Test
	public void tesGetDeliveredVotes() {
		assertEquals(500, dt.getDeliveredVotes());		
	}
	
	@Test
	public void tesGetRegisteredVoters() {
		assertEquals(600, dt.getRegistVoters());		
	}
	
	@Test
	public void tesGetYes() {
		assertEquals(400, dt.getYes());		
	}
	
	@Test
	public void tesGetNo() {
		assertEquals(100, dt.getNo());		
	}
	
	@Test
	public void tesGetValidVotes() {
		assertEquals(499, dt.getValidVotes());		
	}
	
	@Test
	public void testYesPercent() {
		boolean result1 = false;
			if (80 == dt.getYesPercent()) 
				{
					result1 = true;
				}
		assertTrue(result1);
	}
	
	@Test
	public void testNoPercent() {
		boolean result2 = false;
			if (20 == dt.getNoPercent()) 
				{
					result2 = true;
				}
		assertTrue(result2);
	}
	
	@Test
	public void testTurnout() {
		boolean result3 = false;
			if (2 == dt.getTurnout()) 
				{
					result3 = true;
				}
		assertTrue(result3);
	}
	
}
