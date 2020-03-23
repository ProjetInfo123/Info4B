import java.util.List;
import twitter4j.*;


public class descrtw {
	public void description(Status tweet){
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
	}


	public class Indexation extends Thread {//ajouter des hashtables
		public ArrayList<Status> fa;

		public Indexation(ArrayList<Status> fa){
								this.fa=fa;
		}

		public void run(){
				while(fa.size()>0){
					



				}



		}

	}




    public static void main(String[] args) throws TwitterException{
	 // The factory instance is re-useable and thread safe.
	  ArrayList<Status> fa=new ArrayList();
    Twitter twitter = TwitterFactory.getSingleton();
    Query query = new Query("je souhaite a changer de binome");
    QueryResult result = twitter.search(query);
		descrtw d=new descrtw();
    for (Status status : result.getTweets()) {
        d.description(status);
    }
    }
}
