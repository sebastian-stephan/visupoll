package com.uni.hs13.visupoll.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uni.hs13.visupoll.client.rpc.PollDataService;
import com.uni.hs13.visupoll.datastructures.Poll;

public class PollDataServiceImpl extends RemoteServiceServlet implements
		PollDataService {

	@Override
	public Poll getPoll(int _id) {
		Poll poll = null;
		try {
			FileInputStream fileIn = new FileInputStream("data/poll_" + String.format("%03d", _id) + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			poll = (Poll) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return poll;
	}

	@Override
	public ArrayList<Poll> getListOfPolls() {
		ArrayList<Poll> pollList = null;
		try {
			FileInputStream fileIn = new FileInputStream("data/pollList.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			pollList = (ArrayList<Poll>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pollList;
	}

}
