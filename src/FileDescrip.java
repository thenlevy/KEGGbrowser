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


/**
 * open local files from a data repertory in KEGGbrowser path 
 * after downloading them from KEGG internet data base
 * */
public class FileDescrip {
    /**
     * get a url and a file path
     * search the info in the internet using the url
     * store the information in a local file
     * @param the URL where the info is, the location to save the file 
     * created
     * */
    private static void download_from_kegg(String url_name , String file_path){
        // search the last reccurence  of the "/" in the file
        int last_seperator = file_path.lastIndexOf('/') ;
        String directory;
        if (last_seperator == -1){ // if there is not a "/"
            directory = "." ;
        }
        else { // if we found the last "/"
            // directory takes the str before the slash and the slash
            directory = file_path.substring(0,last_seperator); 
        }
        
        try{
            // create a new url
            URL url = new URL(url_name);
            // open the url
            URLConnection path = url.openConnection();
            // store the informations in a variable
            System.out.println(path.getContent());
            InputStream input = path.getInputStream();
            
            // create a new file  
            File dir = new File (directory);
            dir.mkdirs();
            // put it in a file path
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file_path));

            int i=0;
            // read the variable with info and write in the file created
            while((i=input.read())!=-1){
                fileOutputStream.write((char)i);
            }
            // close new file
            fileOutputStream.close();
            // close input
            input.close();

		}
        // treat url exception
		catch(MalformedURLException e){
			System.out.println(e);
		}
        // treat interrputed exception
		catch(IOException e){
			System.out.println(e);
		}
		
	}
    /**
     * checks if the information exists locally or should be downloaded 
     * and returns the file corresponding
     * @param the URL where the info is, the location of the file if
     * it's already downloaded
     * @return the file needed   */
    private static File get_file(String url ,String file_path){
        File f = new File(file_path);
        // if the file exists in the directory then return it
        if(f.exists() && !f.isDirectory()){
                return f;
        }
        else{ // if the file doesn't exists
            // download info and create the file and returns it
            download_from_kegg(url, file_path);
            return f;
        }
    }
    
    /**
     * returns a string with all information in a file
     * @param the file needed to extract informations
     * @return a string containing all file lines
     * */
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
    /**
     * Return a file corresponding to a given gene and specie
     * @param initials of a wanted specie, the ID of the wanted gene
     * @return a file with gene information
     * */
    public static File get_gene_info(String specie, String gene_id){
        // create the file_path
        String file_path = ("../data/" + specie + "/gene/" + specie + "_"
                            + gene_id );
        // create the url to search the informations
        String url_name = ("http://rest.kegg.jp/get/"+ specie + ":"  
							+ gene_id);
        // return a file by calling get_file to check either it's local
        // or not
        return get_file(url_name , file_path); 
                            
    }
    /**
     * Return a conf file corresponding to a given pathway and specie
     * @param initials of a wanted specie, the ID of the wanted pathway 
     * implicated
     * @return a file with conf information of a pathway
     * */
    public static File get_org_conf(String specie, String path_id){
        String file_path = ("../data/" + specie + "/conf/" + specie + "_"
                            + path_id + ".conf" );
        String url_name = ("http://rest.kegg.jp/get/" + specie
                            + path_id + "/conf");
        
        return get_file(url_name , file_path); 
    }
    
    /**
     * Return a file with informations of a chosen reaction
     * @param the ID of a wanted reaction
     * @return a file with information of the reaction 
     * */
    public static File get_reaction_data(String reaction_id){
        String file_path = ("../data/reaction/" + reaction_id );
        String url_name = ("http://rest.kegg.jp/get/rn:" + reaction_id);
        
        return get_file(url_name , file_path); 
    }
    
    /**
     * Return a file with a map of a chosen reaction
     * @param the ID of a wanted reaction 
     * @return an image file showing a map of the reaction 
     * */
    public static File get_reaction_img(String reaction_id){
        String file_path = ("../data/reaction/image/" + reaction_id );
        String url_name = ("http://rest.kegg.jp/get/rn:" + reaction_id + "/image");
        
        return get_file(url_name , file_path); 
    }
    
    /**
     * Return a conf file corresponding to a given pathway map
     * @param the ID of a chosen map
     * @return a file with conf information of a pathway map
     * */
    public static File get_map_conf(String map_id){
        String file_path =("../data/map_conf/" + map_id + ".conf");
        String url_name = ("http://rest.kegg.jp/get/map"
                            + map_id + "/conf");
        return get_file(url_name , file_path);
    }
    
    /**
     * Return an image file of the pathway map corresponding 
     * to a given pathway and specie
     * @param initials of a wanted specie, the ID of the wanted pathway 
     * @return an image file of the pathway 
     * */
    public static File get_image_pathway(String specie, String pathway) {
		String file_path =("../data/"+ specie + "/pathway/" +
                            specie + pathway);
        String url_name = ("http://rest.kegg.jp/get/" + specie + pathway
                            + "/image");
        return get_file(url_name,file_path);

	}

			    
   // public static void main(String[] argv) {
    //    get_image_pathway("eco","00020");
    //    }

}

