package com.uni.hs13.visupoll.client.rpc;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uni.hs13.visupoll.datastructures.Poll;

@RemoteServiceRelativePath("PollDataService")
public interface PollDataService extends RemoteService{
	public Poll getPoll(int _id);
	ArrayList<Poll> getListOfPolls();
	void sendEmail(String email, String text, String picture);
}
