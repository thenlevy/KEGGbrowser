import java.io.InputStream;
import java.io.OutputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
public class Path_image {
    public static void get_path_data(String specie, String pathway) {
		String url_name = ("http://rest.kegg.jp/get/" + specie + pathway
                            + "/image");
        get_path_image(url_name);

	}
    
    
    
    private static void get_path_image(String url_name){
        try {
            
            URL url = new URL(url_name);
            InputStream in = new BufferedInputStream(url.openStream());
            OutputStream out = new BufferedOutputStream(new FileOutputStream("espece"));

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
	
        get_path_data("eco" , "00010");
		
	}
}
