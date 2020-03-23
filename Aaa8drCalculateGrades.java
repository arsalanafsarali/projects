/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaa8drcalculategrades;

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author arsal
 */
public class Aaa8drCalculateGrades extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Calculate Grades");
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        
        VBox vert = new VBox();
        vert.setPadding(new Insets(10,0,15,0));
        vert.setSpacing(10);
        
        Label first = new Label("Score 1 (70%):");
        first.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        root.add(first, 0,0);
        
        TextField Score1 = new TextField();
        root.add(Score1, 1, 0);
        Score1.setPrefWidth(350);
        
        Label second = new Label("Score 2 (30%):");
        second.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        root.add(second,0,1);
        
        TextField Score2 = new TextField();
        root.add(Score2, 1, 1);
        Score2.setPrefWidth(350);
        
        Label third = new Label("Bonus:");
        third.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        root.add(third,0,2);
        
        TextField Score3 = new TextField();
        root.add(Score3, 1, 2);
        Score3.setPrefWidth(350);
        
        Label fourth = new Label("Final Score:");
        fourth.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        root.add(fourth,0,3);
        
        TextField FinalScore = new TextField();
        root.add(FinalScore,1,3);
        FinalScore.setPrefWidth(350);
        
        Button FullScore = new Button("Full Score");
        vert.getChildren().add(FullScore);
        
        Button Calculate = new Button("Calculate");
        vert.getChildren().add(Calculate);
        
        Button Clear = new Button("Clear");
        vert.getChildren().add(Clear);
        
        Button Alert = new Button("Alert");
        vert.getChildren().add(Alert);
        
        FullScore.setMaxWidth(Double.MAX_VALUE);
        Calculate.setMaxWidth(Double.MAX_VALUE);
        Clear.setMaxWidth(Double.MAX_VALUE);
        Alert.setMaxWidth(Double.MAX_VALUE);
        root.add(vert,0,4,2,1);
        
        FullScore.setOnAction((ActionEvent e) -> {
            Score1.setText("100");
            Score2.setText("100");
            Score3.setText("10");
        });
        
        Calculate.setOnAction((ActionEvent e)->{
            double result1 = Double.parseDouble(Score1.getText());
            double result2 = Double.parseDouble(Score2.getText());
            double result3 = Double.parseDouble(Score3.getText());
            double FinalValue = (result1*.7) + (result2*.3) + result3;
            
            DecimalFormat df = new DecimalFormat("#.##");
            String realFinalValue = df.format(FinalValue);
            
            FinalScore.setText("My final score should be "+ Score1.getText()+"*.7 " + " + " +
            Score2.getText()+"*.3 " +" +" + Score3.getText()+" = " + realFinalValue);
        });
        
        Clear.setOnAction((ActionEvent e) -> {
            Score1.clear();
            Score2.clear();
            Score3.clear();
            FinalScore.clear();
        });
        
         Alert.setOnAction((ActionEvent e)->{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Message");
            alert.setContentText(FinalScore.getText());
            alert.showAndWait();
        });
       
        Scene scene = new Scene(root, 500, 360);
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
