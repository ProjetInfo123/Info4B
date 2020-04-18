import java.io.*;
import java.net.*;
import java.util.*;
import twitter4j.*;


public class testclient{
   static int port = 8080;
   static String ip="127.0.0.1";
   static boolean arreter=false;



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


       algorigolo a=new algorigolo();
       EssaiClient saisie=new EssaiClient(a,sisw);
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
}





 class EssaiClient extends Thread{
  private algorigolo a;
  private ArrayList<Status> tweets;
  private PrintWriter pw;

  public EssaiClient(algorigolo a,PrintWriter pw){
     this.a=a;
     this.tweets=a.testTweet2("bts");
     this.pw=pw;
  }

  public void run(){

    /*try{
      for(int i=0;i<tweets.size();i++){
        pw.println(tweets.get(i).getText());
      }
    }catch(IOException e){e.printStackTrace();}*/
    testclient.arreter=true;
  }

}
