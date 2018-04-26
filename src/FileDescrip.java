import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Desktop;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.*;
import java.awt.Graphics;
import java.awt.Image;

public class FileDescrip {
    public static void open_file_local(String specie, String info, String categorie ){
        if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)){
            try {
                java.awt.Desktop.getDesktop().open(new File("/Users/hassenebenyedder/KEGGbrowser/"+specie+"/"+categorie+"/"+info+"/"+ specie+ info));
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    public static void main(String[] argv) {
//        open_file_local("eco");
//    }
    

    public static void get_genome_data(String specie, String gene_id) {
		String url_name = ("http://rest.kegg.jp/get/"+ specie+ ":" 
							+ gene_id);
        get_text_url(url_name,specie,gene_id);

	}
	private static void get_text_url(String url_name,String specie, String gene_id){

		try{
			URL url = new URL(url_name);

			URLConnection path = url.openConnection();
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
//	public static void main(String[] argv) {
//        get_genome_data("eco" , "b0002");
//		
//	}
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
                            "/"+specie + pathway));

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
    public static void file_finder(String specie, String info){
        if (info.startsWith("b")){
            File f = new File("/Users/hassenebenyedder/KEGGbrowser/"+specie + "/" 
                + "GI" +"/" + info + "/" + specie + info );
            if(f.exists() && !f.isDirectory()){
                open_file_local(specie, info , "GI");
            }
            else {
                get_genome_data(specie, info);
                open_file_local(specie, info , "GI");
            }
        }
        else{
            File f = new File("/Users/hassenebenyedder/KEGGbrowser/"
                            + specie + "/" + "pathway" + "/"+ info );
            
            if(f.exists() && !f.isDirectory()){
                open_file_local(specie, info , "pathway");
            }
            else {
                get_path_data(specie, info);
                open_file_local(specie, info , "pathway");
            }
        }
    }
                
            
            


        
    public static void main(String[] argv) {
        file_finder("eco","00260");
        }

}

