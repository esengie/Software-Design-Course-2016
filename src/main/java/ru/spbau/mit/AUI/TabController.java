package ru.spbau.mit.AUI;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.spbau.mit.Apps.ClientApp;
import ru.spbau.mit.Chat.Chat;
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
    private final ListView chatContents = new ListView();
    private final Tab tab;
    private final Chat chat;

    public TabController(Tab tab, JabClient client, Chat chat) {
        this.tab = tab;
        this.chat = chat;
        this.tab.setText(chat.getFriendName());

        inputWindow.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                onTextUpdate();
            }
        });

        this.client = client;

        VBox vbox = new VBox(chatContents);

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

    private synchronized void messageCameIn(JabMessage msg) {
        String friendName = chat.getFriendName();
        if (!tab.getText().equals("") && !tab.getText().equals(friendName)){
            printHelperMessage(String.format("%s changed his name to %s\n", tab.getText(), friendName));
        }
        tab.setText(friendName);
        printMessage(msg);
    }

    private void printHelperMessage(String s){
        Text text2 = new Text(s);
        text2.setFill(Color.BLUE);
        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
        chatContents.getItems().add(text2);
    }

    private void printMessage(JabMessage msg) {
        printHelperMessage(String.format("%s at %s\n%s\n",
                msg.name.equals("") ? client.getMyName() : chat.getFriendName(), millisToStr(msg.time), msg.message));
    }

    @Override
    public void update(Observable observable, Object obj) {
        JabMessage message = (JabMessage) obj;
        Platform.runLater(() -> messageCameIn(message));
    }
}
