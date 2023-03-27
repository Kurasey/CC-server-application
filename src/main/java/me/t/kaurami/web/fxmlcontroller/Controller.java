package me.t.kaurami.web.fxmlcontroller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.data.ClientRepository;
import me.t.kaurami.data.RequestRepository;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.entities.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class Controller {

    private AgentRepository agentRepository;
    private ClientRepository clientRepository;
    private RequestRepository requestRepository;


    private FadeTransition ft;
    private static Preferences preferencesSizeTC = Preferences.userNodeForPackage(Controller.class);
    JFrame frame = new JFrame();

   /* @FXML
    private AnchorPane Pane;

    @FXML
    private ResourceBundle resources;*/

    @FXML
    private CheckBox CHBOnlyUnprocessed;

    @FXML
    private ListView EventListView;

    @FXML
    private TableView<Request> tableRequest;

/*

private AbstractStorage storage = DataUploader.connect();
    @FXML
    private TableColumn<Request, Integer> ColumnID;

    @FXML
    private TableColumn<Request, String> ColumnDate;

    @FXML
    private TableColumn<Request, String> ColumnAgentName;

    @FXML
    private TableColumn<Request, String> ColumnClientName;

    @FXML
    private TableColumn<Request, String> ColumnClientINN;

    @FXML
    private TableColumn<Request, String> ColumnClientOwner;

    @FXML
    private TableColumn<Request, Request.Operation> ColumnRequestOperation;

    @FXML
    private TableColumn<Request, String> ColumnRequestComment;

    @FXML
    private TableColumn<Request, Request.Decision> ColumnRequestDesicion;

    @FXML
    private TableColumn<Request, String> ColumnRequestCause;

    @FXML
    private TableColumn<Request, Date> ColumnRequestReminde;
*/

    @FXML
    private Button ButtonCreateRequest;

    @FXML
    private Menu MenuFile;

    @FXML
    private MenuItem MenuLoadDataFromTextFile;

    @FXML
    private MenuItem MenuLoadDataFromExcel;

    @FXML
    private MenuItem MenuExportData;

    @FXML
    private MenuItem MenuUpdateFromExcel;

    @FXML
    private MenuItem MenuSaveData;

    @FXML
    private MenuItem MenuRefreshLinks;

    @FXML
    private MenuItem MenuSetAgentData;

    @FXML
    private MenuItem MenuSetClientData;

    @FXML
    private MenuItem MenuSetExcelFromAccessData;

    @FXML
    private MenuItem MenuClearAgentData;

    @FXML
    private MenuItem MenuClearClientData;

    @FXML
    private MenuItem MenuClearRequestData;

    @FXML
    private MenuItem MenuCloseApp;

    @FXML
    private MenuItem MenuAddAgent;

    @FXML
    private MenuItem MenuAddSourc;

    @FXML
    private MenuItem MenuAddReminde;

    @FXML
    private MenuItem MenuAddCall;

    @FXML
    private MenuItem MenuAddDeception;

    @FXML
    private MenuItem MenuShowAgents;

    @FXML
    private MenuItem MenuShowClients;

    @FXML
    private MenuItem MenuShowOwners;

    @FXML
    private MenuItem MenuShowSource;

    @FXML
    private MenuItem MenuShowRemindes;

    @FXML
    private MenuItem MenuShowDeception;

    @FXML
    private MenuItem MenuShowCalls;

    @FXML
    private RadioMenuItem MenuDarkTheme;

    @FXML
    private MenuItem MenuContacts;

    @FXML
    private ComboBox<Agent> CBAgent;

    @FXML
    private ComboBox<Client> CBClient;

    @FXML
    private RadioButton RBStop;

    @FXML
    private ToggleGroup RequestRadio;

    @FXML
    private RadioButton RBLimit;

    @FXML
    private RadioButton RBSB;

    @FXML
    private RadioButton RBElse;

    @FXML
    private TextField TBComment;

    @FXML
    private TextField TBSearch;

    @FXML
    private ComboBox<?> CBSource;

    @FXML
    private DatePicker CDate;

    @FXML
    private Label StatisticAgent;

    @FXML
    private Label StatisticClient;

    @FXML
    private Label StatisticRequestAll;

    @FXML
    private Label StatisticRequestAgreed;

    @FXML
    private Label StatisticRequestDisAgreed;

    @FXML
    private Label StatisticRequestOnApproval;

    @FXML
    private Label StatisticRequestsLast30Days;

    @FXML
    private Label StatisticRequestsToday;

    @FXML
    private Label StatisticRequestsPastAgreed;

    @FXML
    private Button ButtonChangeRequest;

    @FXML
    private Button ButtonRemoveRequest;

    @FXML
    private Button ButtonRemoveForm;

    @FXML
    private Button ButtonClearSearchBar;

    @FXML
    void handleButtonChangeRequest(ActionEvent event) {
        try {
            if (ButtonChangeRequest.getText().equals("Изменить")) {
                CBAgent.setValue(tableRequest.getSelectionModel().getSelectedItem().getAgent());
                CBClient.setValue(tableRequest.getSelectionModel().getSelectedItem().getClient());
                TBComment.setText(tableRequest.getSelectionModel().getSelectedItem().getCommentary());
//                CDate.setValue(tableRequest.getSelectionModel().getSelectedItem().getDateReminde());
                switch (tableRequest.getSelectionModel().getSelectedItem().getType()) {
                    case STOPLIST:
                        RequestRadio.selectToggle(RBStop);
                        break;
                    case LIMIT:
                        RequestRadio.selectToggle(RBLimit);
                        break;
                    case SEQURITYORDER:
                        RequestRadio.selectToggle(RBSB);
                        break;
                    case OTHER:
                        RequestRadio.selectToggle(RBElse);
                }
                ButtonChangeRequest.setText("Принять");
            } else {
                Request request = tableRequest.getSelectionModel().getSelectedItem();
                request.setAgent(CBAgent.getSelectionModel().getSelectedItem());
                request.setClient(CBClient.getSelectionModel().getSelectedItem());
                request.setType(((RadioButton) RequestRadio.getSelectedToggle()).getText());
                request.setCommentary("" + TBComment.getText());
//                request.setDateReminde(CDate.getEditor().getText().length() > 1 ? CDate.getValue() : null);
/*                if (CDate.getEditor().getText().length() < 3) {
                    storage.removeRemindeFromList(request);
                } else {
                    request.setDateReminde(CDate.getValue());
                    request.setRemindeFromRequest(new RemindeFromRequest(request));
                }*/

                ButtonChangeRequest.setText("Изменить");
                clearForm();
                tableRequest.refresh();
            }
        } catch (Exception e) {
            ButtonChangeRequest.setText("Изменить");
            JOptionPane.showMessageDialog(frame, "Ошибка", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    @FXML
    void handleButtonCreateRequest(ActionEvent event) {
        StringBuilder commentary = new StringBuilder();
        try {
            Agent agent = CBAgent.getValue() != null ?
                    CBAgent.getSelectionModel().getSelectedItem() : CBClient.getValue().getAgent();
            Client client = CBClient.getSelectionModel().getSelectedItem();
            Request.RequestType requestType = Request.RequestType.valueOf(((RadioButton) RequestRadio.getSelectedToggle()).getText());
            if (requestType.equals(Request.RequestType.LIMIT)) {
                try {
                    LocalDate contractDate = clientRepository.findById(client.getAccessID()).get().getContractDate();
                    if (contractDate.plusYears(3).isBefore(LocalDate.now())) {
                        commentary.append("Договор от " + contractDate);
                        JOptionPane.showMessageDialog(frame, "Внимание! \tСрок договора с клиентом истек. Догоров от " + contractDate);
                    }
                } catch (NullPointerException e) {
                    commentary.append("С клиентом отсутствует договор");
                    JOptionPane.showMessageDialog(frame, "С клиентом отсутствует договор");
                }

            }
            LocalDate date = CDate.getValue();
            commentary.append(TBComment.getText());
            if (CBSource.getEditor().getText() != null && CBSource.getEditor().getText().length() > 1) {
                commentary.append("* от " + CBSource.getEditor().getText());
            }
            requestRepository.save(new Request(client, agent, requestType, commentary.toString()));
            fillTable();
            refreshStatistic();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Ошибка!", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @FXML
    void handleButtonRemoveForm(ActionEvent event) {
        clearForm();
    }

    @FXML
    void handleButtonRemoveRequest(ActionEvent event) {
        if (tableRequest.getItems() != null) {
            requestRepository.delete(tableRequest.getSelectionModel().getSelectedItem());
            fillTable();
            refreshStatistic();
        }
    }


    @FXML
    void handleCBAgent(ActionEvent event) {

    }

    @FXML
    void handleCBClient(ActionEvent event) {

    }

    @FXML
    void handleCBSource(ActionEvent event) {

    }

    @FXML
    void handleContextCBAgent(ContextMenuEvent event) {

    }

    @FXML
    void handleContextCBClient(ContextMenuEvent event) {

    }

    @FXML
    void handleContextCBSource(ContextMenuEvent event) {

    }

    @FXML
    void handleMenuAddAgent(ActionEvent event) {

    }

    @FXML
    void handleMenuAddCall(ActionEvent event) {

    }

    @FXML
    void handleMenuAddDeception(ActionEvent event) {

    }

    @FXML
    void handleMenuAddReminde(ActionEvent event) {
//        FormCreator.showAddReminde();
    }

    @FXML
    void handleMenuAddSourc(ActionEvent event) {

    }

    @FXML
    void handleMenuClearAgentData(ActionEvent event) {
/*        storage.clearAg();
        CBAgent.setItems(filterAgents());
        refreshStatistic();*/
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuClearClientData(ActionEvent event) {
/*        storage.clearCl();
        CBClient.setItems(filterClients());
        refreshStatistic();*/
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuClearRequestData(ActionEvent event) {
/*        storage.clearRe();
        fillTable();
        refreshStatistic();*/
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuCloseApp(ActionEvent event) {
//        Main.closeApp();
    }

    @FXML
    void handleMenuContacts(ActionEvent event) {
        JOptionPane.showMessageDialog(frame, "Ецков Дмитрий\nE-mail: d.yetskov@gmail.com");
    }

    @FXML
    void handleMenuDarkTheme(ActionEvent event) {
//        FormCreator.changeTheme(MenuDarkTheme.isSelected());
    }

    @FXML
    void handleMenuLoadDataFromTextFile(ActionEvent event) {
/*        DataUploader.fromTextFile();
        CBAgent.setItems(filterAgents());
        CBClient.setItems(filterClients());
        fillTable();
        refreshStatistic();*/
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuLoadDataFromExcel(ActionEvent event) {
//        DataUploader.fromExcel_97_03();
        fillAgents();
        fillClients();
        fillTable();
        refreshStatistic();
    }

    @FXML
    void handleMenuExportData(ActionEvent event) {
//        DataUploader.exportDBToExcel(new DirectoryChooser().showDialog(null).getPath());
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuUpdateFromExcel(ActionEvent event) {
//        DataUploader.updateDBCollectionsFromExcel_97_03();
        fillAgents();
        fillClients();
        fillTable();
        tableRequest.refresh();
        refreshStatistic();
    }

    @FXML
    void handleMenuRefreshLinks(ActionEvent event) {
//        storage.refreshLinks();
    }

    @FXML
    void handleMenuSaveData(ActionEvent event) {
//        storage.save();
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuSetAgentData(ActionEvent event) {
//        DataUploader.setTextFile("Agents", chooseFile());
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuSetClientData(ActionEvent event) {
//        DataUploader.setTextFile("Clients", chooseFile());
        throw new UnsupportedOperationException();
    }

    @FXML
    void handleMenuSetExcelFromAccessData(ActionEvent event) {
//        DataUploader.setExcelFile(chooseFile());
    } // Переработка

    @FXML
    void handleMenuShowAgents(ActionEvent event) {

    }

    @FXML
    void handleMenuShowCalls(ActionEvent event) {

    }

    @FXML
    void handleMenuShowClients(ActionEvent event) {

    }

    @FXML
    void handleMenuShowOwners(ActionEvent event) {

    }

    @FXML
    void handleMenuShowRemindes(ActionEvent event) {
//        FormCreator.showReminde();
    }

    @FXML
    void handleMenuShowSource(ActionEvent event) {

    }

    static <T, V> TableColumn<T, V> createColumn(String name, String property, String columnId) {
        TableColumn<T, V> column = new TableColumn<>(name);
        column.setId(columnId);
        column.setCellValueFactory(new PropertyValueFactory<T, V>(property));
        column.setPrefWidth(preferencesSizeTC.getDouble(columnId, column.getPrefWidth()));
        column.widthProperty().addListener(observable -> {
            preferencesSizeTC.putDouble(columnId, column.getWidth());
        });
        return column;
    }

    @FXML
    void initialize() {
        { /*fill data*/
            fillAgents();
            fillClients();
            fillTable();
        }

        { /*create columns*/
            TableColumn<Request, Request.RequestType> requestTypeColumn = Controller.<Request, Request.RequestType>createColumn("Операция", "operation", "operationColumn");
            TableColumn<Request, Request.Decision> requestDecisionColumn = Controller.<Request, Request.Decision>createColumn("Решение", "decision", "decisionColumn");
            TableColumn<Request, String> requestCauseColumn = Controller.<Request, String>createColumn("Обоснование", "decisionCause", "decisionCauseColumn");

            tableRequest.getColumns().add(Controller.<Request, Integer>createColumn("ID", "requestId", "requestIdColumn"));
            tableRequest.getColumns().add(Controller.<Request, String>createColumn("Дата", "requestDate", "requestDateColumn"));
            tableRequest.getColumns().add(Controller.<Request, String>createColumn("Агент", "agentName", "agentNameColumn"));
            tableRequest.getColumns().add(Controller.<Request, String>createColumn("Клиент", "clientName", "clientNameColumn"));              //?????
            tableRequest.getColumns().add(Controller.<Request, String>createColumn("ИНН", "clientINN", "clientInnColumn"));
            tableRequest.getColumns().add(Controller.<Request, String>createColumn("Хозяин сети", "ownerName", "ownerNameColumn"));
            tableRequest.getColumns().add(requestTypeColumn);
            tableRequest.getColumns().add(Controller.<Request, String>createColumn("Комментарий", "comment", "commentColumn"));
            tableRequest.getColumns().add(requestDecisionColumn);
            tableRequest.getColumns().add(requestCauseColumn);
            tableRequest.getColumns().add(Controller.<Request, Date>createColumn("Дата напоминания", "dateReminde", "dateRemindeColumn"));

            requestDecisionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Request, Request.Decision>, ObservableValue<Request.Decision>>() {
                @Override
                public ObservableValue<Request.Decision> call(TableColumn.CellDataFeatures<Request, Request.Decision> param) {
                    return new SimpleObjectProperty<Request.Decision>(param.getValue().getDecision());
                }
            });
            requestDecisionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Request.Decision.values())));
            requestDecisionColumn.setOnEditCommit(((TableColumn.CellEditEvent<Request, Request.Decision> event) -> {
                TablePosition<Request, Request.Decision> pos = event.getTablePosition();
                Request.Decision decision = event.getNewValue();
                int row = pos.getRow();
                Request request = event.getTableView().getItems().get(row);
                request.setDecision(decision);
                refreshStatistic();
                fillTable();
            }));

            requestTypeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Request, Request.RequestType>, ObservableValue<Request.RequestType>>() {
                @Override
                public ObservableValue<Request.RequestType> call(TableColumn.CellDataFeatures<Request, Request.RequestType> param) {
                    return new SimpleObjectProperty<Request.RequestType>(param.getValue().getType());
                }
            });

            requestCauseColumn.setCellValueFactory(new PropertyValueFactory<>("Cause"));
            requestCauseColumn.setCellFactory(TextFieldTableCell.<Request>forTableColumn());
            requestCauseColumn.setOnEditCommit(((TableColumn.CellEditEvent<Request, String> event) -> {
                TablePosition<Request, String> pos = event.getTablePosition();
                String cause = event.getNewValue();
                int row = pos.getRow();
                Request request = event.getTableView().getItems().get(row);
                request.setDecisionCause(cause);
            }));

        }

        createAnimations();
        frame.setAlwaysOnTop(true);
        EventListView.setPlaceholder(new Label("...все события обработаны..."));
        tableRequest.setEditable(true);
        tableRequest.setPlaceholder(new Label("Все текущие запросы обработаны"));




        CBAgent.setCellFactory(p -> new ListCell<Agent>() {
            @Override
            protected void updateItem(Agent item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getShortName());
                } else {
                    setText(null);
                }
            }
        });

        CBClient.setCellFactory(p -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getAccessID() + ": " + item.getName() + " /-\\ " + item.getAddress());
                } else {
                    setText(null);
                }
            }
        });

        CBAgent.setConverter(new StringConverter<Agent>() {
            @Override
            public String toString(Agent object) {
                return object == null ? "" : object.getShortName();
            }

            @Override
            public Agent fromString(String string) {
                for (Agent agent : CBAgent.getItems()) {
                    if (agent.getShortName().equalsIgnoreCase(string)) {
                        return agent;
                    }
                }
                return null;
            }
        });
        CBClient.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Client fromString(String string) {
                for (Client client : CBClient.getItems()) {
                    if (client.getName().equalsIgnoreCase(string)) {
                        return client;
                    }
                }
                return null;
            }
        });
        setListeners();
        refreshStatistic();
    }


    private void setListeners() {

//        FormCreator.getStageMain().setOnShown(event -> fillTable());


        CBAgent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fillClients();
        });
        CBClient.setOnKeyReleased(event -> {
            fillClients();
            CBClient.show();
        });
        CBAgent.setOnKeyReleased(event -> {
            fillAgents();
            CBAgent.show();
        });

        tableRequest.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && event.getButton() == MouseButton.SECONDARY) {
                if (tableRequest.getSelectionModel().getSelectedCells().get(0).getTableColumn().getId().equalsIgnoreCase("clientInnColumn")) {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(
                            tableRequest.getSelectionModel().getSelectedItem().getClient().getIndividualTaxpayerNumber()), null);
                } else if (tableRequest.getSelectionModel().getSelectedCells().get(0).getTableColumn().getId().equalsIgnoreCase("clientNameColumn")) {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(
                            tableRequest.getSelectionModel().getSelectedItem().getClient().getName()), null);
                }
            }
        });

        CHBOnlyUnprocessed.setOnAction(event -> fillTable());
/*//        FormCreator.getStageReminde().setOnHiding(event -> checkEvent());

        EventListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (storage.getRemindersMap().get(EventListView.getSelectionModel().getSelectedItem().toString().replaceAll(": \\d+", "")).get(0).getClass().equals(RemindeFromRequest.class)) {
                    FormCreator.showReminde();
                }
            }
        });*/

        TBSearch.setOnKeyReleased(event -> {
            if (TBSearch.getText().length() > 0) {
                fillTable(TBSearch.getText());
            } else {
                fillTable();
            }
        });

        ButtonClearSearchBar.setOnAction(event -> {
            TBSearch.clear();
            fillTable();
        });
    }

    private void refreshStatistic() {
        StatisticAgent.setText("" + agentRepository.count());
        StatisticClient.setText("" + clientRepository.count());
        StatisticRequestAll.setText("" + requestRepository.count());
        int Agreedrequests = 0;
        int DisagreedRequests = 0;
        int OnApprovalRequests = 0;
        int Inlast30DaysRequests = 0;
        int TodayRequest = 0;
        try {
            for (Request request : requestRepository.findAll()) {
                switch (request.getDecision()) {
                    case AGREED:
                        Agreedrequests += 1;
                        break;
                    case DISAGREED:
                        DisagreedRequests += 1;
                        break;
                    case APPROVAL:
                        OnApprovalRequests += 1;
                        break;
                    case ND:
                        OnApprovalRequests += 1;

                }
                if (request.getRequestDate().toLocalDate().isAfter(LocalDate.now().minusDays(30))) {
                    Inlast30DaysRequests += 1;
                }
                if (request.getRequestDate().toLocalDate().equals(LocalDate.now())) {
                    TodayRequest += 1;
                }
            }
        } catch (Exception e) {
        }
        StatisticRequestAgreed.setText("" + Agreedrequests);
        StatisticRequestDisAgreed.setText("" + DisagreedRequests);
        StatisticRequestOnApproval.setText("" + OnApprovalRequests);
        StatisticRequestsLast30Days.setText("" + Inlast30DaysRequests);
        StatisticRequestsToday.setText("" + TodayRequest);
        if (Agreedrequests > 0 || DisagreedRequests > 0) {
            StatisticRequestsPastAgreed.setText("" + String.format("%.2f", (double) Agreedrequests / (Agreedrequests + DisagreedRequests) * 100) + "%");
        } else {
            StatisticRequestsPastAgreed.setText("" + (Agreedrequests > 0 ? 100 : 0) + "%");
        }
        checkEvent();
    }

    private static File chooseFile() {
        FileChooser chooser = new FileChooser();
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("TEXT FILES(*.txt)", "*.txt"));
        return chooser.showOpenDialog(null);
    }

/*    private ObservableList<Client> filterClients() {
        if (CBAgent.getValue() == null) {
            if (CBClient.getEditor().getText() == null) {
                return storage.getClientList();
            }
            return storage.getClientList(null, CBClient.getEditor().getText());
        }
        return storage.getClientList(CBAgent.getValue().getAgentName(), CBClient.getEditor().getText());
    }*/

    private void fillClients(){
        Iterable<Client> clients;
        List<Client> clientList;
        if (CBClient.getEditor().getText() == null) {
            clients = clientRepository.findAll();
        } else {
            clients = clientRepository.findByNameContaining(CBClient.getEditor().getText());
        }
        if (CBAgent.getValue()!=null){
            clients = StreamSupport
                    .stream(clients.spliterator(), false)
                    .filter(client -> client.getAgentName().equals(CBAgent.getValue().getAgentName()))
                    .collect(Collectors.toList());
        }
        CBClient.setItems(FXCollections.observableList(StreamSupport
                .stream(clients.spliterator(), false).collect(Collectors.toList())));

    }

    private void fillAgents() {
        Iterable<Agent> agents;
        if (CBAgent.getEditor().getText().length() > 0) {
           agents = agentRepository.findByAgentNameContaining(CBAgent.getEditor().getText());
        }else {
            agents = agentRepository.findAll();
        }
        CBAgent.setItems(FXCollections.observableList(StreamSupport.stream(agents.spliterator(),false).collect(Collectors.toList())));
    }

    private void clearForm() {
        CBAgent.setValue(null);
        CBAgent.getEditor().setText(null);
        CBClient.setValue(null);
        CBClient.getEditor().setText(null);
        CDate.setValue(null);
        RequestRadio.selectToggle(null);
        TBComment.setText("");
        CBSource.setValue(null);
        CBSource.getEditor().setText(null);
        ButtonChangeRequest.setText("Изменить");
    }

    private void fillTable() {
        if (CHBOnlyUnprocessed.isSelected()) {
            tableRequest.setItems(FXCollections.observableList(StreamSupport.stream(requestRepository.findAll().spliterator(), false).filter(request -> !request.getDecision().isCompleted()).collect(Collectors.toList())));
        } else {
            tableRequest.setItems(FXCollections.observableList(StreamSupport.stream(requestRepository.findAll().spliterator(), false).collect(Collectors.toList())));
        }
    }

    private void fillTable(String containingText) {
        StreamSupport.stream(requestRepository.findAll().spliterator(), false).filter(request -> (request.getAgent().getShortName().toLowerCase() + " " + request.getClient().getName().toLowerCase()).contains(containingText.toLowerCase()));
    }

    private void createAnimations() {
        Duration duration = new Duration(1500.0);
        ft = new FadeTransition(duration, EventListView);
        ft.setDelay(duration);
        ft.setAutoReverse(true);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(Animation.INDEFINITE);
    }

    private void checkEvent() {
        /*ObservableList<String> eventList = FXCollections.observableArrayList();
        int size = 0;
        for (Map.Entry entry : storage.getRemindersMap().entrySet()) {
            if ((size = ((ArrayList<Reminde>) entry.getValue()).stream().filter(reminde -> (reminde.getDateReminde().isBefore(LocalDate.now()) || reminde.getDateReminde().isEqual(LocalDate.now())) && reminde.getStatus() != RemindeFromRequest.RemindeStatus.PROCESSED).collect(Collectors.toList()).size()) > 0) {
                eventList.add(entry.getKey() + ": " + size);
            }
        }
        if (eventList.size() > 0) {
            ft.play();
            EventListView.setVisible(true);
        } else {
            ft.stop();
            EventListView.setVisible(false);
        }
        EventListView.setItems(eventList);*/
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text);
        alert.showAndWait();
    }
}

