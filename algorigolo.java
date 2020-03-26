import java.util.*;
import twitter4j.*;

public class algorigolo {

    public void algoHash(String debut,String fin,String h) throws TwitterException{
      TwitterFactory tf = new TwitterFactory();   //utiliser TwitterStream, TwitterListener et FilterQuery
      Twitter tweet = tf.getInstance();
      Query q = new Query("changer binome");
      q.setSince(debut);
      q.setUntil(fin);
      q.setCount(100);
      QueryResult qr = tweet.search(q);
      for(Status status : qr.getTweets()){
        HashtagEntity ht[]=status.getHashtagEntities();
          for(int i=0;i<ht.length;i++){
                  System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
                  System.out.println("Ce tweet date du "+status.getCreatedAt());

          }
      }
    }








    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
      algorigolo a=new algorigolo();
      a.algoHash("2020-03-21","2020-03-22","");
    }
}
