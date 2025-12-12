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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
//import java.util.Arrays;
//import java.util.Collections;
import java.time.DayOfWeek;
import javafx.stage.Popup;

import com.ics.client.util.IcsUtil;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalTime;

public class ClientApp extends Application {
    private final ApiClient apiClient = new ApiClient("http://localhost:8080");
    //private TextArea resultArea;
    private HBox optionsContainer;   //
    private GridPane calendarGrid;   //
    private Label statusLabel;       // "Found X schedules"

    private ProgressIndicator progressIndicator;
    private Button generateButton;

    private final Spinner<Integer> minCreditsSpinner = new Spinner<>(0, 30, 8);
    private final Spinner<Integer> maxCreditsSpinner = new Spinner<>(0, 30, 18);
    private final Spinner<Integer> topKSpinner = new Spinner<>(1, 20, 5);
    private final ComboBox<String> startTimeBox = new ComboBox<>();

    private List<ScheduleResult> currentResults = new ArrayList<>();
    private boolean[] currentVisibility;

    private final List<MeetingTime> permanentExclusions = new ArrayList<>();
    private Label exclusionSummaryLabel;

    private int lastStartMinutes = 390;
    private int lastEndMinutes = 1350;
    private final boolean[] lastDaySelections = new boolean[6];

    private final String[] OPTION_COLORS = {
            "#6366f1", // Indigo
            "#ec4899", // Pink
            "#10b981", // Emerald
            "#f59e0b", // Amber
            "#3b82f6", // Blue
            "#8b5cf6", // Violet
            "#f43f5e", // Rose
            "#0ea5e9", // Sky
            "#84cc16", // Lime
            "#d946ef", // Fuchsia
            "#06b6d4", // Cyan
            "#f97316"  // Orange
    };
//  @Override
//  public void start(Stage primaryStage) {
//    primaryStage.setTitle("Intelligent Course Scheduler");
//
//    // Create UI components
//    VBox root = new VBox(10);
//    root.setPadding(new Insets(15));
//    root.setAlignment(Pos.TOP_CENTER);
//
//    // Course codes input
//    Label courseLabel = new Label("Course Codes (comma-separated):");
//    TextField courseField = new TextField();
//    courseField.setPromptText("e.g., CS101, MATH201");
//    courseField.setText("CS101, MATH201");
//
//    // Preferences
//    Label prefsLabel = new Label("Preferences:");
//    Spinner<Integer> minCreditsSpinner = new Spinner<>(0, 30, 8);
//    Spinner<Integer> maxCreditsSpinner = new Spinner<>(0, 30, 18);
//    Spinner<Integer> topKSpinner = new Spinner<>(1, 20, 5);
//
//     HBox prefsBox = new HBox(10);
//    prefsBox.getChildren().addAll(
//        new Label("Min Credits:"), minCreditsSpinner,
//        new Label("Max Credits:"), maxCreditsSpinner,
//        new Label("Top K:"), topKSpinner
//    );
//
//    // Generate button
//    generateButton = new Button("Generate Schedules");
//    progressIndicator = new ProgressIndicator();
//    progressIndicator.setVisible(false);
//    progressIndicator.setPrefSize(20, 20);
//
//    HBox buttonBox = new HBox(10);
//    buttonBox.setAlignment(Pos.CENTER);
//    buttonBox.getChildren().addAll(generateButton, progressIndicator);
//
//    // Result area
//    Label resultLabel = new Label("Results:");
//    resultArea = new TextArea();
//    resultArea.setEditable(false);
//    resultArea.setPrefRowCount(15);
//    resultArea.setWrapText(true);
//
//    // Generate button action
//    generateButton.setOnAction(e -> {
//      String courseCodesStr = courseField.getText().trim();
//      if (courseCodesStr.isEmpty()) {
//        resultArea.setText("Please enter course codes.");
//        return;
//      }
//
//      String[] codes = courseCodesStr.split(",");
//      List<String> courseCodes = new ArrayList<>();
//      for (String code : codes) {
//        courseCodes.add(code.trim());
//      }
//
//      UserPrefs prefs = new UserPrefs(
//          minCreditsSpinner.getValue(),
//          maxCreditsSpinner.getValue(),
//          List.of(),
//          null
//      );
//
//      GenerateRequest request = new GenerateRequest(
//          courseCodes,
//          prefs,
//          topKSpinner.getValue()
//      );
//
//      generateButton.setDisable(true);
//      progressIndicator.setVisible(true);
//      resultArea.setText("Generating schedules...");
//
//      CompletableFuture.supplyAsync(() -> {
//        try {
//          return apiClient.generate(request);
//        } catch (Exception ex) {
//          Platform.runLater(() -> {
//            resultArea.setText("Error: " + ex.getMessage());
//            generateButton.setDisable(false);
//            progressIndicator.setVisible(false);
//          });
//          return null;
//        }
//      }).thenAccept(results -> {
//        Platform.runLater(() -> {
//          if (results != null) {
//            displayResults(results);
//          }
//          generateButton.setDisable(false);
//          progressIndicator.setVisible(false);
//        });
//      });
//    });
//
//    // Build layout
//    root.getChildren().addAll(
//        courseLabel,
//        courseField,
//        prefsLabel,
//        prefsBox,
//        buttonBox,
//        resultLabel,
//        resultArea
//    );
//
//    Scene scene = new Scene(root, 800, 600);
//    primaryStage.setScene(scene);
//    primaryStage.show();
//  }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Intelligent Course Scheduler");

        Label appTitle = new Label("Intelligent Course Scheduler");
        appTitle.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-text-fill: #111827;" +
                        "-fx-padding: 20 20 6 20;"
        );

        VBox inputBar = new VBox(12);
        inputBar.setPadding(new Insets(16, 20, 16, 20));
        inputBar.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");

        Label courseLabel = new Label("Courses");
        TextField courseField = new TextField("CS101, MATH201");
        courseField.setPromptText("e.g. CS101, MATH201");

        minCreditsSpinner.setPrefWidth(100);
        maxCreditsSpinner.setPrefWidth(100);
        topKSpinner.setPrefWidth(100);

        startTimeBox.getItems().addAll("Any Time", "After 9am", "After 10am", "After 11am", "After 12pm");
        startTimeBox.setPromptText("Select...");
        startTimeBox.setPrefWidth(110);

        // 👇👇👇【修改 2A】新增：Filter 按钮和 Summary Label 👇👇👇
        Button excludeButton = new Button("Add days/times");
        excludeButton.getStyleClass().add("secondary-button");
        excludeButton.setOnAction(e -> showExclusionDialog()); // 触发模态窗口

        // 用于显示当前排除规则的摘要标签
        exclusionSummaryLabel = new Label("No Exclusions Set");
        exclusionSummaryLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        VBox excludeBox = new VBox(5, excludeButton, exclusionSummaryLabel);
        excludeBox.setAlignment(Pos.CENTER_LEFT);
        // ------------------------------------------

        generateButton = new Button("Generate Schedules");
        generateButton.getStyleClass().add("primary-button");

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(20, 20);

        Label minLabel = new Label("Min Credits");
        Label maxLabel = new Label("Max Credits");
        Label topKLabel = new Label("Top K");
        Label startLabel = new Label("Start Time");
        Label exclusionsLabel = new Label("Exclusions");

        String labelStyle = "-fx-text-fill: #374151; -fx-font-size: 12px;";

        for (Label lbl : new Label[]{courseLabel, minLabel, maxLabel, topKLabel, startLabel, exclusionsLabel}) {
            lbl.setStyle(labelStyle);
            lbl.setMinWidth(Region.USE_PREF_SIZE);
            lbl.setMaxWidth(Region.USE_PREF_SIZE);
        }

        courseField.setPrefWidth(260);
        minCreditsSpinner.setPrefWidth(80);
        maxCreditsSpinner.setPrefWidth(80);
        topKSpinner.setPrefWidth(80);
        startTimeBox.setPrefWidth(130);

        courseField.setPrefHeight(32);
        minCreditsSpinner.setPrefHeight(32);
        maxCreditsSpinner.setPrefHeight(32);
        topKSpinner.setPrefHeight(32);
        startTimeBox.setPrefHeight(32);

// ------- SINGLE ROW: Courses | Min Credits | Max Credits | Top K | Start Time -------
        HBox row1 = new HBox(24);
        row1.setAlignment(Pos.CENTER_LEFT);

        HBox coursesBox = new HBox(8, courseLabel, courseField);
        HBox minBox     = new HBox(8, minLabel,    minCreditsSpinner);
        HBox maxBox     = new HBox(8, maxLabel,    maxCreditsSpinner);
        HBox topKBox    = new HBox(8, topKLabel,   topKSpinner);
        HBox startBox   = new HBox(8, startLabel,  startTimeBox);

        coursesBox.setAlignment(Pos.CENTER_LEFT);
        minBox.setAlignment(Pos.CENTER_LEFT);
        maxBox.setAlignment(Pos.CENTER_LEFT);
        topKBox.setAlignment(Pos.CENTER_LEFT);
        startBox.setAlignment(Pos.CENTER_LEFT);

        row1.getChildren().setAll(
                coursesBox,
                minBox,
                maxBox,
                topKBox,
                startBox
        );

        HBox row3 = new HBox(16);
        row3.setAlignment(Pos.CENTER_LEFT);

        VBox exclusionInfoBox = new VBox(4, exclusionsLabel, exclusionSummaryLabel);

        HBox leftExclusionBox = new HBox(10, exclusionInfoBox, excludeBox);
        leftExclusionBox.setAlignment(Pos.CENTER_LEFT);

        HBox rightGenerateBox = new HBox(8, generateButton, progressIndicator);
        rightGenerateBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(rightGenerateBox, Priority.ALWAYS);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row3.getChildren().addAll(leftExclusionBox, spacer, rightGenerateBox);

        inputBar.getChildren().setAll(row1, row3);

        VBox optionsArea = new VBox(10);
        optionsArea.setPadding(new Insets(10, 20, 10, 20));
        optionsArea.setStyle("-fx-background-color: #f8f9fa;");

        statusLabel = new Label("Ready to generate.");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        ScrollPane optionsScroll = new ScrollPane();
        optionsScroll.setFitToHeight(true);
        optionsScroll.setFitToWidth(true);
        optionsScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        optionsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        optionsContainer = new HBox(15);
        optionsContainer.setAlignment(Pos.CENTER);
        optionsContainer.setStyle("-fx-padding: 5;");

        optionsScroll.setContent(optionsContainer);

        optionsArea.getChildren().addAll(statusLabel, optionsScroll);

        calendarGrid = new GridPane();
        calendarGrid.getStyleClass().add("calendar-grid");
        calendarGrid.setHgap(5);
        calendarGrid.setVgap(5);
        initEmptyCalendar();

        ScrollPane calendarScroll = new ScrollPane(calendarGrid);
        calendarScroll.setFitToWidth(true);
        calendarScroll.setStyle("-fx-background-color: white;");

        Button exportAllBtn = new Button("Export Selected Schedules (.ics)");
        exportAllBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px;");

        HBox bottomBar = new HBox(10, exportAllBtn);
        bottomBar.setPadding(new Insets(10, 20, 10, 20));
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

        BorderPane root = new BorderPane();

        VBox topContainer = new VBox(appTitle, inputBar, optionsArea);
        topContainer.setStyle("-fx-background-color: white;");

        root.setTop(topContainer);
        root.setCenter(calendarScroll);
        root.setBottom(bottomBar);

        exportAllBtn.setOnAction(e -> {
            if (currentResults.isEmpty() || currentVisibility == null) {
                statusLabel.setText("No schedules available to export.");
                return;
            }

            int exportedCount = 0;
            boolean cancelled = false;

            for (int i = 0; i < currentResults.size(); i++) {
                if (i < currentVisibility.length && currentVisibility[i]) {
                    boolean success = exportSchedule(currentResults.get(i).sections(), i + 1);
                    if (success) {
                        exportedCount++;
                    } else {
                        cancelled = true;
                        break;
                    }
                }
            }

            if (cancelled) {
                statusLabel.setText("Export canceled after " + exportedCount + " file(s) saved.");
            } else if (exportedCount > 0) {
                statusLabel.setText("Successfully exported " + exportedCount + " selected option(s).");
            } else {
                statusLabel.setText("Please select at least one schedule option to export.");
            }
        });

        generateButton.setOnAction(e -> {
            String courseCodesStr = courseField.getText().trim();
            if (courseCodesStr.isEmpty()) return;

            String[] codes = courseCodesStr.split(",");
            List<String> courseCodes = new ArrayList<>();
            for (String code : codes) courseCodes.add(code.trim());

            String selectedTime = startTimeBox.getValue();
            Integer preferredStartHour = null;

            if (selectedTime != null) {
                if (selectedTime.contains("9am")) preferredStartHour = 9;
                else if (selectedTime.contains("10am")) preferredStartHour = 10;
                else if (selectedTime.contains("11am")) preferredStartHour = 11;
                else if (selectedTime.contains("12pm")) preferredStartHour = 12;
            }

            UserPrefs prefs = new UserPrefs(
                    minCreditsSpinner.getValue(),
                    maxCreditsSpinner.getValue(),
                    List.of(),
                    null,
                    preferredStartHour,
                    permanentExclusions
            );
            GenerateRequest request = new GenerateRequest(courseCodes, prefs, topKSpinner.getValue());

            generateButton.setDisable(true);
            progressIndicator.setVisible(true);
            statusLabel.setText("Calculating...");
            optionsContainer.getChildren().clear();

            CompletableFuture.supplyAsync(() -> {
                try {
                    return apiClient.generate(request);
                } catch (Exception ex) {
                    Platform.runLater(() -> statusLabel.setText("Error: " + ex.getMessage()));
                    return null;
                }
            }).thenAccept(results -> Platform.runLater(() -> {
                generateButton.setDisable(false);
                progressIndicator.setVisible(false);
                if (results != null) {
                    handleResults(results);
                }
            }));
        });

        Scene scene = new Scene(root, 1200, 800);
        try {
            var cssUrl = getClass().getResource("/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("CSS not found!");
            }
        } catch (Exception e) { System.err.println("CSS not found!"); }

        primaryStage.setScene(scene);
        primaryStage.show();
    }


//  private void displayResults(List<ScheduleResult> results) {
//    StringBuilder sb = new StringBuilder();
//    sb.append("Found ").append(results.size()).append(" schedule(s):\n\n");
//
//    for (int i = 0; i < results.size(); i++) {
//      ScheduleResult result = results.get(i);
//      sb.append("Schedule ").append(i + 1).append(" (score: ").append(result.score()).append(")\n");
//      sb.append("Explanations: ").append(String.join(", ", result.explanations())).append("\n");
//      sb.append("Sections:\n");
//
//      for (var section : result.sections()) {
//        sb.append("  - ").append(section.id()).append(" (Course: ").append(section.courseId())
//            .append(", Building: ").append(section.buildingId())
//            .append(", Instructor: ").append(section.instructor()).append(")\n");
//        for (var mt : section.meetingTimes()) {
//          sb.append("    ").append(mt.day()).append(": ").append(mt.start())
//              .append(" - ").append(mt.end()).append("\n");
//        }
//      }
//      sb.append("\n");
//    }
//
//    resultArea.setText(sb.toString());
//  }
//


    private void handleResults(List<ScheduleResult> results) {
        if (results.isEmpty()) {
            statusLabel.setText("No schedules found.");
            return;
        }
        statusLabel.setText("Found " + results.size() + " Schedule Options:");

        this.currentResults = results;
        this.currentVisibility = new boolean[results.size()];

        //if (!results.isEmpty()) this.currentVisibility[0] = true;
        boolean[] visibility = this.currentVisibility;

        for (int i = 0; i < results.size(); i++) {
            final int index = i;
            String color = OPTION_COLORS[i % OPTION_COLORS.length];

            HBox card = new HBox(10);
            card.getStyleClass().add("option-card");

            card.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(card, Priority.ALWAYS);
            card.setMinWidth(140);

            Pane colorStrip = new Pane();
            colorStrip.setPrefWidth(6);
            colorStrip.setMinWidth(6);
            colorStrip.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 4 0 0 4;");

            VBox textBox = new VBox(3);
            textBox.setAlignment(Pos.CENTER_LEFT);

            Label title = new Label("Option " + (i + 1));
            title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #334155;");

            Label score = new Label("Score: " + String.format("%.1f", results.get(i).score()));
            score.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");

            textBox.getChildren().addAll(title, score);

            card.getChildren().addAll(colorStrip, textBox);

            Runnable updateStyle = () -> {
                if (visibility[index]) {
                    card.setStyle("-fx-background-color: white; -fx-border-color: " + color + "; -fx-border-width: 1.5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
                    title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + color + ";");
                } else {
                    card.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1;");
                    title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #334155;");
                }
            };

            updateStyle.run();

            card.setOnMouseClicked(e -> {
                visibility[index] = !visibility[index];
                updateStyle.run();
                drawMultipleSchedules(results, visibility);
            });

            optionsContainer.getChildren().add(card);
        }

        drawMultipleSchedules(results, visibility);
    }


// --- UPDATED: Draw Multiple Schedules Side-by-Side ---
    private void drawMultipleSchedules(List<ScheduleResult> allResults, boolean[] visibility) {
        initEmptyCalendar();

        java.util.Map<String, HBox> cellContainers = new java.util.HashMap<>();

        for (int i = 0; i < allResults.size(); i++) {
            if (!visibility[i]) continue;

            ScheduleResult result = allResults.get(i);
            String color = OPTION_COLORS[i % OPTION_COLORS.length];

            for (var section : result.sections()) {
                for (var mt : section.meetingTimes()) {
                    try {
                        DayOfWeek day = parseDay(mt.day());
                        if (day == null || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) continue;

                        int col = getDayColumn(day);

                        String timeStr = mt.start();
                        int startHour;
                        if (timeStr.contains(":")) {
                            startHour = Integer.parseInt(timeStr.split(":")[0]);
                        } else {
                            startHour = Integer.parseInt(timeStr);
                        }

                        int row = startHour - 7;
                        if (row < 1) row = 1;

                        VBox block = new VBox(2);
                        block.setStyle(
                                "-fx-background-color: " + color + "CC;" + // CC = 80% 不透明
                                        "-fx-padding: 4px;" +
                                        "-fx-background-radius: 6px;" +
                                        "-fx-cursor: hand;"
                        );
                        HBox.setHgrow(block, Priority.ALWAYS);
                        block.setMaxWidth(Double.MAX_VALUE);

                        Text code = new Text("Opt" + (i + 1) + ": " + section.courseId());
                        code.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 9px;");

                        Text info = new Text(mt.start() + "-" + mt.end());
                        info.setStyle("-fx-fill: white; -fx-font-size: 8px;");

                        block.getChildren().addAll(code, info);


                        block.setOpacity(0);
                        block.setScaleX(0.5);
                        block.setScaleY(0.5);

                        javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(400), block);
                        fade.setFromValue(0);
                        fade.setToValue(1);

                        javafx.animation.ScaleTransition scale = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(400), block);
                        scale.setFromX(0.5);
                        scale.setToX(1.0);
                        scale.setFromY(0.5);
                        scale.setToY(1.0);

                        javafx.animation.ParallelTransition entryAnimation = new javafx.animation.ParallelTransition(fade, scale);
                        entryAnimation.setDelay(javafx.util.Duration.millis(i * 50));
                        entryAnimation.play();

                        String detailText = "Course: " + section.courseId() + "\n" +
                                "Building: " + section.buildingId() + "\n" +
                                "Instructor: " + section.instructor();

                        block.setOnMouseClicked(event -> {
                            Popup popup = new Popup();
                            popup.setAutoHide(true);

                            Label content = new Label(detailText);
                            content.setStyle("-fx-background-color: white;" +
                                    "-fx-text-fill: #333;" +
                                    "-fx-padding: 10px;" +
                                    "-fx-border-color: #ccc;" +
                                    "-fx-border-width: 1px;" +
                                    "-fx-border-radius: 5px;" +
                                    "-fx-background-radius: 5px;" +
                                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" +
                                    "-fx-font-size: 13px;" +
                                    "-fx-font-family: 'Segoe UI', sans-serif;");

                            popup.getContent().add(content);

                            popup.show(block.getScene().getWindow(), event.getScreenX() + 10, event.getScreenY() + 10);

                            event.consume();
                        });

                        String key = col + "-" + row;
                        HBox cellBox = cellContainers.get(key);

                        if (cellBox == null) {
                            cellBox = new HBox(2);
                            cellBox.setAlignment(Pos.TOP_LEFT);
                            cellBox.setStyle("-fx-padding: 2px;");
                            calendarGrid.add(cellBox, col, row);
                            cellContainers.put(key, cellBox);
                        }
                        cellBox.getChildren().add(block);

                    } catch (Exception e) {
                        System.err.println("Render failed: " + e.getMessage());
                    }
                }
            }
        }
    }
    // --- HELPER: Handle MON, TUE, WED mapping ---
    private DayOfWeek parseDay(String dayStr) {
        if (dayStr == null) return null;
        String d = dayStr.trim().toUpperCase();

        return switch (d) {
            case "MON" -> DayOfWeek.MONDAY;
            case "TUE" -> DayOfWeek.TUESDAY;
            case "WED" -> DayOfWeek.WEDNESDAY;
            case "THU" -> DayOfWeek.THURSDAY;
            case "FRI" -> DayOfWeek.FRIDAY;
            case "SAT" -> DayOfWeek.SATURDAY;
            case "SUN" -> DayOfWeek.SUNDAY;
            default -> null;
        };
    }



    private void initEmptyCalendar() {
        calendarGrid.getChildren().clear();

        calendarGrid.getColumnConstraints().clear();
        calendarGrid.getRowConstraints().clear();

        String[] days = {"Time", "Mon", "Tue", "Wed", "Thu", "Fri"};

        for (int i = 0; i < days.length; i++) {
            Label l = new Label(days[i]);
            l.getStyleClass().add("day-header");
            l.setMaxWidth(Double.MAX_VALUE);
            calendarGrid.add(l, i, 0);

            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(i == 0 ? 10 : 18);
            calendarGrid.getColumnConstraints().add(cc);
        }

        for (int h = 8; h <= 20; h++) {
            int row = h - 7;
            Label time = new Label(h + ":00");
            time.getStyleClass().add("time-label");
            GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);
            calendarGrid.add(time, 0, row);

            for (int d = 1; d <= 5; d++) {
                Pane p = new Pane();
                p.getStyleClass().add("grid-cell");
                p.setMinHeight(50);
                calendarGrid.add(p, d, row);
            }
        }
    }

    private int getDayColumn(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> 1;
            case TUESDAY -> 2;
            case WEDNESDAY -> 3;
            case THURSDAY -> 4;
            case FRIDAY -> 5;
            default -> -1;
        };
    }


    private boolean exportSchedule(List<Section> schedule, int optionIndex) {
        String icsContent = IcsUtil.generateIcsContent(schedule);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Schedule Option " + optionIndex + " to Calendar");

        String filename = "schedule_option_" + optionIndex + ".ics";

        fileChooser.setInitialFileName(filename);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("iCalendar File", "*.ics"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                Files.writeString(file.toPath(), icsContent);
                return true;
            } catch (Exception ex) {
                System.err.println("Failed to save file: " + ex.getMessage());
                return false;
            }
        }
        return false;
    }



    private void showExclusionDialog() {
        Dialog<List<MeetingTime>> dialog = new Dialog<>();

        dialog.setTitle("Day & Time Exclusions");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);

        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.CANCEL);

        Button hiddenCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        if (hiddenCancel != null) {
            hiddenCancel.setVisible(false);
            hiddenCancel.setManaged(false);
        }

        dialog.getDialogPane().setPrefWidth(520);
        dialog.getDialogPane().setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;"
        );

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        CheckBox[] dayChecks = new CheckBox[days.length];

        HBox dayRow = new HBox(18);
        dayRow.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < days.length; i++) {
            dayChecks[i] = new CheckBox(days[i]);
            if (i < lastDaySelections.length) {
                dayChecks[i].setSelected(lastDaySelections[i]);
            }
            dayRow.getChildren().add(dayChecks[i]);
        }

        int finalMIN_TIME_MINUTES = 390;
        int finalMAX_TIME_MINUTES = 1350;

        int initialStart = Math.max(finalMIN_TIME_MINUTES, Math.min(finalMAX_TIME_MINUTES, lastStartMinutes));
        int initialEnd   = Math.max(finalMIN_TIME_MINUTES, Math.min(finalMAX_TIME_MINUTES, lastEndMinutes));

        Slider startSlider = new Slider(finalMIN_TIME_MINUTES, finalMAX_TIME_MINUTES, initialStart);
        Slider endSlider   = new Slider(finalMIN_TIME_MINUTES, finalMAX_TIME_MINUTES, initialEnd);

        startSlider.getStyleClass().add("range-slider-main");
        endSlider.getStyleClass().add("range-slider-bg");

        for (Slider s : new Slider[]{startSlider, endSlider}) {
            s.setShowTickMarks(false);
            s.setShowTickLabels(false);
            s.setBlockIncrement(15);
            s.setMajorTickUnit(60);
            s.setMinWidth(440);
        }

        Label timeRangeLabel = new Label();
        timeRangeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 13px;");
        timeRangeLabel.setAlignment(Pos.CENTER);

        java.util.function.IntFunction<String> formatTime = minutes -> {
            int hour24 = minutes / 60;
            int minute = minutes % 60;
            String ampm = (hour24 < 12) ? "AM" : "PM";

            int hour12 = hour24;
            if (hour12 == 0) hour12 = 12;
            else if (hour12 > 12) hour12 -= 12;

            return String.format("%d:%02d %s", hour12, minute, ampm);
        };

        Runnable updateLabel = () -> {
            if (startSlider.getValue() > endSlider.getValue()) {
                startSlider.setValue(endSlider.getValue());
            }
            int startMin = (int) startSlider.getValue();
            int endMin   = (int) endSlider.getValue();
            String startStr = formatTime.apply(startMin);
            String endStr   = formatTime.apply(endMin);
            timeRangeLabel.setText(startStr + " -- " + endStr);
        };

        startSlider.valueProperty().addListener((obs, o, n) -> updateLabel.run());
        endSlider.valueProperty().addListener((obs, o, n) -> updateLabel.run());
        updateLabel.run();

        VBox sliderBoxInner = new VBox(10, timeRangeLabel, startSlider, endSlider);
        sliderBoxInner.setAlignment(Pos.CENTER_LEFT);

        VBox sliderCard = new VBox(sliderBoxInner);
        sliderCard.setPadding(new Insets(12, 14, 14, 14));
        sliderCard.setStyle(
                "-fx-background-color: #f9fafb;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: #e5e7eb;"
        );

        Button clearButton = new Button("Clear");
        Button cancelButton = new Button("Cancel");
        Button addButton = new Button("Add");

        clearButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #d1d5db;" +
                        "-fx-text-fill: #374151;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-background-radius: 999;" +
                        "-fx-border-radius: 999;"
        );

        cancelButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #4b5563;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-background-radius: 999;" +
                        "-fx-border-radius: 999;"
        );

        addButton.setStyle(
                "-fx-background-color: #2563eb;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 22 6 22;" +
                        "-fx-background-radius: 999;" +
                        "-fx-border-radius: 999;"
        );

        addButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);

        HBox buttonRow = new HBox(8, clearButton, cancelButton, addButton);
        buttonRow.setAlignment(Pos.CENTER_RIGHT);
        buttonRow.setPadding(new Insets(6, 0, 0, 0));

        VBox card = new VBox(16,
                new Label("Exclude sections that meet on..."),
                dayRow,
                sliderCard,
                buttonRow
        );
        card.getChildren().get(0).setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #111827;"
        );

        card.setPadding(new Insets(18, 20, 14, 20));
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.18), 16, 0, 0, 4);"
        );

        StackPane rootPane = new StackPane(card);
        rootPane.setPadding(new Insets(8));

        dialog.getDialogPane().setContent(rootPane);

        clearButton.setOnAction(ev -> {
            permanentExclusions.clear();
            updateExclusionSummary();

            lastStartMinutes = finalMIN_TIME_MINUTES;
            lastEndMinutes   = finalMAX_TIME_MINUTES;
            Arrays.fill(lastDaySelections, false);
            startSlider.setValue(finalMIN_TIME_MINUTES);
            endSlider.setValue(finalMAX_TIME_MINUTES);
            for (CheckBox cb : dayChecks) {
                cb.setSelected(false);
            }
            updateLabel.run();
        });

        cancelButton.setOnAction(ev -> {
            dialog.setResult(null);
            dialog.close();
        });

        addButton.setOnAction(ev -> {
            List<MeetingTime> newExclusions = new ArrayList<>();
            String[] dayCodes = {"MON", "TUE", "WED", "THU", "FRI", "SAT"};

            int startMinutes = (int) startSlider.getValue();
            int endMinutes   = (int) endSlider.getValue();

            LocalTime startLocal = LocalTime.of(startMinutes / 60, startMinutes % 60);
            LocalTime endLocal   = LocalTime.of(endMinutes / 60, endMinutes % 60);

            String startTime = startLocal.toString();
            String endTime   = endLocal.toString();

            lastStartMinutes = startMinutes;
            lastEndMinutes   = endMinutes;

            for (int i = 0; i < dayChecks.length; i++) {
                boolean selected = dayChecks[i].isSelected();
                if (i < lastDaySelections.length) {
                    lastDaySelections[i] = selected;
                }
                if (selected) {
                    newExclusions.add(new MeetingTime(dayCodes[i], startTime, endTime));
                }
            }

            dialog.setResult(newExclusions);
            dialog.close();
        });

        dialog.showAndWait().ifPresent(newExclusions -> {
            if (!newExclusions.isEmpty()) {
                permanentExclusions.addAll(newExclusions);
                updateExclusionSummary();
            }
        });
    }

    private void updateExclusionSummary() {
        if (permanentExclusions.isEmpty()) {
            exclusionSummaryLabel.setText("No Exclusions Set");
            return;
        }

        exclusionSummaryLabel.setText(permanentExclusions.size() + " Exclusion Rule(s) Set");
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
    launch(args);
  }
}





