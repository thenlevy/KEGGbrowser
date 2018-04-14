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
	rectangles = new ArrayList();
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

	return (new Conf_rectangle(left, top, right, bot));
    }


    public String to_str() {
	return ("(" + left + "," + top + ")(" + right + "," + bot + ")");
    }
}
	



