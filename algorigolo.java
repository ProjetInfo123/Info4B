import java.util.*;
import twitter4j.*;
/*import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;*/
import java.io.*;

public class algorigolo{
    List<Status> st = new ArrayList<Status>();
    public void algoHash(String debut,String fin,String h) throws TwitterException{
      TwitterFactory tf = new TwitterFactory();   //utiliser TwitterStream, TwitterListener et FilterQuery
      Twitter tweet = tf.getInstance();
      Query q = new Query(h);
      int nbt=201;
      long lastID = Long.MAX_VALUE;
      q.setSince(debut);
      q.setUntil(fin);
      q.setCount(100);
      QueryResult qr = tweet.search(q);
      while (st.size () < nbt) {
          if (nbt - st.size() > 100)
            q.setCount(100);
          else
            q.setCount(nbt - st.size());
        try {
            QueryResult result = tweet.search(q);
              st.addAll(result.getTweets());
                System.out.println("Gathered " + st.size() + " tweets");
                for (Status t: st)
                  if(t.getId() < lastID) lastID = t.getId();

            }catch (TwitterException te) {
      System.out.println("Couldn't connect: " + te);
    };
    q.setMaxId(lastID-1);
  }
    for (int i = 0; i < st.size(); i++) {
      Status status = (Status)st.get(i);

                  System.out.println(i + " USER: " + status.getUser() + " wrote: " + status.getText());


                }
    }
  public void stockage(ArrayList<Status> st,String file){

    FileInputStream fs = null;  //ouverture file
      try {
          fs = new FileInputStream(file);
      } catch(FileNotFoundException e) {
          System.err.println("Fichier '" + file+ "' introuvable");
          System.exit(-1);
      }
      FileOutputStream fo = null;
        try{                                                    //écriture de st dans le file
          fo = new FileOutputStream(file);
          ObjectOutputStream out = new ObjectOutputStream(fo);
          out.writeObject(st);
        }
        catch(Exception e)
        {
          System.out.println(e);
         }


      }

      public void testTweet(String term){
        {
          TwitterFactory tf = new TwitterFactory();   //utiliser TwitterStream, TwitterListener et FilterQuery
          Twitter tweet = tf.getInstance();
          int wantedTweets = 214;
          long firstQueryID =0;
          long lastSearchID = Long.MAX_VALUE;
          int remainingTweets = wantedTweets;
          Query query = new Query(term);
          try
            {

                while(remainingTweets > 0)
                {
                    remainingTweets = wantedTweets - st.size();
                    if(remainingTweets > 100)
                    {
                      query.count(100);
                    }
                    else
                    {
                      query.count(remainingTweets);
                    }
            QueryResult result = tweet.search(query);
            st.addAll(result.getTweets());
            Status ds = st.get(st.size()-1);
            for (int i = 0; i < st.size(); i++) {
               ds = st.get(st.size()-1);

                          System.out.println(i + " User: " + ds.getUser().getScreenName());
            }
            firstQueryID = ds.getId();
            query.setMaxId(firstQueryID-1);
            remainingTweets = wantedTweets - st.size();
          }
          System.out.println("tweets.size() "+st.size() );
        }
        catch(TwitterException te)
        {
          System.out.println("Failed to search tweets: " + te.getMessage());
          System.exit(-1);
        }
}

}












    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
      algorigolo a=new algorigolo();
      //a.algoHash("2020-03-21","2020-03-22","mort");
      a.testTweet("mort");




}
}






/*
public class CreerJSON {

public static void main(String[] args) {

  if(args.length != 1) {
            System.err.println("Erreur : vous devez spécifier le nom du fichier JSON.");
            System.err.println();
            System.err.println("Usage : java LecteurJSON fichier.json");
            System.err.println("\toù 'fichier.json' est le nom du fichier à ouvrir");
            System.exit(-1);
        }



  JSONObject jo = new JSONObject();
  try{
    jo.put("tweets",new JSONArray(st));   //on met une liste mais faut surement mettre un tableau, a revoir
  } catch(JSONException e){
        System.err.println("Erreur lors de l'insertion du tableau.");
        System.err.println(e);
        System.exit(-1);
  }

  FileWriter fs = null;
    try {
        fs = new FileWriter(args[0]);
    } catch(IOException e) {
        System.err.println("Erreur lors de l'ouverture du fichier '" + args[0] + "'.");
        System.err.println(e);
        System.exit(-1);
    }

    try {
        objet.write(fs, 3, 0);
        fs.flush();
    } catch(IOException e) {
        System.err.println("Erreur lors de l'écriture dans le fichier.");
        System.err.println(e);
        System.exit(-1);
    }

    try {
        fs.close();
    } catch(IOException e) {
        System.err.println("Erreur lors de la fermeture du fichier.");
        System.err.println(e);
        System.exit(-1);
    }

}
}*/
