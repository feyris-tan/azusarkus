package moe.yo3explorer.azusa.js3db.control;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.js3db.entity.Js3DbChara;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

@Singleton
public class CharasXmlBuilder
{
    public String buildXml(List<Js3DbChara> charaList) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element chars = document.createElement("chars");
        document.appendChild(chars);

        for (Js3DbChara js3DbChara : charaList) {
            Element theChar = document.createElement("char");

            Element date = document.createElement("date");
            date.appendChild(document.createTextNode(js3DbChara.time_added.toString()));
            theChar.appendChild(date);

            Element charName = document.createElement("charName");
            charName.appendChild(document.createTextNode(js3DbChara.name));
            theChar.appendChild(charName);

            Element hits = document.createElement("hits");
            hits.appendChild(document.createTextNode(Integer.toString(js3DbChara.hits)));
            theChar.appendChild(hits);

            Element rating = document.createElement("rating");
            rating.appendChild(document.createTextNode(Double.toString(js3DbChara.rating)));
            theChar.appendChild(rating);

            Element tags = document.createElement("tags");
            tags.appendChild(document.createTextNode(js3DbChara.tags));
            theChar.appendChild(tags);

            Element crc32 = document.createElement("crc32");
            crc32.appendChild(document.createTextNode(js3DbChara.crc32));
            theChar.appendChild(crc32);

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(Integer.toString(js3DbChara.id)));
            theChar.appendChild(id);

            Element userId = document.createElement("userId");
            userId.appendChild(document.createTextNode(Integer.toString(js3DbChara.user_id)));
            theChar.appendChild(userId);

            //TODO: Username auflösen...
            Element username = document.createElement("username");
            username.appendChild(document.createTextNode(String.format("user%d",js3DbChara.user_id)));
            theChar.appendChild(username);

            Element size = document.createElement("size");
            size.appendChild(document.createTextNode(Integer.toString(js3DbChara.size)));
            theChar.appendChild(size);

            chars.appendChild(theChar);
        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StringWriter outputString = new StringWriter();
        StreamResult streamResult = new StreamResult(outputString);
        transformer.transform(domSource,streamResult);
        return outputString.toString();
    }
}
