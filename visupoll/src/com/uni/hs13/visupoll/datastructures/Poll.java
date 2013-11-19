package com.uni.hs13.visupoll.datastructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Poll implements Serializable {
	public int	 	pollID; 	// Primary Key
	public String 	pollName, 
					description;
	public Date 	date;
	public int 		deliveredVotes, 
					registVoters, 
					yes, 
					no, 
					validVotes;
	public float 	yesPercent, 
					noPercent, 
					turnout;
	public DemographicData demographicData;
	public ArrayList<CantonData> cantons = new ArrayList<CantonData>();
	
	// Return CantonData of Poll based on cantonID
	public CantonData getCanton(int _cantonID) {
		for (int i = 0; i < cantons.size(); i++) {
			if (cantons.get(i).getCantonID() == _cantonID) {
				return cantons.get(i);
			}
		}
		
		return null;
	}
	
	// Prints summary statistics of the poll
	public void printStats() {
				
		System.out.println("Poll Results Switzerland: Registered Voters: " 
				+ registVoters + ", Delivered Votes: " + deliveredVotes 
				+ ", Turnout: " + turnout + ", Yes Votes: "
				+ yes + ", No Votes: " + no + ", in Percent: " + yesPercent);
	}
	
	// Getter methods.
	public int getPollID() {
		return pollID;
	}
 	public String getPollName() {
		return pollName;
	}
	public String getDescription() {
		return description;
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