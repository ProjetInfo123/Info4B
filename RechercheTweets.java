import java.util.*;
import java.io.*;
import java.net.*;
import twitter4j.*;
import java.util.concurrent.Semaphore;


public class RechercheTweets {
  static String ip="127.0.0.1";
  static int port = 8080;

  public static void main(String args[]) throws Exception{
    if (args.length != 0) {
      ip=args[0];
      port = Integer.parseInt(args[1]);
    }
    Scanner s=new Scanner(System.in);
    System.out.println("Quel est le terme à rechercher ?");
    String terme=s.next();
    Socket socket = new Socket(ip,port);

    System.out.println("Nouvelle connexion  = " + socket);


    PrintWriter sisw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

    Recup a = new Recup();
    Description d = new Description(terme);
    Semaphore sem=new Semaphore(1,true);
    a.recupTweet(terme);
    LinkedList<Status> st=a.getListe();
    a.ecrire(terme+".ser");
    Indexation indun =new Indexation(st,d,sem);
    Indexation indeux=new Indexation(st,d,sem);
    Indexation indrois=new Indexation(st,d,sem);
    Indexation indatre=new Indexation(st,d,sem);
    indun.start();
    indeux.start();
    indrois.start();
    indatre.start();
    while(st.size()!=0){System.out.println("Analyse en cours...");}
    d.serial();

    sisw.println(terme);
    sisw.close();
    socket.close();
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

class Description {
	private Stockage utilisateurs,date,message,hashtags,mentions,url;
	private String terme="";

	public Description(String t){
		this.terme=t;
		this.utilisateurs=new Stockage();
		this.date=new Stockage();
		this.message=new Stockage();
		this.hashtags=new Stockage();
		this.mentions=new Stockage();
		this.url=new Stockage();
	}

	public void remplirHashtables(Status status){
		this.utilisateurs.ajoutTweet("@"+status.getUser().getScreenName(),status);
		this.date.ajoutTweet(status.getCreatedAt().toString(),status);
		this.message.ajoutTweet(status.getText(),status);
		HashtagEntity ht[]=status.getHashtagEntities();
		UserMentionEntity ume[]=status.getUserMentionEntities();
		URLEntity ue[]=status.getURLEntities();
		for(int i=0;i<ht.length;i++){
			this.hashtags.ajoutTweet("#"+ht[i].getText(),status);
		}
		for(int i=0;i<ume.length;i++){
			this.mentions.ajoutTweet("@"+ume[i].getScreenName(),status);
		}
		for(int i=0;i<ue.length;i++){
			this.url.ajoutTweet(ue[i].getURL(),status);
		}
	}

	public void serial(){
		this.utilisateurs.write(terme+"_user.ser");
		this.date.write(terme+"_date.ser");
		this.message.write(terme+"_text.ser");
		this.hashtags.write(terme+"_hashtag.ser");
		this.mentions.write(terme+"_mention.ser");
		this.url.write(terme+"_url.ser");
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
      s+="Cette HashTable contient "+tri.size()+" éléments.";
      s+="\n";
      if(k==-1 || k>tri.size()){
        for(int i=0;i<tri.size();i++){
          ArrayList<Status> a=this.tweets.get(tri.get(i));
          s+="Top "+(i+1)+" : "+tri.get(i)+" contient "+a.size()+" éléments.";
          s+="\n";
        }
      }
      else{
        for(int i=0;i<k;i++){
          ArrayList<Status> a=this.tweets.get(tri.get(i));
          s+="Top "+(i+1)+" : "+tri.get(i)+" contient "+a.size()+" éléments.";
          s+="\n";
        }
      }

      return s;
    }




	}

   class Indexation extends Thread {
		private LinkedList<Status> fa;
		private Description d;
    private Semaphore s;

		public Indexation(LinkedList<Status> fa,Description d,Semaphore s){
			this.fa=fa;
			this.d=d;
      this.s=s;
		}

		public void run(){
				while(fa.size()>0){
          try{
            s.acquire();
            if(fa.size()!=0){
              Status tweet=fa.removeFirst();
              this.d.remplirHashtables(tweet);
            }
            s.release();
          }catch(Exception e){System.out.println(e);}
				}
		}
	}
