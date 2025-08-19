import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

public class CalculatorFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Input fields
        TextField numberField1 = new TextField();
        numberField1.setPromptText("Enter first number");

        TextField numberField2 = new TextField();
        numberField2.setPromptText("Enter second number");

        // Buttons
        Button addButton = new Button("Add");
        Button subtractButton = new Button("Subtract");
        Button multiplyButton = new Button("Multiply");
        Button divideButton = new Button("Divide");
        Button modulusButton = new Button("Modulus");

        // Result label
        Label resultLabel = new Label("Result will appear here");

        // Button Actions
        addButton.setOnAction(e -> {
            try {
                double num1 = Double.parseDouble(numberField1.getText());
                double num2 = Double.parseDouble(numberField2.getText());
                resultLabel.setText("Sum: " + (num1 + num2));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        subtractButton.setOnAction(e -> {
            try {
                double num1 = Double.parseDouble(numberField1.getText());
                double num2 = Double.parseDouble(numberField2.getText());
                resultLabel.setText("Difference: " + (num1 - num2));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        multiplyButton.setOnAction(e -> {
            try {
                double num1 = Double.parseDouble(numberField1.getText());
                double num2 = Double.parseDouble(numberField2.getText());
                resultLabel.setText("Product: " + (num1 * num2));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        divideButton.setOnAction(e -> {
            try {
                double num1 = Double.parseDouble(numberField1.getText());
                double num2 = Double.parseDouble(numberField2.getText());
                if (num2 == 0) {
                    resultLabel.setText("Cannot divide by zero.");
                } else {
                    resultLabel.setText("Quotient: " + (num1 / num2));
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        modulusButton.setOnAction(e -> {
            try {
                int num1 = Integer.parseInt(numberField1.getText());
                int num2 = Integer.parseInt(numberField2.getText());
                if (num2 == 0) {
                    resultLabel.setText("Cannot mod by zero.");
                } else {
                    resultLabel.setText("Modulus: " + (num1 % num2));
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid integers for modulus.");
            }
        });

        // Layout
        VBox inputBox = new VBox(10, numberField1, numberField2);
        inputBox.setAlignment(Pos.CENTER);

        HBox buttonBox1 = new HBox(10, addButton, subtractButton);
        buttonBox1.setAlignment(Pos.CENTER);

        HBox buttonBox2 = new HBox(10, multiplyButton, divideButton, modulusButton);
        buttonBox2.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, inputBox, buttonBox1, buttonBox2, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Scene
        Scene scene = new Scene(layout, 500, 320);
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
