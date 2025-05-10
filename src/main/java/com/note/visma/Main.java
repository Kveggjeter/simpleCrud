package com.note.visma;

import com.note.visma.data.datasource.ConnectDb;
import com.note.visma.data.repository.AnsattRepository;
import com.note.visma.data.repository.OppgaveRepository;
import com.note.visma.data.repository.StillingRepository;
import com.note.visma.domain.repository.IAnsattRepository;
import com.note.visma.domain.repository.IOppgaveRepository;
import com.note.visma.domain.repository.IStillingRepository;
import com.note.visma.viewmodel.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        ConnectDb.initialize();
        ViewModelAnsatt vma = getViewModelAnsatt();
        ViewModelStilling vms = getViewModelStilling();
        ViewModelOppgave vmo = getViewModelOppgave();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ViewController controller = fxmlLoader.getController();
        controller.setViewModel(vma, vmo, vms);

        stage.setTitle("Visma sysselsettingsportal");
        stage.setScene(scene);
        stage.show();
    }

    private static ViewModelStilling getViewModelStilling() {
        IStillingRepository stillingRepository = new StillingRepository();
        return new ViewModelStilling(stillingRepository);
    }

    private static ViewModelOppgave getViewModelOppgave() {
        IOppgaveRepository oppgaveRepository = new OppgaveRepository();
        return new ViewModelOppgave(oppgaveRepository);
    }

    private static ViewModelAnsatt getViewModelAnsatt() {
        IAnsattRepository ansattRepository = new AnsattRepository();
        return new ViewModelAnsatt(ansattRepository);
    }

    public static void main(String[] args) {
        launch();
    }
}