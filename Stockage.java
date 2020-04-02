import java.util.*;
import twitter4j.*;
import java.io.*;

public class Stockage {
	public Hashtable<User,Hashtable<Date,Status>> stock;

	public Stockage(){
		this.stock=new Hashtable<User,Hashtable<Date,Status>>();
	}

	public void ajoutTweet(Status t ){
		User u=t.getUser();
		Date d=t.getCreatedAt();
		if(this.stock.containsKey(u)){
			Hashtable<Date,Status> stockDate=this.stock.get(u);
			stockDate.put(d,t);
			this.stock.put(u,stockDate);
		}
		else{
			Hashtable<Date,Status> stockDate=new Hashtable<Date,Status>();
			stockDate.put(d,t);
			this.stock.put(u,stockDate);

		}

	}

	public Status getTweet(Status t){
		User u=t.getUser();
		Date d=t.getCreatedAt();
		if(this.stock.containsKey(u)){
			if(this.stock.get(u).containsKey(d)){
				return this.stock.get(u).get(d);
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
