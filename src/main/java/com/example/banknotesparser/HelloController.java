package com.example.banknotesparser;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloController {

    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputField;

    @FXML
    private TextArea counterField;

    @FXML
    private TextArea expectedField;
    @FXML
    private TextArea actualField;
    @FXML
    private TextArea testResultField;

    int counter50 = 0;
    int counter20 = 0;
    int counter10 = 0;
    int counter5 = 0;
    int counter1 = 0;

    @FXML
    protected void onHelloButtonClick() {

        AtomicInteger counter50 = new AtomicInteger();
        AtomicInteger counter20 = new AtomicInteger();
        AtomicInteger counter10 = new AtomicInteger();
        AtomicInteger counter5 = new AtomicInteger();
        AtomicInteger counter1 = new AtomicInteger();

        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> integerList;

        try {
            testResultField.setStyle("");
            actualField.clear();
            expectedField.clear();
            outputField.clear();
            counterField.clear();

            integerList = Arrays.asList(inputField.getText().split(",")).stream()
                    .map(Integer::parseInt)
                    .toList();

            int expected = integerList.stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            expectedField.setText("Ожидается $: " + expected);

            Thread thread = new Thread(() -> {
                for (Integer num : integerList) {

                    if (num >= 100 || num <= 0) {
                        stringBuilder.append("Некорректное значение. Требование: больше нуля и меньше 100!\n");
                        continue;
                    }
                    if (num >= 10) {
                        stringBuilder.append("Число: $").append(num).append("  ->  ");
                    } else {
                        stringBuilder.append("Число: $").append(num).append("    ->  ");
                    }

                    if (num >= 50) {
                        stringBuilder.append("$50: 1  ");
                        counter50.getAndIncrement();
                        num -= 50;
                    } else {
                        stringBuilder.append("$50: 0  ");
                    }
                    if (num >= 40) {
                        stringBuilder.append("$20: 2  ");
                        counter20.addAndGet(2);
                        num -= 40;
                    } else if (num >= 20) {
                        stringBuilder.append("$20: 1  ");
                        counter20.getAndIncrement();
                        num -= 20;

                    } else {
                        stringBuilder.append("$20: 0  ");
                    }

                    if (num >= 10) {
                        stringBuilder.append("$10: 1  ");
                        counter10.getAndIncrement();
                        num -= 10;
                    } else {
                        stringBuilder.append("$10: 0  ");
                    }

                    if (num >= 5) {
                        stringBuilder.append("$5: 1  ");
                        counter5.getAndIncrement();
                        num -= 5;
                    } else {
                        stringBuilder.append("$5: 0  ");
                    }
                    stringBuilder.append("$1: ").append(num).append("\n");
                    counter1.addAndGet(num);

                    Platform.runLater(() -> outputField.setText(stringBuilder.toString()));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                int actual = counter50.get() * 50 + counter20.get() * 20 +
                        counter10.get() * 10 + counter5.get() * 5 + counter1.get();
                actualField.setText("Итого $: " + actual);

                if (expected == actual) {
                    testResultField.setStyle("-fx-control-inner-background: #00FF00;");
                } else {
                    testResultField.setStyle("-fx-control-inner-background: #FF0000;");
                }

                counterField.setText("$50: " + counter50 + "\n" + "$20: " + counter20 + "\n" + "$10: " + counter10 + "\n"
                        + "$5: " + counter5 + "\n" + "$1: " + counter1 + "\n");
            });

            thread.start();

        } catch (RuntimeException ex) {
            outputField.setText(ex.toString());
            counterField.clear();
            testResultField.setStyle("");
            actualField.clear();
            expectedField.clear();
        }
    }

    @FXML
    protected void onResetButtonClick() {
        inputField.clear();
        outputField.clear();
        counterField.clear();
        testResultField.setStyle("");
        actualField.clear();
        expectedField.clear();
        counter50 = 0;
        counter20 = 0;
        counter10 = 0;
        counter5 = 0;
        counter1 = 0;
    }
}