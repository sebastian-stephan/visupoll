package com.uni.hs13.visupoll.client.datastructures;

import java.util.ArrayList;

public class DistrictData {
	
	private String districtName, cantonName; 
	private int cantonId, districtId, deliveredVotes = 0, registVoters = 0, yes = 0, no = 0, validVotes = 0; //id is the townships identification
	private float yesPercent = 0.f, noPercent = 0.f, turnout = 0.f;
	private ArrayList<TownshipData> townships = new ArrayList<TownshipData>();

	public DistrictData() { }
	
	public void printStats() {
		System.out.println("Canton: " + cantonId + ", " + districtId + ", " + districtName + " , Registered Voters: " + registVoters + ", Delivered Votes: " + deliveredVotes + ", Turnout: " + turnout + ", Yes Votes: "
				+ yes + ", No Votes: " + no + ", in Percent: " + yesPercent);
	}
	public TownshipData getTownship(int _id) {
		return townships.get(_id);
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
	public int getCantonId() {
		return cantonId;
	}
	public int getDistrictId() {
		return districtId;
	}

}