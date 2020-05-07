/*pour compiler :
    javac -cp twitter4j-core-4.0.7.jar *.java
    java -cp twitter4j-core-4.0.7.jar Assaghir_Eguienta.java
*/



import java.util.List;
import twitter4j.*;


public class projet {
    public static void main(String[] args) throws TwitterException{
	 // The factory instance is re-useable and thread safe.
    Twitter twitter = TwitterFactory.getSingleton();
    Query query = new Query("programmation java");
	query.setSince("2020-03-13");
    QueryResult result = twitter.search(query);
    for (Status status : result.getTweets()) {
        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
		System.out.println("Ce tweet date du "+status.getCreatedAt());
    }
    }
}
