import java.util.*;
import java.io.*;
import java.net.*;
import twitter4j.*;

public class Superviseur {
    private static int port = 8080;
    private static int maxNoeuds = 2;
    private static int numNoeud = 0;
    private static Rangement user =new Rangement();
    private static Rangement date =new Rangement();
    private static Rangement text =new Rangement();
    private static Rangement hashtag =new Rangement();
    private static Rangement mention =new Rangement();
    private static Rangement url =new Rangement();

    public static void main(String[] args) throws Exception {
      if (args.length != 0) {
        port = Integer.parseInt(args[0]);
      }
      ServerSocket s = new ServerSocket(port);
      System.out.println("Le serveur attend une connexion " + s);
      while (numNoeud < maxNoeuds){
        Socket soc = s.accept();
        ConnexionNoeud cn = new ConnexionNoeud(soc,user,date,text,hashtag,mention,url);
        System.out.println("Nouvelle connexion =" + soc);
        numNoeud++;
        cn.start();
        if(numNoeud==maxNoeuds){
          while(!cn.isArret()){}
        }
      }
      System.out.println(user.toString());
      System.out.println(date.toString());
      System.out.println(text.toString());
      System.out.println(hashtag.toString());
      System.out.println(mention.toString());
      System.out.println(url.toString());
  }
}

  class ConnexionNoeud extends Thread {
    private String terme;
    private Socket s;
    private BufferedReader sisr;
    private PrintWriter sisw;
    private Rangement u,d,t,h,m,l;
    private boolean arret=false;

    public ConnexionNoeud(Socket s, Rangement u,Rangement d,Rangement t,Rangement h,Rangement m,Rangement l) {
      this.s=s;
      this.u=u;
      this.d=d;
      this.t=t;
      this.h=h;
      this.m=m;
      this.l=l;
      try {
        sisr = new BufferedReader(new InputStreamReader(s.getInputStream()));
      } catch(IOException e) {
        e.printStackTrace();
      }
    }

    public boolean isArret(){
      return this.arret;
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
        this.arret=true;
        sisr.close();
        s.close();
      }catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

class Rangement {
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


    public Set getCles(){
      return this.tweets.keySet();
    }

    public int maxElem(ArrayList<String> a){
      int max=0;
      int indice=-1;
      for(int i=0;i<a.size();i++){
        if(max<this.tweets.get(a.get(i)).size()){
          max=this.tweets.get(a.get(i)).size();
          indice=i;
        }
      }
      return indice;
    }

    public String toString(){
      ArrayList<String> tri=new ArrayList<>();

      Set ec=this.tweets.keySet();
      ArrayList<String> c=new ArrayList(ec);

      while(c.size()!=0){
        int indice=maxElem(c);
        String s=c.get(indice);
        c.remove(indice);
        tri.add(s);
      }

      String s="";

      s+="Cette HashTable contient "+tri.size()+" éléments.";
      s+="\n";

      for(int i=0;i<tri.size();i++){
        ArrayList<Status> a=this.tweets.get(tri.get(i));
        s+=tri.get(i)+" contient "+a.size()+" éléments.";
        s+="\n";
      }

      return s;
    }

}
