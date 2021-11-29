import java.net.*;  
import java.io.*;
import java.util.Date; 
public class server{  
    public static void main(String args[])throws Exception{  
	    ServerSocket ss=new ServerSocket(3333);  
	    Socket s=ss.accept(); 
	    System.out.println("vijay joined the conversation"); 
	    DataInputStream din=new DataInputStream(s.getInputStream());  
	    DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
	    BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
	    Date d=new Date();
	    String date="Current Date and time on sever is: " +d;
	    dout.writeUTF(date); 
	   dout.flush();
	    String str="",str2="";  
	    while(!str.equals("stop") & !str2.equals("stop")){
		    str2=br.readLine();  
		    dout.writeUTF(str2);
		    str=din.readUTF(); 
		    System.out.println("vijay says: "+str);  
  
		    dout.flush();  
	    }  
	    System.out.println("Connection closed");
	    din.close();  
	    s.close();  
	    ss.close();  
    }}  
