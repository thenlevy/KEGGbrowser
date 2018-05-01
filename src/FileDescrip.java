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
                java.awt.Desktop.getDesktop().open(new File("../data/"+specie+"/"+categorie+"/"+ specie+ info));
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
          
            File dir = new File ("../data/"
                            + specie + "/GI/");
            dir.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(
                "../data/"+specie + "/GI/" 
                 + specie + gene_id));

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
    
    public static void org_conf_file(String specie, String gene_id) {
		String url_name = ("http://rest.kegg.jp/get/" + specie
                            + gene_id + "/conf");
        save_conf_file(url_name,specie,gene_id);

	}
	private static void save_conf_file(String url_name,String specie, String gene_id){

		try{
			URL url = new URL(url_name);

			URLConnection path = url.openConnection();
			System.out.println(path.getContent());
			InputStream input = path.getInputStream();
          
            File dir = new File ("../data/"
                            + specie + "/conf/");
            dir.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(
                "../data/"+specie + "/conf/" 
                 + specie + gene_id ));

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

     public static void get_reaction_data(String specie, String reaction_id) {
        
		String url_name = ("http://rest.kegg.jp/get/rn:" + reaction_id);
        get_reaction_url(url_name,specie,reaction_id);

	}
	private static void get_reaction_url(String url_name,String specie, String reaction_id){

		try{
			URL url = new URL(url_name);

			URLConnection path =url.openConnection();
			System.out.println(path.getContent());
			InputStream input = path.getInputStream();
          
            File dir = new File ("../data/"+ specie +
                                "/" + "reaction");
            dir.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(
                "../data/"+ specie + 
                "/reaction/"+ specie + reaction_id));

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

    
     public static void get_path_data(String specie, String pathway) {
		String url_name = ("http://rest.kegg.jp/get/" + specie + pathway
                            + "/image");
        get_path_image(url_name,specie,pathway);

	}
    
    
    
    
    private static void get_path_image(String url_name,String specie,String pathway){
        try {
            
            URL url = new URL(url_name);
            InputStream in = new BufferedInputStream(url.openStream());
            
            File dir = new File ("../data/"
                            + specie + "/pathway/");
            dir.mkdirs();
            OutputStream out = new BufferedOutputStream(
            new FileOutputStream("../data/"
                            + specie + "/pathway/" +specie + pathway));

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
    public static void map_conf_file(String specie, String mapID) {
		String url_name = ("http://rest.kegg.jp/get/map"
                            + mapID + "/conf");
        save_map_conf(url_name,specie,mapID);

	}
	private static void save_map_conf(String url_name,String specie, String mapID){

		try{
			URL url = new URL(url_name);

			URLConnection path = url.openConnection();
			System.out.println(path.getContent());
			InputStream input = path.getInputStream();
          
            File dir = new File ("../data/"
                            + specie + "/map_conf/");
            dir.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(
                "../data/"+specie + "/map_conf/" 
                 + specie + mapID ));

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
    public static void file_finder(String specie, String info){
        if (info.startsWith("b")){
            File f = new File("../data/"+specie + 
            "/GI/" + info + "/" + specie + info );
            
            if(f.exists() && !f.isDirectory()){
                open_file_local(specie, info , "GI");
            }
            else {
                
                get_genome_data(specie, info);
                
                open_file_local(specie, info , "GI");
                
            }
        }
        else if (info.startsWith("R")){
            File f = new File("../data/"+ specie +
                                "reaction/"+specie + info);
            if(f.exists() && !f.isDirectory()){
                open_file_local(specie, info , "reaction");
            }
            else {
                get_reaction_data(specie, info);
                open_file_local(specie, info , "reaction");
            }
            
                
        }
            
        else{
            
            File f1 = new File("../data/"
                            + specie + "/" + "pathway" + "/"+ info );
            File f2 = new File ("../data/"
                                + specie + "/conf/" + specie + info );
            File f3 = new File ("../data/"
                                +specie + "/map_conf/" + specie + info);
            if( (f1.exists() && !f1.isDirectory()) && 
                (f2.exists() && !f2.isDirectory()) && 
                (f3.exists() && !f3.isDirectory())){
                
                open_file_local(specie, info , "pathway");
                open_file_local(specie, info , "conf");
                open_file_local(specie , info , "map_conf");
            }
            else {
                get_path_data(specie, info);
                org_conf_file(specie,info);
                map_conf_file(specie,info);
                open_file_local(specie, info , "pathway");
                open_file_local(specie, info , "conf");
                open_file_local(specie , info , "map_conf");
            }
        }
    }
                
            
            


        
    public static void main(String[] argv) {
        file_finder("eco","00020");
        }

}

