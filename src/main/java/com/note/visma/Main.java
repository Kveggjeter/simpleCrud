package com.note.visma;

import com.note.visma.data.datasource.ConnectDb;
import com.note.visma.data.repository.AnsattRepository;
import com.note.visma.data.repository.OppgaveRepository;
import com.note.visma.data.repository.StillingRepository;
import com.note.visma.domain.repository.IAnsattRepository;
import com.note.visma.domain.repository.IOppgaveRepository;
import com.note.visma.domain.repository.IStillingRepository;
import com.note.visma.domain.usecase.ansatt.AnsattUseCase;
import com.note.visma.domain.usecase.oppgave.OppgaveUseCase;
import com.note.visma.domain.usecase.stilling.StillingUseCase;
import com.note.visma.viewmodel.ViewController;
import com.note.visma.viewmodel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        ConnectDb.initialize();
        ViewModel viewModel = getViewModel();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ViewController controller = fxmlLoader.getController();
        controller.setViewModel(viewModel);

        stage.setTitle("Visma sysselsettingsportal");
        stage.setScene(scene);
        stage.show();
    }

    private static ViewModel getViewModel() {
        IAnsattRepository ansattRepository = new AnsattRepository();
        IStillingRepository stillingRepository = new StillingRepository();
        IOppgaveRepository oppgaveRepository = new OppgaveRepository();

        AnsattUseCase ansattUseCase = new AnsattUseCase(ansattRepository);
        StillingUseCase stillingUseCase = new StillingUseCase(stillingRepository);
        OppgaveUseCase oppgaveUseCase = new OppgaveUseCase(oppgaveRepository);

        return new ViewModel(
                ansattUseCase,
                stillingUseCase,
                oppgaveUseCase
        );
    }

    public static void main(String[] args) {
        launch();
    }
}