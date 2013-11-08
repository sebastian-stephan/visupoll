package com.uni.hs13.visupoll.client.datastructures;

import java.util.ArrayList;

public class CantonData {
	// Set all to private for main Application
	private int		cantonID,	// Primary Key
					pollID;		// Foreign Key of Poll
	private String 	cantonNameShort, 
					cantonNameLong; 
	private int	 	deliveredVotes, 
					registVoters, 
					yes,
					no,
					validVotes;
	private float 	yesPercent, 
					noPercent, 
					turnout;
	private ArrayList<DistrictData> districts = new ArrayList<DistrictData>();
	
	// Return DistrictData of Canton based on districtID
	public DistrictData getDistrict(int _districtID) {
		for (int i = 0; i < districts.size(); i++) {
			if (districts.get(i).getDistrictID() == _districtID) {
				return districts.get(i);
			}
		}

		return null;
	}
	
	// Prints summary statistics of the poll
	public void printStats() {
		System.out.println("Canton: " + cantonID + ", " + cantonNameShort 
				+ ", " + cantonNameLong + " , Registered Voters: " + registVoters 
				+ ", Delivered Votes: " + deliveredVotes + ", Turnout: " 
				+ turnout + ", Yes Votes: "	+ yes + ", No Votes: " 
				+ no + ", in Percent: " + yesPercent);
	}
	
	// Getter methods.
	public int getCantonID() {
		return cantonID;
	}
	public int getPollID() {
		return pollID;
	}
	public String getCantonNameShort() {
		return cantonNameShort;
	}
	public String getCantonNameLong() {
		return cantonNameLong;
	}
	public int getDeliveredVotes() {
		return deliveredVotes;
	}
	public int getRegistVoters() {
		return registVoters;
	}
	public int getYes() {
		return yes;
	}
	public int getNo() {
		return no;
	}
	public int getValidVotes() {
		return validVotes;
	}
	public float getYesPercent() {
		return yesPercent;
	}
	public float getNoPercent() {
		return noPercent;
	}
	public float getTurnout() {
		return turnout;
	}
}