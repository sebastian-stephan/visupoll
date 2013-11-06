package com.uni.hs13.visupoll.client.datastructures;

public class TownshipData {
	
	private String name; 
	private int id, deliveredVotes, registVoters, yes, no, validVotes, cantonId, districtId, townshipId; //id is the townships identification
	private float yesPercent, noPercent, turnout;

	//constructor, saves all data into variables of the class.
	public TownshipData() { }
	
	public void printStats() {
		System.out.println("Canton: " + cantonId + ", " + districtId + ", " + townshipId + ", " + name + " , Registered Voters: " + registVoters + ", Delivered Votes: " + deliveredVotes + ", Turnout: " + turnout + ", Yes Votes: "
				+ yes + ", No Votes: " + no + ", in Percent: " + yesPercent);
		
	}
	//Getter methods.
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
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
	public float getTurnout() {
		return turnout;
	}

}