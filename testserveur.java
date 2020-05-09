import java.util.*;
import java.io.*;
import java.net.*;
import twitter4j.*;

/**
* Contributeurs : Eric Leclercq, Annabelle Gillet
*/
public class testserveur {
    private static int port = 8080;
    private static int maxClients = 3;
    private static int numClient = 0;
    private static Rangement user =new Rangement();
    private static Rangement date =new Rangement();
    private static Rangement text =new Rangement();
    private static Rangement hashtag =new Rangement();
    private static Rangement mention =new Rangement();
    private static Rangement url =new Rangement();

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
        ConnexionClient cc = new ConnexionClient(soc,user,date,text,hashtag,mention,url);
        System.out.println("NOUVELLE CONNEXION - SOCKET => " + soc);
        numClient++;
        cc.start();
    }
    while(hashtag.size()==0){
      System.out.println(hashtag.size());
    }
    if(numClient==3){
    System.out.println(hashtag.size());
    System.out.println(hashtag.toString());}
  }

  }

  class ConnexionClient extends Thread {
    private String terme;
    private Socket s;
    private BufferedReader sisr;
    private PrintWriter sisw;
    private Rangement u,d,t,h,m,l;

    public ConnexionClient(Socket s, Rangement u,Rangement d,Rangement t,Rangement h,Rangement m,Rangement l) {
      this.s=s;
      this.u=u;
      this.d=d;
      this.t=t;
      this.h=h;
      this.m=m;
      this.l=l;

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


      Rangement st1=new Rangement(terme+"_user.ser");
      Rangement st2=new Rangement(terme+"_date.ser");
      Rangement st3=new Rangement(terme+"_text.ser");
      Rangement st4=new Rangement(terme+"_hashtag.ser");
      Rangement st5=new Rangement(terme+"_mention.ser");
      Rangement st6=new Rangement(terme+"_url.ser");

      Set ec;
      ArrayList<String> c;

      ec=st1.getCles();
      c=new ArrayList(ec);
      for(int i=0;i<c.size();i++){
        ArrayList<Status> a=st1.getListe().get(c.get(i));
        for(int j=0;j<a.size();j++){
          this.u.ajoutTweet(c.get(i),a.get(j));
        }
      }

      ec=st2.getCles();
      c=new ArrayList(ec);
      for(int i=0;i<c.size();i++){
        ArrayList<Status> a=st2.getListe().get(c.get(i));
        for(int j=0;j<a.size();j++){
          this.d.ajoutTweet(c.get(i),a.get(j));
        }
      }

      ec=st3.getCles();
      c=new ArrayList(ec);
      for(int i=0;i<c.size();i++){
        ArrayList<Status> a=st3.getListe().get(c.get(i));
        for(int j=0;j<a.size();j++){
          this.t.ajoutTweet(c.get(i),a.get(j));
        }
      }

      ec=st4.getCles();
      c=new ArrayList(ec);
      for(int i=0;i<c.size();i++){
        ArrayList<Status> a=st4.getListe().get(c.get(i));
        for(int j=0;j<a.size();j++){
          this.h.ajoutTweet(c.get(i),a.get(j));
        }
      }

      ec=st5.getCles();
      c=new ArrayList(ec);
      for(int i=0;i<c.size();i++){
        ArrayList<Status> a=st5.getListe().get(c.get(i));
        for(int j=0;j<a.size();j++){
          this.m.ajoutTweet(c.get(i),a.get(j));
        }
      }

      ec=st6.getCles();
      c=new ArrayList(ec);
      for(int i=0;i<c.size();i++){
        ArrayList<Status> a=st6.getListe().get(c.get(i));
        for(int j=0;j<a.size();j++){
          this.l.ajoutTweet(c.get(i),a.get(j));
        }
      }




      try{
        sisr.close();
        sisw.close();
        s.close();
      }catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

class Rangement {//franchement bien réfléchir à comment ranger selon le type avec plusieurs tweets
		public Hashtable<String,ArrayList<Status>> tweets;

		public Rangement(){
			this.tweets=new Hashtable<String,ArrayList<Status>>();
		}

		public Rangement(String file) {
      try {
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.tweets = (Hashtable<String,ArrayList<Status>>)in.readObject();
      }
      catch (Exception e) {
        System.out.println(e);
      }
    }

    public Hashtable<String,ArrayList<Status>> getListe(){
      return this.tweets;
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
        boolean dedans=false;
        for(int i=0;i<a.size();i++){
          long id=a.get(i).getId();
          if(t.getId()==id){
            dedans=true;
          }
        }
        if(!dedans){
          a.add(t);
          this.tweets.put(s,a);
        }
      }
      else{
        ArrayList<Status> a=new ArrayList<>();
        a.add(t);
        this.tweets.put(s,a);
      }
    }


    public int size(){
      return this.tweets.size();
    }

    /*public Collection<Status> elements(){
      return this.tweets.values();
    }*/

    public Set getCles(){
      return this.tweets.keySet();
    }

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
