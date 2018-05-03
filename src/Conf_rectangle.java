import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Conf_rectangle {
    private int left = -1;
    private int top = -1;
    private int right = -1;
    private int bot = -1;

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

    public Conf_rectangle() {
    }

    public boolean is_in(int x, int y) {
	return (x >= left && x <= right && y >= top && y <= bot);
    }

    public boolean is_true_rectangle() {
	return left >= 0;
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

    public int get_left() {
	return left;
    }

    public int get_top() {
	return top;
    }

    public int get_right() {
	return right;
    }

    public int get_bot() {
	return bot;
    }
}
