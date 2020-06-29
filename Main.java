package com.teddy;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class Main {

    public static void main(String args[]) throws Exception {
        File stylesheet = new File(System.getProperty("user.dir") + "/xml2csv.xsl");

        if (args.length == 0) {
            System.out.println("xml2csv.jar <input_path> (<output_path>)");
            return;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        builder.setEntityResolver(new EntityResolver() {

            @Override
            public InputSource resolveEntity(String publicId, String systemId)
                    throws SAXException, IOException {
                //System.out.println("Ignoring " + publicId + ", " + systemId);
                return new InputSource(new StringReader(""));
            }
        });

        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer(stylesource);


        String inPath = args[0];
        String outPath;
        if (args.length > 1)
            outPath = args[1];
        else {
            outPath = inPath;
        }

        File[] files = new File(inPath).listFiles();
        for (File file : files) {
            if (file.isDirectory() || !file.getName().toLowerCase().endsWith(".xml"))
                continue;
            File xmlSource = file;
            System.out.println("Converting " + file);
            Document document = builder.parse(xmlSource);
            Source source = new DOMSource(document);

            String inputName = file.getName();
            Result outputTarget = new StreamResult(new File(outPath + "/" + inputName.substring(0, inputName.lastIndexOf('.')) + ".csv"));
            transformer.transform(source, outputTarget);
        }
    }
}