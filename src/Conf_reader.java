import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/** Reads and interpret the informations contained in a .conf file
 */
public class Conf_reader {

    private List<Conf_rectangle> rectangles;

    public Conf_reader(File conf_file) {
	rectangles = new ArrayList<Conf_rectangle>();
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(conf_file));
	    String line = "";
	    while ( (line = reader.readLine()) != null) {
		if (line.startsWith("rect")) {
		    rectangles.add(Conf_rectangle.read_rect(line));
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    public static void main(String arg[]) {
	File test_file = new File("test.conf");
	Conf_reader test = new Conf_reader(test_file);
	for (Conf_rectangle cr : test.rectangles) {
	    System.out.println(cr.to_str());
	}
    }
}


class Conf_rectangle {
    private int left;
    private int top;
    private int right;
    private int bot;
    private List<String> k_nums;
    private List<String> genes;
    private List<String> r_nums;

    private Conf_rectangle(int l, int t, int r, int b) {
	left = l;
	top = t;
	right = r;
	bot = b;
    }

    public boolean is_in(int x, int y) {
	return (x >= left && x <= right && y >= top && y <= bot);
    }

    /** Reads the line a conf file describing a recantgle and returns the rectangle
     * @param s the line describing the rectangle
     * @return the rectangle discribed by that line
     **/
    static Conf_rectangle read_rect(String s) {
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

	Conf_rectangle ret = (new Conf_rectangle(left, top, right, bot));

	ret.k_nums = new ArrayList<String>();
	Pattern knum = Pattern.compile("K[0-9]{5}");
	Matcher knum_getter = knum.matcher(s);
	while (knum_getter.find()) {
	    ret.k_nums.add(knum_getter.group());
	}

	ret.genes = new ArrayList<String>();
	Pattern gene = Pattern.compile("[a-zA-Z]+\\:[a-zA-Z0-9]+");
	Matcher gene_getter = gene.matcher(s);
	while (gene_getter.find()) {
	    ret.genes.add(gene_getter.group());
	}

	ret.r_nums = new ArrayList<String>();
	Pattern rnum = Pattern.compile("R[0-9]{5}");
	Matcher rnum_getter = rnum.matcher(s);
	while (rnum_getter.find()) {
	    ret.r_nums.add(rnum_getter.group());
	}

	return ret;
	    
    }


    public String to_str() {
	String ret = "";
	ret += ("(" + left + "," + top + ")(" + right + "," + bot + ")\n");
	for (String knum : k_nums)
	    ret += (knum + " ");
	ret += "\n";
	for (String gene : genes)
	    ret += (gene + " ");
	ret += "\n";
	for (String rnum : r_nums)
	    ret += (rnum + " ");
	ret += "\n";
	return ret;
    }
}
	 


