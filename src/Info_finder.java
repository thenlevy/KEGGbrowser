import java.util.List;
import java.util.ArrayList;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Info_finder {
    /** Find the reactions that involves a given gene in a given pathway
     * @param gene the gene to be searched
     * @param kgml_file a reader that reads from the kgml file discribing the pathway
     * @retrun the list of the IDs of the reactions that involves the gene
     */
    public static List<String> gene_in_pathway(String gene, File kgml_file) {
	List<String> ret = new ArrayList();
	try {
	    DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbF.newDocumentBuilder();
	    Document kgml_doc = db.parse(kgml_file);
	    NodeList entries = kgml_doc.getElementsByTagName("entry");
	    for (int i = 0; i < entries.getLength(); i++) {
		Node current_entry = entries.item(i);
		Element entry_elmt = (Element) current_entry;
		if (entry_elmt.getAttribute("name").matches("(.*)" + gene + "(.*)")) {
		    ret.add(entry_elmt.getAttribute("reaction"));
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
	return ret;
    }

    public static void main(String[] args) {
	File test_file = new File("test.kgml");
	List<String> ret = gene_in_pathway("eco:b0630", test_file);
	for (String s : ret) {
	    System.out.println(s);
	}

    }
	
}
