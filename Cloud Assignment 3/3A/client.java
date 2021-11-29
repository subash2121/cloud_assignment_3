import java.net.*;  
import java.io.*;  
import java.util.Date;
public class client{  
    public static void main(String args[])throws Exception{  
	    Socket s=new Socket("127.0.0.1",8000); 
	    System.out.println("You successfully created a connection with"+"  ubuntu Virtual machine"); 
	    DataInputStream din=new DataInputStream(s.getInputStream());  
	    DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
	    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
 
	    String str="",str2="";  
	    while(!str.equals("stop") & !str2.equals("stop")){  
		    str=br.readLine();  
		    dout.writeUTF(str);  
		    dout.flush(); 
		    str2=din.readUTF();  
		    System.out.println("Server says: "+str2); 
	    }  
	    System.out.println("Connection closed");  
	    dout.close();  
	    s.close();  
    }} 
