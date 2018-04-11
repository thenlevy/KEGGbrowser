import java.io.*;
import java.net.*;
 
public class KEGG{
	public static String get_genome_data(String specie, String gene_id) {
		
		String url_name = ("http://rest.kegg.jp/get/"+ specie+ ":" 
						  + gene_id);
		String ret= "";				  
		try{
		    URL url = new URL(url_name);
		
		    URLConnection path =url.openConnection();
		    System.out.println(path.getContent());
		    InputStream input = path.getInputStream();
		    
		
		    while(input.available()>0)
		    ret += (char)input.read();
		}
		catch(MalformedURLException e){
		System.out.println(e);
	}
	    catch(IOException e){
		    System.out.println(e);
		
	}
	return ret;
			
	}
public static void main(String[] argv) {
	
	String test = get_genome_data("eco" , "b0630");
	System.out.println(test);
}
}
