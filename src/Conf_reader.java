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

    public static void main(String arg[]) {
	File org_file = new File("test_org.conf");
	File map_file = new File("test_map.conf");
	List<String> test = get_reaction("b0688", org_file, map_file);
	for (String s : test) {
	    System.out.println(s);
	}
    }

    public static List<String> get_reaction(String gene_interest, File org_file, File map_file) {
	List<String> ret = new ArrayList<String>();
	Hashtable<Integer, Map_rectangle> hash_rectangles = new Hashtable<Integer, Map_rectangle>();

	try {
	    // Read the map conf file and extrcts all the rectangle informations
	    BufferedReader map_reader = new BufferedReader(new FileReader(map_file));
	    String line = "";
	    while ((line = map_reader.readLine()) != null) {
		if (line.startsWith("rect")) {
		    Map_rectangle rect = Map_rectangle.from_line(line);
		    hash_rectangles.put(rect.get_hash(), rect);
		}
	    }

	    // Read the org conf file and look for the rectangles that involves the gene
	    // of interest
	    BufferedReader org_reader = new BufferedReader(new FileReader(org_file));
	    while ((line = org_reader.readLine()) != null) {
		if (line.contains(gene_interest)) {
		    Org_rectangle rect = Org_rectangle.from_line(line);
		    Map_rectangle map_rect = hash_rectangles.get(rect.get_hash());
		    for (String reaction : map_rect.get_reactions())
			ret.add(reaction);
		}
	    }

	} catch (Exception e) {
	    System.out.println(e);
	}
	return ret;
    }

		
}


class Conf_rectangle {
    private int left;
    private int top;
    private int right;
    private int bot;
    private List<String> genes;

    private Conf_rectangle(int l, int t, int r, int b) {
	left = l;
	top = t;
	right = r;
	bot = b;
    }

    protected Conf_rectangle(Conf_rectangle cr) {
	left = cr.left;
	top = cr.top;
	right = cr.right;
	bot = cr.bot;
    }

    public boolean is_in(int x, int y) {
	return (x >= left && x <= right && y >= top && y <= bot);
    }

    /** Reads the line a conf file describing a recantgle and returns the rectangle
     * @param s the line describing the rectangle
     * @return the rectangle discribed by that line
     **/
    static Conf_rectangle read_coordonates(String s) {
	Pattern first_coordonate = Pattern.compile("\\(\\b*[0-9]+\\b*,");
	Pattern second_coordonate = Pattern.compile(",\\b*[0-9]+\b*\\)");
	Matcher x_getter = first_coordonate.matcher(s);
	Matcher y_getter = second_coordonate.matcher(s);

	x_getter.find();
	// x_getter.group() is "(xxxx," so we want to take out first and last character
	int left = Integer.parseInt(s.substring(x_getter.start() + 1, x_getter.end() - 1));
	x_getter.find();
	int right = Integer.parseInt(s.substring(x_getter.start() + 1, x_getter.end() - 1));

	y_getter.find();
	// y_getter.group is ",xxxx)" so we want to take out first and last character
	int top = Integer.parseInt(s.substring(y_getter.start() + 1, y_getter.end() - 1));
	y_getter.find();
	int bot = Integer.parseInt(s.substring(y_getter.start() + 1, y_getter.end() - 1));

	return new Conf_rectangle(left, top, right, bot);
    }

    public int get_hash() {
	// Unique, assuming that (left + right) / 2 < 100000
	return  100000 * (top + bot) / 2 + (left + right) / 2;
    }

    public String to_str() {
	return ("(" + left + "," + top + ")(" + right + "," + bot + ")");
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
    private List<String> genes;

    private Org_rectangle(Conf_rectangle cr) {
	super(cr);
	genes = new ArrayList<String>();
    }

    public static Org_rectangle from_line(String s) {
	Org_rectangle ret = new Org_rectangle(Conf_rectangle.read_coordonates(s));

	Pattern gene = Pattern.compile("[a-zA-Z]+\\:[a-zA-Z0-9]+");
	Matcher gene_getter = gene.matcher(s);
	while (gene_getter.find()) {
	    ret.genes.add(gene_getter.group());
	}

	return ret;
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
