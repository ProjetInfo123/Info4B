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
		System.out.println("Ce tweet date du : "+tweet.getCreatedAt().toString());
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
		Stockage u=new Stockage();
		Stockage d=new Stockage();
		Stockage c=new Stockage();
		Stockage h=new Stockage();
		Stockage m=new Stockage();
		Stockage l=new Stockage();

		for (Status status : result.getTweets()) {
				//o=new DescrTweets(status);
				fa.addLast(status);
		}

		System.out.println("La taille est de "+fa.size());

		for (Status status : result.getTweets()){
			u.ajoutTweet("@"+status.getUser().getScreenName(),status);
			d.ajoutTweet(status.getCreatedAt().toString(),status);
			c.ajoutTweet(status.getText(),status);
			HashtagEntity ht[]=status.getHashtagEntities();
			UserMentionEntity ume[]=status.getUserMentionEntities();
			URLEntity ue[]=status.getURLEntities();
			for(int i=0;i<ht.length;i++){
				h.ajoutTweet("#"+ht[i].getText(),status);
			}
			for(int i=0;i<ume.length;i++){
				m.ajoutTweet("@"+ume[i].getScreenName(),status);
			}
			for(int i=0;i<ue.length;i++){
				l.ajoutTweet(ue[i].getURL(),status);
			}
		}

		/*for (Status status : result.getTweets()){
			o=new DescrTweets(s.getTweet(status));
		}*/


		System.out.println(u.toString());
		System.out.println(d.toString());
		System.out.println(c.toString());
		System.out.println(h.toString());
		System.out.println(m.toString());
		System.out.println(l.toString());




    }

	}

	class Stockage {//franchement bien réfléchir à comment ranger selon le type avec plusieurs tweets
		public Hashtable<String,ArrayList<Status>> tweets;

		public Stockage(){
			this.tweets=new Hashtable<String,ArrayList<Status>>();
		}

		public void ajoutTweet(String s,Status t){
			if(this.tweets.containsKey(s)){
				ArrayList<Status> a=this.tweets.get(s);
				a.add(t);
				this.tweets.put(s,a);
			}
			else{
				ArrayList<Status> a=new ArrayList<>();
				a.add(t);
				this.tweets.put(s,a);
			}
		}

		/*public Status getTweet(Status t){
			if(this.tweets.containsKey(u)){
				return this.tweets.get(u).get(d);
			}
			else{
					return null;
			}
		}*/

		/*public int size(){
			return this.tweets.size();
		}

		public Collection<Status> elements(){
			return this.tweets.values();
		}

		public Set cles(){
			return this.tweets.keySet();
		}*/

		public String toString(){
			String s="";

			Set ec=this.tweets.keySet();
			ArrayList<String> c=new ArrayList(ec);

			s+="Cette HashTable contient "+c.size()+" éléments.";
			s+="\n";

			for(int i=0;i<c.size();i++){
				ArrayList<Status> a=this.tweets.get(c.get(i));
				s+=c.get(i)+" contient "+a.size()+" éléments.";
				s+="\n";
			}

			return s;
		}


	}
