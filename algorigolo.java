import java.util.List;
import twitter4j.*;
import java.util.Date;

public class algorigolo {

    public void algoHash(String debut,String fin,String h) throws TwitterException{
      Twitter tweet = TwitterFactory.getSingleton();
      Query q = new Query("e");
      q.setSince(debut);
      q.setUntil(fin);
      QueryResult qr = tweet.search(q);
      for(Status status : qr.getTweets()){
        HashtagEntity ht[]=status.getHashtagEntities();
          for(int i=0;i<ht.length;i++){
                if(ht[i].getText().equals(h)){
                  System.out.println("Ce tweet a été écrit par : @"+status.getUser().getScreenName());
                  System.out.println("Ce tweet date du "+status.getCreatedAt());
                }
          }
      }
    }








    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
    }
}
