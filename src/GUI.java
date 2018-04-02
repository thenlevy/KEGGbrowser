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

    // Genome browser
    private JPanel gen_global_panel;
    private final static int GEN_HIGHT = 250;
    private final static int PATHWAY_HIGHT = 350;

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
    private JTextField pathway_genID_field;
    private final static int PATHWAY_GENID_FIELD_LENGTH = 6;
    private JButton pathway_search_btn;

    private JPanel pathway_right_panel;
    private JPanel pathway_right_menu;
    private JButton pathway_image_btn;
    private JScrollPane reaction_information;
    private JScrollPane involves_gens;

    public GUI() {

    //Genome browser

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

    //Genome browser left panel
    gen_left_panel = new JPanel();
    gen_left_panel.setLayout(new BoxLayout(gen_left_panel, BoxLayout.PAGE_AXIS));
    gen_left_panel.setAlignmentY(Component.TOP_ALIGNMENT);
    gen_left_panel.add(gen_left_menu);
    genome_display = new JScrollPane();
    gen_left_panel.add(genome_display);

    gen_left_panel.setPreferredSize(new Dimension(LEFT_WIDTH, GEN_HIGHT));
    gen_left_panel.setMaximumSize(new Dimension(LEFT_WIDTH, GEN_HIGHT));


    gen_global_panel = new JPanel();
    gen_global_panel.setLayout(new BoxLayout(gen_global_panel, BoxLayout.LINE_AXIS));
    gen_global_panel.add(gen_left_panel);

    Container cp = getContentPane();
    cp.add(gen_global_panel);
    
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

