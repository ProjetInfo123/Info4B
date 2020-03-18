import java.util.List;
import twitter4j.*;

public class info4bis {
	
	 public static void main(String[] args){
	
	
	Twitter twit = TwitterFactory.getSingleton();
	twitter.setOAuthConsumer("3mvRhoAcdYBOdAqARS3XuPwwy", "xhyGCJYDBUkGVO0YG7VkfeBeKj0WYehSyr7P39zpI2FDicv0Pw");
	twitter.setOAuthAccessToken("954367945626529798-wbMULzoFfntKnVMcEXv7e3B2IFFB3YP")
    twitter.setOAuthAccessTokenSecret("4xFQKsiYio3f05TDS4UURQLnSLKrX1F9l71E94YXyGY0O");
	Query q = new Query("e");
	QueryResult result = twitter.search(query);
	for (Status status : result.getTweets()) {
        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
    }
	
	TwitterListener listener = new TwitterAdapter() {
        @Override public void updatedStatus(Status status) {
          System.out.println("Successfully updated the status to [" +
                   status.getText() + "].");
        }

        @Override public void onException(TwitterException e, int method) {
          if (method == TwitterMethods.UPDATE_STATUS) {
            e.printStackTrace();
          } else {
            throw new AssertionError("Should not happen");
          }
        }
    }
    // The factory instance is re-useable and thread safe.
    AsyncTwitterFactory factory = new AsyncTwitterFactory();
    AsyncTwitter asyncTwitter = factory.getInstance();
    asyncTwitter.addListener(listener);
    asyncTwitter.updateStatus(args[0]);
	
	 }
}