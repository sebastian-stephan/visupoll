package com.uni.hs13.visupoll.client.datastructures;

public class TownshipData {
	
	private int		townshipID,		// Primary Key
					districtID;		// Foreign Key of District
	private String 	townshipName; 
	private int 	deliveredVotes, 
					registVoters, 
					yes, 
					no, 
					validVotes;
	private float 	yesPercent, 
					noPercent, 
					turnout;

	// Prints summary statistics of the Township	
	public void printStats() {
		System.out.println("Township: " + townshipID + ", " + townshipName 
				+ " , Registered Voters: " + registVoters + ", Delivered Votes: " 
				+ deliveredVotes + ", Turnout: " + turnout + ", Yes Votes: "
				+ yes + ", No Votes: " + no + ", in Percent: " + yesPercent);
	}
	
	//Getter methods.
	public int getTownshipID() {
		return townshipID;
	}
	public int getDistrictID() {
		return districtID;
	}
	public String getTownshipName() {
		return townshipName;
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