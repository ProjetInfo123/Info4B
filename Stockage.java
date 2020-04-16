import java.util.*;
import twitter4j.*;
import java.io.*;

public class Stockage {
	public Hashtable<User,Hashtable<Date,Status>> tweets;

	public Stockage(){
		this.tweets=new Hashtable<User,Hashtable<Date,Status>>();
	}

	public void ajoutTweet(Status t ){
		User u=t.getUser();
		Date d=t.getCreatedAt();
		if(this.tweets.containsKey(u)){
			Hashtable<Date,Status> stockDate=this.tweets.get(u);
			stockDate.put(d,t);
			this.tweets.put(u,stockDate);
		}
		else{
			Hashtable<Date,Status> stockDate=new Hashtable<Date,Status>();
			stockDate.put(d,t);
			this.tweets.put(u,stockDate);

		}

	}

	public Status getTweet(Status t){
		User u=t.getUser();
		Date d=t.getCreatedAt();
		if(this.tweets.containsKey(u)){
			if(this.tweets.get(u).containsKey(d)){
				return this.tweets.get(u).get(d);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}

}
