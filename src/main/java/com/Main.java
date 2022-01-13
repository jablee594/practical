package com;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    private static String date;{}

    private static String codeval;{}

    public static void main(String[] args) throws IOException, XPathExpressionException {


        Scanner in = new Scanner(System.in);

        System.out.print("Введите дату в формате dd/mm/yyyy: ");
        date = in.nextLine();

        System.out.print("Введите код валюты: ");
        codeval = in.nextLine();

        StringBuffer list = getStringBuffer();
        //extracted(list);

        //System.out.println(list);
        //System.out.println(extracted(list));
    }

    public static @NotNull StringBuffer getStringBuffer() throws IOException {
        String url = String.format("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date);

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = null;
        StringBuffer list = new StringBuffer();

        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.append(inputLine);
        }
        in.close();

        //System.out.println(list.toString());
        return list;
    }

    private static NodeList extracted(StringBuffer list) throws XPathExpressionException {
        NodeList nlval = null;
        NodeList nlcod = null;

        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(list.toString())));
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            XPathExpression val = xpath.compile("//ValCurs/Valute/Value/text()");
            XPathExpression cod = xpath.compile("//ValCurs/Valute/CharCode/text()");

            nlval = (NodeList) val.evaluate(doc, XPathConstants.NODESET);
            nlcod = (NodeList) cod.evaluate(doc, XPathConstants.NODESET);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return nlval;
    }
}
