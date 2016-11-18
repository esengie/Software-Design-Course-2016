package ru.spbau.mit.AUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ru.spbau.mit.Apps.ClientApp;
import ru.spbau.mit.Chat.JabMessage;
import ru.spbau.mit.Client.JabClient;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabController implements Observer {
    private static final Logger logger = Logger.getLogger(ClientApp.class.getName());

    private final JabClient client;
    private final TextField inputWindow = new TextField();
    private final ListView chat = new ListView();

    public TabController(Tab tab, JabClient client) {
        inputWindow.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                onTextUpdate();
            }
        });

        this.client = client;

        VBox vbox = new VBox(chat);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(inputWindow);

        tab.setContent(root);
    }

    private void onTextUpdate() {
        try {
            client.sendMessage(inputWindow.getText());
            inputWindow.clear();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Couldn't send a message");
        }
    }

    private static String millisToStr(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    private void messageCameIn(JabMessage msg) {
        String s = String.format("%s at %s\n%s\n", msg.name, millisToStr(msg.time), msg.message);
        Text text2 = new Text(s);
        text2.setFill(Color.BLUE);
        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
        System.out.println(s);
        chat.getItems().add(text2);
    }

    @Override
    public void update(Observable observable, Object obj) {
        JabMessage message = (JabMessage) obj;
        Platform.runLater(() -> messageCameIn(message));
    }
}
