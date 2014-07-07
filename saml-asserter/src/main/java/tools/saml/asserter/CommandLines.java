/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.saml.asserter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author JL06436S
 */
public class CommandLines {

    private static final Logger LOGGER = Logger.getLogger(CommandLines.class);
            
    public static void debugArgs(String[] args) {
        LOGGER.debug(Arrays.asList(args));
    }
    
    public static String scanCommandLinePipedInput() {
        StringBuilder capturedString = new StringBuilder("");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String input = in.nextLine();
            if(input.length() > 0) {
                LOGGER.debug("Captured Line : " + input);
                capturedString.append(input);
            }
        }
        LOGGER.debug("Captured String : " + capturedString);
        return capturedString.toString();
    }

    public static String readFromCommandLinePipedInput() {
        StringBuilder capturedString = new StringBuilder("");
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);
        while (true) {
            try {
                String inputStr = null;
                if ((inputStr = bufReader.readLine()) != null) {
                    capturedString.append(inputStr);
                } else {
                    System.out.println("inputStr is null");
                    break;
                }
            } catch (Exception e) {
            }
        }
        LOGGER.debug("Captured String : " + capturedString);
        return capturedString.toString();
    }

}
