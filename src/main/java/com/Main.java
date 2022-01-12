package com;

import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, XPathExpressionException {
        /*

        //Запрос данных
        Scanner in = new Scanner(System.in);

        System.out.print("Введите дату в формате dd/mm/yyyy: ");
        String date = in.nextLine();

        System.out.print("Введите код валюты: ");
        String codeval = in.nextLine();

        */

        //Запрос get

        String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=11/01/2022";

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

        System.out.println(list.toString());

        //Парсер

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        InputSource source = new InputSource(new StringReader(list.toString()));
        String result = xpath.evaluate("/ValCurs/Valute/Value", source);

        System.out.println("/n");
        System.out.println(result);
    }
}
