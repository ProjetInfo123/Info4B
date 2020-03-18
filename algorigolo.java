import java.util.List;
import twitter4j.*;

public class algorigolo {

    void algoHash(String debut,String fin,String h){
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








    public static void main(String[] args){
      Twitter twitter = TwitterFactory.getSingleton();
      Date d = new Date(System.currentTimeMillis());
      Query q = new Query("e");













    }
}
