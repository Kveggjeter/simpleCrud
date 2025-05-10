package com.note.visma.viewmodel;

import com.note.visma.domain.model.AnsattDom;
import com.note.visma.domain.model.OppgaveDom;
import com.note.visma.domain.model.StillingDom;
import com.note.visma.domain.service.validator.CheckForDate;
import com.note.visma.viewmodel.common.AlertBox;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ViewController {

    private ViewModelAnsatt viewModelAnsatt;
    private ViewModelOppgave viewModelOppgave;
    private ViewModelStilling viewModelStilling;

    public void setViewModel(ViewModelAnsatt viewModelAnsatt, ViewModelOppgave viewModelOppgave, ViewModelStilling viewModelStilling) {
        this.viewModelAnsatt = viewModelAnsatt;
        this.viewModelOppgave = viewModelOppgave;
        this.viewModelStilling = viewModelStilling;
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
    private ChoiceBox<AnsattDom> ansattChoiceStillingBox;
    @FXML
    private ChoiceBox<StillingDom> stillingChoiceBox;
    @FXML
    private ChoiceBox<AnsattDom> ansattChoiceOppgaveBox;
    @FXML
    private ChoiceBox<OppgaveDom> oppgaveChoiceBox;

    private boolean ansattVindu = false;
    private boolean stillingVindu = false;
    private boolean oppgaveVindu = false;

    private void initializeTables() {

        ansattIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().ansattID()));
        fornavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fornavn()));
        etternavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().etternavn()));

        stillingIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().stillingID()));
        stillingNavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().stillingNavn()));
        stillingAnsattIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().ansattID()));
        stillingStartCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startDato()));
        stillingSluttCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().sluttDato()));

        oppgaveIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().oppgaveID()));
        oppgaveNavnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().oppgaveNavn()));
        oppgaveAnsattIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().ansattID()));
        oppgaveStartCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startDato()));
        oppgaveSluttCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().sluttDato()));
        try {
            populateChoiceBox(ansattChoiceOppgaveBox, viewModelAnsatt.gettingAvailible(), a -> "[" + a.ansattID() + "] " + a.fornavn() + " " + a.etternavn());
            populateChoiceBox(ansattChoiceStillingBox, viewModelAnsatt.execute(), a -> "[" + a.ansattID() + "] " + a.fornavn() + " " + a.etternavn());
            populateChoiceBox(oppgaveChoiceBox, viewModelOppgave.execute(), o -> "[" + o.oppgaveID() + "] " + o.oppgaveNavn());
            populateChoiceBox(stillingChoiceBox, viewModelStilling.getAllStillingWithoutAnsatt(), s -> "[" + s.stillingID() + "] " + s.stillingNavn());

        } catch (Exception e) {
            System.out.println("Oopsie tihi");
        }
    }

    public void loadTables() {
        try {
            ansattTable.getItems().setAll(viewModelAnsatt.execute());
            stillingTable.getItems().setAll(viewModelStilling.execute());
            oppgaveTable.getItems().setAll(viewModelOppgave.execute());
        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Uventet feil ved lasting, vennligst se: " + e.getMessage());
        }
    }

    @FXML
    private void onSearchTable() {
        if(ansattVindu) {
            try {
                ansattTable.getItems().setAll(viewModelAnsatt.execute(searchTableField.getText()));
            } catch (Exception e) {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Uventet feil ved lasting, vennligst se: " + e.getMessage());
            }
        }
        if(stillingVindu) {
            try {
                stillingTable.getItems().setAll(viewModelStilling.execute(searchTableField.getText()));
            } catch (Exception e) {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Uventet feil ved lasting, vennligst se: " + e.getMessage());
            }
        }
        if(oppgaveVindu) {
            try {
                oppgaveTable.getItems().setAll(viewModelOppgave.execute(searchTableField.getText()));
            } catch (Exception e) {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Uventet feil ved lasting, vennligst se: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onTildelNyStilling(){
        AnsattDom ansatt = ansattChoiceStillingBox.getValue();
        StillingDom stilling = stillingChoiceBox.getValue();
        boolean check = true;

        try {
            List<StillingDom> stillinger = viewModelStilling.getAllByAnsattID(ansatt.ansattID());
            for (StillingDom stillingDom : stillinger) {
                if (!CheckForDate.isDateValid(stillingDom, stilling))
                    check = false;
            }
            if(check) {
                viewModelStilling.updateById(ansatt.ansattID(), stilling.stillingID());
                loadTables();
                AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess", "Ansatt tildelt ny stilling");
            }else {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Den ansatte har allerede en annen" +
                        " stilling for denne perioden");
            }

        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Uventet feil ved tildeling, vennligst se: " + e.getMessage());
        } finally {
            ansattChoiceStillingBox.setValue(null);
            stillingChoiceBox.setValue(null);
            initializeTables();
        }
    }

    @FXML
    private void onTildelNyOppgave() {
        OppgaveDom oppgave =  oppgaveChoiceBox.getValue();
        AnsattDom ansatt = ansattChoiceOppgaveBox.getValue();

        try {
            StillingDom stilling = viewModelStilling.getByAnsattId(ansatt.ansattID());
            if (CheckForDate.isDateValid(stilling, oppgave)) {
                viewModelOppgave.execute(ansatt.ansattID(), oppgave.oppgaveID());
                loadTables();
                AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess", "Ansatt tildelt ny oppgave");
            }
            else {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Den ansatte har ikke et" +
                        " stillingsforhold i denne perioden");
            }
        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Her gikk noe fryktelig galt, se hvorfor:" +
                    " " + e.getMessage());
        } finally {
            oppgaveChoiceBox.setValue(null);
            ansattChoiceOppgaveBox.setValue(null);
            initializeTables();
        }


    }

    private <T> void populateChoiceBox(ChoiceBox<T> box, List<T> items, Function<T, String> fun) {
        items.removeIf(Objects::isNull);
        box.setItems(FXCollections.observableList(items));
        box.setConverter(new StringConverter<>() {
            @Override public String toString(T o) { return o==null ? "" : fun.apply(o); }
            @Override public T fromString(String s) { return null; }
        });
    }


    @FXML
    private void onOprettAnsatt() {
        try {
            AnsattDom ansatt = new AnsattDom(
                    0, fornavnField.getText(), etternavnField.getText()
            );

            if(fornavnField.getText().isEmpty() || etternavnField.getText().isEmpty()) {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Vennligst fyll ut alle feltene");
                return;
            }
            viewModelAnsatt.execute(ansatt);
            loadTables();
            fornavnField.clear();
            etternavnField.clear();
            AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess", "Ansatt opprettet!");

        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Klarte ikke å lage ansatt. Les: " + e.getMessage());
        } finally {
            initializeTables();
        }
    }

    @FXML
    private void onSlettAnsatt() {
        int ansattID = 0;

        try {
            ansattID = Integer.parseInt(ansattIdField.getText());
            if(ansattID == 0) {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Nummeret du har valgt" +
                        "ser ikke ut til å være gyldig.");
                return;
            }
        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Får ikke slettet ansatt! Prøv igjen");
            return;
        }

        try {
            viewModelAnsatt.execute(ansattID);
            loadTables();
            ansattIdField.clear();
            AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess!", "Ansatt slettet.");
        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Her gikk det galt, se hvorfor: " + e.getMessage());
        } finally {
            initializeTables();
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
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Vennligst fyll ut alle feltene.");
                return;
            }
            viewModelStilling.execute(stilling);
            loadTables();
            stillingnavnField.clear();
            stillingStartPicker.setValue(null);
            stillingSluttPicker.setValue(null);
            AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess!", "Stilling opprettet.");

        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Her gikk det galt, se hvorfor: " + e.getMessage());
        } finally {
            initializeTables();
        }
    }

    @FXML
    private void onSlettStilling() {
        int stillingId = 0;

        try {
            stillingId = Integer.parseInt(stillingIdField.getText());
            if(stillingId == 0) {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Felt for ID er tomt");
                return;
            }
        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "ID ikke gyldig, prøv igjen med gyldig ID");
            return;
        }

        try {
            viewModelStilling.execute(stillingId);
            loadTables();
            stillingIdField.clear();
            AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess!", "Stilling er slettet.");

        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Her gikk noe fryktelig galt, les hvorfor: " + e.getMessage());
        } finally {
            initializeTables();
        }
    }


    @FXML
    private void onDeleteOppgave() {
        int oppgaveId = 0;

        try {
            oppgaveId = Integer.parseInt(oppgaveIdField.getText());
            if(oppgaveId == 0) {
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Felt for ID er tomt");
                return;
            }
        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "ID ikke gyldig, prøv igjen med gyldig ID");
            return;
        }

        try {
            viewModelOppgave.execute(oppgaveId);
            loadTables();
            oppgaveIdField.clear();
            AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess!", "Oppgave slettet");
        } catch (Exception e) {
            AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Her gikk noe fryktelig galt. Les hvorfor: " + e.getMessage());
        } finally {
            initializeTables();
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
                AlertBox.alertBox(Alert.AlertType.ERROR, "Ops!", "Vennligst fyll ut alle feltene.");
                return;
            }

            viewModelOppgave.execute(oppgave);
            loadTables();
            oppgaveNavnField.clear();
            oppgaveStartPicker.setValue(null);
            oppgaveSluttPicker.setValue(null);
            AlertBox.alertBox(Alert.AlertType.INFORMATION, "Suksess!", "Oppgave opprettet!");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initializeTables();
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