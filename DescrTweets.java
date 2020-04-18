import java.util.*;
import twitter4j.*;
import java.io.*;



public class DescrTweets {
	private User utilisateur;
	private Date date;
	private String message;
	private HashtagEntity hashtags[];
	private UserMentionEntity mentions[];
	private URLEntity url[];

	public DescrTweets(Status tweet){
		System.out.println();
		System.out.println("Ce tweet a été écrit par : @"+tweet.getUser().getScreenName());
		System.out.println("Ce tweet date du : "+tweet.getCreatedAt());
		System.out.println("Le message du tweet est : "+tweet.getText());
		HashtagEntity ht[]=tweet.getHashtagEntities();
		UserMentionEntity ume[]=tweet.getUserMentionEntities();
		URLEntity ue[]=tweet.getURLEntities();
		for(int i=0;i<ht.length;i++){
			System.out.println("Ce tweet contient le hashtag : #"+ht[i].getText());
		}
		for(int i=0;i<ume.length;i++){
			System.out.println("Ce tweet contient la mention d'utilisateur de : @"+ume[i].getScreenName());
		}
		for(int i=0;i<ue.length;i++){
			System.out.println("Ce tweet contient les URL : "+ue[i].getURL());
		}

		this.utilisateur=tweet.getUser();
		this.date=tweet.getCreatedAt();
		this.message=tweet.getText();
		this.hashtags=tweet.getHashtagEntities();
		this.mentions=tweet.getUserMentionEntities();
		this.url=tweet.getURLEntities();
	}






	public class Indexation extends Thread {//ajouter des hashtables
		private LinkedList<Status> fa;
		private DescrTweets d;

		public Indexation(LinkedList<Status> fa,DescrTweets d){
								this.fa=fa;
								this.d=d;
		}

		public void run(){
				while(fa.size()>0){
					Status tweet=fa.removeFirst();





				}



		}

	}



    public static void main(String[] args) throws TwitterException{
	 // The factory instance is re-useable and thread safe.
	  LinkedList<Status> fa=new LinkedList<>();
    Twitter twitter = TwitterFactory.getSingleton();
    Query query = new Query("je binome");
		query.setCount(100);
    QueryResult result = twitter.search(query);
		DescrTweets o;
		Stockage<User> u=new Stockage();
		Stockage<Date> d=new Stockage();
		Stockage<String> c=new Stockage();
		Stockage<HashtagEntity[]> h=new Stockage();
		Stockage<UserMentionEntity[]> m=new Stockage();
		Stockage<URLEntity[]> l=new Stockage();

		for (Status status : result.getTweets()) {
				//o=new DescrTweets(status);
				fa.addLast(status);
		}

		System.out.println("La taille est de "+fa.size());

		for (Status status : result.getTweets()){
			u.ajoutTweet(status.getUser(),status);
			d.ajoutTweet(status.getCreatedAt(),status);
			c.ajoutTweet(status.getText(),status);
			h.ajoutTweet(status.getHashtagEntities(),status);
			m.ajoutTweet(status.getUserMentionEntities(),status);
			l.ajoutTweet(status.getURLEntities(),status);
		}

		/*for (Status status : result.getTweets()){
			o=new DescrTweets(s.getTweet(status));
		}*/

		System.out.println(u.size());
		System.out.println(d.size());
		System.out.println(c.size());
		System.out.println(h.size());
		System.out.println(m.size());
		System.out.println(l.size());



    }

	}

	class Stockage<Type> {//franchement bien réfléchir à comment ranger selon le type avec plusieurs tweets
		public Hashtable<Type,Status> tweets;

		public Stockage(){
			this.tweets=new Hashtable<Type,Status>();
		}

		public void ajoutTweet(Type type,Status t){
				this.tweets.put(type,t);
		}

		/*public Status getTweet(Status t){
			if(this.tweets.containsKey(u)){
				return this.tweets.get(u).get(d);
			}
			else{
					return null;
			}
		}*/

		public int size(){
			return this.tweets.size();
		}


	}
