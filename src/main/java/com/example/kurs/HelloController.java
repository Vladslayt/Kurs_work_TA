package com.example.kurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.Data;
import javafx.application.Platform;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Data
class Point {
    private final double x;
    private final double y;
}

public class HelloController {
    private enum Place {
        WORK("Пойти на работу"),
        PROJECT("Заняться проектом"),
        EAT("Пойти кушать"),
        SLEEP("Пойти спать"),
        WALK("Прогуляться");

        Place(String place) {
        }
    }

    @FXML
    private Button goToWorkButton;
    @FXML
    private Button doYourProjectButton;
    @FXML
    private Button goEatButton;
    @FXML
    private Button goToSleepButton;
    @FXML
    private Button goToWalkButton;
    @FXML
    private TextField infoTextField;
    @FXML
    private TextField eatTextField;
    @FXML
    private TextField tiredTextField;
    @FXML
    private TextField moneyTextField;
    @FXML
    private ImageView houseImage;
    @FXML
    private ImageView manImage;
    private static ScheduledExecutorService executorService;
    private static ScheduledFuture<?> future;

    public void startTimer(int seconds) {
        future = executorService.schedule(() -> {
//            System.out.println("10 секунд прошло");
            if(valueMoney == 0){
                if(valueEat - 10 <= 0){
                    valueEat = 0;
                    drawPersonInPlace(Place.EAT);
                    infoTextField.setText("Бездействие. Срочно кушать!!!");
                }
                else {
                    valueEat -= 10;
                    infoTextField.setText("Бездействие. Голод увеличился.");
                }
            }
            else{
                drawPersonInPlace(Place.SLEEP);
                infoTextField.setText("Бездействие. Пошел спать!!!");
            }
            updateTextField();
            updateTimer(10);
        }, seconds, TimeUnit.SECONDS);
    }

    public void updateTimer(int seconds) {
        if(future != null)
            future.cancel(false);
        startTimer(seconds);
    }
    @FXML
    public void initialize() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        startTimer(10);
    }

    private final Map<Place, Point> placesAndItsMapPoints = Map.of(
            Place.WORK, new Point(265, 110),
            Place.PROJECT, new Point(150, 175),
            Place.EAT, new Point(155, 245),
            Place.SLEEP, new Point(380, 115),
            Place.WALK, new Point(255, 440)
    );

    private final int EAT_MAX = 100;
    private final int TIRED_MAX = 100;
    private final int MONEY_MAX = 10000;
    private int valueEat = 0;
    private int valueTired = 0;
    private int valueMoney = 50;



    @FXML
    void goToWorkButtonIsClicked(ActionEvent event) {
        updateTimer(10);
        if (valueEat != EAT_MAX && valueTired != TIRED_MAX) {
            if (valueEat + 5 >= EAT_MAX)
                valueEat = EAT_MAX;
            else
                valueEat += 5;

            if (valueTired + 10 >= TIRED_MAX)
                valueTired = TIRED_MAX;
            else
                valueTired += 10;
            valueMoney += 50;
            drawPersonInPlace(Place.WORK);
            infoTextField.setText("Работать");
        } else if (valueEat == EAT_MAX) {
            infoTextField.setText("Голод максимален, нужно поесть!");
        } else {
            infoTextField.setText("Усталость максимальна, нужно поспать!");
        }
    }
    @FXML
    void goEatButtonIsClicked(ActionEvent event) {
        updateTimer(10);
        infoTextField.setText("Есть");
        if(valueMoney - 10 <= 0){
            infoTextField.setText("Нужны деньги, чтобы поесть!!!");
        }
        else {
            if(valueEat - 50 <= 0)
                valueEat = 0;
            else
                valueEat -= 50;
            valueTired += 2;
            valueMoney -= 10;
            drawPersonInPlace(Place.EAT);
        }

    }

    @FXML
    void goToSleepButtonIsClicked(ActionEvent event) {
        updateTimer(10);
        if (valueEat != EAT_MAX) {
            if (valueEat + 5 >= EAT_MAX)
                valueEat = EAT_MAX;
            else
                valueEat += 5;

            if (valueTired - 50 <= 0)
                valueTired = 0;
            else
                valueTired -= 50;
            drawPersonInPlace(Place.SLEEP);
            infoTextField.setText("Спать");
        } else {
            infoTextField.setText("Голод максимален, нужно поесть!");
        }
    }
    @FXML
    void doYourProjectButtonIsClicked(ActionEvent event) {
        updateTimer(10);
        if (valueEat != EAT_MAX && valueTired != TIRED_MAX) {
            if (valueEat + 5 >= EAT_MAX)
                valueEat = EAT_MAX;
            else
                valueEat += 5;

            if (valueTired + 5 >= TIRED_MAX)
                valueTired = TIRED_MAX;
            else
                valueTired += 5;
            valueMoney += 10;
            drawPersonInPlace(Place.PROJECT);
            infoTextField.setText("Работать над своим проектом");
        } else if (valueEat == EAT_MAX) {
            infoTextField.setText("Голод максимален, нужно поесть!");
        } else {
            infoTextField.setText("Усталость максимальна, нужно поспать!");
        }
    }

    @FXML
    void goToWalkButtonIsClicked(ActionEvent event) {
        updateTimer(10);
        if (valueEat != EAT_MAX && valueTired != TIRED_MAX) {
            if (valueEat + 5 >= EAT_MAX)
                valueEat = EAT_MAX;
            else
                valueEat += 5;

            if (valueTired + 5 >= TIRED_MAX)
                valueTired = TIRED_MAX;
            else
                valueTired += 5;
            drawPersonInPlace(Place.WALK);
            infoTextField.setText("Гулять");
        } else if (valueEat == EAT_MAX) {
            infoTextField.setText("Голод максимален, нужно поесть!");
        } else {
            infoTextField.setText("Усталость максимальна, нужно поспать!");
        }
    }

    private void drawPersonInPlace(Place place) {
        Platform.runLater(() -> {
            Point placePoint = placesAndItsMapPoints.get(place);
            manImage.setX(placePoint.getX());
            manImage.setY(placePoint.getY());
        });
        updateTextField();
    }
    private void updateTextField(){
        eatTextField.setText(String.valueOf(valueEat));
        tiredTextField.setText(String.valueOf(valueTired));
        moneyTextField.setText(String.valueOf(valueMoney));
    }

}