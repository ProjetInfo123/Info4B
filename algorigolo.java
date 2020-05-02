import java.util.*;
import twitter4j.*;
import java.io.*;

public class algorigolo{
    ArrayList<Status> st = new ArrayList<Status>();
    public void algoHash(String debut,String fin,String h) throws TwitterException{
      TwitterFactory tf = new TwitterFactory();   //utiliser TwitterStream, TwitterListener et FilterQuery
      Twitter tweet = tf.getInstance();
      Query q = new Query(h);
      int nbt=1000;
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


int testTweet3(String term,String debut,String fin){

  Twitter twitter = new TwitterFactory().getInstance();
  Query query = new Query(term);
  query.setSince(debut);
  query.setUntil(fin);
  int numberOfTweets = 30;
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
  return tweets.size();
  }



  int testTweet4(User u,String term,String debut,String fin){

  Twitter twitter = new TwitterFactory().getInstance();
  Query query = new Query(term);
  query.setSince(debut);
  query.setUntil(fin);
  int numberOfTweets = 500;
  long lastID = u.getId();
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
  }catch (TwitterException te) {
    System.out.println("Couldn't connect: " + te);
  };
}
  int nbr=0;
  for(int i=0;i<tweets.size();i++){
    if(u.getScreenName().equals(tweets.get(i).getUser().getScreenName()) && tweets.get(i).isRetweet()){
      nbr++;
    }
  }
  return nbr;

}




int retournechar(String s, char a){
  for(int i=0;i<s.length();i++){
    if(s.charAt(i)==a){
      return i;
    }
  }
  return -1;
}




public void classerCH(ArrayList<Status> tweets){   //classer les hashtag dans une arraylist et ajouter un compteurtw
      Hashtable<String,ArrayList<Status>> htt =new Hashtable<String,ArrayList<Status>>(); //htt ??
      ArrayList<String> fin = new ArrayList<String>();
      int comptefin[];
      ArrayList<String> hash=new ArrayList<String>();
      HashtagEntity ht[];


      for(int l=0;l<tweets.size();l++){
        ht=tweets.get(l).getHashtagEntities();

        int v=0;
        //y a des doublons.
        for(int i=0;i<ht.length;i++){
          for(int j=i+1;j<ht.length;j++){
            String h1="#"+ht[i].getText();
            String h2="#"+ht[j].getText();
            v=0;
            if(!(h1.equalsIgnoreCase(h2))){
              for(int k=0;k<fin.size();k++){
                String h3="";
                String h4="";
                for(int m=0;m<retournechar(fin.get(k),'/');m++){
                  h3+=fin.get(k).charAt(m);
                }
                for(int n=retournechar(fin.get(k),'/')+1;n<fin.get(k).length();n++){
                  h4+=fin.get(k).charAt(n);
                }
                if((h3.equalsIgnoreCase(h1) && h4.equalsIgnoreCase(h2)) || (h3.equalsIgnoreCase(h2) && h4.equalsIgnoreCase(h1))){
                  v=1;
                }
              }
              if(v==0){
                fin.add(h1+"/"+h2);
              }
            }
          }
        }
      }

    comptefin=new int[fin.size()];
    for(int k=0;k<comptefin.length;k++){
      comptefin[k]=0;
    }

    for(int i=0;i<tweets.size();i++){
      ht=tweets.get(i).getHashtagEntities();
      hash.clear();
      for(int j=0;j<ht.length;j++){
        for(int k=j+1;k<ht.length;k++){
            hash.add("#"+ht[j].getText()+"/#"+ht[k].getText());
            hash.add("#"+ht[k].getText()+"/#"+ht[j].getText());
        }
      }

        for(int l=0;l<hash.size();l++){
          for(int m=0;m<fin.size();m++){
            if(hash.get(l).equalsIgnoreCase(fin.get(m))){
              //System.out.println("bah la taille c'est "+fin.size());
              int c=comptefin[m];
              comptefin[m]=c+1;
            }
          }
        }

    }

    for(int i=0;i<fin.size();i++){
      System.out.println("Le couple "+fin.get(i)+" est apparu "+comptefin[i]+" fois.");
    }

    System.out.println("Il y a "+fin.size()+" couples.");

  }

 public void evolution(User u,String hashtag){
   algorigolo a=new algorigolo();
   if(u==null && hashtag!=null){
     int evol[]=new int[2];
     evol[0]=this.testTweet3(hashtag,"2020-04-24","2020-04-25");
     evol[1]=this.testTweet3(hashtag,"2020-04-25","2020-04-26");
     if(evol[0]<evol[1]){
        System.out.println("augmentation");
      }
      else{
        if(evol[0]>evol[1]){
          System.out.println("diminution");
        }
        else{
          System.out.println("pas de diff");
        }
      }
   }else{
     if(u!=null && hashtag==null)
     {
       int evol[]=new int[2];
       evol[0]=this.testTweet4(u,"macron","2020-04-24","2020-04-25");
       evol[1]=this.testTweet4(u,"macron","2020-04-25","2020-04-26");
       if(evol[0]<evol[1]){
          System.out.println("augmentation");
        }
        else{
          if(evol[0]>evol[1]){
            System.out.println("diminution");
            }
          else{
            System.out.println("pas de diff");
            }
          }
        }
     else{
     System.out.println("erreur");
   }

 }


 }








    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
      algorigolo a=new algorigolo();
      //a.algoHash("2020-03-21","2020-03-22","mort");
      ArrayList<Status> t=a.testTweet2("macron");
      //System.out.println(t.get(0).getRetweetCount());
      //a.classerCH(a.testTweet2("Deconfinement"));
      //User u=new User();
      a.evolution(t.get(0).getUser(),null);




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


}
