import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/** Reads and interpret the informations contained in a .conf file
 */
public class Conf_reader {

    private File org_file;
    private File map_file;
    private Hashtable<Integer, Map_rectangle> hash_map;
    private Hashtable<String, Map_rectangle> hash_reaction;
    private Hashtable<Integer, Org_rectangle> hash_org;

    public static void main(String arg[]) {
	List<String> test = get_all_reactions("eco", "b0630");
	for (String s : test) {
	    System.out.println(s);
	}
    }
    /**
      *renvoie la liste des reactions( contexte de voies metaboliques)
      *  impliquant un gene donné */
    public static List<String> get_all_reactions(String specie, String gene_id) {
        //cherche toutes les voies impliquant le gene
        List<String> pathways = get_pathway_files(specie, gene_id); 
        List<String> ret = new ArrayList<String>();
        for (String path : pathways) { 
            // pour chaque voie on cherche les fichiers conf
            File org_file = FileDescrip.get_org_conf(specie, path);
            File map_file = FileDescrip.get_map_conf(path);
            // on récupère les reactions à partir des fichiers conf
            List<String> reactions = get_reaction(org_file, map_file, gene_id);
            // on ajoute les reactions aux résultats
            for (String reaction : reactions) {
            ret.add(reaction + " @ " + specie + path);
            }
        }
        return ret;
    }
    
    /**
      * Return a list of all metabolic pathways that involve  
      * a given gene
      */
    private static List<String> get_pathway_files(String specie, String gene_id) {
        // get the KEGG file describing the gene
        File f = FileDescrip.get_gene_info(specie, gene_id);
        List<String> ret = new ArrayList<String>();
        try { // read the file and look for the pathways
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String line = "";
            // We need to detect the keyword 'PATHWAY' 
            boolean reading_pathways = false;
            while ((line = bf.readLine()) != null) {

                if (line.startsWith("PATHWAY"))
                    reading_pathways = true;

                if (reading_pathways) {
                    // As long as we can match this pattern, we are 
                    // reading pathways
                    Pattern pathway = Pattern.compile("\\s([a-zA-Z]{3,4})([0-9]{5})");
                    Matcher pathway_getter = pathway.matcher(line);
                    if (pathway_getter.find()) {
                        // extract the pathway from the pattern
                        String path = pathway_getter.group(2);
                        // add it to the result
                        ret.add(path);
                    }
                    else break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ret;
    }
    
    /**
      * Return a list of reaction involving a given gene 
      * from a pair of conf files 
      */
    public static List<String> get_reaction(File org_file, File map_file, String gene_id) {
	Conf_reader cr = new Conf_reader(org_file, map_file);
	return cr.get_reaction(gene_id);
    }
   
    public Conf_reader(File org_f, File map_f) {
	org_file = org_f;
	map_file = map_f;

	make_hash_map();
	make_hash_org();
    }

    /** Read the map conf file and create the Map_rectangle and store them in the Hashtable hash_map
     */
    private void make_hash_map() {
	hash_map = new Hashtable<Integer, Map_rectangle>();
	hash_reaction = new Hashtable<String, Map_rectangle>();
	try { // read the generic conf file 
	    BufferedReader map_reader = new BufferedReader(new FileReader(map_file));
	    String line = "";
	    while ((line = map_reader.readLine()) != null) {
            if (line.startsWith("rect")) {
                // if the current line describes a rectangle 
                // we create the corresponding object
                Map_rectangle rect = Map_rectangle.from_line(line);
                // store it in a hash table 
                hash_map.put(rect.get_hash(), rect);
                // associate the corresponding reaction to the rectangle
                String reaction = rect.get_reactions();
                hash_reaction.put(reaction, rect);
            }
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    /** Read the org conf file and create the Org_rectangle and store them in the Hashtable hash_org
     */
    private void make_hash_org() {
	hash_org = new Hashtable<Integer, Org_rectangle>();
	try { // read the organism conf file
	    BufferedReader org_reader = new BufferedReader(new FileReader(org_file));
	    String line = "";
	    while ((line = org_reader.readLine()) != null) {
		if (line.startsWith("rect")) {
            // When we find a rectangle we store in a hash table
		    Org_rectangle rect = Org_rectangle.from_line(line);
		    hash_org.put(rect.get_hash(), rect);
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
    }
    
    /**
      *  Return the rectangle containing a given point (x,y)
      */
    public Conf_rectangle find_rect(int x, int y) {
	for (Conf_rectangle cr: hash_map.values()) {
	    if (cr.is_in(x, y)) {
		return new Conf_rectangle(cr);
	    }
	}
	return new Conf_rectangle();
    }
    
    /**
      * Get the reaction associated to a rectangle 
      */
    public String get_reaction_from_rect(Conf_rectangle rect) {
	Map_rectangle m_rect = hash_map.get(rect.get_hash());
	return m_rect.get_reactions();
    }

    /**
      * Return the list of all reaction involving a given gene
      */
    public List<String> get_reaction(String gene_interest) {
	List<String> ret = new ArrayList<String>();
	// We look in the organism rectangle if the gene is involved in it
	for (Org_rectangle rect : hash_org.values()) {
	    if (rect.has_gene(gene_interest)) {
            // We look for the reaction in the corresponding generic 
            // rectangle
            Map_rectangle map_rect = hash_map.get(rect.get_hash());
            String reaction = map_rect.get_reactions();
            ret.add(reaction);
	    }
	}
	return ret;
    }
    
    /**
      * Return the list of gene involved in a reaction
      */
    public List<String> get_genes_involved(String reaction) {
        // We get the generic rectangle involving the reaction
        // and then the corresponding organism rectangle
	    int key = hash_reaction.get(reaction).get_hash();
        // return the gene involved in the organism rectangle
        if (hash_org.containsKey(key))
            return hash_org.get(key).get_genes();
        else
            return null;
    }
    
    /**
      * Return True if the given files are the current organism and
      * generic files
      */  
    public boolean check(File o_file, File m_file) {
	try {
	    return (o_file.getCanonicalPath().equals(org_file.getCanonicalPath())
		    && m_file.getCanonicalPath().equals(map_file.getCanonicalPath()));
	} catch (Exception e) {
	    System.out.println(e);
	    return false;
	}
    }
    
    public Conf_rectangle get_rect_from_reaction(String reaction) {
        return hash_reaction.get(reaction);
    }
	
}



class Map_rectangle extends Conf_rectangle {
    private HashSet<String> k_nums;
    private String r_nums = "";

    private Map_rectangle(Conf_rectangle cr) {
	super(cr);
	k_nums = new HashSet<String>();
    }
	
    /**
     * Create a rectangle from a line in the generic conf file
     */ 
    public static Map_rectangle from_line(String s) {
	Map_rectangle ret = new Map_rectangle(Conf_rectangle.read_coordonates(s));
    // Search Knums
	Pattern knum = Pattern.compile("K[0-9]{5}");
	Matcher knum_getter = knum.matcher(s);
	while (knum_getter.find()) { 
	    ret.k_nums.add(knum_getter.group());
	}
    // Search Rnums 
	Pattern rnum = Pattern.compile("R[0-9]{5}");
	Matcher rnum_getter = rnum.matcher(s);
	while (rnum_getter.find()) {
	    ret.r_nums = (rnum_getter.group());
	}
	return ret;
    }
    /**
     * Return True if a given reaction is the one associated 
     * to the rectangle 
     */
    public boolean has_reacton(String react) {
	return r_nums.equals(react);
    }

    /**
      * Make the rectangle printable
      */
    @Override
    public String to_str() {
	String ret = super.to_str();

	if (!k_nums.isEmpty()) {
	    ret += "\n";
	    for (String knum : k_nums)
		ret += (knum + " ");
	}

	if (!r_nums.equals("")) {
	    ret += "\n";
	    ret += r_nums;
	}

	return ret;
    }
    /** 
      * return the associated reaction
      */
    public String get_reactions() {
	return r_nums;
    }
}

class Org_rectangle extends Conf_rectangle {
    private HashSet<String> genes;

    private Org_rectangle(Conf_rectangle cr) {
	super(cr);
	genes = new HashSet<String>();
    }
    
    /**
      * Create a rectangle from a line in an organism conf file
      */
    public static Org_rectangle from_line(String s) {
	Org_rectangle ret = new Org_rectangle(Conf_rectangle.read_coordonates(s));

	// Search for genes in the line
	// gene have are in the form org:geneID
	Pattern gene = Pattern.compile("([a-zA-Z]+\\:)([a-zA-Z0-9]+)");
	Matcher gene_getter = gene.matcher(s);
	while (gene_getter.find()) {
	    ret.genes.add(gene_getter.group(2)); // store only the geneID 
	}

	return ret;
    }
    
    /**
      * Return True if the given gene is associated to the rectangle
      */
    public boolean has_gene(String gene) {
	return genes.contains(gene);
    }
    
    /**
      * Return the list of genes associated to rectangles
      */
    public List<String> get_genes() {
	return new ArrayList<String>(genes);
    }

    /**
      * Make a rectangle printable
      */
    @Override
    public String to_str() {
	String ret = super.to_str();

	if (!genes.isEmpty()) {
	    for (String gene : genes)
		ret += (gene + " ");
	    ret += "\n";
	}

	return ret;
    }
}
