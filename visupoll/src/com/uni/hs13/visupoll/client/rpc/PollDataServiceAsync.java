package com.uni.hs13.visupoll.client.rpc;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uni.hs13.visupoll.datastructures.Poll;

public interface PollDataServiceAsync {

	void getPoll(int _id, AsyncCallback<Poll> callback);

	void getListOfPolls(AsyncCallback<ArrayList<Poll>> callback);


}
