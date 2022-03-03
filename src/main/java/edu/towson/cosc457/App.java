package edu.towson.cosc457;

import edu.towson.cosc457.connection.DBConnector;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BARCS Database App
 */
public class App extends Application {
    
    private static Scene scene;
    
    public static final Dotenv dotenv = Dotenv.configure().load();
    
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    
    static List<String> userAssociations = new ArrayList<>();
    
    public static ExecutorService getExecutor() {
        return executor;
    }
    
    static void setUserAssociations(List<String> associations) {
        userAssociations = associations;
    }
    
    static List<String> getUserAssociations() {
        return userAssociations;
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        DBConnector.getInstance();
        scene = new Scene(loadFXML("login"), 800, 600);
        stage.setScene(scene);
        stage.setTitle("BARCS Database");
        stage.show();
    }
    
    @Override
    public void stop() throws Exception {
    	executor.shutdownNow();
        DBConnector.getInstance().close();
        if (DBConnector.getInstance().isClosed())
            System.out.println("SSH Closed");
        super.stop();
    }
    
    static void setRoot(String fxml) throws IOException {
    	scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    	return fxmlLoader.load();
    }

    public static void main(String[] args) {
    	launch();
    }

}
