package de.gdgbremen.mapsapibattle.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;
import android.util.Log;

public final class XMLLandmarkParser {

	private String filePath;

	private AssetManager assetManager;

	public XMLLandmarkParser(AssetManager assetManager, String filePath) {
		this.assetManager = assetManager;
		this.filePath = filePath;
	}

	public List<Landmark> parseLandmarks() {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		final List<Landmark> landmarks = new ArrayList<Landmark>();
		final List<AdditionalInformation> additionalInformations = new ArrayList<AdditionalInformation>();
		try {
			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(assetManager.open(filePath));

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sixcms_article");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					final Element element = (Element) nNode;
					final String container = element.getElementsByTagName("container")
							.item(0).getTextContent().replaceAll("\n", "").trim();
					if (container != null) {
						final String name = element.getElementsByTagName("title").item(0)
								.getTextContent().replaceAll("\n", "").trim();
						if (container.equals("01_bremen.de: 01_stadtplan: 01_poi")) {
							final NodeList fieldList = element.getElementsByTagName("field");
							String strasse = "";
							String hausnummer = "";
							String relId = null;
							for (int j = 0; j < fieldList.getLength(); j++) {
								final Element field = (Element) fieldList.item(j);
								final String fieldType = field.getAttribute("name");
								if (fieldType != null) {
									if (fieldType.equals("strasse")) {
										strasse = field.getTextContent().replaceAll("\n", "")
												.trim();
									} else if (fieldType.equals("hausnummer")) {
										hausnummer = field.getTextContent().replaceAll("\n", "")
												.trim();
									} else if (fieldType.equals("rel_poi_einr")) {
										relId = field.getTextContent().replaceAll("\n", "").trim();
									}
								}
							}
							landmarks.add(new Landmark(name, strasse + " " + hausnummer,
									relId));
						} else if (container
								.equals("01_bremen.de: 01_einrichtungen: 01_einrichtungen")) {
							final AdditionalInformation information = new AdditionalInformation();
							information.id = element.getElementsByTagName("id").item(0)
									.getTextContent().replaceAll("\n", "").trim();
							final NodeList fieldList = element.getElementsByTagName("field");
							for (int j = 0; j < fieldList.getLength(); j++) {
								final Element field = (Element) fieldList.item(j);
								final String fieldType = field.getAttribute("name");
								if (fieldType != null) {
									this.setAttributeOfInformation(information, field, fieldType);
								}
							}
							additionalInformations.add(information);
						}
					}
				}
			}

		} catch (ParserConfigurationException e) {
			Log.e(XMLLandmarkParser.class.getSimpleName(),
					"ParserConfigurationException occured: " + e.getMessage());
		} catch (SAXException e) {
			Log.e(XMLLandmarkParser.class.getSimpleName(), "SAXException occured: "
					+ e.getMessage());
		} catch (IOException e) {
			Log.e(XMLLandmarkParser.class.getSimpleName(), "IOException occured: "
					+ e.getMessage());
		}

		for (final Landmark landmark : landmarks) {
			if (landmark.additionalInformation != null) {
				for (final AdditionalInformation ai : additionalInformations) {
					if (landmark.additionalInformation.id.equals(ai.id)) {
						landmark.additionalInformation = ai;
						break;
					}
				}
			}
		}
		return landmarks;
	}

	private void setAttributeOfInformation(
			final AdditionalInformation information, final Element field,
			final String fieldType) {
		if (fieldType.equals("abkuerzung")) {
			information.abkuerzung = field.getTextContent().replaceAll("\n", "")
					.trim();
			return;
		}
		if (fieldType.equals("strasse")) {
			information.strasse = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("hausnummer")) {
			information.hausnummer = field.getTextContent().replaceAll("\n", "")
					.trim();
			return;
		}
		if (fieldType.equals("lage")) {
			information.lage = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("adresszusatz")) {
			information.adresszusatz = field.getTextContent().replaceAll("\n", "")
					.trim();
			return;
		}
		if (fieldType.equals("postfach")) {
			information.postfach = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("ortsteil")) {
			information.ortsteil = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("plzPostfach")) {
			information.plzPostfach = field.getTextContent().replaceAll("\n", "")
					.trim();
			return;
		}
		if (fieldType.equals("plz")) {
			information.plz = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("ort")) {
			information.ort = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("email")) {
			information.email = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("telefon")) {
			information.telefon = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("fax")) {
			information.fax = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("homepage")) {
			information.homepage = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("oeffnungszeiten")) {
			information.oeffnungszeiten = field.getTextContent().replaceAll("\n", "")
					.trim();
			return;
		}
		if (fieldType.equals("latitude")) {
			information.latitude = field.getTextContent().replaceAll("\n", "").trim();
			return;
		}
		if (fieldType.equals("longitude")) {
			information.longitude = field.getTextContent().replaceAll("\n", "")
					.trim();
			return;
		}
		if (fieldType.equals("beschreibung")) {
			information.beschreibung = field.getTextContent().replaceAll("\n", "")
					.trim();
			return;
		}
	}
}