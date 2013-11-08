package com.uni.hs13.visupoll.client.datastructures;

import java.util.ArrayList;

public class DistrictData {
	private int 	districtID, // Primary Key
					cantonID;	// Foreign Key of Canton
	private String 	districtName;
	private int 	deliveredVotes, 
					registVoters, 
					yes,
					no,
					validVotes;
	private float 	yesPercent, 
					noPercent, 
					turnout;
	private ArrayList<TownshipData> townships = new ArrayList<TownshipData>();

	// Return TownshipData of District based on townshipID
	public TownshipData getTownship(int _townshipID) {
		for (int i = 0; i < townships.size(); i++) {
			if (townships.get(i).getTownshipID() == _townshipID) {
				return townships.get(i);
			}
		}

		return null;
	}
	
	// Prints summary statistics of the District
	public void printStats() {
		System.out.println("District: " + districtID + ", " + districtName 
				+" , Registered Voters: " + registVoters + ", Delivered Votes: " 
				+ deliveredVotes + ", Turnout: " + turnout + ", Yes Votes: "
				+ yes + ", No Votes: " + no + ", in Percent: " + yesPercent);
	}

	// Getter methods
	public int getDistrictID() {
		return districtID;
	}
	public int getCantonID() {
		return cantonID;
	}
	public String getDistrictName() {
		return districtName;
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