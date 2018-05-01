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
    private Hashtable<Integer, Org_rectangle> hash_org;

    public static void main(String arg[]) {
	List<String> test = get_all_reactions("eco", "b0630");
	for (String s : test) {
	    System.out.println(s);
	}
    }

    public static List<String> get_reaction(File org_file, File map_file, String gene_id) {
	Conf_reader cr = new Conf_reader(org_file, map_file);
	return cr.get_reaction(gene_id);
    }

    public static List<String> get_all_reactions(String specie, String gene_id) {
	List<File[]> pathways = FileDescrip.get_pathway_files(specie, gene_id);
	List<String> ret = new ArrayList<String>();
	for (File p[] : pathways) {
	    File org_file = p[0];
	    File map_file = p[1];
	    ret.addAll(get_reaction(org_file, map_file, gene_id));
	}
	return ret;
    }

    public Conf_reader(File org_f, File map_f) {
	org_file = org_f;
	map_file = map_f;

	make_hash_map();
	make_org_map();
    }

    /** Read the map conf file and create the Map_rectangle and store them in the Hashtable hash_map
     */
    private void make_hash_map() {
	hash_map = new Hashtable<Integer, Map_rectangle>();
	try {
	    BufferedReader map_reader = new BufferedReader(new FileReader(map_file));
	    String line = "";
	    while ((line = map_reader.readLine()) != null) {
		if (line.startsWith("rect")) {
		    Map_rectangle rect = Map_rectangle.from_line(line);
		    hash_map.put(rect.get_hash(), rect);
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    /** Read the org conf file and create the Org_rectangle and store them in the Hashtable hash_org
     */
    private void make_org_map() {
	hash_org = new Hashtable<Integer, Org_rectangle>();
	try {
	    BufferedReader org_reader = new BufferedReader(new FileReader(org_file));
	    String line = "";
	    while ((line = org_reader.readLine()) != null) {
		if (line.startsWith("rect")) {
		    Org_rectangle rect = Org_rectangle.from_line(line);
		    hash_org.put(rect.get_hash(), rect);
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    public Conf_rectangle find_rect(int x, int y) {
	for (Conf_rectangle cr: hash_map.values()) {
	    if (cr.is_in(x, y)) {
		return new Conf_rectangle(cr);
	    }
	}
	return new Conf_rectangle();
    }

    public List<String> get_reaction(String gene_interest) {
	List<String> ret = new ArrayList<String>();
	
	for (Org_rectangle rect : hash_org.values()) {
	    if (rect.has_gene(gene_interest)) {
		Map_rectangle map_rect = hash_map.get(rect.get_hash());
		for (String reaction : map_rect.get_reactions()) {
		    ret.add(reaction);
		}
	    }
	}
	return ret;
    }
}



class Map_rectangle extends Conf_rectangle {
    private HashSet<String> k_nums;
    private HashSet<String> r_nums;

    private Map_rectangle(Conf_rectangle cr) {
	super(cr);
	k_nums = new HashSet<String>();
	r_nums = new HashSet<String>();
    }
	 
    public static Map_rectangle from_line(String s) {
	Map_rectangle ret = new Map_rectangle(Conf_rectangle.read_coordonates(s));

	Pattern knum = Pattern.compile("K[0-9]{5}");
	Matcher knum_getter = knum.matcher(s);
	while (knum_getter.find()) {
	    ret.k_nums.add(knum_getter.group());
	}

	Pattern rnum = Pattern.compile("R[0-9]{5}");
	Matcher rnum_getter = rnum.matcher(s);
	while (rnum_getter.find()) {
	    ret.r_nums.add(rnum_getter.group());
	}
	return ret;
    }

    public boolean has_reacton(String react) {
	return r_nums.contains(react);
    }

    @Override
    public String to_str() {
	String ret = super.to_str();

	if (!k_nums.isEmpty()) {
	    ret += "\n";
	    for (String knum : k_nums)
		ret += (knum + " ");
	}

	if (!r_nums.isEmpty()) {
	    ret += "\n";
	    for (String rnum : r_nums)
		ret += (rnum + " ");
	}

	return ret;
    }

    public HashSet<String> get_reactions() {
	return r_nums;
    }
}

class Org_rectangle extends Conf_rectangle {
    private HashSet<String> genes;

    private Org_rectangle(Conf_rectangle cr) {
	super(cr);
	genes = new HashSet<String>();
    }

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

    public boolean has_gene(String gene) {
	return genes.contains(gene);
    }

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
