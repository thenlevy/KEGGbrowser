import javax.swing.SwingUtilities; 
import java.io.File;
import java.util.List;
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
    private List<String> reaction_list;
    private List<String> involved_genes_list;

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
	reaction_list = Conf_reader.get_all_reactions(gen_species, gen_genID);
	gui.set_gene_reaction_menu(reaction_list);
    }

    public void pathway_search() {
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
	if (conf_reader == null || !conf_reader.check(org_file, map_file))
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
	if (index >= 0) {
	    String pathway = reaction_list.get(index).split(" ")[2];
	    String reaction = reaction_list.get(index).split(" ")[0];
	    update_reaction(reaction, pathway);
	}
    }

    private void update_reaction(String reaction, String pathway) {
	int specie_name_length = pathway.length() - 5;
	pathway_species = pathway.substring(0, specie_name_length);
	pathway_mapID = pathway.substring(specie_name_length);
	update_pathway(pathway);
	File f = FileDescrip.get_reaction_data(reaction);
	gui.set_reaction_info(FileDescrip.get_text_from_file(f));

	File org_file = FileDescrip.get_org_conf(pathway_species, pathway_mapID);
	File map_file = FileDescrip.get_map_conf(pathway_mapID);
	set_conf(org_file, map_file);
	involved_genes_list = conf_reader.get_genes_involved(reaction);
	for (String s : involved_genes_list) {
	    System.out.println(s);
	}
    }
	

    private void update_pathway(String pathway) {
	if (!displayed_pathway.equals(pathway)) {
	    gui.update_pathway_img(FileDescrip.get_image_pathway(pathway_species, pathway_mapID));
	    displayed_pathway = pathway;
	}
    }
    
	
	

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
		    Browser b = new Browser(true);
		    
		}
	    });
    }
}
	


	
