import java.io.InputStream;
import java.io.OutputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;

public class Path_image {
    public static void get_path_data(String specie, String pathway) {
		String url_name = ("http://rest.kegg.jp/get/" + specie + pathway
                            + "/image");
        get_path_image(url_name,specie,pathway);

	}
    
    
    
    private static void get_path_image(String url_name,String specie,String pathway){
        try {
            
            URL url = new URL(url_name);
            InputStream in = new BufferedInputStream(url.openStream());
            
            File dir = new File ("/Users/hassenebenyedder/KEGGbrowser/"
                            + specie + "/" + "pathway" + "/"+ pathway);
            dir.mkdirs();
            OutputStream out = new BufferedOutputStream(
            new FileOutputStream("/Users/hassenebenyedder/KEGGbrowser/"
                            + specie + "/" + "pathway" + "/"+ pathway +
                            "/"+specie+"_"+pathway));

            for ( int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
            in.close();
            out.close();
        }
        catch(MalformedURLException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
        }
        

    }


        
    public static void main(String[] argv) {
	
        get_path_data("eco" , "00020");
		
	}
}
