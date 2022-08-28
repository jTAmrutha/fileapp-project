package com.brooklet.fileapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.brooklet.fileapp.dao.DetailDAO;
import com.brooklet.fileapp.dao.HeaderDAO;
import com.brooklet.fileapp.model.Detail;
import com.brooklet.fileapp.model.Header;

public class FileServiceImpl implements FileService {

	@Autowired
	private HeaderDAO headerDAO;

	@Autowired
	private DetailDAO detailDAO;

	@Value("${input.file}")
	private String inputFile;

	@Value("${output.file}")
	private String outputFile;

	public void saveFile() {
		FileReader fr = null;
		try {
			fr = new FileReader(inputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		int lineNumber = 1;
		try {
			while (br.ready()) {
				String line = br.readLine();
				if (lineNumber == 1) {
					saveHeader(line);
				} else {
					saveDetail(line);
				}
				System.out.println(line);
				lineNumber++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveHeader(String headerLine) {
		String headerInfo[] = headerLine.split("\\|");
		System.out.println(headerInfo.length);
		Header header = new Header(headerInfo[0], headerInfo[1], headerInfo[2], headerInfo[3], headerInfo[4],
				headerInfo[5], headerInfo[6], headerInfo[7], headerInfo[8], headerInfo[9], headerInfo[10]);
		for (String s : headerInfo) {
			System.out.println(s);
		}
		headerDAO.save(header);
	}

	private void saveDetail(String detailLine) {
		String detailInfo[] = detailLine.split("\\|");
		System.out.println(detailInfo.length);

		Detail detail = new Detail(detailInfo[0], detailInfo[1], detailInfo[2], detailInfo[3], detailInfo[4],
				detailInfo[5], detailInfo[6], detailInfo[7], detailInfo[8]);
		detailDAO.save(detail);
	}

	@Override
	public void loadFile() {
		List<Header> headerList = headerDAO.findAll();
		Header header = headerList.get(0);
		List<Detail> detailList = detailDAO.findAll();
		createXML(header, detailList);
	}

	private void createXML(Header header, List<Detail> detailList) {
		System.out.println(header.getField1());
		System.out.println(detailList.size());
		final String xmlFilePath = outputFile;

		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("output");
			document.appendChild(root);

			Element head = document.createElement("header");
			root.appendChild(head);

			Element filed1 = document.createElement("field1");
			filed1.appendChild(document.createTextNode(header.getField1()));
			head.appendChild(filed1);

			Element filed2 = document.createElement("field2");
			filed2.appendChild(document.createTextNode(header.getField2()));
			head.appendChild(filed2);

			Element filed3 = document.createElement("field3");
			filed3.appendChild(document.createTextNode(header.getField3()));
			head.appendChild(filed3);

			Element filed4 = document.createElement("field4");
			filed4.appendChild(document.createTextNode(header.getField4()));
			head.appendChild(filed4);

			Element filed5 = document.createElement("field5");
			filed5.appendChild(document.createTextNode(header.getField5()));
			head.appendChild(filed5);

			Element filed6 = document.createElement("field6");
			filed6.appendChild(document.createTextNode(header.getField6()));
			head.appendChild(filed6);

			Element filed7 = document.createElement("field7");
			filed7.appendChild(document.createTextNode(header.getField7()));
			head.appendChild(filed7);

			Element filed8 = document.createElement("field8");
			filed8.appendChild(document.createTextNode(header.getField8()));
			head.appendChild(filed8);

			Element filed9 = document.createElement("field9");
			filed9.appendChild(document.createTextNode(header.getField9()));
			head.appendChild(filed9);

			Element filed10 = document.createElement("field10");
			filed10.appendChild(document.createTextNode(header.getField10()));
			head.appendChild(filed10);

			Element filed11 = document.createElement("field11");
			filed11.appendChild(document.createTextNode(header.getField11()));
			head.appendChild(filed11);

			for (int i = 0; i < detailList.size(); i++) {
				Element detail = document.createElement("detail");
				root.appendChild(detail);

				Detail details = detailList.get(i);

				Element fieldOne = document.createElement("field1");
				fieldOne.appendChild(document.createTextNode(details.getField1()));
				detail.appendChild(fieldOne);

				Element fieldTwo = document.createElement("field2");
				fieldTwo.appendChild(document.createTextNode(details.getField2()));
				detail.appendChild(fieldTwo);

				Element fieldThree = document.createElement("field3");
				fieldThree.appendChild(document.createTextNode(details.getField3()));
				detail.appendChild(fieldThree);

				Element fieldFour = document.createElement("field4");
				fieldFour.appendChild(document.createTextNode(details.getField4()));
				detail.appendChild(fieldFour);

				Element fieldFive = document.createElement("field5");
				fieldFive.appendChild(document.createTextNode(details.getField5()));
				detail.appendChild(fieldFive);

				Element fieldSix = document.createElement("field6");
				fieldSix.appendChild(document.createTextNode(details.getField6()));
				detail.appendChild(fieldSix);

				Element fieldSeven = document.createElement("field7");
				fieldSeven.appendChild(document.createTextNode(details.getField7()));
				detail.appendChild(fieldSeven);

				Element fieldEight = document.createElement("field8");
				fieldEight.appendChild(document.createTextNode(details.getField8()));
				detail.appendChild(fieldEight);

				Element fieldNine = document.createElement("field9");
				fieldNine.appendChild(document.createTextNode(details.getField9()));
				detail.appendChild(fieldNine);

			}

			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging

			transformer.transform(domSource, streamResult);

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}