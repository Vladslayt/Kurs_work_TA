package com.example.kurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

/*    private static final String STYLE_FOR_AVAILABLE_BUTTON = "activePanelButton";
    private static final String STYLE_FOR_UNAVAILABLE_BUTTON = "notActivePanelButton";*/


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
    private Label welcomeText;
    @FXML
    private Circle personCircle;
    @FXML
    private Rectangle workRectangle;
    @FXML
    private Rectangle projectRectangle;
    @FXML
    private Rectangle walkRectangle;
    @FXML
    private Rectangle sleepRectangle;
    @FXML
    private Rectangle eatRectangle;
    @FXML
    private TextField infoTextField;
    @FXML
    private TextField eatTextField;
    @FXML
    private TextField tiredTextField;
    @FXML
    private TextField moneyTextField;

    private static ScheduledExecutorService executorService;
    private static ScheduledFuture<?> future;

    public void startTimer(int seconds) {
        future = executorService.schedule(() -> {
            System.out.println("Таймер закончился");
            if(valueMoney == 0){
                if(valueEat - 10 <= 0){
                    valueEat = 0;
                    infoTextField.setText("Бездействие. Срочно нужно кушать!!!");
                }
                else {
                    valueEat -= 10;
                    infoTextField.setText("Бездействие. Голод увеличился.");
                }
            }
            else{
                drawPersonInPlace(Place.SLEEP);
                infoTextField.setText("Бездействие. Пошёл спать");
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
            Place.WORK, new Point(47 + 108 / 2, 58 + 64 * 3 / 4),
            Place.PROJECT, new Point(180 + 108 / 2, 58 + 64 * 3 / 4),
            Place.EAT, new Point(315 + 108 / 2, 58 + 64 * 3 / 4),
            Place.SLEEP, new Point(101 + 108 / 2, 149 + 64 * 3 / 4),
            Place.WALK, new Point(261 + 108 / 2, 149 + 64 * 3 / 4)
    );

    private final int EAT_MAX = 100;
    private final int TIRED_MAX = 100;
    private final int MONEY_MAX = 150;
    private int valueEat = 0;
    private int valueTired = 0;
    private int valueMoney = 10;



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
            infoTextField.setText("Работа");
        } else if (valueEat == EAT_MAX) {
            infoTextField.setText("Голод максимален, нужно поесть!");
            /*drawPersonInPlace(Place.EAT);
            goEatButtonIsClicked(event);*/
        } else {
            infoTextField.setText("Усталость максимальна, нужно поспать!");
            /*drawPersonInPlace(Place.SLEEP);
            goToSleepButtonIsClicked(event);*/
        }
    }
    @FXML
    void goEatButtonIsClicked(ActionEvent event) {
        updateTimer(10);
        infoTextField.setText("Еда");
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
            infoTextField.setText("Сон");
        } else {
            infoTextField.setText("Голод максимален, нужно поесть!");
            /*drawPersonInPlace(Place.EAT);
            goEatButtonIsClicked(event);*/
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
            drawPersonInPlace(Place.PROJECT);
            infoTextField.setText("Работа над своим проектом");
        } else if (valueEat == EAT_MAX) {
            infoTextField.setText("Голод максимален, нужно поесть!");
            /*drawPersonInPlace(Place.EAT);
            goEatButtonIsClicked(event);*/
        } else {
            infoTextField.setText("Усталость максимальна, нужно поспать!");
            /*drawPersonInPlace(Place.SLEEP);
            goToSleepButtonIsClicked(event);*/
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
            infoTextField.setText("На прогулке");
        } else if (valueEat == EAT_MAX) {
            infoTextField.setText("Голод максимален, нужно поесть!");
            /*drawPersonInPlace(Place.EAT);
            goEatButtonIsClicked(event);*/
        } else {
            infoTextField.setText("Усталость максимальна, нужно поспать!");
            /*drawPersonInPlace(Place.SLEEP);
            goToSleepButtonIsClicked(event);*/
        }
    }

    private void drawPersonInPlace(Place place) {
        Platform.runLater(() -> {
            Point placePoint = placesAndItsMapPoints.get(place);
            personCircle.setCenterX(placePoint.getX());
            personCircle.setCenterY(placePoint.getY());
        });
        updateTextField();
    }
    private void updateTextField(){
        eatTextField.setText(String.valueOf(valueEat));
        tiredTextField.setText(String.valueOf(valueTired));
        moneyTextField.setText(String.valueOf(valueMoney));
    }

}