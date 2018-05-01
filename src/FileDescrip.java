import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Desktop;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;



public class FileDescrip {
    public static void open_file_local(String specie, String info, String categorie ){
        if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)){
            try {
                java.awt.Desktop.getDesktop().open(new File("../data/"+
                                specie+"/"+categorie+"/"+ specie+ info));
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    private static void download_from_kegg(String url_name , String file_path){
        int last_seperator = file_path.lastIndexOf('/') ;
        String directory;
        if (last_seperator == -1){
            directory = "." ;
        }
        else {
            directory = file_path.substring(0,last_seperator);
        }
        
        try{
			URL url = new URL(url_name);

			URLConnection path = url.openConnection();
			System.out.println(path.getContent());
			InputStream input = path.getInputStream();
          
            File dir = new File (directory);
            dir.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(
                                                new File(file_path ));

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
    private static File get_file(String url ,String file_path){
        File f = new File(file_path);
        if(f.exists() && !f.isDirectory()){
                return f;
        }
        else{
            download_from_kegg(url, file_path);
            return f;
        }
    }
    
    
    public static String get_text_from_file(File f) {
        String ret = "";
        try {
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String line = "";
            while ((line = bf.readLine()) != null){
                ret += line + "\n" ;
                }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return ret;
    }
    
    public static File get_gene_info(String specie, String gene_id){
        String file_path = ("../data/" + specie + "/gene/" + specie + "_"
                            + gene_id );
        String url_name = ("http://rest.kegg.jp/get/"+ specie + ":"  
							+ gene_id);
        return get_file(url_name , file_path); 
                            
    }
    public static File get_org_conf(String specie, String path_id){
        String file_path = ("../data/" + specie + "/conf/" + specie + "_"
                            + path_id + ".conf" );
        String url_name = ("http://rest.kegg.jp/get/" + specie
                            + path_id + "/conf");
        
        return get_file(url_name , file_path); 
    }
    public static File get_reaction_data(String specie, String reaction_id){
        String file_path = ("../data/"+ specie + "/reaction/"+ specie 
                            + reaction_id );
        String url_name = ("http://rest.kegg.jp/get/rn:" + reaction_id);
        
        return get_file(url_name , file_path); 
    }
    public static File get_map_conf(String map_id){
        String file_path =("../data/map_conf/" + map_id + ".conf");
        String url_name = ("http://rest.kegg.jp/get/map"
                            + map_id + "/conf");
        return get_file(url_name , file_path);
    }
    
    public static File get_image_pathway(String specie, String pathway) {
		String file_path =("../data/"+ specie + "/pathway/" +
                            specie + pathway);
        String url_name = ("http://rest.kegg.jp/get/" + specie + pathway
                            + "/image");
        return get_file(url_name,file_path);

	}

    public static List<File[]> get_pathway_files(String specie, String gene_id) {
	File f = get_gene_info(specie, gene_id);
	List<File[]> ret = new ArrayList<File[]>();
	try {
	    BufferedReader bf = new BufferedReader(new FileReader(f));
	    String line = "";
	    boolean reading_pathways = false;
	    while ((line = bf.readLine()) != null) {

		if (line.startsWith("PATHWAY"))
			reading_pathways = true;

		if (reading_pathways) {
			Pattern pathway = Pattern.compile("\\s([a-zA-Z]{3,4})([0-9]{5})");
			Matcher pathway_getter = pathway.matcher(line);
			if (pathway_getter.find()) {
			    String sp = pathway_getter.group(1);
			    String path = pathway_getter.group(2);
			    File org_conf = get_org_conf(sp, path);
			    File map_conf = get_map_conf(path);
			    ret.add(new File[]{org_conf, map_conf});
			}
			else break;
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
	return ret;
    }
			    
    public static void main(String[] argv) {
        get_image_pathway("eco","00020");
        }

}

