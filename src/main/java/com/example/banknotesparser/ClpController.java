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
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.banknotesparser.HelloApplication.scene;

public class ClpController {

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

    AtomicInteger counter20000 = new AtomicInteger();
    AtomicInteger counter10000 = new AtomicInteger();
    AtomicInteger counter5000 = new AtomicInteger();
    AtomicInteger counter2000 = new AtomicInteger();
    AtomicInteger counter1000 = new AtomicInteger();
    AtomicInteger counter500 = new AtomicInteger();
    AtomicInteger counter100 = new AtomicInteger();
    AtomicInteger counter50 = new AtomicInteger();
    AtomicInteger counter10 = new AtomicInteger();

    AtomicInteger counterNum20000 = new AtomicInteger();
    AtomicInteger counterNum10000 = new AtomicInteger();
    AtomicInteger counterNum5000 = new AtomicInteger();
    AtomicInteger counterNum2000 = new AtomicInteger();
    AtomicInteger counterNum1000 = new AtomicInteger();
    AtomicInteger counterNum500 = new AtomicInteger();
    AtomicInteger counterNum100 = new AtomicInteger();
    AtomicInteger counterNum50 = new AtomicInteger();
    AtomicInteger counterNum10 = new AtomicInteger();
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


            counter20000.set(0);
            counter10000.set(0);
            counter5000.set(0);
            counter2000.set(0);
            counter1000.set(0);
            counter500.set(0);
            counter100.set(0);
            counter50.set(0);
            counter10.set(0);

            counterNum20000.set(0);
            counterNum10000.set(0);
            counterNum5000.set(0);
            counterNum2000.set(0);
            counterNum1000.set(0);
            counterNum500.set(0);
            counterNum100.set(0);
            counterNum50.set(0);
            counterNum10.set(0);


            numbersCounter.set(1);

            integerList = Arrays.asList(inputField.getText().split(",")).stream()
                    .map(Integer::parseInt)
                    .toList();

            int expected = integerList.stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            expectedSizeField.setText("Кол-во чисел: " + integerList.size());
            expectedField.setText("Ожидается CLP: " + expected);


            Thread thread = new Thread(() -> {
                for (Integer num : integerList) {

                    if (num <= 0) {
                        stringBuilder.append("Некорректное значение.\nТребование: больше нуля!\n");
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

                    if (num >= 20000) {
                        counterNum20000.set(num / 20000);

                        counter20000.addAndGet(counterNum20000.get());
                        num -= 20000 * counterNum20000.get();
                    }

                    if (num >= 19995) {
                        counterNum20000.incrementAndGet();
                        counter20000.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP20000: ").append(counterNum20000.get()).append(" ");

                    if (num >= 10000) {
                        counterNum10000.set(num / 10000);

                        counter10000.addAndGet(counterNum10000.get());
                        num -= 10000 * counterNum10000.get();
                    }

                    if (num >= 9995) {
                        counterNum10000.incrementAndGet();
                        counter10000.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP10000: ").append(counterNum10000.get()).append(" ");

                    if (num >= 5000) {
                        counterNum5000.set(num / 5000);

                        counter5000.addAndGet(counterNum5000.get());
                        num -= 5000 * counterNum5000.get();
                    }

                    if (num >= 4995) {
                        counterNum5000.incrementAndGet();
                        counter5000.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP5000: ").append(counterNum5000.get()).append(" ");


                    if (num >= 2000) {
                        counterNum2000.set(num / 2000);

                        counter2000.addAndGet(counterNum2000.get());
                        num -= 2000 * counterNum2000.get();
                    }

                    if (num >= 1995) {
                        counterNum2000.incrementAndGet();
                        counter2000.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP2000: ").append(counterNum2000.get()).append(" ");


                    if (num >= 1000) {
                        counterNum1000.set(num / 1000);

                        counter1000.addAndGet(counterNum1000.get());
                        num -= 1000 * counterNum1000.get();
                    }

                    if (num >= 995) {
                        counterNum1000.incrementAndGet();
                        counter1000.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP1000: ").append(counterNum1000.get()).append(" ");

                    if (num >= 500) {
                        counterNum500.set(num / 500);
                        counter500.addAndGet(counterNum500.get());
                        num -= 500 * counterNum500.get();
                    }

                    if (num >= 495) {
                        counterNum500.incrementAndGet();
                        counter500.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP500: ").append(counterNum500.get()).append(" ");

                    if (num >= 100) {
                        counterNum100.set(num / 100);
                        counter100.addAndGet(counterNum100.get());
                        num -= 100 * counterNum100.get();
                    }

                    if (num >= 95) {
                        counterNum100.incrementAndGet();
                        counter100.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP100: ").append(counterNum100.get()).append(" ");

                    if (num >= 50) {
                        counterNum50.set(num / 50);
                        counter50.addAndGet(counterNum50.get());
                        num -= 50 * counterNum50.get();
                    }

                    if (num >= 45) {
                        counterNum50.incrementAndGet();
                        counter50.incrementAndGet();
                        num -= num;
                    }
                    stringBuilder.append("CLP50: ").append(counterNum50.get()).append(" ");

                    if (num >= 10) {
                        counterNum10.set(num / 10);
                        counter10.addAndGet(counterNum10.get());
                        num -= 10 * counterNum10.get();
                    }

                    if (num >= 5) {
                        counterNum10.incrementAndGet();
                        counter10.incrementAndGet();
                    }
                    stringBuilder.append("CLP10: ").append(counterNum10.get()).append(" -> ");


                    int actualNum = counterNum20000.get() * 20000
                            + counterNum10000.get() * 10000
                            + counterNum5000.get() * 5000
                            + counterNum2000.get() * 2000
                            + counterNum1000.get() * 1000
                            + counterNum500.get() * 500
                            + counterNum100.get() * 100
                            + counterNum50.get() * 50
                            + counterNum10.get() * 10;

                    stringBuilder.append(actualNum).append("\n");

                    Platform.runLater(() -> {
                        outputField.setText(stringBuilder.toString());

                        actualSizeField.setText("Распарсено: " + numbersCounter.getAndIncrement());

                        counterNum20000.set(0);
                        counterNum10000.set(0);
                        counterNum5000.set(0);
                        counterNum2000.set(0);
                        counterNum1000.set(0);
                        counterNum500.set(0);
                        counterNum100.set(0);
                        counterNum50.set(0);
                        counterNum10.set(0);

                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                int actual = counter20000.get() * 20_000 +
                        counter10000.get() * 10_000 +
                        counter5000.get() * 5_000 +
                        counter2000.get() * 2_000 +
                        counter1000.get() * 1_000 +
                        counter500.get() * 500 +
                        counter100.get() * 100 +
                        counter50.get() * 50 +
                        counter10.get() * 10;
                actualField.setText("Итого CLP: " + actual);

                if ((expected == actual) && (integerList.size() == numbersCounter.get() - 1)) {
                    testResultField.setStyle("-fx-control-inner-background: #00FF00;");
                } else {
                    testResultField.setStyle("-fx-control-inner-background: #FF0000;");
                }

                counterField.setText(
                        "CLP20000: " + counter20000 + "\n" +
                        "CLP10000: " + counter10000 + "\n" +
                        "CLP5000: " + counter5000 + "\n" +
                        "CLP2000: " + counter2000 + "\n" +
                        "CLP1000: " + counter1000 + "\n" +
                        "CLP500: " + counter500 + "\n" +
                        "CLP100: " + counter100 + "\n" +
                        "CLP50: " + counter50 + "\n" +
                        "CLP10: " + counter10 + "\n");
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

        counter20000.set(0);
        counter10000.set(0);
        counter5000.set(0);
        counter2000.set(0);
        counter1000.set(0);
        counter500.set(0);
        counter100.set(0);
        counter50.set(0);
        counter10.set(0);

        counterNum20000.set(0);
        counterNum10000.set(0);
        counterNum5000.set(0);
        counterNum2000.set(0);
        counterNum1000.set(0);
        counterNum500.set(0);
        counterNum100.set(0);
        counterNum50.set(0);
        counterNum10.set(0);


        numbersCounter.set(1);
    }

    // Метод для создания PDF-документа на основе сцены
    @FXML
    protected void createPdfFromScene() {
        try (PDDocument document = new PDDocument()) {
            // Создаем страницу с размерами сцены
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Создаем WritableImage с учетом размера сцены
            WritableImage writableImage = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
            scene.snapshot(writableImage);

            // Конвертируем WritableImage в BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

            // Если размер изображения превышает страницу PDF, масштабируем его
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float scale = Math.min(pageWidth / bufferedImage.getWidth(), pageHeight / bufferedImage.getHeight());
            float scaledWidth = bufferedImage.getWidth() * scale;
            float scaledHeight = bufferedImage.getHeight() * scale;

            // Создаем PDImageXObject
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

            // Добавляем изображение на страницу PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float xOffset = (pageWidth - scaledWidth) / 2; // Центрирование по горизонтали
                float yOffset = (pageHeight - scaledHeight) / 2; // Центрирование по вертикали
                contentStream.drawImage(pdImage, xOffset, yOffset, scaledWidth, scaledHeight);
            }

            // Форматируем имя файла и сохраняем PDF
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            document.save(LocalDateTime.now().format(formatter) + ".pdf");
        } catch (IOException e) {
            outputField.setText(e.toString());
        }
    }

    @FXML
    public void switchToUSD(ActionEvent event) throws IOException {
        // Закрытие текущего окна
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Загрузка нового окна
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("usd.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.setTitle("USD");
        newStage.show();
    }
}