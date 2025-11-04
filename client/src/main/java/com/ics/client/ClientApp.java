package com.ics.client;

import com.ics.client.api.ApiClient;
import com.ics.common.dto.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ClientApp extends Application {
  private final ApiClient apiClient = new ApiClient("http://localhost:8080");
  private TextArea resultArea;
  private ProgressIndicator progressIndicator;
  private Button generateButton;

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Intelligent Course Scheduler");

    // Create UI components
    VBox root = new VBox(10);
    root.setPadding(new Insets(15));
    root.setAlignment(Pos.TOP_CENTER);

    // Course codes input
    Label courseLabel = new Label("Course Codes (comma-separated):");
    TextField courseField = new TextField();
    courseField.setPromptText("e.g., CS101, MATH201");
    courseField.setText("CS101, MATH201");

    // Preferences
    Label prefsLabel = new Label("Preferences:");
    Spinner<Integer> minCreditsSpinner = new Spinner<>(0, 30, 8);
    Spinner<Integer> maxCreditsSpinner = new Spinner<>(0, 30, 18);
    Spinner<Integer> topKSpinner = new Spinner<>(1, 20, 5);

    HBox prefsBox = new HBox(10);
    prefsBox.getChildren().addAll(
        new Label("Min Credits:"), minCreditsSpinner,
        new Label("Max Credits:"), maxCreditsSpinner,
        new Label("Top K:"), topKSpinner
    );

    // Generate button
    generateButton = new Button("Generate Schedules");
    progressIndicator = new ProgressIndicator();
    progressIndicator.setVisible(false);
    progressIndicator.setPrefSize(20, 20);

    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(generateButton, progressIndicator);

    // Result area
    Label resultLabel = new Label("Results:");
    resultArea = new TextArea();
    resultArea.setEditable(false);
    resultArea.setPrefRowCount(15);
    resultArea.setWrapText(true);

    // Generate button action
    generateButton.setOnAction(e -> {
      String courseCodesStr = courseField.getText().trim();
      if (courseCodesStr.isEmpty()) {
        resultArea.setText("Please enter course codes.");
        return;
      }

      String[] codes = courseCodesStr.split(",");
      List<String> courseCodes = new ArrayList<>();
      for (String code : codes) {
        courseCodes.add(code.trim());
      }

      UserPrefs prefs = new UserPrefs(
          minCreditsSpinner.getValue(),
          maxCreditsSpinner.getValue(),
          List.of(),
          null
      );

      GenerateRequest request = new GenerateRequest(
          courseCodes,
          prefs,
          topKSpinner.getValue()
      );

      generateButton.setDisable(true);
      progressIndicator.setVisible(true);
      resultArea.setText("Generating schedules...");

      CompletableFuture.supplyAsync(() -> {
        try {
          return apiClient.generate(request);
        } catch (Exception ex) {
          Platform.runLater(() -> {
            resultArea.setText("Error: " + ex.getMessage());
            generateButton.setDisable(false);
            progressIndicator.setVisible(false);
          });
          return null;
        }
      }).thenAccept(results -> {
        Platform.runLater(() -> {
          if (results != null) {
            displayResults(results);
          }
          generateButton.setDisable(false);
          progressIndicator.setVisible(false);
        });
      });
    });

    // Build layout
    root.getChildren().addAll(
        courseLabel,
        courseField,
        prefsLabel,
        prefsBox,
        buttonBox,
        resultLabel,
        resultArea
    );

    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void displayResults(List<ScheduleResult> results) {
    StringBuilder sb = new StringBuilder();
    sb.append("Found ").append(results.size()).append(" schedule(s):\n\n");

    for (int i = 0; i < results.size(); i++) {
      ScheduleResult result = results.get(i);
      sb.append("Schedule ").append(i + 1).append(" (score: ").append(result.score()).append(")\n");
      sb.append("Explanations: ").append(String.join(", ", result.explanations())).append("\n");
      sb.append("Sections:\n");
      
      for (var section : result.sections()) {
        sb.append("  - ").append(section.id()).append(" (Course: ").append(section.courseId())
            .append(", Building: ").append(section.buildingId())
            .append(", Instructor: ").append(section.instructor()).append(")\n");
        for (var mt : section.meetingTimes()) {
          sb.append("    ").append(mt.day()).append(": ").append(mt.start())
              .append(" - ").append(mt.end()).append("\n");
        }
      }
      sb.append("\n");
    }

    resultArea.setText(sb.toString());
  }

  public static void main(String[] args) {
    launch(args);
  }
}





