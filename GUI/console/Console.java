package console;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import RNG.RNG;
import javafx.scene.control.TextArea;

public class Console extends TextArea{
	
	private static Console instance = new Console();
	private static Map<String, Set<String>> messages;
	
	private Console() {
		messages = new HashMap<>();
		//FIXME solo anda el font weight, lo demás se ignora
		setStyle("-fx-control-inner-background: BLACK; "
				+ "-fx-font-family: Consolas; "
				+ "-fx-font-fill: BLACK;"
				+ "-fx-font-weight: BOLD ");
		setEditable(false);
		setMouseTransparent(true);// Con esto en false si se hace click en la consola despues no se le pueden agregar mensajes
		setFocusTraversable(false);
		setWrapText(true);
		loadMessages();
	}
	
	public static Console getInstance() {
		return instance;
	}
	
	//TODO test
	//TODO agregar una forma de usar color
	public void addText(String type) {
		String text = RNG.getRandom(messages.get(type));
		this.appendText(text + "\n");
	}
	
	public void addText(String type, String... extraText){
		String text = RNG.getRandom(messages.get(type));
		
		//Reemplazar tags
		for (int i = 0; i < extraText.length; i++){
			String tag = "/" + i + "/";
			if(text.contains(tag)){
				text = text.replace(tag, extraText[i]);
			}
		}
		this.appendText(text + "\n");
	}
	
	//TODO pensar otro nombre
	public void addLiteralText(String text) {
		appendText(text + "\n");
	}
	
	//FIXME al scrollear despues de haber llegado a un limite la consola queda quieta pero el valor sigue cambiando, 
	// y cuando se quiere volver a scrollear para el otro lado hay que mantener el boton un rato
	public void scroll(int value) {
		setScrollTop(getScrollTop() + value);
	}
	
	private static void loadMessages() {
		String DOC_PATH = "../RogueWorld/assets/Data/Messages.xml";
		Document messagesDoc = null;
		
		XPath XPath = XPathFactory.newInstance().newXPath();
		
		try {
			messagesDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(DOC_PATH));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			System.out.println(e);
		}
		
		try {
			/* Se buscan todos mensajes, agrupados por tipo */
			XPathExpression expr = XPath.compile("//Message");
			NodeList list = (NodeList) expr.evaluate(messagesDoc, XPathConstants.NODESET);
			
			/* Se iteran todos los grupos (nodos) encontrados */
			for(int i = 0; i < list.getLength(); i++) {
				Node messageNode = list.item(i);
				String[] text = messageNode.getTextContent().split("\n");
				
				/* Todos los mensajes del mismo tipo se guardan en un Set*/
				Set<String> group = new HashSet<>();
				for(int j = 2; j < text.length-1; j++) {
					group.add(text[j].trim());
				}
				messages.put(text[1].trim(), group);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
