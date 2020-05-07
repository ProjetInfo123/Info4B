import java.util.*;
import java.io.*;
import java.net.*;
import twitter4j.*;

/**
* Contributeurs : Eric Leclercq, Annabelle Gillet
*/
public class testserveur {
    static int port = 8080;
    static final int maxClients = 1;
    static int numClient = 0;

    // Pour utiliser un autre port pour le serveur, l'exécuter avec la commande : java ServeurMC 8081
    public static void main(String[] args) throws Exception {
      if (args.length != 0) {
        port = Integer.parseInt(args[0]);
      }
      // 1 - Ouverture du ServerSocket par le serveur
      ServerSocket s = new ServerSocket(port);
      System.out.println("SOCKET ECOUTE CREE => " + s);
      while (numClient < maxClients){
        /* 2 - Attente d'une connexion client (la méthode s.accept() est bloquante
        tant qu'un client ne se connecte pas) */
        Socket soc = s.accept();
        /* 3 - Pour gérer plusieurs clients simultanément, le serveur attend que les clients se connectent,
        et dédie un thread à chacun d'entre eux afin de le gérer indépendamment des autres clients */
        ConnexionClient cc = new ConnexionClient(numClient, soc);
        System.out.println("NOUVELLE CONNEXION - SOCKET => " + soc);
        numClient++;
        cc.start();
    }
  }

  }

  class ConnexionClient extends Thread {
    private String terme;
    private boolean arret = false;
    private Socket s;
    private BufferedReader sisr;
    private PrintWriter sisw;
    private static ArrayList<Stockage> as=new ArrayList<>();

    public ConnexionClient(int id, Socket s) {
      this.s = s;

      /* 5a - A partir du Socket connectant le serveur à un client, le serveur ouvre 2 flux :
      1) un flux entrant (BufferedReader) afin de recevoir ce que le client envoie
      2) un flux sortant (PrintWriter) afin d'envoyer des messages au client */
      // BufferedReader permet de lire par ligne
      try {
        sisr = new BufferedReader(new InputStreamReader(s.getInputStream()));
        sisw = new PrintWriter( new BufferedWriter(
                new OutputStreamWriter(s.getOutputStream())),true);
      } catch(IOException e) {
        e.printStackTrace();
      }
    }

    public void run(){
      try {
        terme=sisr.readLine();
      } catch(IOException e) {
        e.printStackTrace();
      }

      System.out.println(terme+"_user.ser");
      Stockage st1=new Stockage(terme+"_user.ser");
      Stockage st2=new Stockage(terme+"_date.ser");
      Stockage st3=new Stockage(terme+"_text.ser");
      Stockage st4=new Stockage(terme+"_hashtag.ser");
      Stockage st5=new Stockage(terme+"_mention.ser");
      Stockage st6=new Stockage(terme+"_url.ser");
      this.as.add(st1);
      this.as.add(st2);
      this.as.add(st3);
      this.as.add(st4);
      this.as.add(st5);
      this.as.add(st6);


      System.out.println("as contient "+as.size()+" éléments.");




      try{
        sisr.close();
        sisw.close();
        s.close();
      }catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

class Stockage {//franchement bien réfléchir à comment ranger selon le type avec plusieurs tweets
		public Hashtable<String,ArrayList<Status>> tweets;

		public Stockage(){
			this.tweets=new Hashtable<String,ArrayList<Status>>();
		}

		public Stockage(String file) {
    try {
      FileInputStream fileIn = new FileInputStream(file);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      this.tweets = (Hashtable<String,ArrayList<Status>>)in.readObject();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public void write(String file) {
    try {
      FileOutputStream fileOut = new FileOutputStream(file);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this.tweets);
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public void ajoutTweet(String s,Status t){
    if(this.tweets.containsKey(s)){
      ArrayList<Status> a=this.tweets.get(s);
      a.add(t);
      this.tweets.put(s,a);
    }
    else{
      ArrayList<Status> a=new ArrayList<>();
      a.add(t);
      this.tweets.put(s,a);
    }
  }

  public Status getTweet(String s,Status t){
    if(this.tweets.containsKey(s)){
      ArrayList<Status> a=this.tweets.get(s);
      for(int i=0;i<a.size();i++){
        if(a.get(i).equals(t)){
          return a.get(i);
        }
      }
      return null;
      }
      else{
        return null;
      }
  }

  /*public int size(){
    return this.tweets.size();
  }

  public Collection<Status> elements(){
    return this.tweets.values();
  }

  public Set cles(){
    return this.tweets.keySet();
  }*/

  public String toString(){
    String s="";

    Set ec=this.tweets.keySet();
    ArrayList<String> c=new ArrayList(ec);

    s+="Cette HashTable contient "+c.size()+" éléments.";
    s+="\n";

    for(int i=0;i<c.size();i++){
      ArrayList<Status> a=this.tweets.get(c.get(i));
      s+=c.get(i)+" contient "+a.size()+" éléments.";
      s+="\n";
    }

    return s;
  }




}
