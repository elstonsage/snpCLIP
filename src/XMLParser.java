package snpclip;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.net.URL;
import java.net.URISyntaxException;
import java.io.*;

public class XMLParser {
    String Version = "";
    String Change = "";
    String Download = "";
    Document dom;

        public XMLParser(){
        }

        public void ParseGeneral(URL FileName) {

                //parse the xml file and get the dom object
                parseXmlFile(FileName);

                //get each employee element and create a Employee object
                parseDocument();
        }
        private void parseXmlFile(URL F){
                //get the factory
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                try {

                        //Using factory get an instance of document builder
                        DocumentBuilder db = dbf.newDocumentBuilder();

                        //parse using builder to get DOM representation of the XML file
                        dom = db.parse(F.toURI().toString());


                }catch(ParserConfigurationException pce) {
                        pce.printStackTrace();
                }catch(SAXException se) {
                        se.printStackTrace();
                }catch(IOException ioe) {
                        ioe.printStackTrace();
                    }catch(URISyntaxException uoe){
                        uoe.printStackTrace();
                    }

        }
        private void parseDocument(){
                //get the root element
                Element docEle = dom.getDocumentElement();

                //get a nodelist of  elements
                NodeList nl = docEle.getElementsByTagName("version");
                if(nl != null && nl.getLength() > 0) {
                        for(int i = 0 ; i < nl.getLength();i++) {

                                //get the employee element
                                Element el = (Element)nl.item(i);
                                System.out.print(                                el.getTagName());
                                //get the Employee object
                                getVersion(el);

                                //add it to list
                                //myEmpls.add(e);
                        }
                }
                nl = docEle.getElementsByTagName("changelog_url");
                if(nl != null && nl.getLength() > 0) {
                        for(int i = 0 ; i < nl.getLength();i++) {

                                //get the employee element
                                Element el = (Element)nl.item(i);
                                //get the Employee object
                                getChange(el);

                                //add it to list
                                //myEmpls.add(e);
                        }
                }
                nl = docEle.getElementsByTagName("download_url");
if(nl != null && nl.getLength() > 0) {
        for(int i = 0 ; i < nl.getLength();i++) {

                //get the employee element
                Element el = (Element)nl.item(i);
                //get the Employee object
                getDownload(el);

                //add it to list
                //myEmpls.add(e);
        }
}

        }

        /**
         * I take an employee element and read the values in, create
         * an Employee object and return it
         */
        private void getVersion(Element empEl) {

                //for each <employee> element get text or int values of
                //name ,id, age and name
                Version = getTextValue(empEl,"Num");

                //Create a new Employee with the value read from the xml nodes
                //Employee e = new Employee(name,id,age,type);

               // return e;
        }
        private void getChange(Element empEl) {

                //for each <employee> element get text or int values of
                //name ,id, age and name
                Change = getTextValue(empEl,"URL");

                //Create a new Employee with the value read from the xml nodes
                //Employee e = new Employee(name,id,age,type);

               // return e;
        }
        private void getDownload(Element empEl) {

                //for each <employee> element get text or int values of
                //name ,id, age and name
                Download = getTextValue(empEl,"URL");

                //Create a new Employee with the value read from the xml nodes
                //Employee e = new Employee(name,id,age,type);

               // return e;
        }

        /**
         * I take a xml element and the tag name, look for the tag and get
         * the text content
         * i.e for <employee><name>John</name></employee> xml snippet if
         * the Element points to employee node and tagName is 'name' I will return John
         */
        private String getTextValue(Element ele, String tagName) {
                String textVal = null;
                NodeList nl = ele.getElementsByTagName(tagName);
                if(nl != null && nl.getLength() > 0) {
                        Element el = (Element)nl.item(0);
                        textVal = el.getFirstChild().getNodeValue();
                }

                return textVal;
        }


        /**
         * Calls getTextValue and returns a int value
         */
        private int getIntValue(Element ele, String tagName) {
                //in production application you would catch the exception
                return Integer.parseInt(getTextValue(ele,tagName));
        }


}
