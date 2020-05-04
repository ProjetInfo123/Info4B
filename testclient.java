import java.io.*;
import java.net.*;
import java.util.*;
//import twitter4j.*;


public class testclient{
   static int port = 6463;
   static String ip="127.0.0.1";
   static boolean arreter=false;


//utiliser DescrTweets dans la méthode ecrire de algorigolo pour l'utiliser dans un client
   public static void main(String[] args) throws Exception {
       if (args.length!=0){
          ip=args[0];
          port=Integer.parseInt(args[1]);
        }
       Socket socket = new Socket(ip,port);

       System.out.println("SOCKET = " + socket);


       BufferedReader sisr = new BufferedReader(
                              new InputStreamReader(socket.getInputStream()));

       PrintWriter sisw = new PrintWriter(new BufferedWriter(
                               new OutputStreamWriter(socket.getOutputStream())),true);

       algorigolo a = new algorigolo();

       EssaiClient saisie=new EssaiClient(sisw,a.testTweet2("suicune"),"suicune.txt");
       saisie.start();

       String str;
       while(arreter!=true) {
          str = sisr.readLine();
          System.out.println(str);
       }

       System.out.println("END");
       //sisw.println("END") ;
       sisr.close();
       sisw.close();
       socket.close();
  }


/*ArrayList<Status> testTweet2(String term){

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
}*/

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



 class EssaiClient extends Thread{
  private PrintWriter pw;
  private ArraList<Status> st;
  private String file;

  public EssaiClient(PrintWriter pw,ArrayList<Status> st,String file){
      this.st=st;
      this.file=file;
      this.pw=pw;
  }

  public void run(){
    this.ecrire(this.st,this.file);
    for(int i=0;i<this.st.size();i++){
      pw.println(st.get(i).getText());
    }
      }
  }

}
