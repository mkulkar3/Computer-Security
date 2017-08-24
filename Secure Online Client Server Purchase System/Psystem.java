import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.StringTokenizer;
import javax.crypto.Cipher;


public class Psystem {

	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		// TODO Auto-generated method stub
		
		String passFile = "password";
		String itemFile = "item";
		
		while(true)
    {
        
		    ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
		    Socket s1 = ss.accept();		
		    Socket sock = new Socket(args[1], Integer.parseInt(args[2]));
		
		
		    ObjectOutputStream outb = new ObjectOutputStream(sock.getOutputStream());
		    ObjectInputStream inb = new ObjectInputStream(s1.getInputStream());
		
		
	    	BufferedReader inbank = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		    PrintStream outbank = new PrintStream(sock.getOutputStream());

		    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Pub.key"));
        ObjectInputStream inputStream1 = new ObjectInputStream(new FileInputStream("Pua.key"));
	    	ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream("Put.key"));
		    ObjectInputStream inb1 = new ObjectInputStream(new FileInputStream("Prp.key"));

		
		    BufferedReader in = new BufferedReader(new InputStreamReader(s1.getInputStream()));
		    PrintStream out = new PrintStream(s1.getOutputStream());
		
		
		
        FileReader fp1 = new FileReader(itemFile);
        BufferedReader initem = new BufferedReader(fp1);
		
	      int passValidFlag = 0;
		
		    String id = in.readLine();
		    //System.out.println(id);
            
            
        final PublicKey digitSigKey;
		
		    if(id.equals("alice"))
		    {
			    digitSigKey = (PublicKey) inputStream1.readObject();
		    }
		    else
		    {
			    digitSigKey = (PublicKey) inputStream2.readObject();
		    }    
            
            
		

		    do
        {
			
				    String hashPAss = "";
				    hashPAss = in.readLine();
	    			//System.out.println(hashPAss);
		
			    	out.println("Validating Password.....");
		
				    String line;
				    //passValidFlag = 0;
				
				
				    FileReader fp = new FileReader(passFile);
            BufferedReader inpass = new BufferedReader(fp);
				
				    while((line = inpass.readLine()) != null)
				    {
					      if(line.equals(""))
						    continue;
					
					      //System.out.println(line);
					
				      	StringTokenizer defaultTokenizer = new StringTokenizer(line);
					      //System.out.println("Total number of tokens found : " + defaultTokenizer.countTokens());
			
					      while (defaultTokenizer.hasMoreTokens())
					      {
						        //System.out.println(defaultTokenizer.nextToken());
						        String temp = defaultTokenizer.nextToken();
						        if(temp.equals(id))
						        {
                        line = inpass.readLine();
                        defaultTokenizer = new StringTokenizer(line);
                        temp = defaultTokenizer.nextToken();
                        temp = defaultTokenizer.nextToken();
                        if(temp.equals(hashPAss))
                        {
							            //System.out.println("match....");					
							            passValidFlag = 1;
							            break;
                        }
						        }
				
					      }
			
					      if(passValidFlag == 1)
						        break;
			
				    }
		
				    if(passValidFlag == 0)
				    {
					    out.println("error");
				    }
				    else
				    {
					    //System.out.println("Password Authentication successfull");
					    out.println("Password Authentication successfull");
				    }
		
				
				    inpass.close();
		
        }while(passValidFlag == 0);
		
	    	String line1;
		
		    while((line1 = initem.readLine()) != null)
		    {
			    out.println(line1);
		    }
		
		    out.println("finish");
		
		
		
		    final PrivateKey privateKey = (PrivateKey) inb1.readObject();		
		
		
	    	//reading item#
		    byte[] in2 = (byte[])inb.readObject();		
		    String plainTextitemno = decrypt(in2, privateKey);			
		    String ItemNum = plainTextitemno;
		
	    	//reading item quantity
		    byte[] in3 = (byte[])inb.readObject();
		    String plainTextqnt = decrypt(in3, privateKey);
		    String Quant = plainTextqnt;
   
   
       
        byte[] digitSign = (byte[])inb.readObject();	
    		Signature digitalSign = Signature.getInstance("MD5WithRSA");
		    digitalSign.initVerify(digitSigKey);
		    digitalSign.update(in2);
		    digitalSign.update(in3);
		
		    if(digitalSign.verify(digitSign))
	    	{
			    out.println("Signature verified...");
		    }
		    else
		    {
			    out.println("error");
		    }       
       
               
   
		
		    FileReader fp2 = new FileReader(itemFile);
		    BufferedReader init = new BufferedReader(fp2);
		
	    	//String retVal;
		    //String[] parts;
		    int qnt =0 , price=0, itemno=0;
		    String ItemName="";
		
		    while((line1 = init.readLine()) != null)
	    	{
                         
   	        String[] parts = line1.split(", ");
			      //System.out.println(parts[0] + parts[1] + parts[2] + parts[3]);

			
			      if(parts[0].equals(ItemNum))
			      {
				        itemno = Integer.parseInt(parts[0]);
				        ItemName = parts[1];
				        String tmp = parts[2].substring(1);
				        price = Integer.parseInt(tmp);
				        //System.out.println(price);
				        qnt = Integer.parseInt(parts[3]);
				        break;				
             }
         }
		    //System.out.println(itemno + " " + ItemName + " " + price + " " + qnt);
		
		    if(qnt < Integer.parseInt(Quant) || Integer.parseInt(Quant) < 1)
		    {
			      out.println("Please enter valid quantity...");
			      outbank.println("error");
			      init.close();
			      initem.close();
            inb1.close();
			      inputStream.close();
            inputStream1.close();
			      inputStream2.close();                          
			      ss.close();
			      sock.close();
			      continue;
		    }
		    else
        {
            out.println("okay");
		        outbank.println("okay");      
		    }
		
		
		
        byte[] in1 = (byte[])inb.readObject();
	    	byte[] in0 = (byte[])inb.readObject();
		
		
		
    		//calculating total amount
	    	int totalAmount = Integer.parseInt(Quant) * price;
		    //System.out.println(totalAmount);		
		    String tot = Integer.toString(totalAmount);
		
		
		
    		//sending id and card number to bank
    		outb.writeObject(in1);
    		outb.writeObject(in0);
	
		
		
    		//sending total amount to bank
	    	final byte[] cipherTextprice = encrypt(tot, privateKey);
	    	outb.writeObject(cipherTextprice);
	    	//outbank.println(tot);
		
	    	//reading whether card number correct or not
		    String cardStatus = inbank.readLine();

		    //sending the card number validity to client
		    out.println(cardStatus);
		
	    	String line2;
		    init.close();
		    initem.close();
		
		    if(cardStatus.equals("ok"))
		    {
			      BufferedWriter bw = new BufferedWriter(new FileWriter("newitem"));
			      FileReader fp3 = new FileReader(itemFile);
			      BufferedReader init1 = new BufferedReader(fp3);
		    
			
			      while((line2 = init1.readLine()) != null)
			      {
				        String[] parts = line2.split(", ");
				        //System.out.println(parts[0] + parts[1] + parts[2] + parts[3]);

				        //balance = Integer.parseInt(parts[2]);
				
			    	    if(parts[0].equals(ItemNum) )
				        {
					        int updated_qnt = qnt - Integer.parseInt(Quant);
				    	
				        	bw.write(parts[0] + ", "+ parts[1] + ", " + parts[2] + ", " + updated_qnt);
					        bw.write("\n");
					        //out.println("ok");
				        }
				        else
				        {
                  bw.write(line2);
                  bw.write("\n");
				        }
			      }
				
				    bw.close();
				    init1.close();
   	        File file1 = new File("item");
   	        File file2 = new File("newitem");
   	        if(file2.renameTo(file1))
   	        {
		     	    //System.out.println("rename success");
   	        }
   	        else
   	        {
  		        System.out.println("rename failed");
 	          }
				
				
				}
		
		
		
		    inb1.close();
		    inputStream.close();
        inputStream1.close();
			  inputStream2.close();    
		    ss.close();
        sock.close();
    }
		
		
		
		
	}

	private static byte[] encrypt(String tot, PrivateKey Key) {
		// TODO Auto-generated method stub

		byte[] cipherText = null;
		try {
  			// get an RSA cipher object and print the provider
  			final Cipher cipher = Cipher.getInstance("RSA");
  			// encrypt the plain text using the public key
  			cipher.init(Cipher.ENCRYPT_MODE, Key);
  			cipherText = cipher.doFinal(tot.getBytes());
		} catch (Exception e) {
  			e.printStackTrace();
		  }

	return (cipherText);

		
	}




	private static String decrypt(byte[] ciphertext, PrivateKey Key) 
  {
		// TODO Auto-generated method stub
		
		byte[] dectyptedText = null;
		try {
  			// get an RSA cipher object and print the provider
  			final Cipher cipher = Cipher.getInstance("RSA");

  			// decrypt the text using the private key
  			cipher.init(Cipher.DECRYPT_MODE, Key);
  			dectyptedText = cipher.doFinal(ciphertext);

		    } catch (Exception ex) {
  				ex.printStackTrace();
		      }
	 
	return new String(dectyptedText);
		
	}



}
