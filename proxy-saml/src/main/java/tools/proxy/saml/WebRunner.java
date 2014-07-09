/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools.proxy.saml;

import java.net.URL;
import java.security.ProtectionDomain;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author jlamande
 */
 public class WebRunner implements Runnable {

    public static void main(String[] args) throws Exception {
        WebRunner runner = new WebRunner();
        runner.run(args);
    }
    
    /**
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String[] args) throws Exception {
        System.out.println("Mode Web Server");
        Server server = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        if(args.length >= 1) {
            connector.setPort(8080);
        } else {
            connector.setPort(8080);
        }
        server.addConnector(connector);

        ProtectionDomain domain = WebRunner.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}

