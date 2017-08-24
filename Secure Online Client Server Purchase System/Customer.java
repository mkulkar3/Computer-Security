import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.security.*;
import javax.crypto.*;


public class Customer {

	public static void main(String[] args) throws UnknownHostException, IOException, NoSuchAlgorithmException, ClassNotFoundException, NoSuchProviderException, InvalidKeyException, SignatureException {
		// TODO Auto-generated method stub
		
		
		Socket sock = new Socket(args[0], Integer.parseInt(args[1]));
		Scanner sc = new Scanner(System.in);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		PrintStream out = new PrintStream(sock.getOutputStream());
		
		ObjectOutputStream outb = new ObjectOutputStream(sock.getOutputStream());
		//ObjectInputStream inb = new ObjectInputStream(sock.getInputStream());
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Pup.key"));
		ObjectInputStream inputStream1 = new ObjectInputStream(new FileInputStream("Pub.key"));
    ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream("Pra.key"));
		ObjectInputStream inputStream3 = new ObjectInputStream(new FileInputStream("Prt.key"));
		
		
    System.out.println("");
		System.out.print("Enter you ID: ");
		String id = sc.nextLine();
		out.println(id);
		//System.out.println("");
	  final PrivateKey digitSigKey;
		
		if(id.equals("alice"))
		{
			digitSigKey = (PrivateKey) inputStream2.readObject();
		}
		else
		{
			digitSigKey = (PrivateKey) inputStream3.readObject();
		}	
   
   
   
   
		int flag = 0;
		
		do{
					
			    System.out.print("\nEnter your password: ");
			    String pass = "";
			    pass = sc.nextLine();
			
		
    			MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(pass.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) 
          {
	           sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	     
	        String hashPass="";
	        hashPass = sb.toString();
	        //System.out.println("Hashed Password: " + hashPass);
	        
	        out.println(hashPass);
	        
	        String str = in.readLine();
	        System.out.println("");                 
	        System.out.println(str);
          System.out.println("");

	        String passMatch = in.readLine();
	        //System.out.println(passMatch);
	        if(passMatch.equals("error"))
	        {
	        	System.out.println("\nIncorrect Password...");
	        }
	        else
	        {
	        	System.out.println("\nPassword Authentication Successfull....\n");
	        	flag = 1;
	        }
		
		
	        
	        
			}while(flag == 0);
		
		
		
			String line;
			
			
			while(!(line = in.readLine()).equals("finish"))
			{
				System.out.println(line);
			}
			
      System.out.println("");

		
		
		System.out.print("Enter Item number: ");
		String itemNumber = sc.nextLine();
		
    System.out.println("\n");
	
   
		//out.println(itemNumber);
		
		
		System.out.print("Enter the quantity: ");
		String quant = sc.nextLine();
		
    System.out.println("\n");

		
		//out.println(quant);
		
		
		final PublicKey publicKey = (PublicKey) inputStream.readObject();
		
		
		//sending encrypted item number
		final byte[] cipherTextItemno = encrypt(itemNumber, publicKey);
		outb.writeObject(cipherTextItemno);
		//out.println(itemNumber);
		
		//sending encrypted item quantity
		final byte[] cipherTextQnt = encrypt(quant, publicKey);
		outb.writeObject(cipherTextQnt);
		//out.println(quant);
   
   
   
   
   
    Signature dsa = Signature.getInstance("MD5WithRSA"); 
		dsa.initSign(digitSigKey);
		
		dsa.update(cipherTextItemno);
		dsa.update(cipherTextQnt);
		
		final byte[] digitalSign = dsa.sign();
		outb.writeObject(digitalSign);
		
		String SignStat = in.readLine();
		
		if(SignStat.equals("error"))
		{
			System.out.println("\nDigital Signature verification failed....");
			inputStream.close();
			inputStream1.close();
			inputStream2.close();
			inputStream3.close();
			sock.close();
			sc.close();
			System.exit(0);
		}
   
   
   
   
   
   
   
   
    String quantStatus = in.readLine();
		
		if(quantStatus.equals("okay"))
			System.out.println("valid quantity...");
		else
		{
			System.out.println("Invalid quantity...");
      inputStream.close();
			inputStream1.close();
      inputStream2.close();
			inputStream3.close();
			sock.close();
			sc.close();
			System.exit(0);
		}
   
   
 		final PublicKey publicKeyBank = (PublicKey) inputStream1.readObject();

    //sending encrypted id
    final byte[] cipherTextid = encrypt(id, publicKeyBank);
		outb.writeObject(cipherTextid);
		//out.println(id);
		
		
		//sending encrypted card number
		System.out.print("\nEnter your credit card number: ");
		String cardnumb = sc.nextLine();
		final byte[] cipherTextcard = encrypt(cardnumb, publicKeyBank);
		outb.writeObject(cipherTextcard);
		//out.println(cardnumb);
		
    System.out.println("");

    
		
		String cardStatus = in.readLine();
		if(cardStatus.equals("ok"))
			System.out.println("Order complete...\n");
		else
		{
			System.out.println("Wrong credit card number...");
		}
		
		inputStream.close();
		inputStream1.close();
    inputStream2.close();
    inputStream3.close();
		sc.close();
		sock.close();
	}

	private static byte[] encrypt(String itemNumber, PublicKey publicKey) {
		// TODO Auto-generated method stub
		
		byte[] cipherText = null;
		try {
  			// get an RSA cipher object and print the provider
  			final Cipher cipher = Cipher.getInstance("RSA");
  			// encrypt the plain text using the public key
  			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
  			cipherText = cipher.doFinal(itemNumber.getBytes());
		} catch (Exception e) {
  			e.printStackTrace();
		  }

	return (cipherText);
		
	}

}
