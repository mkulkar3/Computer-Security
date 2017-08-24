import java.io.*;
import java.net.*;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;


public class Bank {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

    while(true)
    {
    
		    ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
		    Socket s1 = ss.accept();
		
		
		    BufferedReader in = new BufferedReader(new InputStreamReader(s1.getInputStream()));
		    PrintStream out = new PrintStream(s1.getOutputStream());
		
		    ObjectInputStream inb = new ObjectInputStream(s1.getInputStream());
		
		    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Pup.key"));
		    ObjectInputStream inb1 = new ObjectInputStream(new FileInputStream("Prb.key"));

		
        FileReader fp1 = new FileReader("balance");
        BufferedReader bal = new BufferedReader(fp1);
	    
	      BufferedWriter bw = new BufferedWriter(new FileWriter("newbalance"));
	    
        String qntstat = in.readLine();
	      if(qntstat.equals("error"))
    	  {
	    		ss.close();
	    		continue;
	    	}
	      else
	      {
	    	
	      }
	    
  	    final PrivateKey privateKey = (PrivateKey) inb1.readObject();
	      byte[] in1 = (byte[])inb.readObject();
		    String plainTextid = decrypt(in1, privateKey);
	      String id = plainTextid;
	    
	    
	    
		    byte[] in2 = (byte[])inb.readObject();
		    String plainTextcard = decrypt(in2, privateKey);
        String cardnumb = plainTextcard;
	    
	    
	      final PublicKey publicKey = (PublicKey) inputStream.readObject();
	      byte[] in3 = (byte[])inb.readObject();
	      String plainTextprice = decrypt(in3, publicKey);
	      String price1 = plainTextprice;
	    
	    
	      int price = Integer.parseInt(price1);
	      //System.out.println(price);
	      String line;
	      int balance=0, flag = 0;
	    
	      while((line = bal.readLine()) != null)
		    {
			      String[] parts = line.split(", ");
			      balance = Integer.parseInt(parts[2]);
			
			      if(parts[0].equals(id))
			      {
               if(parts[1].equals(cardnumb))
               {
                //System.out.println(balance);
				        //System.out.println(price);
				        balance += price;
				
				        flag = 1;
				        bw.write(parts[0] + ", "+ parts[1] + ", " + balance);
				        bw.write("\n");
				        out.println("ok");
              }
			      }
			      else
			      {
				        bw.write(line);
				        bw.write("\n");
			      }
			
			
			
		    }
	    
	      //System.out.println(balance);
		
		
	      if(flag == 0)
	      {
	    	    out.println("error");
        }
	      else
	      {
  	        bal.close();
	    	    File file1 = new File("balance");
	    	    File file2 = new File("newbalance");
	    	  
            if(file2.renameTo(file1))
	    	    {
	    		    
	    	    }
	    	    else
	    	    {
	    		    System.out.println("rename failed");
	    	    }
    			
	      }
	    
	    
		
	    
	    
        inb1.close();
        inb.close();
		    inputStream.close();
	      bal.close();	    
	      bw.close();
	      ss.close();
        s1.close();
	  
    }
	
 }


	private static String decrypt(byte[] in3, PublicKey Key) {
		// TODO Auto-generated method stub
		byte[] dectyptedText = null;
		try {
  			// get an RSA cipher object and print the provider
  			final Cipher cipher = Cipher.getInstance("RSA");

  			// decrypt the text using the private key
  			cipher.init(Cipher.DECRYPT_MODE, Key);
  			dectyptedText = cipher.doFinal(in3);

		    } catch (Exception ex) {
  				ex.printStackTrace();
		      }
	 
	return new String(dectyptedText);
		
	}



	private static String decrypt(byte[] in1, PrivateKey Key) {
		// TODO Auto-generated method stub
		
		byte[] dectyptedText = null;
		try {
  			// get an RSA cipher object and print the provider
  			final Cipher cipher = Cipher.getInstance("RSA");

  			// decrypt the text using the private key
  			cipher.init(Cipher.DECRYPT_MODE, Key);
  			dectyptedText = cipher.doFinal(in1);

		    } catch (Exception ex) {
  				ex.printStackTrace();
		      }
	 
	return new String(dectyptedText);

	}




}
