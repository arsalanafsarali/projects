/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaa8drfxmlcpumonitor;

import com.sun.prism.paint.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author arsal
 */
public class FXMLDocumentController implements Initializable {
    
    Timeline timeline;
    double digitalRotation = getCPUUsage() * 100;
    
    @FXML
    ImageView handImageView = new ImageView();
    
    @FXML
    Button start = new Button();
    
    @FXML
    Button record = new Button();
    
    @FXML 
    Label digital = new Label();
    
    @FXML
    ListView listView = new ListView();
    
    public void start(){
        timeline.play();
    }
    
    public void stop(){
        timeline.pause();
    }
    
    public void setUpUi(){
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent e) -> {
            double rotation = (300*getCPUUsage() - 150);
            double digitalRotation = getCPUUsage() * 100;
            digital.setText(String.format("%.2f%%",digitalRotation));
            handImageView.setRotate(rotation);
        }));
    
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    public void reset(){
        digital.setText("0.00%");
        handImageView.setRotate(-150);
        listView.getItems().clear();
    }
    
    public boolean isRunning() {
        if (timeline != null) {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                return true;
            }
        }
        return false;
    }
    
     public double getCPUUsage() {
        OperatingSystemMXBean operatingSystemMXBean
                = ManagementFactory.getOperatingSystemMXBean();
        double value = 0;
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("getSystemCpuLoad")
                    && Modifier.isPublic(method.getModifiers())) {
                try {
                    value = (double) method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = 0;
                }
                return value;
            }
        }
        return value;
    }
     
    @FXML
    public void handleStart(ActionEvent e){
        if(isRunning()){
            stop();
            start.setText("Start");
            record.setText("Reset");
           
        }else{
            start();
            start.setText("Stop");
            record.setText("Record");
        }
    }
    
    int number = 1;
    
    @FXML
    public void handleRecord(){
        SimpleDateFormat dateTime = new SimpleDateFormat("hh:mm:ss aa");
        dateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        double recordUpdate = getCPUUsage() * 100;
        if(isRunning()){
            listView.getItems().add(0,String.format("Record %d: %.2f%% at " + dateTime.format(new Date()), number, recordUpdate));
            number++;
        }else{
            reset();
            number = 1;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpUi();
    }    
    
}
