import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Info_finder {
    /** Find the reaction that involves a given gene in a given pathway
     * @param gene the gene to be searched
     * @param kgml_file a reader that reads from the kgml file discribing the pathway
     * @retrun the ID of the reaction that involves the gene
     */
    public static String gene_in_pathway(String gene, File kgml_file) {
	String ret = "";
	try {
	    DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbF.newDocumentBuilder();
	    Document kgml_doc = db.parse(kgml_file);
	    ret = kgml_doc.getDocumentElement().getNodeName();
	} catch (Exception e) {
	    System.out.println(e);
	}
	return ret;
    }

    public static void main(String[] args) {
	File test_file = new File("test.kgml");
	String s = gene_in_pathway("", test_file);
	System.out.println(s);

    }
	
}
