/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaa8drtimer;

import java.util.Optional;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author arsal
 */
public class Aaa8drTimer extends Application {
    
    public double secondsElapsed = 0.0;
    public double tickTimeInSeconds = 0.01;
    public double angleDeltaPerSeconds = 6.0;
    public Boolean startButtonPressed = false;
    Timeline timeline;
    
    public int min = 0;
    public int sec = 0;
    public int centi = 0;
    
    void adjust(double min, double sec, double centi){
        if (centi == 100){
            sec++;
            centi = 0;        
        }
        if(sec == 60){
            min++;
            sec = 0;
        }
    }
 
    public void start(){
	timeline.play();
    }
    
    public void stop(){
            timeline.pause();
        }
    
    public boolean isRunning(){
        if(timeline != null){
            if(timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        return false;
    }

    public void setTickTime(){
        this.tickTimeInSeconds = tickTimeInSeconds;
    }
    public void textInput() {
        TextInputDialog dialog = new TextInputDialog("00:00:00");
        dialog.setTitle("Set up timer!");
        dialog.setHeaderText("Enter your time: ");
        Optional<String> result = dialog.showAndWait();
    }
    
    public void timeUpNotification(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Message");
        alert.setContentText("Time is up!!!!");
        alert.showAndWait();
    }
    
    @Override
    public void start(Stage primaryStage) {
        textInput();
      
        primaryStage.setTitle("Timer");
       
        StackPane root = new StackPane();
        ImageView dialImageView = new ImageView();
        ImageView handImageView = new ImageView();
        root.getChildren().addAll(dialImageView, handImageView);
        Image dialImage = new Image(getClass().getResourceAsStream("clockface.png"));
        Image handImage = new Image(getClass().getResourceAsStream("hand.png"));
        
        dialImageView.setImage(dialImage);
        handImageView.setImage(handImage);
        
        VBox recordBoard = new VBox();
        recordBoard.setAlignment(Pos.BOTTOM_CENTER);
        Label digital = new Label("Timer 00:00:00");
        digital.setFont(Font.font("Ariel", 30));
        Label recordLabel = new Label("Rec --:--:--");
        recordLabel.setFont(Font.font("Ariel",30));
        Label lap = new Label("Lap --:--:--");
        lap.setFont(Font.font("Ariel", 30));
        
        recordBoard.getChildren().addAll(digital, recordLabel, lap);
        
        HBox controlButtons = new HBox();
        Button start = new Button("Start");  
        Button record =  new Button("Record");
        controlButtons.setSpacing(10);
        controlButtons.setAlignment(Pos.BOTTOM_CENTER);
        controlButtons.setPadding(new Insets(25,25,25,25));
        controlButtons.getChildren().addAll(record, start);
        
        GridPane grid = new GridPane();
        grid.add(root,0,0);
        grid.add(recordBoard, 0, 1);
        grid.add(controlButtons, 0, 2);
        grid.setAlignment(Pos.CENTER);
        
        start.setOnAction((ActionEvent event) -> {
            if(isRunning()){
                stop();
                start.setText("Start");
                record.setText("Reset");
            }else{
                start();
                start.setText("Stop");
                record.setText("Record");
            }
        });
        
        record.setOnAction((ActionEvent event) -> {
            if(isRunning()){
                recordLabel.setText("Rec " + Integer.toString(min)+":"+ Integer.toString(sec) +":"+ Integer.toString(centi));
            }else{
                handImageView.setRotate(0);
                digital.setText("Timer 00:00:00");
                recordLabel.setText("Rec --:--:--");
                lap.setText("Lap --:--:--");
                centi = 0;
                min = 0;
                sec = 0;
            }
        });
        
        timeline = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent e) -> {
        secondsElapsed += tickTimeInSeconds;
        double rotation = secondsElapsed * angleDeltaPerSeconds;
        handImageView.setRotate(rotation);
        centi++;
            if(centi == 100 ){
             sec++;
             centi = 0;
            }
            if(sec==60){
             min++;
             sec=0;
            }   
        digital.setText("Timer " + Integer.toString(min)+":"+ Integer.toString(sec) +":"+ Integer.toString(centi));
        }));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        
        Scene scene = new Scene(grid, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
