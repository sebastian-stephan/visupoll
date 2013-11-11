package com.uni.hs13.visupoll.datastructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CantonData implements Serializable {
	public int 		cantonID,	// Primary Key
					pollID;		// Foreign Key of Poll
	public String 	cantonNameShort, 
					cantonNameLong; 
	public int	 	deliveredVotes, 
					registVoters, 
					yes,
					no,
					validVotes;
	public float 	yesPercent, 
					noPercent, 
					turnout;
	public ArrayList<DistrictData> districts = new ArrayList<DistrictData>();
	
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