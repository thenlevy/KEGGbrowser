import java.io.*;
import java.io.File;
import java.net.*;
import java.io.InputStream;
import java.io.OutputStream;
 
public class KEGG{
	public static void get_genome_data(String specie, String gene_id) {
		String url_name = ("http://rest.kegg.jp/get/"+ specie+ ":" 
							+ gene_id);
        get_text_url(url_name,specie,gene_id);

	}
	private static void get_text_url(String url_name,String specie, String gene_id){

		try{
			URL url = new URL(url_name);

			URLConnection path =url.openConnection();
			System.out.println(path.getContent());
			InputStream input = path.getInputStream();
          
            File dir = new File ("/Users/hassenebenyedder/KEGGbrowser/"
                            + specie + "/" + "GI" + "/"+ gene_id);
            dir.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(
                "/Users/hassenebenyedder/KEGGbrowser/"+specie + "/" 
                + "GI" +"/" + gene_id + "/" + specie + gene_id));

            int i=0;
            while((i=input.read())!=-1){
                fileOutputStream.write((char)i);
            }
            fileOutputStream.close();
            input.close();

		}
		catch(MalformedURLException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}
		
	}
	public static void main(String[] argv) {
        get_genome_data("eco" , "b0002");
		
	}
}
