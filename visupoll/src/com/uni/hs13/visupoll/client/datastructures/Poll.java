package com.uni.hs13.visupoll.client.datastructures;

import java.util.Date;
import java.util.ArrayList;

public class Poll {
	
	private String name, description; //id is the polls identification
	private Date date;
	private boolean hasTownships;
	private int deliveredVotes = 0, registVoters = 0, yes = 0, no = 0, id = 0, validVotes = 0;
	private float yesPercent, noPercent, turnout;
	private ArrayList<CantonData> cantons = new ArrayList<CantonData>();
	
	
	public Poll() {	}
	
	//returns selected Canton via searching after its ID, searches ArrayList for the given id, if not found returns Null
	public CantonData getCanton(int _id) {
		return cantons.get(_id);
	}
	
	//prints stats of a canton
	public void printStats() {
		System.out.println("Poll Results Switzerland: Registered Voters: " + registVoters + ", Delivered Votes: " + deliveredVotes + ", Turnout: " + turnout + ", Yes Votes: "
				+ yes + ", No Votes: " + no + ", in Percent: " + yesPercent);
	}
	
	//Getter methods.
 	public String getName() {
		return name;
	}
	public Date getDate() {
		return date;
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
	public int getId() {
		return id;
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
	public String getDescription() {
		return description;
	}
	
}