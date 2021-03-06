/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.saml.asserter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author JL06436S
 */
public class SignedSamlTokenAssertionGeneratorTest extends SamlTokenAssertionGeneratorTest {

    public SignedSamlTokenAssertionGeneratorTest() {
    }

    /**
     * Test of generate method, of class SignedSamlTokenAssertionGenerator.
     * @throws java.lang.Exception
     */
    @Test
    @Override
    public void testGeneratePassing() throws Exception {
        String login = "tester";
        Long time = 1200L;
        String issuer = "http://issuer.org";
        SignedSamlTokenAssertionGenerator generator = new SignedSamlTokenAssertionGenerator();
        String assertion = generator.generate(login, time, issuer,
                SignedSamlTokenAssertionGeneratorTest.class.getResource("/demo.jks"), "keypass", "datapower", "keypass");
        System.out.println("Assertion : " + assertion);
        assertNotNull("Returned assertion must not be null", assertion);
        assertNotEquals("Returned assertion must not be empty", "", assertion);
        assertTrue("Returned assertion must be in an XML form", assertion.contains("<"));
        assertTrue("Returned assertion must be an SAML Assertion", assertion.contains("Assertion"));
        assertTrue("Returned assertion must provide an SAML token", assertion.contains("AuthenticationStatement"));
        assertTrue("Returned assertion must provide NotBefore", assertion.contains("NotBefore"));
        assertTrue("Returned assertion must provide NotOnOrAfter", assertion.contains("NotOnOrAfter"));
        // control dates
        //NotBefore="2014-06-04T00:37:36.906Z" NotOnOrAfter="2014-07-08T00:37:36.906Z";
        assertEquals("Duration of assertion is not the one expected.", time, extractAssertionDuration(assertion));
        assertTrue("Returned assertion must provide conditions", assertion.contains("Issuer=\"" + issuer + "\""));
        assertTrue("Returned assertion should contain the provided login", assertion.contains(login));
        assertTrue("Returned assertion must be signed", assertion.contains("Signature"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGenerateBadParams() throws Exception {
        String login = null;
        Long time = 0L;
        String issuer = "";
        SignedSamlTokenAssertionGenerator generator 
                = new SignedSamlTokenAssertionGenerator();
        String assertion = generator.generate(login, time, issuer,
                SignedSamlTokenAssertionGeneratorTest.class.getResource("/demo.jks"), "", "", "");
        System.out.println("Assertion : " + assertion);
    }

}
