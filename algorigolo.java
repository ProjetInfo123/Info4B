import java.util.*;
import twitter4j.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class algorigolo {
    LinkedList l = new LinkedList<Status>;
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
  public void stockage(LinkedList<Status> st,String file){
    FileInputStream fs = null;  //ouverture file
      try {
          fs = new FileInputStream(file);
      } catch(FileNotFoundException e) {
          System.err.println("Fichier '" + file+ "' introuvable");
          System.exit(-1);
      }
      FileOutputStream fo = null;
        try{                                                    //écriture de st dans le file
          FileOutputStream fo = new FileOutputStream(file);
          ObjectOutputStream out = new ObjectOutputStream(fo);
          out.writeObject(st);
        }
        catch(Exception e)
        {
          System.out.println(e);
         }


      }




    }






    public static void main(String[] args) throws TwitterException{
      Twitter twitter = TwitterFactory.getSingleton();
      algorigolo a=new algorigolo();
      a.algoHash("2020-03-21","2020-03-22","");




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
