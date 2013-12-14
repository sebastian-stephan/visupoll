package com.uni.hs13.visupoll.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.google.gwt.user.server.Base64Utils;
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

	@Override
	public void sendEmail(String email, String text, String picture) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);        
        // Get Picture
        
        //System.out.println("Length on server: " + picture.length());
        byte[] imageByte = Base64Utils.fromBase64(picture.substring(22).replace("/", "_").replace("+", "$"));

        try {
            Message msg = new MimeMessage(session);
            
            msg.setFrom(new InternetAddress("admin@visupoll.appspotmail.com", "Visupoll"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
            msg.setSubject("Visupoll");
            
            // HTML part of email
            MimeBodyPart mbpText = new MimeBodyPart();
            mbpText.setContent(text, "text/html; charset=utf-8");
            
            MimeBodyPart mbpPicture = new MimeBodyPart();
	        DataSource ds = null;
			ds = new ByteArrayDataSource(imageByte, "image/png");
	        mbpPicture.setDataHandler(new DataHandler(ds));
	        mbpPicture.setHeader("Content-Type", "image/png");
	        mbpPicture.setFileName("results.png");
	        
	        // Create the Multipart and add its parts to it
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(mbpText);
	        mp.addBodyPart(mbpPicture);
			
			msg.setContent(mp);
			msg.saveChanges();
 
			Transport.send(msg);
 
            
            // Send
            Transport.send(msg);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
