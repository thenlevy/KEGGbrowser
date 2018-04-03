import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Component;

public class GUI extends JFrame {


    private final static int LEFT_WIDTH = 500;
    private final static int RIGHT_WIDTH = 300;
    private final static int GEN_HEIGHT = 250;
    private final static int PATHWAY_HEIGHT = 350;
    private final static int GENE_INFO_HEIGHT = GEN_HEIGHT * 9/16;
    private final static int REACTION_INFO_HEIGHT = GEN_HEIGHT * 2/3;

    // Genome browser
    private JPanel gen_global_panel;

    private JPanel gen_left_panel;
    private JPanel gen_left_menu;
    private JTextField gen_species_field;
    private final static int GEN_SPECIES_FIELD_LENGTH = 4;
    private JTextField gen_genID_field;
    private final static int GEN_GENID_FIELD_LENGTH = 6;
    private JButton gen_search_btn;
    private JScrollPane genome_display;

    private JPanel gen_right_panel;
    private JPanel gen_right_menu;
    private JScrollPane gene_information;
    private JScrollPane involved_in_reactions;

    // Pathway browser
    private JPanel pathway_global_panel;

    private JPanel pathway_left_panel;
    private JPanel pathway_left_menu;
    private JTextField pathway_species_field;
    private final static int PATHWAY_SPECIES_FIELD_LENGTH = 4;
    private JTextField pathway_mapID_field;
    private final static int PATHWAY_MAPID_FIELD_LENGTH = 6;
    private JButton pathway_search_btn;
    private JScrollPane pathway_display;

    private JPanel pathway_right_panel;
    private JPanel pathway_right_menu;
    private JButton pathway_image_btn;
    private JScrollPane reaction_information;
    private JScrollPane involves_gens;

    public GUI() {

    //Genome browser

    //=============================================================================================
    // Genome browser left menu
    gen_left_menu = new JPanel();
    gen_left_menu.setLayout(new BoxLayout(gen_left_menu, BoxLayout.LINE_AXIS));
    gen_left_menu.setAlignmentX(Component.LEFT_ALIGNMENT);
    gen_left_menu.add(new JLabel("Genome Browser"));
    gen_left_menu.add(Box.createHorizontalGlue());
    gen_left_menu.add(new JLabel("Species"));

    gen_species_field = new JTextField(GEN_SPECIES_FIELD_LENGTH);
    gen_species_field.setColumns(GEN_SPECIES_FIELD_LENGTH);
    gen_species_field.setMaximumSize(gen_species_field.getPreferredSize());
    gen_left_menu.add(gen_species_field);

    gen_left_menu.add(new JLabel("Gene ID"));

    gen_genID_field = new JTextField(GEN_GENID_FIELD_LENGTH);
    gen_genID_field.setColumns(GEN_GENID_FIELD_LENGTH);
    gen_genID_field.setMaximumSize(gen_genID_field.getPreferredSize());
    gen_left_menu.add(gen_genID_field);
    gen_left_menu.add(Box.createHorizontalGlue());

    gen_search_btn = new JButton("Search");
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
    genome_display = new JScrollPane();
    gen_left_panel.add(genome_display);

    gen_left_panel.setPreferredSize(new Dimension(LEFT_WIDTH, GEN_HEIGHT));
    gen_left_panel.setMaximumSize(new Dimension(LEFT_WIDTH, GEN_HEIGHT));
    //=============================================================================================


    //=============================================================================================
    //Genome browser right panel
    gen_right_panel = new JPanel();
    gen_right_panel.setLayout(new BoxLayout(gen_right_panel, BoxLayout.PAGE_AXIS));
    gen_right_panel.setAlignmentY(Component.TOP_ALIGNMENT);
    gen_right_panel.add(new JLabel("Gene Information"));

    gene_information = new JScrollPane();
    gen_right_panel.add(gene_information);
    gene_information.setPreferredSize(new Dimension(RIGHT_WIDTH, GENE_INFO_HEIGHT));
    gene_information.setMaximumSize(new Dimension(gene_information.getMaximumSize().width,
						  GENE_INFO_HEIGHT));

    gen_right_panel.add(new JLabel("Involved in reaction(s)"));

    involved_in_reactions = new JScrollPane();
    gen_right_panel.add(involved_in_reactions);

    gen_right_panel.setPreferredSize(new Dimension(RIGHT_WIDTH, GEN_HEIGHT));
    gen_right_panel.setMaximumSize(new Dimension(RIGHT_WIDTH, GEN_HEIGHT));
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

    pathway_species_field = new JTextField(PATHWAY_SPECIES_FIELD_LENGTH);
    pathway_species_field.setColumns(PATHWAY_SPECIES_FIELD_LENGTH);
    pathway_species_field.setMaximumSize(pathway_species_field.getPreferredSize());
    pathway_left_menu.add(pathway_species_field);

    pathway_left_menu.add(new JLabel("Map ID"));

    pathway_mapID_field = new JTextField(PATHWAY_MAPID_FIELD_LENGTH);
    pathway_mapID_field.setColumns(PATHWAY_MAPID_FIELD_LENGTH);
    pathway_mapID_field.setMaximumSize(pathway_mapID_field.getPreferredSize());
    pathway_left_menu.add(pathway_mapID_field);
    pathway_left_menu.add(Box.createHorizontalGlue());

    pathway_search_btn = new JButton("Search");
    pathway_left_menu.add(pathway_search_btn);

    pathway_left_menu.setPreferredSize(new Dimension(LEFT_WIDTH,
						 gen_search_btn.getPreferredSize().height));
    pathway_left_menu.setMaximumSize(new Dimension(LEFT_WIDTH,
						 gen_search_btn.getPreferredSize().height));
    //=============================================================================================
    //Pathway browser left panel
    pathway_left_panel = new JPanel();
    pathway_left_panel.setLayout(new BoxLayout(pathway_left_panel, BoxLayout.PAGE_AXIS));
    pathway_left_panel.setAlignmentY(Component.TOP_ALIGNMENT);
    pathway_left_panel.add(pathway_left_menu);
    pathway_display = new JScrollPane();
    pathway_left_panel.add(pathway_display);

    pathway_left_panel.setPreferredSize(new Dimension(LEFT_WIDTH, PATHWAY_HEIGHT));
    pathway_left_panel.setMaximumSize(new Dimension(LEFT_WIDTH, PATHWAY_HEIGHT));
    //=============================================================================================

    pathway_global_panel = new JPanel();
    pathway_global_panel.setLayout(new BoxLayout(pathway_global_panel, BoxLayout.LINE_AXIS));
    pathway_global_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
    pathway_global_panel.add(pathway_left_panel);
    pathway_global_panel.add(Box.createHorizontalGlue());
    


    Container cp = getContentPane();
    cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
    cp.add(gen_global_panel);
    cp.add(Box.createVerticalGlue());
    cp.add(pathway_global_panel);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("KEGG browser");
    setSize(800, 600);
    setVisible(true);
    }

    public static void main(String[] args) {

	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
		    new GUI();
		}
	    });
    }
}

