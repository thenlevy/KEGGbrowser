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
    private String reaction_species;
    private String displayed_pathway;
    private String reaction;
    private GUI gui;
    private Conf_reader conf_reader;
    private boolean debug_mode = false;
    private List<String> reaction_list;
    private List<String> involved_genes_list;

    /**
     * Create the browser object
     */
    public Browser() {
	gen_species = "...";
	gen_genID = ".....";
	pathway_species = "...";
	pathway_mapID = ".....";
	displayed_pathway = "";

	gui = new GUI(this);
    }

    /**
     * Create the browser object, possibly in debug mode
     * @param b whether the debug mode should be activated or not
     */
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

    /**
     * Search and display all information about current gene.
     * Update the webpage in the genome browser, the list of reaction involing gene
     * and the information about the gene.
     */
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
	gui.refresh_gui();
    }

    /**
     * Search and display all information about current pathway.
     * Update the pathway image.
     */
    public void pathway_search() {
	if (debug_mode) {
	    System.out.println("Pathway Browser Search " + pathway_species + " " + pathway_mapID);
	}
	update_pathway(pathway_species + pathway_mapID);
    }

    /**
     * Display the image of the current reaction
     */
    public void image() {
	if (debug_mode) {
	    System.out.println("Image button pressed" + reaction);
	}
	gui.popup_image(FileDescrip.get_reaction_img(reaction));
    }

    /**
     * Change conf_files if necessary
     */
    public void set_conf(File org_file, File map_file) {
	if (conf_reader == null || !conf_reader.check(org_file, map_file))
	    conf_reader = new Conf_reader(org_file, map_file);
    }

    /**
     * Determine wether an action is to be performed when the user clicked on
     * the pathway image.
     * @param (x,y) the coordonates of the point where the user clicked
     */
    public void click_on_rectangle(int x, int y) {
	Conf_rectangle rect = conf_reader.find_rect(x, y);
	if (rect.is_true_rectangle()) {
	    System.out.println("Clicked on rectangle" + rect.to_str());
	    gui.select_rectangle(rect);
	    reaction_species = pathway_species;
	    reaction = conf_reader.get_reaction_from_rect(rect);
	    update_reaction(reaction, displayed_pathway);
	    
	}
	else {
	    System.out.println("Clicked somewhere else");
	}
    }

    /**
     * Update the selected reaction when the user click on the reaction list.
     */
    public void select_reaction(int index) {
	if (debug_mode) {
	    System.out.println("Selected index " + index);
	}
	if (index >= 0) {
	    String pathway = reaction_list.get(index).split(" ")[2];
	    reaction = reaction_list.get(index).split(" ")[0];
	    reaction_species = gen_species;
	    update_reaction(reaction, pathway);
	    Conf_rectangle rect = conf_reader.get_rect_from_reaction(reaction);
	    gui.select_rectangle(rect);
	}
    }

    /**
     * Update the selected gene when the user clid on the gene list
     */
    public void select_gene(int index) {
	if (debug_mode) {
	    System.out.println("Selected index " + index);
	}
	if (index >= 0) {
	    if (!gen_genID.equals(involved_genes_list.get(index))) {
		gen_genID = involved_genes_list.get(index);
		gen_species = reaction_species;
		gen_search();
	    }
	}
    }

    /**
     * Update the reaction, and the gene involed list.
     */
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
	gui.set_involved_gene_menu(involved_genes_list);
	gui.refresh_gui();
    }
	

    /**
     * Update the current pathway.
     */
    private void update_pathway(String pathway) {
	if (!displayed_pathway.equals(pathway)) {
	    gui.update_pathway_img(FileDescrip.get_image_pathway(pathway_species, pathway_mapID));
	    displayed_pathway = pathway;
	    File org_file = FileDescrip.get_org_conf(pathway_species, pathway_mapID);
	    File map_file = FileDescrip.get_map_conf(pathway_mapID);
	    set_conf(org_file, map_file);
	    gui.refresh_gui();
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
	


	
