import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.fit.cssbox.swingbox.BrowserPane;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.List;
import java.io.File;

/**
 * Graphical interface for KEGGbrowser
 */
public class GUI extends JFrame {
    private Browser browser;

    private final static int LEFT_WIDTH = 500; // Width of the left part
    private final static int RIGHT_WIDTH = 300; // Width of the right part 
    private final static int GEN_HEIGHT = 250;  // Height of the 'genome browser'
    private final static int PATHWAY_HEIGHT = 350; // Height of the 'pathway browser'
    private final static int GENE_INFO_HEIGHT = GEN_HEIGHT * 9/16;
    // Height of the 'Gene information' panel

    private final static int REACTION_INFO_HEIGHT = PATHWAY_HEIGHT * 4/10;
    // Height of the 'Reaction information' panel

    // Genome browser componenet
    private JPanel gen_global_panel; // Whole genome browser

    private JPanel gen_left_panel; // left part of the genome browser
    private JPanel gen_left_menu; 
    private JTextField gen_species_field;
    private final static int GEN_SPECIES_FIELD_LENGTH = 4;
    private JTextField gen_genID_field;
    private final static int GEN_GENID_FIELD_LENGTH = 6;
    private JButton gen_search_btn;
    private Genome_display genome_display;

    private JPanel gen_right_panel; // right part of the genome browser
    private JPanel gen_right_menu;
    private Gene_info_display gene_information;
    private Gene_reaction_menu involved_in_reactions;

    // Pathway browser component
    private JPanel pathway_global_panel; // Whole pathway browser

    private JPanel pathway_left_panel; // left part of the pathway browser
    private JPanel pathway_left_menu;
    private JTextField pathway_species_field;
    private final static int PATHWAY_SPECIES_FIELD_LENGTH = 4;
    private JTextField pathway_mapID_field;
    private final static int PATHWAY_MAPID_FIELD_LENGTH = 6;
    private JButton pathway_search_btn;
    private Pathway_display pathway_display;

    private JPanel pathway_right_panel;
    private JPanel pathway_right_menu;
    private JButton pathway_image_btn;
    private Reaction_info_display reaction_information;
    private JScrollPane involves_gens;

    public GUI(Browser b) {
	browser = b;

	//Genome browser

	//=============================================================================================
	// Genome browser left menu
	gen_left_menu = new JPanel();
	gen_left_menu.setLayout(new BoxLayout(gen_left_menu, BoxLayout.LINE_AXIS));
	gen_left_menu.setAlignmentX(Component.LEFT_ALIGNMENT);
	gen_left_menu.add(new JLabel("Genome Browser"));
	gen_left_menu.add(Box.createHorizontalGlue());
	gen_left_menu.add(new JLabel("Species"));


	gen_species_field = new Gen_species_field();
	gen_left_menu.add(gen_species_field);
	gen_left_menu.add(new JLabel("Gene ID"));

	gen_genID_field = new Gen_genID_field();
	gen_left_menu.add(gen_genID_field);
	gen_left_menu.add(Box.createHorizontalGlue());

	gen_search_btn = new Gen_search_button();
	gen_left_menu.add(gen_search_btn);

	gen_left_menu.setPreferredSize(new Dimension(LEFT_WIDTH,
						    gen_search_btn.getPreferredSize().height));
	gen_left_menu.setMaximumSize(new Dimension(LEFT_WIDTH,
						    gen_search_btn.getPreferredSize().height));
	//=============================================================================================

	//=============================================================================================
	//Genome browser left panel
	gen_left_panel = new JPanel();
	gen_left_panel.setLayout(new BoxLayout(gen_left_panel, BoxLayout.PAGE_AXIS));
	gen_left_panel.setAlignmentY(Component.TOP_ALIGNMENT);
	gen_left_panel.add(gen_left_menu);
	genome_display = new Genome_display();
	gen_left_panel.add(genome_display);

	gen_left_panel.setPreferredSize(new Dimension(LEFT_WIDTH, GEN_HEIGHT));
	//gen_left_panel.setMaximumSize(new Dimension(LEFT_WIDTH, GEN_HEIGHT));
	//=============================================================================================


	//=============================================================================================
	//Genome browser right panel
	gen_right_panel = new JPanel();
	gen_right_panel.setLayout(new BoxLayout(gen_right_panel, BoxLayout.PAGE_AXIS));
	gen_right_panel.setAlignmentY(Component.TOP_ALIGNMENT);
	gen_right_panel.add(new JLabel("Gene Information"));

	gene_information = new Gene_info_display();
	gen_right_panel.add(gene_information);

	gen_right_panel.add(new JLabel("Involved in reaction(s)"));

	involved_in_reactions = new Gene_reaction_menu();
	gen_right_panel.add(involved_in_reactions);

	gen_right_panel.setPreferredSize(new Dimension(RIGHT_WIDTH, GEN_HEIGHT));
	//gen_right_panel.setMaximumSize(new Dimension(RIGHT_WIDTH, GEN_HEIGHT));
	//=============================================================================================
	//Genome browser global

	gen_global_panel = new JPanel();
	gen_global_panel.setLayout(new BoxLayout(gen_global_panel, BoxLayout.LINE_AXIS));
	gen_global_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	gen_global_panel.add(gen_left_panel);
	gen_global_panel.add(Box.createHorizontalGlue());
	gen_global_panel.add(gen_right_panel);

	//=============================================================================================
	//Pathway browser left menu
	pathway_left_menu = new JPanel();
	pathway_left_menu.setLayout(new BoxLayout(pathway_left_menu, BoxLayout.LINE_AXIS));
	pathway_left_menu.setAlignmentX(Component.LEFT_ALIGNMENT);

	pathway_left_menu.add(new JLabel("Pathway Browser"));
	pathway_left_menu.add(Box.createHorizontalGlue());
	pathway_left_menu.add(new JLabel("Species"));

	pathway_species_field = new Pathway_species_field();
	pathway_left_menu.add(pathway_species_field);

	pathway_left_menu.add(new JLabel("Map ID"));

	pathway_mapID_field = new Pathway_mapID_field();
	pathway_left_menu.add(pathway_mapID_field);
	pathway_left_menu.add(Box.createHorizontalGlue());

	pathway_search_btn = new Pathway_search_button();
	pathway_left_menu.add(pathway_search_btn);

	pathway_left_menu.setPreferredSize(new Dimension(LEFT_WIDTH,
							pathway_search_btn.getPreferredSize().height));
	pathway_left_menu.setMaximumSize(new Dimension(LEFT_WIDTH,
						       pathway_search_btn.getPreferredSize().height));
	//=============================================================================================
	//Pathway browser left panel
	pathway_left_panel = new JPanel();
	pathway_left_panel.setLayout(new BoxLayout(pathway_left_panel, BoxLayout.PAGE_AXIS));
	pathway_left_panel.setAlignmentY(Component.TOP_ALIGNMENT);
	pathway_left_panel.add(pathway_left_menu);
	pathway_display = new Pathway_display();
	pathway_left_panel.add(pathway_display);

	pathway_left_panel.setPreferredSize(new Dimension(LEFT_WIDTH, PATHWAY_HEIGHT));
	//pathway_left_panel.setMaximumSize(new Dimension(LEFT_WIDTH, PATHWAY_HEIGHT));
	//=============================================================================================
	//Pathway browser right menu
	pathway_right_menu = new JPanel();
	pathway_right_menu.setLayout(new BoxLayout(pathway_right_menu, BoxLayout.LINE_AXIS));
	pathway_right_menu.setAlignmentX(Component.LEFT_ALIGNMENT);

	pathway_right_menu.add(new JLabel("Reaction information"));
	pathway_right_menu.add(Box.createHorizontalGlue());
	pathway_image_btn = new JButton("Image");
	pathway_right_menu.add(pathway_image_btn);
	pathway_right_menu.setPreferredSize(new Dimension(RIGHT_WIDTH,
							pathway_image_btn.getPreferredSize().height));
	pathway_right_menu.setMaximumSize(new Dimension(RIGHT_WIDTH,
							pathway_image_btn.getPreferredSize().height));
	//=============================================================================================
	//Pathway browser right panel
	pathway_right_panel = new JPanel();
	pathway_right_panel.setLayout(new BoxLayout(pathway_right_panel, BoxLayout.PAGE_AXIS));
	pathway_right_panel.setAlignmentY(Component.TOP_ALIGNMENT);

	pathway_right_panel.add(pathway_right_menu);
	reaction_information = new Reaction_info_display();
	pathway_right_panel.add(reaction_information);
	pathway_right_panel.add(new JLabel("Involves gene(s)"));

	involves_gens = new JScrollPane();
	pathway_right_panel.add(involves_gens);
	pathway_right_panel.setPreferredSize(new Dimension(RIGHT_WIDTH, PATHWAY_HEIGHT));
	//pathway_right_panel.setMaximumSize(new Dimension(RIGHT_WIDTH, PATHWAY_HEIGHT));
	//=============================================================================================
	// Pathway browser global

	pathway_global_panel = new JPanel();
	pathway_global_panel.setLayout(new BoxLayout(pathway_global_panel, BoxLayout.LINE_AXIS));
	pathway_global_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	pathway_global_panel.add(pathway_left_panel);
	pathway_global_panel.add(Box.createHorizontalGlue());
	pathway_global_panel.add(pathway_right_panel);



	Container cp = getContentPane();
	cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
	cp.add(gen_global_panel);
	cp.add(Box.createVerticalGlue());
	cp.add(pathway_global_panel);

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("KEGG browser");
	setSize(800, 600);
	setVisible(true);

	refresh_gui();
	}

    private void refresh_gui() {
	gen_species_field.setText(browser.get_gen_species());
	gen_genID_field.setText(browser.get_gen_genID());
	pathway_species_field.setText(browser.get_pathway_species());
	pathway_mapID_field.setText(browser.get_pathway_mapID());

	repaint();
    }

    private class Gen_species_field extends JTextField{
	public Gen_species_field() {
	    super(GEN_SPECIES_FIELD_LENGTH);
	    setColumns(GEN_SPECIES_FIELD_LENGTH);
	    setMaximumSize(getPreferredSize());
	}
	
    }

    private class Gen_genID_field extends JTextField {
	public Gen_genID_field() {
	    super(GEN_GENID_FIELD_LENGTH);
	    setColumns(GEN_GENID_FIELD_LENGTH);
	    setMaximumSize(getPreferredSize());
	}
    }

    private class Gen_search_button extends JButton implements ActionListener {
	public Gen_search_button() {
	    super("Search");
	    addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	    browser.set_gen_species(gen_species_field.getText());
	    browser.set_gen_genID(gen_genID_field.getText());
	    browser.gen_search();
	    refresh_gui();
	}
    }

    private class Pathway_species_field extends JTextField {
	public Pathway_species_field() {
	    super(PATHWAY_SPECIES_FIELD_LENGTH);
	    setColumns(PATHWAY_SPECIES_FIELD_LENGTH);
	    setMaximumSize(getPreferredSize());
	}
    }

    private class Pathway_mapID_field extends JTextField {
	public Pathway_mapID_field() {
	    super(PATHWAY_MAPID_FIELD_LENGTH);
	    setColumns(PATHWAY_MAPID_FIELD_LENGTH);
	    setMaximumSize(getPreferredSize());
	}
    }

    private class Pathway_search_button extends JButton implements ActionListener {
	public Pathway_search_button() {
	    super("Search");
	    addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	    browser.set_pathway_species(pathway_species_field.getText());
	    browser.set_pathway_mapID(pathway_mapID_field.getText());
	    browser.pathway_search();
	    
	}
    }

    private class Genome_display extends JScrollPane {
	private BrowserPane content;
	public Genome_display() {
	    super();
	    content = new BrowserPane();
	    setViewportView(content);
	}

	public void update_page(String new_url) {
	    try {
		content.setPage(new URL(new_url));
	    } catch(MalformedURLException e) {
		content.setText(e.toString());
	    } catch(IOException e) {
		content.setText(e.toString());
	    }
	}
    }

    protected class Info_display extends JScrollPane {
	private JTextPane txt;
	public Info_display() {
	    super();
	    setPreferredSize(new Dimension(RIGHT_WIDTH, GENE_INFO_HEIGHT));
	    //setMaximumSize(new Dimension(getMaximumSize().width,
	    //GENE_INFO_HEIGHT));
	    txt = new JTextPane();
	    setViewportView(txt);
	}

	protected void set_text(String info) {
	    txt.setText(info);
	    repaint();
	}
    }

    private class Gene_info_display extends Info_display {
	public Gene_info_display() {
	    super();
	    setPreferredSize(new Dimension(RIGHT_WIDTH, GENE_INFO_HEIGHT));
	}
    }

    private class Reaction_info_display extends Info_display {
	public Reaction_info_display() {
	    super();
	    setPreferredSize(new Dimension(RIGHT_WIDTH, REACTION_INFO_HEIGHT));
	}
    }
	

    private class Pathway_display extends JScrollPane {
	private ImageIcon ii;
	private JLabel image_label;

	public Pathway_display() {
	    super();
	    ii = new ImageIcon();
	    image_label = new JLabel(ii);
	    setViewportView(image_label);
	}

	public void change_img(File img_file) {
	    try {
		ii = new ImageIcon(ImageIO.read(img_file));
		image_label = new JLabel(ii);
		setViewportView(image_label);
	    } catch (Exception e) {
		System.out.println(e);
	    }
	    repaint();
	}
    }
	

    private class Gene_reaction_menu extends JScrollPane implements ListSelectionListener {
	private JList reaction_jlist;
	public Gene_reaction_menu() {
	    super();
	    reaction_jlist = new JList();
	    reaction_jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    reaction_jlist.addListSelectionListener(this);
	    setViewportView(reaction_jlist);
	}

	public void set_list(List<String> lst) {
	    String[] array = new String[lst.size()];
	    array = lst.toArray(array);
	    reaction_jlist.setListData(array);
	    repaint();
	}

	public void valueChanged(ListSelectionEvent e) {
	    if (!e.getValueIsAdjusting())
		browser.select_reaction(reaction_jlist.getSelectedIndex());
	}
    }

    public void set_gene_reaction_menu(List<String> lst) {
	involved_in_reactions.set_list(lst);
    }

    public void set_gene_info_text(String new_text) {
	    gene_information.set_text(new_text);
    }

    public void set_reaction_info(String new_text) {
	reaction_information.set_text(new_text);
    }

    public void set_browser_url(String new_url) {
	genome_display.update_page(new_url);
    }

    public void update_pathway_img(File img) {
	pathway_display.change_img(img);
    }


}

