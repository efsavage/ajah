package com.ajah.sitemap;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ajah.lang.ConfigException;
import com.ajah.util.date.DateUtils;

/**
 * A writer that generates the actual sitemap files.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class SiteMapWriter {

	private static Node toElement(final Document doc, final SiteMap siteMap) {
		final Element siteMapElement = doc.createElement("sitemap");

		final Element siteMapLocElement = doc.createElement("loc");
		siteMapLocElement.setTextContent(siteMap.getLocationRoot() + "/" + siteMap.getFilename());
		siteMapElement.appendChild(siteMapLocElement);

		// Element siteMapLastModElement = doc.createElement("lastmod");
		// siteMapLastModElement.setTextContent(DateUtils.W3C_FORMAT.format(new
		// Date()));
		// siteMapElement.appendChild(siteMapLastModElement);

		return siteMapElement;
	}

	private static Node toElement(final Document doc, final SiteMapUrl siteMapUrl) {
		final Element siteMapElement = doc.createElement("url");

		final Element siteMapLocElement = doc.createElement("loc");
		siteMapLocElement.setTextContent(siteMapUrl.getLoc());
		siteMapElement.appendChild(siteMapLocElement);

		if (siteMapUrl.getLastMod() != null) {
			final Element siteMapLastModElement = doc.createElement("lastmod");
			siteMapLastModElement.setTextContent(DateUtils.W3C_FORMAT.format(siteMapUrl.getLastMod()));
			siteMapElement.appendChild(siteMapLastModElement);
		}

		if (siteMapUrl.getPriority() != null) {
			final Element siteMapLastModElement = doc.createElement("priority");
			siteMapLastModElement.setTextContent(String.valueOf(siteMapUrl.getPriority()));
			siteMapElement.appendChild(siteMapLastModElement);
		}

		if (siteMapUrl.getChangeFrequency() != null) {
			final Element siteMapLastModElement = doc.createElement("lastmod");
			siteMapLastModElement.setTextContent(DateUtils.W3C_FORMAT.format(siteMapUrl.getChangeFrequency().name().toLowerCase()));
			siteMapElement.appendChild(siteMapLastModElement);
		}

		return siteMapElement;
	}

	/**
	 * Writes a sitemap.
	 * 
	 * @param siteMap
	 *            The sitemap to write.
	 * @throws TransformerException
	 *             If the XML document could not be constructed.
	 */
	public static void write(final SiteMap siteMap) throws TransformerException {

		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			// This never really happens in a normal JVM
			throw new ConfigException(e);
		}

		// root elements
		final Document doc = docBuilder.newDocument();
		final Element rootElement = doc.createElementNS("http://www.sitemaps.org/schemas/sitemap/0.9", "urlset");
		doc.appendChild(rootElement);

		for (final SiteMapUrl siteMapUrl : siteMap.getSiteMapUrls()) {
			rootElement.appendChild(toElement(doc, siteMapUrl));
		}

		// write the content into xml file
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (final TransformerConfigurationException e) {
			// This never really happens in a normal JVM
			throw new ConfigException(e);
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		final DOMSource source = new DOMSource(doc);
		final StreamResult result = new StreamResult(new File(siteMap.getFilename()));
		transformer.transform(source, result);

		System.out.println("File saved!");

	}

	/**
	 * Write an index and all of its children.
	 * 
	 * @param index
	 *            The index to write.
	 * @throws TransformerException
	 *             If the XML document could not be constructed.
	 */
	public static void write(final SiteMapIndex index) throws TransformerException {

		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			// This never really happens in a normal JVM
			throw new ConfigException(e);
		}

		// root elements
		final Document doc = docBuilder.newDocument();
		final Element rootElement = doc.createElementNS("http://www.sitemaps.org/schemas/sitemap/0.9", "sitemapindex");
		doc.appendChild(rootElement);

		for (final SiteMap siteMap : index.getSiteMaps()) {
			rootElement.appendChild(toElement(doc, siteMap));
			write(siteMap);
		}

		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (final TransformerConfigurationException e) {
			// This never really happens in a normal JVM
			throw new ConfigException(e);
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		final DOMSource source = new DOMSource(doc);
		final StreamResult result = new StreamResult(new File("sitemap.xml"));

		transformer.transform(source, result);

		System.out.println("File saved!");

	}

}
