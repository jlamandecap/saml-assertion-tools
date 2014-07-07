/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.saml.asserter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyStore;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.opensaml.SAMLAssertion;
import org.w3c.dom.Document;

/**
 *
 * @author JL06436S
 */
public class SignedSamlTokenAssertionGenerator extends SamlTokenAssertionGenerator {

    private static final Logger LOGGER = 
            Logger.getLogger(SignedSamlTokenAssertionGenerator.class);
    
    private static final String CRYPTO_ALGO = 
            "http://www.w3.org/2000/09/xmldsig#rsa-sha1";

    public String generate(String login, Long timeInSeconds, String issuer, URL keystore,
            String keyStorePass, String keyAlias, String keyPass) throws Exception {
        String assertionString = super.generate(login, timeInSeconds, issuer);

        // unmarshall XML and load
        Document doc = loadXMLFromString(assertionString);
        SAMLAssertion samlAssertion = new SAMLAssertion();
        samlAssertion.fromDOM(doc.getDocumentElement());
        
        samlAssertion.sign(CRYPTO_ALGO, getKey(keystore, keyStorePass, keyAlias, keyPass), null);
        String signedAssertion = samlAssertion.toString();
        traceAssertion(samlAssertion);
        return signedAssertion;
    }

    /**
     * load an XML Document from an XML string
     *
     * @param xml a XML string
     * @return the XML document
     * @throws Exception
     */
    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    public static Key getKey(URL keystore,
            String keyStorePass, String keyAlias, String keyPass) throws Exception {
        Key key = readJKS(keystore, keyStorePass).getKey(keyAlias, keyPass.toCharArray());
        if(key == null) {
            throw new IllegalArgumentException("Key not found in keystore");
        }
        LOGGER.debug("Key : " + key);
        return key;
    }

    private static KeyStore readJKS(URL keystore, String keyStorePass) throws Exception {
        //InputStream jks = Thread.currentThread().getContextClassLoader().getResourceAsStream("demo.jks");
        InputStream ksInputStream = keystore.openStream();

        // Get Default Instance of KeyStore
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

        // Load KeyStore
        ks.load(ksInputStream, keyStorePass.toCharArray());
        if(ks == null) {
            throw new IllegalArgumentException("Incorrect Keystore");
        }

        // Close InputFileStream
        ksInputStream.close();
        return ks;
    }

}
