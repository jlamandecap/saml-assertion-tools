/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.saml.asserter;

import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author JL06436S
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    public static void main(String[] args) throws Exception {
        Main runner = new Main();
        runner.run(args);
    }

    public void run(String[] args) throws Exception {
        LOGGER.debug("Mode CLI");
        CommandLines.debugArgs(args);
        
        System.out.println("Provide a soap request or press enter :");
        String soapRequest = CommandLines.scanCommandLinePipedInput();//readFromCommandLinePipedInput();
        
        if(args.length < 5) {
            System.out.println("Usage : java -jar xxxx.jar cli <login> "
                    + "<token_issuer> <jks_file_path> <jks_passphrase> "
                    + "<key_alias> <key_passphrase> "
                    + "[<validity_interval_in_seconds>]");
        } else {
            SignedSamlTokenAssertionGenerator generator = 
                    new SignedSamlTokenAssertionGenerator();
            URL url = new URL(args[2]);
            LOGGER.debug("JKS url : " + url);
            String assertion;
            if(args.length == 6) {
                assertion = generator.generate(args[0], Long.MIN_VALUE, args[1], url, args[3], args[4], args[5]);
            } else {
                assertion = generator.generate(args[0], Long.valueOf(args[6]), args[1], url, args[3], args[4], args[5]);
            }

            if(!"".equals(soapRequest)) {
                // TODO : insert assertion in soap request
                System.out.println(assertion);
            } else {
                System.out.println(assertion);
            }
        }
    }
}
