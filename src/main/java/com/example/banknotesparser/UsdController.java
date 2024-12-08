package com.example.banknotesparser;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.banknotesparser.HelloApplication.scene;

public class UsdController {

    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputField;

    @FXML
    private TextArea counterField;
    @FXML
    private TextField expectedSizeField;

    @FXML
    private TextField expectedField;

    @FXML
    private TextField actualSizeField;

    @FXML
    private TextField actualField;
    @FXML
    private TextArea testResultField;

    @FXML
    private TextField dataField;

    AtomicInteger counter50 = new AtomicInteger();
    AtomicInteger counter20 = new AtomicInteger();
    AtomicInteger counter10 = new AtomicInteger();
    AtomicInteger counter5 = new AtomicInteger();
    AtomicInteger counter1 = new AtomicInteger();
    AtomicInteger numbersCounter = new AtomicInteger();

    public void initialize() {

        // Создаем форматтер для указанного формата "день месяц год часы минуты"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

        // Форматируем дату и время с помощью форматтера
        dataField.setText(LocalDateTime.now().format(formatter));
        dataField.setStyle("-fx-background-color: transparent; -fx-text-fill: blue");
    }

    @FXML
    protected void onProcessButtonClick() {


        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> integerList;


        try {
            testResultField.setStyle("");
            actualField.clear();
            expectedField.clear();
            outputField.clear();
            counterField.clear();
            actualSizeField.clear();
            expectedSizeField.clear();

            counter50.set(0);
            counter20.set(0);
            counter10.set(0);
            counter5.set(0);
            counter1.set(0);
            numbersCounter.set(1);

            integerList = Arrays.asList(inputField.getText().split(",")).stream()
                    .map(Integer::parseInt)
                    .toList();

            int expected = integerList.stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            expectedSizeField.setText("Кол-во чисел: " + integerList.size());
            expectedField.setText("Ожидается $: " + expected);


            Thread thread = new Thread(() -> {
                for (Integer num : integerList) {

                    if (num >= 100 || num <= 0) {
                        stringBuilder.append("Некорректное значение. Требование: больше нуля и меньше 100!\n");
                        outputField.setText(stringBuilder.toString());
                        continue;
                    }
                    if (numbersCounter.get() <= 9) {
                        if (num >= 10) {
                            stringBuilder.append("  " + numbersCounter + ") Число: $").append(num).append("  ->  ");
                        } else {
                            stringBuilder.append("  " + numbersCounter + ") Число: $").append(num).append("    ->  ");
                        }
                    } else {
                        if (num >= 10) {
                            stringBuilder.append(numbersCounter + ") Число: $").append(num).append("  ->  ");
                        } else {
                            stringBuilder.append(numbersCounter + ") Число: $").append(num).append("    ->  ");
                        }
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

                    Platform.runLater(() -> {
                        outputField.setText(stringBuilder.toString());

                        actualSizeField.setText("Распарсено: " + numbersCounter.getAndIncrement());
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                int actual = counter50.get() * 50 + counter20.get() * 20 +
                        counter10.get() * 10 + counter5.get() * 5 + counter1.get();
                actualField.setText("Итого $: " + actual);

                if ((expected == actual) && (integerList.size() == numbersCounter.get() - 1)) {
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
        actualSizeField.clear();
        expectedSizeField.clear();
        counter50.set(0);
        counter20.set(0);
        counter10.set(0);
        counter5.set(0);
        counter1.set(0);
        numbersCounter.set(1);
    }

    // Метод для создания PDF-документа на основе сцены
    @FXML
    protected void createPdfFromScene() {

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Создание WritableImage из сцены
            WritableImage writableImage = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
            scene.snapshot(writableImage);

            // Создание объекта PDImageXObject из изображения
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, SwingFXUtils.fromFXImage(writableImage, null));

            // Добавление изображения на страницу PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth(), pdImage.getHeight());
            }
            // Сохранение PDF-документа

            // Создаем форматтер для указанного формата "день месяц год часы минуты"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");

            // Форматируем дату и время с помощью форматтера
            document.save(LocalDateTime.now().format(formatter) + ".pdf");
        } catch (IOException e) {
            outputField.setText(e.toString());
        }
    }

    @FXML
    public void switchToCLP(ActionEvent event) throws IOException {
        // Закрытие текущего окна
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Загрузка нового окна
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clp.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.setTitle("CLP");
        newStage.show();
    }
}