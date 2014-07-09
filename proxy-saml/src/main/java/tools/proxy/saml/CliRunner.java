/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.proxy.saml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import tools.saml.asserter.SignedSamlTokenAssertionGenerator;

/**
 *
 * @author JL06436S
 */
public class CliRunner implements Runnable {

    public static void main(String[] args) throws Exception {
        CliRunner runner = new CliRunner();
        runner.run(args);
    }

    @Override
    public void run(String[] args) throws Exception {
        System.out.println("Mode CLI");
        String soapRequest = "";//readFromCommandLinePipedInput();
        if(args.length < 5) {
            System.out.println("Usage : java -jar xxxx.jar cli <login> "
                    + "<token_issuer> <jks_file_path> <jks_passphrase> "
                    + "<key_alias> <key_passphrase> "
                    + "[<validity_interval_in_seconds>]");
        } else {
            SignedSamlTokenAssertionGenerator generator = 
                    new SignedSamlTokenAssertionGenerator();
            URL url = new URL(args[2]);
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
    
    private String readFromCommandLinePipedInput() {
        StringBuilder soapRequest = new StringBuilder("");
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);
        while (true) {
            try {
                String inputStr = null;
                if ((inputStr = bufReader.readLine()) != null) {
                    soapRequest.append(inputStr);
                } else {
                    System.out.println("inputStr is null");
                    break;
                }
            } catch (Exception e) {

            }
        }
        return soapRequest.toString();
    }

}
