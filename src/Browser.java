import javax.swing.SwingUtilities; 
import java.io.File;

/**
 * Holds the results and querry of KEGGbrowser
 */
public class Browser {

    private String gen_species; // "Species" field of genome browser
    private String gen_genID; // "Gene ID" field of genome browser
    private String pathway_species; // "Sepecies" field of the pathway browser
    private String pathway_mapID;
    private String displayed_pathway;
    private GUI gui;
    private Conf_reader conf_reader;
    private boolean debug_mode = false;

    public Browser() {
	gen_species = "...";
	gen_genID = ".....";
	pathway_species = "...";
	pathway_mapID = ".....";
	displayed_pathway = "";

	gui = new GUI(this);
    }

    public Browser(boolean b) {
	this();
	debug_mode = b;
    }

    public void set_gen_species(String s) {
	gen_species = s;
    }

    public String get_gen_species() {
	return gen_species;
    }

    public void set_gen_genID(String s) {
	gen_genID = s;
    }

    public String get_gen_genID() {
	return gen_genID;
    }

    public void set_pathway_species(String s) {
	pathway_species = s;
    }

    public String get_pathway_species() {
	return pathway_species;
    }

    public void set_pathway_mapID(String s) {
	pathway_mapID = s;
    }

    public String get_pathway_mapID() {
	return pathway_mapID;
    }

    public void gen_search() {
	if (debug_mode) {
	    System.out.println("Genome Browser Search " + gen_species + " " + gen_genID);
	}
    File f = FileDescrip.get_gene_info(gen_species, gen_genID);
    
	gui.set_gene_info_text(FileDescrip.get_text_from_file(f));
	String new_browser_url = ("http://www.genome.jp/kegg-bin/show_genomemap?ORG="
				  + gen_species +"&ACCESSION=" + gen_genID);

	gui.set_browser_url(new_browser_url);
	gui.set_gene_reaction_menu(Conf_reader.get_all_reactions(gen_species, gen_genID));
    }

    public void pathway_search() {
	// TODO implement this search function
	if (debug_mode) {
	    System.out.println("Pathway Browser Search " + pathway_species + " " + pathway_mapID);
	}
	update_pathway(pathway_species + pathway_mapID);
    }

    public void image() {
	// TODO implement this function
	if (debug_mode) {
	    System.out.println("Image button pressed");
	}
    }

    public void set_conf(File org_file, File map_file) {
	conf_reader = new Conf_reader(org_file, map_file);
    }

    public void click_on_rectangle(int x, int y) {
	Conf_rectangle rect = conf_reader.find_rect(x, y);
	if (rect.is_true_rectangle()) {
	    System.out.println("Clicked on rectangle" + rect.to_str());
	}
	else {
	    System.out.println("Clicked somewhere else");
	}
    }

    public void select_reaction(int index) {
	if (debug_mode) {
	    System.out.println("Selected index " + index);
	}
    }

    public void update_pathway(String pathway) {
	if (displayed_pathway != pathway) {
	    gui.update_pathway_img(FileDescrip.get_image_pathway(pathway_species, pathway_mapID));
	    displayed_pathway = pathway;
	}
    }
    
	
	

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
		    Browser b = new Browser(true);
		    b.set_conf(new File("test_org.conf"), new File("test_map.conf"));
		    b.click_on_rectangle(5, 5);
		    b.click_on_rectangle(200, 300);
		    
		}
	    });
    }
}
	


	
