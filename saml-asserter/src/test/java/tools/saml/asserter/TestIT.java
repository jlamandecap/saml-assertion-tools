/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools.saml.asserter;

import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author JL06436S
 */
@Ignore
public class TestIT {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    @Test
    public void run() throws Exception {
        URL url = TestIT.class.getClassLoader().getResource("datapower.jks");
        System.out.println("JKS URL : " + url);
        String[] args2 = new String[] {"logintest", "issuer", 
            url.toString(),
            "keypass", "datapower", "keypass"};
        Properties props = new Properties();
        props.load(TestIT.class.getResourceAsStream("/log4j.properties"));
        PropertyConfigurator.configure(props);

        Main runner = new Main();
        runner.run(args2);
    }
}
