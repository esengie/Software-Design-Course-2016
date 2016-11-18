package ru.spbau.mit.Apps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.spbau.mit.AUI.TabController;
import ru.spbau.mit.Chat.Chat;
import ru.spbau.mit.Chat.ChatRepo;
import ru.spbau.mit.Client.JabClient;
import ru.spbau.mit.Client.JabClientImpl;
import ru.spbau.mit.Server.JabServer;
import ru.spbau.mit.Server.JabServerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ClientApp extends Application implements Observer {
    public static void main(String[] args) {
        launch(args);
    }

    private final ChatRepo repo = new ChatRepo();
    private final JabServer server = new JabServerImpl(repo);
    private final JabClient client = new JabClientImpl("Alex", repo);
//    private final List<TabController> tcs = new ArrayList<>();

    private final TabPane tabPane = new TabPane();
    private final Tab plusTab = new Tab();

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Jabber");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(createDisconnectedTab());
        addPlusTab();
        primaryStage.setScene(new Scene(tabPane, 300, 250));

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
                    if (newTab.equals(plusTab)) {
                        Tab addedTab = createDisconnectedTab();
                        addTabBeforePlus(addedTab);
                        tabPane.getSelectionModel()
                                .select(addedTab);
                    }
                });
    }
    private void addTabBeforePlus(Tab t){
        tabPane.getTabs().remove(plusTab);
        tabPane.getTabs().add(t);
        tabPane.getTabs().add(plusTab);
    }

    private void setupBackend() throws IOException {
        server.start((short)8081);
        repo.addObserver(this);
    }

    private Tab createDisconnectedTab() {
        Tab tab = new Tab();
        tab.setText("New");
        TextArea messageBox;
        return tab;
    }

    private void addTab(Chat chat){
        Tab tab = new Tab();
        addTabBeforePlus(tab);

        TabController tc = new TabController(tab);
        chat.addObserver(tc);
    }

    @Override
    public void update(Observable observable, Object o) {
        Chat c = (Chat) o;
        addTab(c);
    }
}