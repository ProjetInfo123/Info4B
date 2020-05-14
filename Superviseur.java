import java.util.*;
import java.io.*;
import java.net.*;
import twitter4j.*;



public class Superviseur {
    private static int port = 8080;
    private static int maxNoeuds = 20;
    private static int numNoeud = 0;
    private static Stockage user =new Stockage();
    private static Stockage date =new Stockage();
    private static Stockage text =new Stockage();
    private static Stockage hashtag =new Stockage();
    private static Stockage mention =new Stockage();
    private static Stockage url =new Stockage();
    private static Recup tweets=new Recup();

    public static void main(String[] args) throws Exception {
      if (args.length != 0) {
        port = Integer.parseInt(args[0]);
      }
      ServerSocket s = new ServerSocket(port);
      System.out.println("Le serveur attend une connexion " + s);
      while (numNoeud < maxNoeuds){
        Socket soc = s.accept();
        ConnexionNoeud cn = new ConnexionNoeud(soc,user,date,text,hashtag,mention,url,tweets);
        System.out.println("Nouvelle connexion =" + soc);
        numNoeud++;
        cn.start();
        if(numNoeud==maxNoeuds){
          System.out.println("Tri en cours...");
          while(!cn.isArret()){}
        }
      }
      Scanner sc=new Scanner(System.in);
      int choix=1;
      System.out.println(maxNoeuds*5000+" tweets ont ete recuperes.");
      while(choix>=1 && choix<=8){
        System.out.println("Taper 1 si vous voulez voir les hashtags les plus populaires.");
        System.out.println("Taper 2 si vous voulez voir les utilisateurs les plus actifs.");
        System.out.println("Taper 3 si vous voulez voir les tweets les plus retweetes.");
        System.out.println("Taper 4 si vous voulez voir les utilisateurs les plus mentionnes.");
        System.out.println("Taper 5 si vous voulez voir les les liens les plus utilises.");
        System.out.println("Taper 6 si vous voulez voir les dates ou il y a eu le plus de tweets.");
        System.out.println("Taper 7 si vous voulez voir les couples d'hashtags les plus populaires.");
        System.out.println("Taper 8 si vous voulez voir l'evolution du nombre de tweets selon un hashtag entre deux periodes.");
        System.out.println("Taper autre chose pour arreter l'analyse.");
        choix=sc.nextInt();
        switch (choix) {
          case 1 : {System.out.println("Combien d'elements souhaitez-vous voir ? Tapez -1 pour tous les voir sinon tapez le nombre souhaite.");
                    int k=sc.nextInt();
                    System.out.println(hashtag.toString(k));
                    break;
                    }
          case 2: {System.out.println("Combien d'elements souhaitez-vous voir ? Tapez -1 pour tous les voir sinon tapez le nombre souhaite.");
                    int k=sc.nextInt();
                    System.out.println(user.toString(k));
                    break;
                    }
          case 3 :{System.out.println("Combien d'elements souhaitez-vous voir ? Tapez -1 pour tous les voir sinon tapez le nombre souhaite.");
                    int k=sc.nextInt();
                    System.out.println(text.toString(k));
                    break;
                    }
          case 4:{System.out.println("Combien d'elements souhaitez-vous voir ? Tapez -1 pour tous les voir sinon tapez le nombre souhaite.");
                    int k=sc.nextInt();
                    System.out.println(mention.toString(k));
                    break;
                    }

          case 5:{System.out.println("Combien d'elements souhaitez-vous voir ? Tapez -1 pour tous les voir sinon tapez le nombre souhaite.");
                  int k=sc.nextInt();
                  System.out.println(url.toString(k));
                  break;
                  }
          case 6:{System.out.println("Combien d'elements souhaitez-vous voir ? Tapez -1 pour tous les voir sinon tapez le nombre souhaite.");
                  int k=sc.nextInt();
                  System.out.println(date.toString(k));
                  break;
                }
          case 7: {tweets.classerCH(tweets.getListe());
                    break;}
          case 8: {System.out.println("Donnez le hashtag à rechercher");
                  String terme=sc.next();
                  System.out.println("Donner la première date de la premiere periode : (de forme YYYY-MM-DD)");
                  String d1=sc.next();
                  System.out.println("Donner la deuxième date de la premiere periode  : (de forme YYYY-MM-DD)");
                  String d2=sc.next();
                  System.out.println("Donner la première date de la deuxieme periode : (de forme YYYY-MM-DD)");
                  String f1=sc.next();
                  System.out.println("Donner la deuxième date de la deuxieme periode  : (de forme YYYY-MM-DD)");
                  String f2=sc.next();
                  tweets.evolution(terme,d1,d2,f1,f2);
                  break;
                  }
      }
    }
    System.out.println("Fin de la recherche.");
  }
}

  class ConnexionNoeud extends Thread {
    private String terme;
    private Socket s;
    private BufferedReader sisr;
    private PrintWriter sisw;
    private Stockage u,d,t,h,m,l;
    private Recup tw;
    private boolean arret=false;

    public ConnexionNoeud(Socket s, Stockage u,Stockage d,Stockage t,Stockage h,Stockage m,Stockage l,Recup tw) {
      this.s=s;
      this.u=u;
      this.d=d;
      this.t=t;
      this.h=h;
      this.m=m;
      this.l=l;
      this.tw=tw;
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


      Stockage st1=new Stockage(terme+"_user.ser");
      Stockage st2=new Stockage(terme+"_date.ser");
      Stockage st3=new Stockage(terme+"_text.ser");
      Stockage st4=new Stockage(terme+"_hashtag.ser");
      Stockage st5=new Stockage(terme+"_mention.ser");
      Stockage st6=new Stockage(terme+"_url.ser");
      Recup r=new Recup(terme+".ser");

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

      for(int i=0;i<r.getListe().size();i++){
        this.tw.ajout(r.getListe().get(i));
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

class Stockage {
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

    public String toString(int k){
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

      s+="Cette HashTable contient "+tri.size()+" elements.";
      s+="\n";

      if(k==-1 || k>tri.size()){
        for(int i=0;i<tri.size();i++){
          ArrayList<Status> a=this.tweets.get(tri.get(i));
          s+="Top "+(i+1)+" : "+tri.get(i)+" contient "+a.size()+" elements.";
          s+="\n";
        }
      }
      else{
        for(int i=0;i<k;i++){
          ArrayList<Status> a=this.tweets.get(tri.get(i));
          s+="Top "+(i+1)+" : "+tri.get(i)+" contient "+a.size()+" elements.";
          s+="\n";
        }
      }

      return s;
    }

}


class Recup{
  private LinkedList<Status> st;

  public Recup(){
    this.st=new LinkedList<>();
  }

  public Recup(String file){
    try {
      FileInputStream fileIn = new FileInputStream(file);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      this.st = (LinkedList<Status>)in.readObject();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public LinkedList<Status> getListe(){
    return this.st;
  }

  public void ajout(Status s){
    this.st.add(s);
  }

  public void ecrire(String file){
      FileOutputStream fo = null;
        try{
          fo = new FileOutputStream(file);
          ObjectOutputStream out = new ObjectOutputStream(fo);
          out.writeObject(this.st);
        }
        catch(Exception e)
        {
          System.out.println(e);
         }
  }

       public void recupTweet(String term){
         Twitter twitter = new TwitterFactory().getInstance();
         Query query = new Query(term);
         int numberOfTweets =5000;
         long lastID = Long.MAX_VALUE;
         while (this.st.size () < numberOfTweets) {
           if (numberOfTweets - this.st.size() > 100)
             query.setCount(100);
           else
             query.setCount(numberOfTweets - this.st.size());
           try {
             QueryResult result = twitter.search(query);
             this.st.addAll(result.getTweets());
             for (Status t: this.st)
               if(t.getId() < lastID) lastID = t.getId();
           }
           catch (TwitterException te) {
             System.out.println("Couldn't connect: " + te);
           };
           query.setMaxId(lastID-1);
         }
       }


 int RTDate(String term,String debut,String fin){

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
     for (Status t: tweets)
       if(t.getId() < lastID) lastID = t.getId();

   }

   catch (TwitterException te) {
     System.out.println("Couldn't connect: " + te);
   };
   query.setMaxId(lastID-1);
   }

   ArrayList<Status>hash=new ArrayList<Status>();
   for(Status t: tweets){
     HashtagEntity ht[]=t.getHashtagEntities();
     for(int i=0;i<ht.length;i++){
 			if(ht[i].getText().equals(term)){
        hash.add(t);
      }
 		}
   }

   return hash.size();
  }




 int retournechar(String s, char a){
   for(int i=0;i<s.length();i++){
     if(s.charAt(i)==a){
       return i;
     }
   }
   return -1;
 }




 public void classerCH(LinkedList<Status> tweets){
       ArrayList<String> fin = new ArrayList<String>();
       int comptefin[];
       ArrayList<String> hash=new ArrayList<String>();
       HashtagEntity ht[];


       for(int l=0;l<tweets.size();l++){
         ht=tweets.get(l).getHashtagEntities();

         int v=0;
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

  public void evolution(String hashtag,String d1,String d2,String f1,String f2){
      int evol[]=new int[2];
      evol[0]=this.RTDate(hashtag,d1,d2);
      evol[1]=this.RTDate(hashtag,f1,f2);
      if(evol[0]<evol[1]){
         System.out.println("augmentation");
       }
       else{
         if(evol[0]>evol[1]){
           System.out.println("diminution");
         }
         else{
           System.out.println("pas de changement");
         }
       }
  }

}
