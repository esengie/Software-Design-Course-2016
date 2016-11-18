package ru.spbau.mit.Apps;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.spbau.mit.AUI.TabController;
import ru.spbau.mit.Chat.Chat;
import ru.spbau.mit.Chat.ChatRepo;
import ru.spbau.mit.Client.JabClient;
import ru.spbau.mit.Client.JabClientImpl;
import ru.spbau.mit.Server.JabServer;
import ru.spbau.mit.Server.JabServerImpl;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ClientApp extends Application implements Observer {
    public static void main(String[] args) {
        launch(args);
    }

    private final ChatRepo repo = new ChatRepo();
    private final JabServer server = new JabServerImpl(repo);
    private final short myServerPort = 8081;
    private final String myName = "Alex";

    private final TabPane tabPane = new TabPane();
    private final Tab plusTab = new Tab();

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Jabber");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(createDisconnectedTab());
        addPlusTab();
        primaryStage.setScene(new Scene(tabPane, 600, 400));

        setupBackend();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
        super.stop();
    }

    private void addPlusTab() {
        plusTab.setText("+");
        tabPane.getTabs().add(plusTab);
        tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldTab, newTab) -> {
                    if (newTab == null) {
                        return;
                    }
                    if (newTab.equals(plusTab)) {
                        Tab addedTab = createDisconnectedTab();
                        addTabBeforePlus(addedTab);
                    }
                });
    }

    private synchronized void addTabBeforePlus(Tab t) {
        tabPane.getTabs().add(t);
        tabPane.getTabs().remove(plusTab);
        tabPane.getTabs().add(plusTab);
        tabPane.getSelectionModel().select(t);
    }

    private void setupBackend() throws IOException {
        repo.addObserver(this);
        server.start(myServerPort);
    }

    private Tab createDisconnectedTab() {
        Tab tab = new Tab();
        tab.setText("New");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Connect");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label hostName = new Label("Host Name:");
        grid.add(hostName, 0, 1);

        TextField hostField = new TextField();
        grid.add(hostField, 1, 1);

        Label port = new Label("Port:");
        grid.add(port, 0, 2);

        TextField portField = new TextField();
        grid.add(portField, 1, 2);

        Button btn = new Button("Connect");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        btn.setOnAction(act -> {
            try {
                JabClient cl = new JabClientImpl(myName, myServerPort, repo);
                cl.connect("localhost", myServerPort);
//                cl.connect(hostField.getText(), Short.parseShort(portField.getText()));
                synchronized (this) {
                    cl.sendMessage(myName + " is connecting");
                    cl.disconnect();
                    tabPane.getTabs().remove(tab);
                }
            } catch (IOException | NumberFormatException e) {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Connection error");
            }
        });

        tab.setContent(grid);

        return tab;
    }

    private synchronized Tab createConnectedTab(Chat chat) {
        Tab tab = new Tab();
        tab.setText(chat.getFriendName());

        JabClient client = new JabClientImpl(myName, myServerPort, repo);
        client.connect(chat.getRemote().getHostName(), (short) chat.getRemote().getPort());

        TabController tc = new TabController(tab, client);

        chat.addObserver(tc);
        return tab;
    }

    @Override
    public void update(Observable observable, Object ob) {
        if (ob == null)
            throw new IllegalStateException("Need to have a chat passed");
        Chat chat = (Chat) ob;

        Platform.runLater(() -> addTabBeforePlus(createConnectedTab(chat)));
    }
}