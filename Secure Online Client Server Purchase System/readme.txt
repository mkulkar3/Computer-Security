-------------------------------------------------------DONE INDIVIDUALLY-------------------------------------------------------

Name:	Mohit Kulkarni
Bmail:	mkulkar3@binghamton.edu

-------------------------------------------------------------------------------------------------------------------------------

Language: JAVA


-------------------------------------------------------------------------------------------------------------------------------

Platform: Tested succesfully on bingsuns and linux computers


-------------------------------------------------------------------------------------------------------------------------------

Steps to execute the program:


1) Execute the make command to compile the 3 files.
2) Execute the servers first by java Bank <port_number>, 
   java Psystem <port_number> <domain_of_bank> <bank_port_no.>,  
   java Customer <Psystem_domain><Psystem_port_no.>
3) To remove the .class files you can execute make clean command.



-------------------------------------------------------------------------------------------------------------------------------

Codes for encryption and decryption:



1)	private static byte[] encrypt(String tot, PrivateKey Key) 
	{
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




2)	private static String decrypt(byte[] ciphertext, PrivateKey Key) 
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



-------------------------------------------------------------------------------------------------------------------------------

