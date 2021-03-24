package database;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import utils.AlertsUtil;

import java.io.File;
import java.net.URL;

public class DatabaseInfoContainer {

    private int portNum;
    private String user;
    private String passwd;
    private String serviceName;
    private String hostName;

    private boolean parseSuccess = false;

    public DatabaseInfoContainer(String xmlPath){
        try{
            URL resource = getClass().getResource(xmlPath);
            File file = new File(resource.toURI());
            DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFactory.newDocumentBuilder();
            Document doc = docBuild.parse(file);
            doc.getDocumentElement().normalize();
            NodeList dbInfoList = doc.getElementsByTagName("databaseInfo");
            Node infoNode = dbInfoList.item(0);
            if(infoNode.getNodeType() == Node.ELEMENT_NODE){
                Element dbInfo = (Element) infoNode;
                this.portNum = Integer.parseInt(dbInfo.getElementsByTagName("portNum").item(0).getTextContent());
                this.user = dbInfo.getElementsByTagName("user").item(0).getTextContent();
                this.passwd = dbInfo.getElementsByTagName("passwd").item(0).getTextContent();
                this.serviceName = dbInfo.getElementsByTagName("serviceName").item(0).getTextContent();
                this.hostName = dbInfo.getElementsByTagName("hostName").item(0).getTextContent();
            }
            this.parseSuccess = true;
        } catch (Exception e) {
            AlertsUtil.showErrorAlert(null, "Błąd odczytu danych połączenia",
                    "Brak lub błedne dane pliku połaczenia z bazą danych: '" + xmlPath + "'.");
        }
    }

    public int getPortNum() {
        return portNum;
    }

    public void setPortNum(int portNum) {
        this.portNum = portNum;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean ifParseSuccess(){
        return parseSuccess;
    }
}
