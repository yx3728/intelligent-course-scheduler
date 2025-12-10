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

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import java.util.Arrays;
import java.util.Collections;
import java.time.DayOfWeek;
import javafx.stage.Popup;

public class ClientApp extends Application {
    private final ApiClient apiClient = new ApiClient("http://localhost:8080");
    //private TextArea resultArea;
    // --- 新增变量 (用于日历视图) ---
    private HBox optionsContainer;   // 顶部放 Option 1, Option 2 卡片的地方
    private GridPane calendarGrid;   // 下面的课表网格
    private Label statusLabel;       // 显示 "Found X schedules"
    // ---------------------------
    private ProgressIndicator progressIndicator;
    private Button generateButton;

    // --- 定义20种不同的颜色 (用于区分不同的 Schedule Option) ---
    // --- 升级版：现代 UI 配色 (莫兰迪/柔和色系) ---
    private final String[] OPTION_COLORS = {
            "#6366f1", // Indigo (主打蓝紫)
            "#ec4899", // Pink (柔和粉)
            "#10b981", // Emerald (清爽绿)
            "#f59e0b", // Amber (温暖黄)
            "#3b82f6", // Blue (天空蓝)
            "#8b5cf6", // Violet (优雅紫)
            "#f43f5e", // Rose (玫瑰红)
            "#0ea5e9", // Sky (天蓝)
            "#84cc16", // Lime (青柠)
            "#d946ef", // Fuchsia (紫红)
            "#06b6d4", // Cyan (青色)
            "#f97316"  // Orange (橙色)
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

        // === 0. 新增：页面顶部大标题 ===
        Label appTitle = new Label("Intelligent Course Scheduler");
        // 设置样式：大字体(24px)、加粗、深灰色、左侧留点空隙
        appTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 20 0 5 20;");

        // 1. 顶部输入栏
        HBox inputBar = new HBox(10);
        inputBar.setPadding(new Insets(15));
        inputBar.setAlignment(Pos.CENTER_LEFT);
        inputBar.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");

        Label courseLabel = new Label("Courses:");
        TextField courseField = new TextField("CS101, MATH201");
        courseField.setPromptText("e.g. CS101, MATH201");

        Spinner<Integer> minCreditsSpinner = new Spinner<>(0, 30, 8);
        Spinner<Integer> maxCreditsSpinner = new Spinner<>(0, 30, 18);
        Spinner<Integer> topKSpinner = new Spinner<>(1, 20, 5);
        minCreditsSpinner.setPrefWidth(100);
        maxCreditsSpinner.setPrefWidth(100);
        topKSpinner.setPrefWidth(100);

        generateButton = new Button("Generate Schedules");
        generateButton.getStyleClass().add("primary-button");

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(20, 20);

        inputBar.getChildren().addAll(
                courseLabel, courseField,
                new Label("Min Credits:"), minCreditsSpinner,
                new Label("Max Credits:"), maxCreditsSpinner,
                new Label("Top K:"), topKSpinner,
                generateButton, progressIndicator
        );

        // 2. 结果筛选区 (顶部 Option 卡片容器)
        VBox optionsArea = new VBox(10);
        optionsArea.setPadding(new Insets(10, 20, 10, 20));
        optionsArea.setStyle("-fx-background-color: #f8f9fa;");

        statusLabel = new Label("Ready to generate.");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // --- 关键修复：ScrollPane 设置 ---
        ScrollPane optionsScroll = new ScrollPane(); // 如果变量名报错，请把前面的 'ScrollPane ' 去掉，只写 optionsScroll = ...
        optionsScroll.setFitToHeight(true);
        optionsScroll.setFitToWidth(true); // <--- 这行代码必须得有！
        optionsScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        optionsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        optionsContainer = new HBox(15);
        optionsContainer.setAlignment(Pos.CENTER); // <--- 这行让卡片居中
        optionsContainer.setStyle("-fx-padding: 5;");

        // 将 HBox 放入 ScrollPane
        optionsScroll.setContent(optionsContainer);

        optionsArea.getChildren().addAll(statusLabel, optionsScroll);

        // 3. 核心：日历网格区
        calendarGrid = new GridPane();
        calendarGrid.getStyleClass().add("calendar-grid");
        calendarGrid.setHgap(5);
        calendarGrid.setVgap(5);
        initEmptyCalendar();

        ScrollPane calendarScroll = new ScrollPane(calendarGrid);
        calendarScroll.setFitToWidth(true);
        calendarScroll.setStyle("-fx-background-color: white;");

        // 4. 组装总布局
        BorderPane root = new BorderPane();

        // 修改：创建一个包含 [标题] + [输入栏] + [选项区] 的垂直容器
        VBox topContainer = new VBox(appTitle, inputBar, optionsArea);
        topContainer.setStyle("-fx-background-color: white;"); // 确保背景是白的

        root.setTop(topContainer);
        root.setCenter(calendarScroll);

        // 5. 事件处理逻辑
        generateButton.setOnAction(e -> {
            String courseCodesStr = courseField.getText().trim();
            if (courseCodesStr.isEmpty()) return;

            String[] codes = courseCodesStr.split(",");
            List<String> courseCodes = new ArrayList<>();
            for (String code : codes) courseCodes.add(code.trim());

            UserPrefs prefs = new UserPrefs(
                    minCreditsSpinner.getValue(),
                    maxCreditsSpinner.getValue(),
                    List.of(),
                    null
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

        Scene scene = new Scene(root, 1100, 750);
        try {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
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

    // --- 新方法 1: 处理结果并生成选项卡 ---
    // --- 修改后：支持多选的 Option 卡片生成 ---
    // --- 优化版：生成高颜值 Option 卡片 ---
    private void handleResults(List<ScheduleResult> results) {
        if (results.isEmpty()) {
            statusLabel.setText("No schedules found.");
            return;
        }
        statusLabel.setText("Found " + results.size() + " Schedule Options:");

        boolean[] visibility = new boolean[results.size()];
        if (results.size() > 0) visibility[0] = true;

        for (int i = 0; i < results.size(); i++) {
            final int index = i;
            String color = OPTION_COLORS[i % OPTION_COLORS.length];

            // 创建卡片容器
            HBox card = new HBox(10); // HBox 让色条和文字横向排列
            card.getStyleClass().add("option-card");

            // 布局逻辑
            card.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(card, Priority.ALWAYS);
            card.setMinWidth(140);

            // 1. 左侧色条 (装饰用)
            Pane colorStrip = new Pane();
            colorStrip.setPrefWidth(6); // 6像素宽的条
            colorStrip.setMinWidth(6);
            // 圆角处理：左上和左下圆角
            colorStrip.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 4 0 0 4;");

            // 2. 文字区域
            VBox textBox = new VBox(3);
            textBox.setAlignment(Pos.CENTER_LEFT);

            Label title = new Label("Option " + (i + 1));
            title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #334155;");

            Label score = new Label("Score: " + String.format("%.1f", results.get(i).score()));
            score.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;"); // 灰色副标题

            textBox.getChildren().addAll(title, score);

            // 组合
            card.getChildren().addAll(colorStrip, textBox);

            // --- 动态样式逻辑 ---
            Runnable updateStyle = () -> {
                if (visibility[index]) {
                    // 选中状态：背景变浅色 (利用颜色的透明度)，边框高亮
                    // 注意：这里用 RGB 拼凑一个极淡的背景色
                    card.setStyle("-fx-background-color: white; -fx-border-color: " + color + "; -fx-border-width: 1.5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
                    title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + color + ";"); // 标题变色
                } else {
                    // 未选中状态：极简白
                    card.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1;");
                    title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #334155;");
                }
            };

            // 初始化样式
            updateStyle.run();

            // 点击事件
            card.setOnMouseClicked(e -> {
                visibility[index] = !visibility[index];
                updateStyle.run();
                drawMultipleSchedules(results, visibility);
            });

            optionsContainer.getChildren().add(card);
        }

        drawMultipleSchedules(results, visibility);
    }

    // --- 新方法 2: 在网格上绘制课表 ---
    // --- 调试版: 在网格上绘制课表 ---
    // --- FINAL FIX: Draw Schedule on Grid ---
    // --- 修改后：在同一个网格上绘制多个课表 ---
// --- UPDATED: Draw Multiple Schedules Side-by-Side ---
    // --- UPDATED: 包含点击悬浮窗功能的绘制方法 ---
    private void drawMultipleSchedules(List<ScheduleResult> allResults, boolean[] visibility) {
        initEmptyCalendar(); // 清空网格

        // 1. 容器映射 (用于处理同一时间段的并排显示)
        java.util.Map<String, HBox> cellContainers = new java.util.HashMap<>();

        // 2. 遍历所有结果
        for (int i = 0; i < allResults.size(); i++) {
            if (!visibility[i]) continue; // 跳过未选中的

            ScheduleResult result = allResults.get(i);
            String color = OPTION_COLORS[i % OPTION_COLORS.length];

            for (var section : result.sections()) {
                for (var mt : section.meetingTimes()) {
                    try {
                        DayOfWeek day = parseDay(mt.day());
                        if (day == null || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) continue;

                        int col = getDayColumn(day);

                        String timeStr = mt.start().toString();
                        int startHour;
                        if (timeStr.contains(":")) {
                            startHour = Integer.parseInt(timeStr.split(":")[0]);
                        } else {
                            startHour = Integer.parseInt(timeStr);
                        }

                        int row = startHour - 7;
                        if (row < 1) row = 1;

                        // --- 创建课程色块 ---
                        VBox block = new VBox(2);
                        block.setStyle("-fx-background-color: " + color + "; -fx-padding: 3px; -fx-background-radius: 4px; -fx-cursor: hand;"); // 加了手型光标
                        HBox.setHgrow(block, Priority.ALWAYS);
                        block.setMaxWidth(Double.MAX_VALUE);

                        Text code = new Text("Opt" + (i + 1) + ": " + section.courseId());
                        code.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 9px;");

                        Text info = new Text(mt.start() + "-" + mt.end());
                        info.setStyle("-fx-fill: white; -fx-font-size: 8px;");

                        block.getChildren().addAll(code, info);

// === 新增：色块弹入动画 (Pop-in Animation) ===
                        // 1. 初始状态：完全透明且缩小
                        block.setOpacity(0);
                        block.setScaleX(0.5);
                        block.setScaleY(0.5);

                        // 2. 创建淡入 + 放大动画
                        javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(400), block);
                        fade.setFromValue(0);
                        fade.setToValue(1);

                        javafx.animation.ScaleTransition scale = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(400), block);
                        scale.setFromX(0.5);
                        scale.setToX(1.0);
                        scale.setFromY(0.5);
                        scale.setToY(1.0);

                        // 3. 组合动画并播放
                        javafx.animation.ParallelTransition entryAnimation = new javafx.animation.ParallelTransition(fade, scale);
                        // 加一点延迟，让它们一个接一个出现，而不是同时出现，效果更棒！
                        entryAnimation.setDelay(javafx.util.Duration.millis(i * 50));
                        entryAnimation.play();
                        // ==========================================

                        // ==========================================
                        // ✨ 新增功能：点击弹出详情浮窗 (Popup)
                        // ==========================================
                        String detailText = "Course: " + section.courseId() + "\n" +
                                "Building: " + section.buildingId() + "\n" +
                                "Instructor: " + section.instructor();

                        block.setOnMouseClicked(event -> {
                            // 1. 创建浮窗
                            Popup popup = new Popup();
                            popup.setAutoHide(true); // 点击其他地方自动关闭

                            // 2. 设置内容 (漂亮的白色卡片样式)
                            Label content = new Label(detailText);
                            content.setStyle("-fx-background-color: white;" +
                                    "-fx-text-fill: #333;" +
                                    "-fx-padding: 10px;" +
                                    "-fx-border-color: #ccc;" +
                                    "-fx-border-width: 1px;" +
                                    "-fx-border-radius: 5px;" +
                                    "-fx-background-radius: 5px;" +
                                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" + // 加阴影
                                    "-fx-font-size: 13px;" +
                                    "-fx-font-family: 'Segoe UI', sans-serif;");

                            popup.getContent().add(content);

                            // 3. 在鼠标位置显示
                            popup.show(block.getScene().getWindow(), event.getScreenX() + 10, event.getScreenY() + 10);

                            // 阻止事件冒泡 (防止触发其他点击)
                            event.consume();
                        });
                        // ==========================================

                        // --- 并排布局逻辑 ---
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
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // --- HELPER: Handle MON, TUE, WED mapping ---
    private DayOfWeek parseDay(String dayStr) {
        if (dayStr == null) return null;
        String d = dayStr.trim().toUpperCase();

        // Handle full names
        try {
            return DayOfWeek.valueOf(d);
        } catch (IllegalArgumentException e) {
            // Handle abbreviations
            switch (d) {
                case "MON": return DayOfWeek.MONDAY;
                case "TUE": return DayOfWeek.TUESDAY;
                case "WED": return DayOfWeek.WEDNESDAY;
                case "THU": return DayOfWeek.THURSDAY;
                case "FRI": return DayOfWeek.FRIDAY;
                case "SAT": return DayOfWeek.SATURDAY;
                case "SUN": return DayOfWeek.SUNDAY;
                default: return null;
            }
        }
    }


    // --- 辅助方法: 初始化空网格 ---
    // --- 修复版: 初始化空网格 (防止越点越窄) ---
    private void initEmptyCalendar() {
        // 1. 清除旧的内容 (色块、标签)
        calendarGrid.getChildren().clear();

        // 2. !!! 关键修复 !!! 清除旧的列约束和行约束
        // 如果不加这两行，每次重绘都会叠加新的约束，导致列越来越窄
        calendarGrid.getColumnConstraints().clear();
        calendarGrid.getRowConstraints().clear();

        String[] days = {"Time", "Mon", "Tue", "Wed", "Thu", "Fri"};

        // 3. 画表头 & 定义列宽
        for (int i = 0; i < days.length; i++) {
            Label l = new Label(days[i]);
            l.getStyleClass().add("day-header");
            l.setMaxWidth(Double.MAX_VALUE);
            calendarGrid.add(l, i, 0);

            // 设置列宽: 时间栏窄一点(10%)，其他平分(18%)
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(i == 0 ? 10 : 18);
            calendarGrid.getColumnConstraints().add(cc);
        }

        // 4. 画时间行 (8点 - 20点)
        for (int h = 8; h <= 20; h++) {
            int row = h - 7;
            // 时间标签
            Label time = new Label(h + ":00");
            time.getStyleClass().add("time-label");
            GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);
            calendarGrid.add(time, 0, row);

            // 空白格子 (用于显示网格线)
            for (int d = 1; d <= 5; d++) {
                Pane p = new Pane();
                p.getStyleClass().add("grid-cell");
                p.setMinHeight(50); // 每个格子的高度
                calendarGrid.add(p, d, row);
            }
        }
    }

    // --- 辅助方法: 映射周几到列索引 ---
    private int getDayColumn(DayOfWeek day) {
        switch (day) {
            case MONDAY: return 1;
            case TUESDAY: return 2;
            case WEDNESDAY: return 3;
            case THURSDAY: return 4;
            case FRIDAY: return 5;
            default: return -1;
        }
    }

  public static void main(String[] args) {
    launch(args);
  }
}





