package gov.nist.toolkit.valregmsg.message;

import gov.nist.toolkit.errorrecording.IErrorRecorder;
import gov.nist.toolkit.errorrecording.IErrorRecorderBuilder;
import gov.nist.toolkit.errorrecording.common.XdsErrorCode;
import gov.nist.toolkit.errorrecording.xml.assertions.Assertion;
import gov.nist.toolkit.errorrecording.xml.assertions.AssertionLibrary;
import gov.nist.toolkit.http.HttpHeader;
import gov.nist.toolkit.http.HttpParserBa;
import gov.nist.toolkit.http.ParseException;
import gov.nist.toolkit.valregmsg.validation.factories.MessageValidatorFactory;
import gov.nist.toolkit.valsupport.client.ValidationContext;
import gov.nist.toolkit.valsupport.engine.MessageValidatorEngine;
import gov.nist.toolkit.valsupport.message.AbstractMessageValidator;
import gov.nist.toolkit.valsupport.registry.RegistryValidationInterface;

/**
 * Validate SIMPLE SOAP message. The input (an HTTP stream) has already been parsed
 * and the headers are in a HttpParserBa class and the body in a byte[]. This 
 * validator only evaluates the HTTP headers. Validation of the body is passed
 * off to MessageValidatorFactory.
 * @author bill
 *
 */
public class SimpleSoapHttpHeaderValidator extends AbstractMessageValidator {
	HttpParserBa hparser;
	IErrorRecorderBuilder erBuilder;
	MessageValidatorEngine mvc;
	byte[] bodyBytes;
	String charset = null;
	RegistryValidationInterface rvi;

	private AssertionLibrary ASSERTIONLIBRARY = AssertionLibrary.getInstance();


	public SimpleSoapHttpHeaderValidator(ValidationContext vc, HttpParserBa hparser, byte[] body, IErrorRecorderBuilder erBuilder, MessageValidatorEngine mvc, RegistryValidationInterface rvi) {
		super(vc);
		this.hparser = hparser;
		this.erBuilder = erBuilder;
		this.mvc = mvc;
		this.rvi = rvi;
		this.bodyBytes = body;
	}

	public void run(IErrorRecorder er, MessageValidatorEngine mvc) {
		this.er = er;

		er.challenge("Validate SIMPLE SOAP HTTP headers");

		String contentTypeString = hparser.getHttpMessage().getHeader("content-type");
		try {
			HttpHeader contentTypeHeader = new HttpHeader(contentTypeString);
			String contentTypeValue = contentTypeHeader.getValue();
			if (contentTypeValue == null) contentTypeValue = "";
			if (!"application/soap+xml".equals(contentTypeValue.toLowerCase())) {
				er.error("??", "Content-Type header", contentTypeValue, "application/soap+xml", "http://www.w3.org/TR/soap12-part0 - Section 4.1.2");
			}
                else {
				Assertion assertion = ASSERTIONLIBRARY.getAssertion("TA181");
				er.success(assertion, this, "");
			}
			charset = contentTypeHeader.getParam("charset");
			if (charset == null || charset.equals("")) {
				charset = "UTF-8";
				er.report("No message CharSet found in Content-Type header - using default", charset);
			} else {
				er.report("Message CharSet", charset);
			}

//			String body = new String(bodyBytes, charset);
			vc.isSimpleSoap = true;
			vc.hasSoap = true;

//			er.detail("Scheduling validation of SOAP content");
            er.sectionHeading("SOAP Message");
			MessageValidatorFactory.getValidatorContext(erBuilder, bodyBytes, mvc, "Validate SOAP", vc, rvi);

		} catch (ParseException e) {
			err(e);
//		} catch (UnsupportedEncodingException e) {
//			err(e);
		}
		finally {
		}

	}
	
	void err(String msg, String ref) {
		er.err(XdsErrorCode.Code.NoCode, msg, this, ref);
	}
	
	void err(Exception e) {
		er.err(XdsErrorCode.Code.NoCode, e);
	}

	@Override
	public boolean isSystemValidator() { return false; }


}