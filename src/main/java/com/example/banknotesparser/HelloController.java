package com.example.banknotesparser;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class HelloController {

    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputField;

    @FXML
    private TextArea counterField;

    int counter50 = 0;
    int counter20 = 0;
    int counter10 = 0;
    int counter5 = 0;
    int counter1 = 0;

    @FXML
    protected void onHelloButtonClick() {

        int counter50 = 0;
        int counter20 = 0;
        int counter10 = 0;
        int counter5 = 0;
        int counter1 = 0;

        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> integerList;

        try {

            integerList = Arrays.asList(inputField.getText().split(",")).stream()
                    .map(Integer::parseInt)
                    .toList();
            for (Integer num : integerList) {

                if (num >= 100 || num <= 0) {
                    stringBuilder.append("Некорректное значение. Требование: больше нуля и меньше 100!\n");
                    continue;
                }
                if (num >= 10){
                    stringBuilder.append("Число: $").append(num).append("  ->  ");
                } else {
                    stringBuilder.append("Число: $").append(num).append("    ->  ");
                }

                if (num >= 50) {
                    stringBuilder.append("$50: 1  ");
                    counter50++;
                    num -= 50;
                } else {
                    stringBuilder.append("$50: 0  ");
                }
                if (num >= 40) {
                    stringBuilder.append("$20: 2  ");
                    counter20 += 2;
                    num -= 40;
                } else if (num >=20){
                    stringBuilder.append("$20: 1  ");
                    counter20++;
                    num -= 20;

                } else {
                    stringBuilder.append("$20: 0  ");
                }

                if (num >= 10) {
                    stringBuilder.append("$10: 1  ");
                    counter10++;
                    num -= 10;
                } else {
                    stringBuilder.append("$10: 0  ");
                }

                if (num >= 5) {
                    stringBuilder.append("$5: 1  ");
                    counter5++;
                    num -= 5;
                } else {
                    stringBuilder.append("$5: 0  ");
                }
                stringBuilder.append("$1: ").append(num).append("\n");
                counter1 += num;
            }

            outputField.setText(stringBuilder.toString());
            counterField.setText("$50: " + counter50 + "\n" + "$20: " + counter20 + "\n" + "$10: " + counter10 + "\n"
                    + "$5: " + counter5 + "\n" + "$1: " + counter1 + "\n" + "Итого $: " + (counter50 * 50 + counter20 * 20 +
                    counter10 * 10 + counter5 * 5 + counter1));

        } catch (RuntimeException ex) {
            outputField.setText(ex.toString());
            counterField.clear();
        }
    }

    @FXML
    protected void onResetButtonClick() {
        inputField.clear();
        outputField.clear();
        counterField.clear();
        counter50 = 0;
        counter20 = 0;
        counter10 = 0;
        counter5 = 0;
        counter1 = 0;
    }
}