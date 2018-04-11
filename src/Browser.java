import javax.swing.SwingUtilities; 

/**
 * Holds the results and querry of KEGGbrowser
 */
public class Browser {

    private String gen_species; // "Species" field of genome browser
    private String gen_genID; // "Gene ID" field of genome browser
    private String pathway_species; // "Sepecies" field of the pathway browser
    private String pathway_mapID;
    private GUI gui;
    private boolean debug_mode = false;

    public Browser() {
	gen_species = "...";
	gen_genID = ".....";
	pathway_species = "...";
	pathway_mapID = ".....";

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

    public String gen_search() {
	if (debug_mode) {
	    System.out.println("Genome Browser Search " + gen_species + " " + gen_genID);
	}
	return KEGG.get_genome_data(gen_species, gen_genID);
    }

    public void pathway_search() {
	// TODO implement this search function
	if (debug_mode) {
	    System.out.println("Pathway Browser Search " + pathway_species + " " + pathway_mapID);
	}
    }

    public void image() {
	// TODO implement this function
	if (debug_mode) {
	    System.out.println("Image button pressed");
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
	


	
