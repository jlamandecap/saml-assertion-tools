/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools.proxy.saml;

import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 *
 * @author JL06436S
 */
public class UiRunner extends Application implements Runnable {
    
    private static final String BOOTSTRAP_PREFIX = "http://getbootstrap.com/components/#";
    
    private enum Anchor { progress, jumbotron, badges, pagination }

    public static void main(String[] args) throws Exception {
        UiRunner runner = new UiRunner();
        runner.run(args);
    }
    
    @Override
    public void run(String[] args) throws Exception {
        System.out.println("Mode UI");
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");
        URL htmlUiPath = this.getClass().getClassLoader().getResource("views/ui.html");
        final WebView webview = new WebView();

        final ToolBar nav = new ToolBar();
        for (Anchor anchor : Anchor.values()) {
            nav.getItems().add(
                new NavButton(
                    anchor,
                    webview, htmlUiPath.toString()
                )
            );
        }

        VBox layout = new VBox();
        layout.getChildren().addAll(
            nav,
            webview
        );

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
    
    private class NavButton extends Button {
        public NavButton(final Anchor anchor, final WebView webview, final String view) {
            setText(anchor.toString());

            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    webview.getEngine().load(view);
                }
            });
        }
    }
    
}
