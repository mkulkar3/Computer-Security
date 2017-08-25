

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.lang.*;


public class FtpCli {
	
	public static void main(String args[]) throws IOException
	{

		//System.out.println(args[0] +" "+ args[1]);
		
		Socket sock = new Socket(args[0], Integer.parseInt(args[1]));
		
		Scanner sc = new Scanner(System.in);
    		
		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    		System.out.print("ftp> ");
    		String s1 = null;
		
		
		PrintStream out = new PrintStream(sock.getOutputStream());
		String s = null;
		//out.println(s1);


		char [][] CharMatrix=new char[26][26];
    		char characters[]= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

		String key = "security";
			
	
	  	for(int p=0; p<=25; p++){
        		for(int q=0; q<=25; q++){
               			CharMatrix[p][q]=characters[(q+p)%26];
            		}
        	}

		
		
		while((s1 = sc.nextLine())!=null)
		{			

			out.println(s1);

			if(s1.equals("quit"))
			{					
				sock.close();
				sc.close();
				
				System.out.println("Terminated");
				System.exit(0);
			}


			
			else if(s1.equals("lls"))
			{
								
				//System.out.print("running lls command...");
				Process p = Runtime.getRuntime().exec("ls");
				BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				while ((s = pin.readLine()) != null)
                			System.out.println("\t"+s);

				//System.out.println("done...");
			}

			else if(s1.equals("ls"))
			{
				
				//System.out.println("process executed on server.. now printing at client side...");
               			while(!(s = in.readLine()).equals("done"))
                			System.out.println("\t"+s);
					
				
				//System.out.println("done...");
			}

			else if(s1.equals("pwd"))
			{
				s = in.readLine();
				System.out.println(s);
			}
	
			else if(s1.contains("mkdir"))
			{
				s = in.readLine();
				//System.out.println(s);				
			}
			
			else if(s1.contains("cd"))
			{
				s = in.readLine();
				//System.out.println(s);	
			}

			else if(s1.contains("get"))
			{

				
				String name = in.readLine();
				int m = name.indexOf(".");
				String filename = name.substring(0, m);
				String rest = name.substring(m);
				String line;
				String filename1 = filename + "_cd" + rest;				
		
				filename += "_ce" + rest; 
				
				
				
				FileWriter fw = new FileWriter(filename);
				BufferedWriter br = new BufferedWriter(fw);

				
				FileWriter fw1 = new FileWriter(filename1);
				BufferedWriter br1 = new BufferedWriter(fw1);

				while(!(s = in.readLine()).equals("$"))	
                		{	
					s += "\n";
					//System.out.println(s);
					br.write(s);
				}
	
				br.close();
			

				FileReader fr = new FileReader(filename);
				Scanner sc1 = new Scanner(fr);
					
				while(sc1.hasNextLine())
				{
					key = "security";				
					line = sc1.nextLine();
					line += "\n";

					for(int i=0;i<key.length()&&i<line.length();i++)
					{		
						if(line.charAt(i)== '\n')
						{
							//System.out.print('\n');
							key += '\n';
							br1.write('\n');
						}
						else if(key.charAt(i)=='\n')
						{
							//System.out.print(line.charAt(i));
							key+= line.charAt(i);
							br1.write(line.charAt(i));
						}

						else if(line.charAt(i)==' ')
						{
							//System.out.print(' ');
							key+= ' ';
							br1.write(' ');
								
						}

						else if(key.charAt(i) == ' ')
						{
							//System.out.print(line.charAt(i));
							key+= line.charAt(i);
							br1.write(line.charAt(i));
										
						}
																
						else
						{
							
							int j1=0;
							while((key.charAt(i)!=(CharMatrix[0][j1])))
								j1++;
						
							int k1=0;
							while((line.charAt(i)!=(CharMatrix[k1][j1])))
								k1++;
						
						
							key+=CharMatrix[k1][0];
							br1.write(CharMatrix[k1][0]);
						}
					}	
				}			  			
				br1.close();
			}

		System.out.print("\nftp> ");			

		}
		
	}
}
