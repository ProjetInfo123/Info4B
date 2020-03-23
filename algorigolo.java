import java.util.List;
import twitter4j.*;
import java.util.Date;

public class algorigolo {

    public void algoHash(String debut,String fin,String h) throws TwitterException{
      Twitter tweet = TwitterFactory.getSingleton();
      Query q = new Query("e");
      q.setSince(debut);
      q.setUntil(fin);
      HashtagEntity ht[]=tweet.getHashtagEntities();
      QueryResult qr = t.search(q);
      int x=0;
      for(Status status : qr.getTweets()){
          for(int i=0;i<ht.length;i++){
                if(ht[i].getText().equals(h)){
                  x++;
                  System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
                  System.out.println("Ce tweet date du "+status.getCreatedAt());
                }
          }
          System.out.println("Il y a "+x+" tweets");
      }
    }








    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
      algorigolo a=new algorigolo();
      a.algoHash("2020-03-21","2020-03-22","DOOMEternal")
    }
}
