import java.util.List;
import twitter4j.*;
import java.util.Date;

public class algorigolo {

    public void algoHash(String debut,String fin,String h) throws TwitterException{
      Twitter tweet = TwitterFactory.getSingleton();
      Query q = new Query("e");
      q.setSince(debut);
      q.setUntil(fin);
<<<<<<< HEAD
      HashtagEntity ht[]=tweet.getHashtagEntities();
      QueryResult qr = t.search(q);
      int x=0;
      for(Status status = qr.getTweets()){
          for(int i=0;i<ht.length;i++){
                if(ht[i].getText().equals(h)){
                  x++;
                  System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
=======
      QueryResult qr = tweet.search(q);
      for(Status status : qr.getTweets()){
        HashtagEntity ht[]=status.getHashtagEntities();
          for(int i=0;i<ht.length;i++){
                if(ht[i].getText().equals(h)){
                  System.out.println("Ce tweet a été écrit par : @"+status.getUser().getScreenName());
>>>>>>> bf9b275e5f410535191aa25643a2eb2dd3a363f4
                  System.out.println("Ce tweet date du "+status.getCreatedAt());
                }
          }
          System.out.println("Il y a "+x+" tweets");
      }
    }








    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
    }
}
