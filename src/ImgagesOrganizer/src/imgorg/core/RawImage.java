package imgorg.core;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class RawImage extends Image {
	
	protected Node metadata = null;

	protected RawImage(File file) throws IOException {
		super(file);
		
		ImageInputStream iis = ImageIO.createImageInputStream(file);
		if (iis != null) {
			Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
			if (!readers.hasNext()) {
				throw new IOException();
			}
			ImageReader reader = readers.next();
			reader.setInput(iis);
			IIOMetadata metadataXML = reader.getImageMetadata(0);
			metadata = metadataXML.getAsTree(metadataXML.getNativeMetadataFormatName());
			iis.close();
		}
	}

	@Override
	public Date getShootDate() {
		if (metadata != null) {
			try {
				XPath xPath = XPathFactory.newInstance().newXPath();
				Node node = (Node) xPath.evaluate(".//TIFFField[@name='DateTime']/TIFFAsciis/TIFFAscii/@value", metadata, XPathConstants.NODE);
				return new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").parse(node.getNodeValue());
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			} catch (DOMException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
