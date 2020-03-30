import java.util.*;
import twitter4j.*;


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

public class Stockage<T> {
	private HashTable

	public static void ecrire(){



	}










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
    QueryResult result = twitter.search(query);
		DescrTweets d;
    for (Status status : result.getTweets()) {
        d=new DescrTweets(status);
				fa.addLast(status);
    }
		System.out.println(fa.size());
    }
}
