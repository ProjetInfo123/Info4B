import java.util.*;
import twitter4j.*;
import java.io.*;



public class DescrTweets {
	/*private User utilisateur;
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
	}*/

	private Stockage utilisateurs,date,message,hashtags,mentions,url;
	private String terme="";

	public DescrTweets(String t){
		this.terme=t;
		this.utilisateurs=new Stockage();
		this.date=new Stockage();
		this.message=new Stockage();
		this.hashtags=new Stockage();
		this.mentions=new Stockage();
		this.url=new Stockage();
	}

	public void remplirHashtables(Status status){
		this.utilisateurs.ajoutTweet("@"+status.getUser().getScreenName(),status);
		this.date.ajoutTweet(status.getCreatedAt().toString(),status);
		this.message.ajoutTweet(status.getText(),status);
		HashtagEntity ht[]=status.getHashtagEntities();
		UserMentionEntity ume[]=status.getUserMentionEntities();
		URLEntity ue[]=status.getURLEntities();
		for(int i=0;i<ht.length;i++){
			this.hashtags.ajoutTweet("#"+ht[i].getText(),status);
		}
		for(int i=0;i<ume.length;i++){
			this.mentions.ajoutTweet("@"+ume[i].getScreenName(),status);
		}
		for(int i=0;i<ue.length;i++){
			this.url.ajoutTweet(ue[i].getURL(),status);
		}

	}

	public void afficher(){
		System.out.println(this.utilisateurs.toString());
		System.out.println(this.date.toString());
		System.out.println(this.message.toString());
		System.out.println(this.hashtags.toString());
		System.out.println(this.mentions.toString());
		System.out.println(this.url.toString());
	}

	public void serial(){
		this.utilisateurs.write(terme+"_user.ser");
		this.date.write(terme+"_date.ser");
		this.message.write(terme+"_text.ser");
		this.hashtags.write(terme+"_hashtag.ser");
		this.mentions.write(terme+"_mention.ser");
		this.url.write(terme+"_url.ser");
	}








    public static void main(String[] args) throws TwitterException{
	 // The factory instance is re-useable and thread safe.
	  LinkedList<Status> fa=new LinkedList<>();
    Twitter twitter = TwitterFactory.getSingleton();
    Query query = new Query("je binome");
		query.setCount(100);
    QueryResult result = twitter.search(query);
		DescrTweets o=new DescrTweets();

		for (Status status : result.getTweets()) {
				//o=new DescrTweets(status);
				fa.addLast(status);
		}

		System.out.println("La taille est de "+fa.size());


		/*for (Status status : result.getTweets()){
			o=new DescrTweets(s.getTweet(status));
		}*/


		Indexation i=new Indexation(fa,o);
		i.start();
		while(!i.isArret()){
			System.out.println("C'est en cours.");
		}
		o.afficher();



    }

	}

	class Stockage {//franchement bien réfléchir à comment ranger selon le type avec plusieurs tweets
		public Hashtable<String,ArrayList<Status>> tweets;

		public Stockage(){
			this.tweets=new Hashtable<String,ArrayList<Status>>();
		}

		public Stockage(String file) {
	    try {
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				this.tweets = (Hashtable<String,ArrayList<Status>>)in.readObject();
			}
			catch (Exception e) {
				System.out.println(e);
			}
	  }

		public void write(String file) {
	    try {
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(this.tweets);
			}
			catch (Exception e) {
				System.out.println(e);
			}
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

		public Status getTweet(String s,Status t){
			if(this.tweets.containsKey(s)){
				ArrayList<Status> a=this.tweets.get(s);
				for(int i=0;i<a.size();i++){
					if(a.get(i).equals(t)){
						return a.get(i);
					}
				}
				return null;
				}
				else{
					return null;
				}
		}

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

 class Indexation extends Thread {//ajouter des hashtables
		private LinkedList<Status> fa;
		private DescrTweets d;

		public Indexation(LinkedList<Status> fa,DescrTweets d){
			this.fa=fa;
			this.d=d;
		}

		public void run(){
				while(fa.size()>0){
					Status tweet=fa.removeFirst();
					this.d.remplirHashtables(tweet);
				}
		}
	}
