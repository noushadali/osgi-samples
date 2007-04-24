//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.04.24 at 04:56:04 PM CEST 
//


package com.osgisamples.congress.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.osgisamples.congress.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CongressName_QNAME = new QName("http://dispatcher.congress.osgisamples.com", "CongressName");
    private final static QName _MiddleName_QNAME = new QName("http://dispatcher.congress.osgisamples.com", "MiddleName");
    private final static QName _RegistrationCode_QNAME = new QName("http://dispatcher.congress.osgisamples.com", "RegistrationCode");
    private final static QName _CongressId_QNAME = new QName("http://dispatcher.congress.osgisamples.com", "CongressId");
    private final static QName _FirstName_QNAME = new QName("http://dispatcher.congress.osgisamples.com", "FirstName");
    private final static QName _LastName_QNAME = new QName("http://dispatcher.congress.osgisamples.com", "LastName");
    private final static QName _Email_QNAME = new QName("http://dispatcher.congress.osgisamples.com", "Email");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.osgisamples.congress.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CongressRegistrationResponse }
     * 
     */
    public CongressRegistrationResponse createCongressRegistrationResponse() {
        return new CongressRegistrationResponse();
    }

    /**
     * Create an instance of {@link Congress }
     * 
     */
    public Congress createCongress() {
        return new Congress();
    }

    /**
     * Create an instance of {@link Registrant }
     * 
     */
    public Registrant createRegistrant() {
        return new Registrant();
    }

    /**
     * Create an instance of {@link CongressRegistrationRequest }
     * 
     */
    public CongressRegistrationRequest createCongressRegistrationRequest() {
        return new CongressRegistrationRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dispatcher.congress.osgisamples.com", name = "CongressName")
    public JAXBElement<String> createCongressName(String value) {
        return new JAXBElement<String>(_CongressName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dispatcher.congress.osgisamples.com", name = "MiddleName")
    public JAXBElement<String> createMiddleName(String value) {
        return new JAXBElement<String>(_MiddleName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dispatcher.congress.osgisamples.com", name = "RegistrationCode")
    public JAXBElement<String> createRegistrationCode(String value) {
        return new JAXBElement<String>(_RegistrationCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dispatcher.congress.osgisamples.com", name = "CongressId")
    public JAXBElement<Integer> createCongressId(Integer value) {
        return new JAXBElement<Integer>(_CongressId_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dispatcher.congress.osgisamples.com", name = "FirstName")
    public JAXBElement<String> createFirstName(String value) {
        return new JAXBElement<String>(_FirstName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dispatcher.congress.osgisamples.com", name = "LastName")
    public JAXBElement<String> createLastName(String value) {
        return new JAXBElement<String>(_LastName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dispatcher.congress.osgisamples.com", name = "Email")
    public JAXBElement<String> createEmail(String value) {
        return new JAXBElement<String>(_Email_QNAME, String.class, null, value);
    }

}
