package virtualEsp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parkingmanagerservice.MessageType;
import parkingmanagerservice.messages;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

public class MessagesParser {
    private DocumentBuilderFactory dataBuilderFactory;
    private DocumentBuilder dataBuilder;
    private Document data;
    private Vector<messages> messagesList;


    public MessagesParser(String  file) {
        try {
            dataBuilderFactory = DocumentBuilderFactory.newInstance();
            dataBuilderFactory.setIgnoringElementContentWhitespace(true);
            dataBuilder = dataBuilderFactory.newDocumentBuilder();
            data = dataBuilder.parse(this.getClass().getResourceAsStream(file));
            messagesList = new Vector<messages>();
            parse();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void parse() {
        data.normalizeDocument();
        //data.getDocumentElement().normalize();
        NodeList msgs = data.getDocumentElement().getElementsByTagName("MESSAGE"); //.getChildNodes();
        try {
            for (int i = 0; i < msgs.getLength(); i++) {
                Node msg = msgs.item(i);
                msg.normalize();
                if (msg.getNodeType() == Node.ELEMENT_NODE && msg.getNodeName() == "MESSAGE"){
                    int id = Integer.parseInt(((Element) msg).getElementsByTagName("ID").item(0).getTextContent());
                    MessageType type = MessageType.valueOf(((Element) msg).getElementsByTagName("TYPE").item(0).getTextContent());
                    Date date = new Date(); // irrelevant because it will be decided upon creating a new message
                    int counter = Integer.parseInt(((Element) msg).getElementsByTagName("COUNTER").item(0).getTextContent());
                    messages formatted_msg = new messages(id, date, type, counter);
                    messagesList.add(formatted_msg);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }


    public Vector<messages> getMessagesList() {
        return messagesList;
    }

}
