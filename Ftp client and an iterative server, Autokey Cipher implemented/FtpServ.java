
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.*;

public class FtpServ
{

	public static void main(String[] args) throws Exception
	{

		
	
		ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
		Scanner sc = new Scanner(System.in);
		
		Socket s1 = ss.accept();
		String s;

		BufferedReader in = new BufferedReader(new InputStreamReader(s1.getInputStream()));
		PrintStream out = new PrintStream(s1.getOutputStream(), true); 		
		int i=1;


		while(true)
		{
			i++;
			String str = " ";
			String sfinal;			

			String st1 = in.readLine();
			System.out.println(st1);


			if(st1.equals("quit"))
			break;



			else if(st1.equals("ls"))
			{
				Process p;
			
 				p = Runtime.getRuntime().exec("ls" +" "+ System.getProperty("user.dir"));
				BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
			

              	  		while ((s = pin.readLine()) != null)
					str +="\n" + s;
				
				str+= "\n"+"done";
				sfinal = str;
				out.println(sfinal);
			}

			else if(st1.equals("pwd"))
			{
				
				s = System.getProperty("user.dir");
				out.println("\t"+s);

			}

			else if(st1.contains("mkdir"))
			{	
				boolean success = false;
				
				String dir=System.getProperty("user.dir");

				int n= st1.indexOf(" ");
				String first= st1.substring(0, n);
				String rest= st1.substring(n+1);
				dir += "/" + rest;
	
				File directory = new File(dir);
				success = directory.mkdir();

				if(success)
				{
					System.out.println("directory created...");
					out.println("directory created..");
				}
				else
					out.println("failed");
				
				
			}

			else if(st1.contains("cd"))
			{	
				
				int k = st1.indexOf(" ");
				String cd= st1.substring(0, k);
				String path= st1.substring(k+1);

				if(!path.equals(".."))
				{

					String s2 =System.getProperty("user.dir");
					s2 += "/" + path;					
					System.setProperty("user.dir", s2);
							
					//System.out.println("directory changed...");
					out.println("directory changed...");
				}
				
				else
				{
					File newDir;
					String userDir = System.getProperty("user.dir");
					newDir = new File(userDir + "/..");
					// System.out.println(file.getCanonicalPath());
					System.setProperty("user.dir", newDir.getCanonicalPath());
					
					//System.out.println("directory changed...");
					out.println("directory changed...");
				}



			
			}

			else if(st1.contains("get"))
			{
				char [][] CharMatrix=new char[26][26];
    				char characters[]= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
				String key = "security";
			
	
			  	for(int p=0; p<=25; p++)
				{
        				for(int q=0; q<=25; q++)
                				CharMatrix[p][q]=characters[(q+p)%26];
            				
        			}
         

			        /*for(int u=0;u<=25;u++) {
            				System.out.print("| ");
						for(int v=0;v<=25;v++) {
							System.out.print(CharMatrix[u][v]+" ");
					         }
				            System.out.println("| ");
             			        }*/



				/*.........................................*/

				int m = st1.indexOf(" ");
				String get = st1.substring(0, m);
				String filename = st1.substring(m+1);
				String line, line2="abcd", keytext=key;
				
				int j = filename.indexOf(".");
				String file2 = filename.substring(0,j);
				String ext = filename.substring(j);
				out.println(filename);
				String path1 = System.getProperty("user.dir");;
				path1 += "/" + filename;
				file2 += "_se" + ext; 
				int k1,j1;
				FileReader fr = new FileReader(path1);
				FileWriter fw = new FileWriter(file2);
				BufferedWriter bw = new BufferedWriter(fw);
				Scanner sc1 = new Scanner(fr);
				


				while(sc1.hasNextLine())
				{
					i=0;
					keytext = key;
				
					line = sc1.nextLine();
					line += "\n";

					if(key.length()<line.length())
					{
						while(keytext.length() != line.length())
						keytext += line.charAt(i++);
					}
					
					//System.out.println(keytext+"\n"+line);

					for(i=0;i<keytext.length();i++)
					{	

						if(line.charAt(i) == ' ')
							{
								//System.out.print(" ");
								out.print(" ");
								bw.write(" ");
							}

						else if(keytext.charAt(i) == ' ')
						{
							//System.out.print(line.charAt(i));
							out.print(line.charAt(i));
							bw.write(line.charAt(i));
						
						}
						
						else if(line.charAt(i) == '\n')
							{
								//System.out.print("\n");
								out.print("\n");
								bw.write("\n");

							}
						else if(keytext.charAt(i) == '\n')
						{
							//System.out.print(line.charAt(i));
							out.print(line.charAt(i));
							bw.write(line.charAt(i));						
						}
		
						else
						{				
							j1=0;
							while((keytext.charAt(i)!=(CharMatrix[0][j1])))
								j1++;
							
							k1=0;
							while((line.charAt(i)!=(CharMatrix[k1][0])))
								k1++;
						
						
							//System.out.print(CharMatrix[k1][j1]);
							out.print(CharMatrix[k1][j1]);
							bw.write(CharMatrix[k1][j1]);
						}
					}


					


					
				}

			out.println("\n$");
			bw.close();

				/*...........................................*/

				
				
			}
		
				
		}

		
		ss.close();
		s1.close();
		sc.close();

	}

}
