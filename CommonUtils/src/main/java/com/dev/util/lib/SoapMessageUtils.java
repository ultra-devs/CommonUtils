/***
 * 
 * Utility class
 * ultradev.nitc@gmail.com
 * 
 * 
 * 
 */

package com.dev.util.lib;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.commons.io.FileUtils;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

@SuppressWarnings("restriction")
public class SoapMessageUtils {

	/**
	 * this api suppose to return soap name from soaprequest/soap response
	 * 
	 * @throws Exception
	 */
	public static String getSoapNameFromSoapRequestMessage(String payload) throws Exception {

		final String REQUEST = "Request";

		String operationName = null;
		try {
			SOAPMessage soapMessage = getSoapMessageFromString(payload);
			// if soapmessage has required info
			if (soapMessage != null && soapMessage.getSOAPPart() != null
					&& soapMessage.getSOAPPart().getEnvelope() != null
					&& soapMessage.getSOAPPart().getEnvelope().getBody() != null
					&& soapMessage.getSOAPPart().getEnvelope().getBody().getChildNodes() != null
					&& soapMessage.getSOAPPart().getEnvelope().getBody().getChildNodes().getLength() > 0) {
				operationName = soapMessage.getSOAPPart().getEnvelope().getBody().getChildNodes().item(1)
						.getLocalName();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new Exception("Could not extract soapEnvolope  " + e.getMessage());
		}

		catch (Exception ex) {
			// TODO Auto-generated catch block
			// ex.printStackTrace();
			throw new Exception("Could not extract soapEnvolope  " + ex.getMessage());
		}
		// remove request/response messages to insure we get correct name
		if (operationName != null) {
			operationName = operationName.replaceAll(REQUEST, "");
			/* operationName = operationName.replaceAll(RESPONSE, ""); */
		}

		return operationName;

	}

	public static SOAPMessage getSoapMessageFromString(String soapMessageContent) throws IOException, SOAPException {
		SOAPMessage soapMessage = null;
		// System.out.println(soapMessageContent);
		if (soapMessageContent != null) {
			MimeHeaders mimeHeaders = new MimeHeaders();
			mimeHeaders.addHeader("Content-Type", "text/xml");
			InputStream is = new ByteArrayInputStream(soapMessageContent.getBytes());
			soapMessage = MessageFactory.newInstance().createMessage(mimeHeaders, is);
		}
		/*
		 * System.out.println(soapMessage);
		 * System.out.println(soapMessage.getSOAPPart().getEnvelope().getBody().
		 * getChildNodes().item(1).getLocalName());
		 */return soapMessage;
	}

	
	  public static void main(String[] args) throws Exception { // read an xml
	//  and send that to getSoapOperationNameFromSoapMessage // read xml string
	  String xmlReq = FileUtils.readFileToString(new
	  File("testdata/test.xml"));
	  System.out.println(getSoapNameFromSoapRequestMessage(xmlReq));
	  
	  }
	 

}
