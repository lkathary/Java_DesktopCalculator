package com.example.calculator;

import com.example.calculator.controllers.ModelController;
import com.example.calculator.models.CreditCalc;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class CalcController {
    private TextField currentBuffer;
    @FXML
    private TextField edit_formula;
    @FXML
    private TextField edit_interval_1;
    @FXML
    private TextField edit_interval_2;
    @FXML
    private TextField edit_x;
    @FXML
    private TextField editCreditInterest;
    @FXML
    private TextField editCreditSum;
    @FXML
    private TextField editCreditTerm;
    @FXML
    private RadioButton radioBtn_1;
    @FXML
    private RadioButton radioBtn_2;
    @FXML
    private Label lblCreditPayment;
    @FXML
    private Label lblCreditSumInterest;
    @FXML
    private Label lblCreditTotal;

    private final History history = new History();

    @FXML
    void onCalculateClicked(MouseEvent event) {
        ModelController controller = ModelController.getInstance();
        double x;
        try {
            try {
                x = Double.parseDouble(edit_x.getText());
            } catch (RuntimeException ex) {
                x = 0.0;
                edit_x.setText("0");
            }
            controller.initExpression(edit_formula.getText());
            double result = controller.calculateValue(x);
            history.addSting(edit_formula.getText().replace("x", edit_x.getText()) + " = " + result);
            history.saveHistory();
            edit_formula.setText(String.valueOf(result));
        } catch (RuntimeException ex) {
            edit_formula.setText(ex.getMessage());
        }
    }

    @FXML
    void onClearClicked(MouseEvent event) {
        if (currentBuffer == edit_interval_1) {
            currentBuffer.setText("-10");
        } else if (currentBuffer == edit_interval_2) {
            currentBuffer.setText("10");
        } else {
            currentBuffer.setText("0");
        }
    }

    @FXML
    void onBufferSelected(MouseEvent event) {
        currentBuffer = (TextField) event.getSource();
    }

    @FXML
    void onDrawClicked(MouseEvent event) {
        ModelController controller = ModelController.getInstance();
        try {
            controller.initExpression(edit_formula.getText());
            controller.calculateValue(0);                                   // for detecting INCORRECT EXPRESSION
            double leftBound = -10;
            double rightBound = 10;
            try {
                leftBound = Double.parseDouble(edit_interval_1.getText());
            } catch (RuntimeException ex) {
                edit_interval_1.setText("-10");
            } finally {
                controller.setLeftBound(leftBound);
            }
            try {
                rightBound = Double.parseDouble(edit_interval_2.getText());
            } catch (RuntimeException ex) {
                edit_interval_2.setText("10");
            } finally {
                controller.setRightBound(rightBound);
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("graph-view.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent parent = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Graph");
            try (InputStream iconStream = getClass().getResourceAsStream("icon-plot.png")) {
                Image image = new Image(Objects.requireNonNull(iconStream));
                stage.getIcons().add(image);
            } catch (Exception e) {
                System.err.println("Error: icon image not found");
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (RuntimeException ex) {
            edit_formula.setText(ex.getMessage());
        }
    }

    @FXML
    void onHistoryClicked(MouseEvent event) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        for (String it : history.getHistory()) {
            textArea.appendText(it + "\n");
        }
        Scene scene = new Scene(textArea);
        Stage stage = new Stage();
        stage.setTitle("History");
        try (InputStream iconStream = getClass().getResourceAsStream("icon-calc-ext.png")) {
            Image image = new Image(Objects.requireNonNull(iconStream));
            stage.getIcons().add(image);
        } catch (Exception e) {
            System.err.println("Error: icon image not found");
        }
        stage.setScene(scene);
        stage.setMinWidth(400.0);
        stage.setMinHeight(400.0);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void onClearHistoryClicked(MouseEvent event) {
        history.clearHistory();
    }

    @FXML
    void onInfoClicked(MouseEvent event) {
        TextArea textArea = new TextArea();
        textArea.setFont(new Font(14));
        textArea.setEditable(false);
        textArea.setText("""
                Main features of the calculator:
                                
                Both integers and real numbers with a dot can be input into the program.
                The calculation is completed by entering the calculating expression and pressing the = symbol.
                Supports calculation of arbitrary bracketed arithmetic expressions in infix notation.
                Supports calculation of arbitrary bracketed arithmetic expressions in infix notation with substitution of the value of x.
                Support the plotting a graph of a function given by an expression in infix notation with the variable x
                (with coordinate axes, mark of the used scale and an adaptive grid).
                Domain and codomain of a function are limited to at least numbers from -1000000 to 1000000.
                Verifiable accuracy of the fractional part is at least to 7 decimal places.
                Supports the entering up to 255 characters.
                Supports brackets arithmetic expressions in infix notation must support the main arithmetic operations
                and mathematical functions.
                                
                Have a nice calculations!""");

        Scene scene = new Scene(textArea);
        Stage stage = new Stage();
        stage.setTitle("Info");
        try (InputStream iconStream = getClass().getResourceAsStream("icon-info.png")) {
            Image image = new Image(Objects.requireNonNull(iconStream));
            stage.getIcons().add(image);
        } catch (Exception e) {
            System.err.println("Error: icon image not found");
        }
        stage.setScene(scene);
        stage.setMinWidth(850.0);
        stage.setMinHeight(350.0);
        //stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void onKeyClicked(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if (currentBuffer.getText().equals("0") && !btn.getText().equals(".")) {
            currentBuffer.setText(btn.getText());
        } else {
            currentBuffer.appendText(btn.getText());
        }
    }

    @FXML
    void onUndoClicked(MouseEvent event) {
        if (currentBuffer.getText().length() != 0) {
            currentBuffer.setText(currentBuffer.getText()
                    .substring(0, currentBuffer.getText().length() - 1));
        }
        if (currentBuffer.getText().equals("")) {
            currentBuffer.setText("0");
        }
    }

    @FXML
    void onCreditClearClicked(MouseEvent event) {
        editCreditSum.setText("");
        editCreditTerm.setText("");
        editCreditInterest.setText("");
        radioBtn_1.fire();
        lblCreditPayment.setText("");
        lblCreditSumInterest.setText("");
        lblCreditTotal.setText("");
    }

    @FXML
    void onCreditClicked(MouseEvent event) {
        double creditSum = 0.0, creditInterest = 0.0;
        int creditTerm = 0;
        boolean sumOk = true, termOk = true, interestOk = true;
        try {
            creditSum = Double.parseDouble(editCreditSum.getText());
        } catch (RuntimeException ex) {
            sumOk = false;
        }
        if (sumOk && creditSum >= 0.01 && creditSum <= 1e10) {
            creditSum = Math.round(creditSum * 100.0) / 100.0;
            editCreditSum.setText(String.valueOf(creditSum));
        } else {
            editCreditSum.setText("0.01 <= sum <= 1e10");
            sumOk = false;
        }
        try {
            creditTerm = Integer.parseInt(editCreditTerm.getText());
        } catch (RuntimeException ex) {
            termOk = false;
        }
        if (termOk && creditTerm > 0 && creditTerm <= 600) {
            editCreditTerm.setText(String.valueOf(creditTerm));
        } else {
            editCreditTerm.setText("1 <= term <= 600");
            termOk = false;
        }
        try {
            creditInterest = Double.parseDouble(editCreditInterest.getText());
        } catch (RuntimeException ex) {
            interestOk = false;
        }
        if (interestOk && creditInterest >= 0.01 && creditInterest <= 999.99) {
            creditInterest = Math.round(creditInterest * 100.0) / 100.0;
            editCreditInterest.setText(String.valueOf(creditInterest));
            creditInterest /= 100;
        } else {
            editCreditInterest.setText("0.01 <= % < 1000");
            interestOk = false;
        }
        if (sumOk && termOk && interestOk) {
            CreditCalc.PayType payType = radioBtn_1.isSelected() ?
                    CreditCalc.PayType.kDiffPay : CreditCalc.PayType.kAnnPay;
            ModelController controller = ModelController.getInstance();
            controller.calculateCredit(creditSum, creditTerm, creditInterest, payType);

            lblCreditPayment.setText(controller.getPayment());
            lblCreditSumInterest.setText(String.format("%-,6.2f", controller.getSumInterest()));
            lblCreditTotal.setText(String.format("%-,6.2f", controller.getSumInterest() + controller.getSumMain()));
        }
    }

    @FXML
    void onCreditPaymentsClicked(MouseEvent event) {
        ModelController controller = ModelController.getInstance();
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.appendText("#\t\tПлатеж, р\tПогашено, р\tПроценты, р\t\tОстаток, р\n\n");
        var result = controller.getPayments();
        for (int i = 1; i < result.size(); ++i) {
            textArea.appendText(i + ".\t\t" + result.get(i).toRecord() + "\n");
        }
        textArea.appendText("\nВсего:\t"
                + String.format("%-,6.2f", controller.getSumInterest() + controller.getSumMain())
                + "\t\t\t\t\t" + String.format("%-,6.2f", controller.getSumInterest()) + "\n");

        Scene scene = new Scene(textArea);
        Stage stage = new Stage();
        stage.setTitle("Payments");
        try (InputStream iconStream = getClass().getResourceAsStream("icon-calc-ext.png")) {
            Image image = new Image(Objects.requireNonNull(iconStream));
            stage.getIcons().add(image);
        } catch (Exception e) {
            System.err.println("Error: icon image not found");
        }
        stage.setScene(scene);
        stage.setMinWidth(400.0);
        stage.setMinHeight(400.0);
        //stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void initialize() {
        currentBuffer = edit_formula;
        history.loadHistory();
    }
}
