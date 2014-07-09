/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.proxy.saml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author JL06436S
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Runnable runnable;
        if (args.length > 0) {
            // can't switch on strings in java 1.6
            if ("web".equals(args[0])) {
                runnable = new WebRunner();
            } else if ("ui".equals(args[0])) {
                runnable = new UiRunner();
            } else {
                //else if("cli".equals(args[0])) {
                runnable = new CliRunner();
            }
            args = pop(args);
        } else {
            //else if("cli".equals(args[0])) {
            runnable = new CliRunner();
        }
        runnable.run(args);
    }

    private static String[] pop(String[] args) {
        List<String> list = new ArrayList<String>(Arrays.asList(args));
        list.remove(0);
        return list.toArray(new String[0]);
    }

}
