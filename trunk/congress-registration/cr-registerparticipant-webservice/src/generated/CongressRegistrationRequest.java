//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.04.01 at 11:32:04 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Congress"/>
 *         &lt;element ref="{}Registrant"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "congress",
    "registrant"
})
@XmlRootElement(name = "CongressRegistrationRequest")
public class CongressRegistrationRequest {

    @XmlElement(name = "Congress", required = true)
    protected Congress congress;
    @XmlElement(name = "Registrant", required = true)
    protected Registrant registrant;

    /**
     * Gets the value of the congress property.
     * 
     * @return
     *     possible object is
     *     {@link Congress }
     *     
     */
    public Congress getCongress() {
        return congress;
    }

    /**
     * Sets the value of the congress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Congress }
     *     
     */
    public void setCongress(Congress value) {
        this.congress = value;
    }

    /**
     * Gets the value of the registrant property.
     * 
     * @return
     *     possible object is
     *     {@link Registrant }
     *     
     */
    public Registrant getRegistrant() {
        return registrant;
    }

    /**
     * Sets the value of the registrant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Registrant }
     *     
     */
    public void setRegistrant(Registrant value) {
        this.registrant = value;
    }

}
