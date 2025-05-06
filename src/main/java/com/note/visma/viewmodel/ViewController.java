package com.note.visma.viewmodel;

import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.model.StillingDom;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewController {

    private ViewModel viewModel;

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
        initializeTables();
        loadTables();
    }

    @FXML
    private TableView<AnsattDom> ansattTable;
    @FXML
    private TableColumn<AnsattDom, Integer> ansattIdCol;
    @FXML
    private TableColumn<AnsattDom, String> fornavnCol;
    @FXML
    private TableColumn<AnsattDom, String> etternavnCol;

    @FXML
    private TableView<StillingDom> stillingTable;
    @FXML
    private TableColumn<StillingDom, Integer> stillingIdCol;
    @FXML
    private TableColumn<StillingDom, String> stillingNavnCol;
    @FXML
    private TableColumn<StillingDom, Integer> stillingAnsattIdCol;
    @FXML
    private TableColumn<StillingDom, LocalDate> stillingStartCol;
    @FXML
    private TableColumn<StillingDom, LocalDate> stillingSluttCol;

    @FXML
    private TableView<OppgaveDom> oppgaveTable;
    @FXML
    private TableColumn<OppgaveDom, Integer> oppgaveIdCol;
    @FXML
    private TableColumn<OppgaveDom, String> oppgaveNavnCol;
    @FXML
    private TableColumn<OppgaveDom, Integer> oppgaveAnsattIdCol;
    @FXML
    private TableColumn<OppgaveDom, LocalDate> oppgaveStartCol;
    @FXML
    private TableColumn<OppgaveDom, LocalDate> oppgaveSluttCol;

    @FXML
    private TextField oppgaveNavnField;
    @FXML
    private DatePicker oppgaveStartPicker;
    @FXML
    private DatePicker oppgaveSluttPicker;
    @FXML
    private Button opprettNyOppgaveButton;
    @FXML
    private TextField oppgaveIdField;
    @FXML
    private Button deleteOppgaveButton;
    @FXML
    private TextField stillingnavnField;
    @FXML
    private DatePicker stillingStartPicker;
    @FXML
    private DatePicker stillingSluttPicker;
    @FXML
    private Button opprettNyStillingButton;
    @FXML
    private Button slettStillingButton;
    @FXML
    private TextField stillingIdField;
    @FXML
    private TextField fornavnField;
    @FXML
    private TextField etternavnField;
    @FXML
    private ToggleButton leggTilAnsattButton;
    @FXML
    private ToggleButton slettAnsattButton;
    @FXML
    private TextField ansattIdField;
    @FXML
    private TextField searchTableField;
    @FXML
    private ChoiceBox<AnsattDom> ansattChoiceBox;
    @FXML
    private ChoiceBox<StillingDom> stillingChoiceBox;

    private boolean ansattVindu = false;
    private boolean stillingVindu = false;
    private boolean oppgaveVindu = false;

    private void initializeTables() {

        ansattIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().ansattID()));
        fornavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fornavn()));
        etternavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().etternavn()));

        // STILLING
        stillingIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().stillingID()));
        stillingNavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().stillingNavn()));
        stillingAnsattIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().ansattID()));
        stillingStartCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startDato()));
        stillingSluttCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().sluttDato()));

        // OPPGAVE
        oppgaveIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().oppgaveID()));
        oppgaveNavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().oppgaveNavn()));
        oppgaveAnsattIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().ansattID()));
        oppgaveStartCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startDato()));
        oppgaveSluttCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().sluttDato()));
        try {
            populateAnsattChoiceBox();
        } catch (Exception e) {
            System.out.println("Oopsie tihi");
        }
    }

    public void loadTables() {
        try {
            ansattTable.getItems().setAll(viewModel.getAnsatte());
            stillingTable.getItems().setAll(viewModel.getStillinger());
            oppgaveTable.getItems().setAll(viewModel.getOppgaver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSearchTable() {
        if(ansattVindu) {
            try {
                ansattTable.getItems().setAll(viewModel.searchAnsatt(searchTableField.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(stillingVindu) {
            try {
                stillingTable.getItems().setAll(viewModel.searchStilling(searchTableField.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(oppgaveVindu) {
            try {
                oppgaveTable.getItems().setAll(viewModel.searchOppgave(searchTableField.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void populateAnsattChoiceBox() throws Exception {

        List<AnsattDom> ansatt = viewModel.gettingAvailible();
        ansatt.removeIf(Objects::isNull);
        ObservableList<AnsattDom> list = FXCollections.observableArrayList(ansatt);
        ansattChoiceBox.setItems(list);
        ansattChoiceBox.setConverter(new StringConverter<AnsattDom>() {
            @Override
            public String toString(AnsattDom a) {
                if (a == null) {
                    return "";
                }
                return "[" + a.ansattID() + "] " + a.fornavn() + " " + a.etternavn();
            }
            @Override
            public AnsattDom fromString(String string) {
                return null;
            }
        });

    }


    @FXML
    private void onOprettAnsatt() {
        try {
            AnsattDom ansatt = new AnsattDom(
                    0, fornavnField.getText(), etternavnField.getText()
            );

            if(fornavnField.getText().isEmpty() || etternavnField.getText().isEmpty()) {
                System.out.println("Vennligst fyll ut alle feltene");
                return;
            }
            viewModel.addAnsatt(ansatt);
            loadTables();
            fornavnField.clear();
            etternavnField.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ansatt opprettet");
            alert.setHeaderText(null);
            alert.setContentText("Ansatt er lagret!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSlettAnsatt() {
        int ansattID = 0;

        try {
            ansattID = Integer.parseInt(ansattIdField.getText());
            if(ansattID == 0) {
                System.err.println("oppgave id er tom");
                return;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Feil innmat");
            alert.setHeaderText(null);
            alert.setContentText("Fyll inn en gyldig oppgave ID!");
            alert.showAndWait();
            return;
        }

        try {
            viewModel.deleteAnsatt(ansattID);
            loadTables();
            ansattIdField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ansatte oppdatert");
            alert.setHeaderText(null);
            alert.setContentText("Ansatt er slettet!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onOpprettNyStilling() {
        try {

            StillingDom stilling = new StillingDom(
                    0, stillingnavnField.getText(), null,
                    stillingStartPicker.getValue(), stillingSluttPicker.getValue()
            );

            if(stillingnavnField.getText().isEmpty() || stillingStartPicker.getValue() == null
                || stillingSluttPicker.getValue() == null ) {
                System.out.println("Vennligst fyll ut alle feltene");
                return;
            }
            viewModel.addStilling(stilling);
            loadTables();
            stillingnavnField.clear();
            stillingStartPicker.setValue(null);
            stillingSluttPicker.setValue(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stilling opprettet");
            alert.setHeaderText(null);
            alert.setContentText("Stillingen er lagret!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSlettStilling() {
        int stillingId = 0;

        try {
            stillingId = Integer.parseInt(stillingIdField.getText());
            if(stillingId == 0) {
                System.err.println("stilling id er tom");
                return;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Feil innmat");
            alert.setHeaderText(null);
            alert.setContentText("Fyll inn en gyldig stilling ID!");
            alert.showAndWait();
            return;
        }

        try {
            viewModel.deleStilling(stillingId);
            loadTables();
            stillingIdField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stillinger oppdatert");
            alert.setHeaderText(null);
            alert.setContentText("Stillingen er slettet!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onDeleteOppgave() {
        int oppgaveId = 0;

        try {
            oppgaveId = Integer.parseInt(oppgaveIdField.getText());
            if(oppgaveId == 0) {
                System.err.println("oppgave id er tom");
                return;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Feil innmat");
            alert.setHeaderText(null);
            alert.setContentText("Fyll inn en gyldig oppgave ID!");
            alert.showAndWait();
            return;
        }

        try {
            viewModel.deleteOppgave(oppgaveId);
            loadTables();
            oppgaveIdField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oppgave oppdatert");
            alert.setHeaderText(null);
            alert.setContentText("Oppgaven er slettet!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onOpprettOppgave() {
        try {

            OppgaveDom oppgave = new OppgaveDom(
                    0, oppgaveNavnField.getText(), null,
                    oppgaveStartPicker.getValue(), oppgaveSluttPicker.getValue()
            );

            // TODO: Bytte ut med label senere
            if (oppgaveNavnField.getText().isEmpty() || oppgaveStartPicker.getValue() == null
                    || oppgaveSluttPicker.getValue() == null) {
                System.out.println("Vennligst fyll ut alle feltene");
                return;
            }

            viewModel.addOppgave(oppgave);
            loadTables();
            oppgaveNavnField.clear();
            oppgaveStartPicker.setValue(null);
            oppgaveSluttPicker.setValue(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oppgave opprettet");
            alert.setHeaderText(null);
            alert.setContentText("Oppgaven er lagret!");
            alert.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAnsattTab() {
        ansattVindu = !ansattVindu;
        if (ansattVindu) System.out.println("ansattVindu");
    }

    @FXML
    private void onStillingTab() {
        stillingVindu = !stillingVindu;
        if (stillingVindu) System.out.println("stillingVindu");
    }

    @FXML
    private void onOppgaverTab() {
        oppgaveVindu = !oppgaveVindu;
        if (oppgaveVindu) System.out.println("Oppgavevindu");
    }

}