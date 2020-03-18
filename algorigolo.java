import java.util.List;
import twitter4j.*;

public class algorigolo {

    void algoHash(String debut,String fin,Twitter t,String h){
      Query q = new Query("e");
      q.setSince(debut);
      q.setUntil(fin);
      HashtagEntity ht[]=t.getHashtagEntities();
      QueryResult qr = t.search(q);
      for(Status status : qr.getTweets()){
          for(int i=0;i<ht.length;i++){
                if(ht[i].getText().equals(h)){
                  System.out.println("Ce tweet a été écrit par : @"+t.getUser().getScreenName());
                  System.out.println("Ce tweet date du "+status.getCreatedAt());
                }
          }
      }
    }








    public static void main(String[] args){
      Twitter twit = TwitterFactory.getSingleton();
    	twitter.setOAuthConsumer("3mvRhoAcdYBOdAqARS3XuPwwy", "xhyGCJYDBUkGVO0YG7VkfeBeKj0WYehSyr7P39zpI2FDicv0Pw");
    	twitter.setOAuthAccessToken("954367945626529798-wbMULzoFfntKnVMcEXv7e3B2IFFB3YP");
      twitter.setOAuthAccessTokenSecret("4xFQKsiYio3f05TDS4UURQLnSLKrX1F9l71E94YXyGY0O");
      Date d = new Date(System.currentTimeMillis());
      Query q = new Query("e");













    }
}
