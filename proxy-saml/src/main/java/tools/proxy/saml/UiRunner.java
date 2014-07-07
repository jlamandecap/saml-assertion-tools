/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools.proxy.saml;

/**
 *
 * @author JL06436S
 */
public class UiRunner implements Runnable {
    
    public static void main(String[] args) throws Exception {
        UiRunner runner = new UiRunner();
        runner.run(args);
    }
    
    @Override
    public void run(String[] args) throws Exception {
        System.out.println("Mode UI");
    }
}
