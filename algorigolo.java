import java.util.*;
import twitter4j.*;
import java.io.*;

public class algorigolo{
    ArrayList<Status> st = new ArrayList<Status>();
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
  public void ecrire(ArrayList<Status> st,String file){

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

  ArrayList<Status> testTweet2(String term){

Twitter twitter = new TwitterFactory().getInstance();
Query query = new Query(term);
int numberOfTweets = 200;
long lastID = Long.MAX_VALUE;
ArrayList<Status> tweets = new ArrayList<Status>();
while (tweets.size () < numberOfTweets) {
  if (numberOfTweets - tweets.size() > 100)
    query.setCount(100);
  else
    query.setCount(numberOfTweets - tweets.size());
  try {
    QueryResult result = twitter.search(query);
    tweets.addAll(result.getTweets());
    System.out.println("Gathered " + tweets.size() + " tweets");
    for (Status t: tweets)
      if(t.getId() < lastID) lastID = t.getId();

  }

  catch (TwitterException te) {
    System.out.println("Couldn't connect: " + te);
  };
  query.setMaxId(lastID-1);
}

for (int i = 0; i < tweets.size(); i++) {
  Status t = (Status) tweets.get(i);
  String user = t.getUser().getScreenName();
  String msg = t.getText();
  String time = "";
    System.out.println(i + " USER: " + user + " wrote: " + msg);
}
return tweets;
}


















    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
      algorigolo a=new algorigolo();
      //a.algoHash("2020-03-21","2020-03-22","mort");
      //a.testTweet2("hardy");
      a.ecrire(a.testTweet2("hardy"),"test.ser");




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


public void swapUser(ArrayList<User> q,int i,int j){
  User u=q.get(i);;User w=q.get(j);
  q.set(i,w); q.set(j,u);
}




public void classerCH(ArrayList<Status> tweets){   //classer les hashtag dans une arraylist et ajouter un compteurtw
      Hashtable<String,ArrayList<Status>> htt =new Hashtable<String,ArrayList<Status>>(); //htt ??
      ArrayList<String> fin = new ArrayList<String>();
      ArrayList<int> comptefin = new ArrayList<int>();
      ArrayList<String> hash=new ArrayList<String>();
      HashtagEntity ht[];

    for(int i=0;i<tweets.size();i++){
      ht=tweets.get(i).getHashtagEntities();
    }

    for(int i=0;i<ht.length;i++){
      for(int j=i+1;j<ht.length;j++){
          fin.add(ht[i].getText()+" "+ht[j].getText());
      }
      for(int i=0;i<fin.size();i++){
        comptefin.add(1);
      }

    }

    for(int i=0;i<tweets.size();i++){
      ht=tweets.get(i).getHashtagEntities();
      for(int j=0;j<ht.length;j++){
        for(int k=j+1;k<ht.length;k++){
            hash.add(ht[j].getText()+" "+ht[k].getText());
        }
      }

        for(int l=0;l<hash.size();l++){
          for(int m=0;m<fin.size();m++){
            if(hash.get(l).equals(fin.get(m)))){
              comptefin.set(m,comptefin.get(m)++);
            }
          }
        }


    }







    }
