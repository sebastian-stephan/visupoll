package com.uni.hs13.visupoll.client.datastructures;

import java.util.ArrayList;

public class CantonData {
	
	private String nameCantonShort, nameCantonLong; 
	private boolean hasDistricts;
	private int cantonId, deliveredVotes = 0, registVoters = 0, validVotes = 0, yes = 0, no = 0; //id is the cantons identification, children the amount of townships / districts
	private float yesPercent, noPercent, turnout;
	private ArrayList<DistrictData> districts = new ArrayList<DistrictData>();
	
	public CantonData() { }
		
	//prints stats of a canton
	public void printStats() {
		System.out.println("Canton: " + cantonId + ", " + nameCantonShort + ", " + nameCantonLong + " , Registered Voters: " + registVoters + ", Delivered Votes: " + deliveredVotes + ", Turnout: " + turnout + ", Yes Votes: "
				+ yes + ", No Votes: " + no + ", in Percent: " + yesPercent);
		
	}
	
	
	//gives back the district with a certain id. Checks whether districts or townships are used.
	public DistrictData searchDistrict(int _id) {
		if (hasDistricts = false) {
			System.out.println("This Poll uses townships instead of districts!");
			return null;
		}
		for (int i = 0; i < districts.size(); i++) {
			if (districts.get(i).getDistrictId() == _id) {
				return districts.get(i);
			}
		}
		System.out.println("District not found.");
		return null;
	}
	
	
	
	//Getter methods.
	public DistrictData getDistrict(int _id) {
		return districts.get(_id);
	}
	public int getCantonId() {
		return cantonId;
	}
	public String getNameCantonShort() {
		return nameCantonShort;
	}
	public String getNameLong() {
		return nameCantonLong;
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
	public float getYesPercent() {
		return yesPercent;
	}
	public float getNoPercent() {
		return noPercent;
	}
	public float getTurnout() {
		return turnout;
	}
	public int getValidVotes() {
		return validVotes;
	}
	
}
