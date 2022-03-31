import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.*;
import java.io.FileInputStream;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.*;
import javafx.scene.input.KeyEvent;
import java.util.Scanner;
import java.util.Arrays;
import javafx.scene.control.Alert.AlertType; 
import javafx.geometry.Insets;

public class BattleshipGUIBryanWu extends Application{
	
	public static BorderPane mainPane = new BorderPane();
	public static GridPane player = new GridPane();
	public static GridPane cpu = new GridPane(); 
	public static GridPane messages = new GridPane();
	public static Button[][] buttonPlayer = new Button[10][10]; 
	public static Button[][] buttonCpu = new Button[10][10];
	public static char[][] charPlayer = new char[10][10]; 
	public static char[][] charCpu = new char[10][10];
	public static Text textPlayer = new Text("Please import the PLAYER.txt file!"); 
	public static Text textCpu = new Text("Please import the CPU.txt file!"); 
	public static MenuItem open;
	public static MenuItem restart;
	public static MenuItem exit;
	FileChooser fileChooser = new FileChooser();
	public static int counterPlayer = 0;
	public static int counterCpu = 0;
	public static Alert alert = new Alert(AlertType.INFORMATION);
	public static String playerBoat = "";
	public static String cpuBoat = "";

	public static void main(String[] args){
		launch(args);
	}
	//Interface Setup
	public void start(Stage primaryStage){
		Scene scene = new Scene(mainPane, 950, 680);
		scene.getStylesheets().add("battleship.css");

		Text playerMessages = new Text("Player Messages");
		playerMessages.setFill(Color.BLUE);
		playerMessages.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		Text cpuMessages = new Text("CPU Messages");
		cpuMessages.setFill(Color.RED);
		cpuMessages.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		textPlayer.setFill(Color.BLUE);
		textPlayer.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		textCpu.setFill(Color.RED);
		textCpu.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		messages.getColumnConstraints().add(new ColumnConstraints(940));
		messages.getRowConstraints().add(new RowConstraints(20));
		messages.getRowConstraints().add(new RowConstraints(60));
		messages.getRowConstraints().add(new RowConstraints(10));
		messages.getRowConstraints().add(new RowConstraints(20));
		messages.getRowConstraints().add(new RowConstraints(60));
		messages.setPrefWidth(950);
		messages.setPrefHeight(190);
		messages.add(playerMessages, 0, 0);
		messages.add(textPlayer, 0, 1);
		messages.add(cpuMessages, 0, 3);
		messages.add(textCpu, 0, 4);
		messages.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 3;" + "-fx-border-radius: 1;" + "-fx-border-color: black;");
		
		Separator sepHor = new Separator();
		sepHor.setId("sepHor");
		sepHor.setStyle("-fx-background-color: black;" + "-fx-background-radius: 2;");
        	messages.setColumnSpan(sepHor, 1);
        	messages.add(sepHor, 0, 2);
		
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		file.setMnemonicParsing(true);
		
		//Open function
		open = new MenuItem("Open");
		open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		open.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				openFile();
			}
		});
		
		//Restart Function	
		restart = new MenuItem("Restart");
		restart.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
		restart.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				for (int i = 0; i < 10; i++) {
      				for (int j = 0; j < 10; j++) {
      				buttonCpu[i][j].setText("");
     				buttonCpu[i][j].setGraphic(null);
      				buttonPlayer[i][j].setText("");
      				buttonPlayer[i][j].setGraphic(null);
      				}
				}
    			counterPlayer = 0;
    			counterCpu = 0;
	    		textPlayer.setText("Please import the PLAYER.txt file!");
	    		textCpu.setText("Please import the CPU.txt file!");
			}
		});
		
		SeparatorMenuItem separator = new SeparatorMenuItem();
		
		//Exit function
		exit = new MenuItem("Exit");
		exit.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
    		exit.setOnAction(new EventHandler<ActionEvent>(){
    			public void handle(ActionEvent event) {
    				System.exit(0);
    			}
    		});

		makeBoards(buttonPlayer, player, true);
		player.setPrefWidth(475);
		makeBoards(buttonCpu, cpu, false);
		cpu.setPrefWidth(475);
		
		file.getItems().addAll(open, restart, separator, exit);
		menuBar.getMenus().addAll(file);
		mainPane.setTop(menuBar);
		mainPane.setLeft(player);
		mainPane.setRight(cpu);
		mainPane.setCenter(null);
		mainPane.setBottom(messages);
		primaryStage.setTitle("Battleship GUI");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		Attack();
		primaryStage.show();
		
	}
	//To create both boards
	public void makeBoards(Button[][] boards, GridPane board, Boolean player){
		GridPane grid = new GridPane();
		for (int i = 0; i < 10; i++){
			grid.getColumnConstraints().add(new ColumnConstraints(40));
			grid.getRowConstraints().add(new RowConstraints(40));
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				for (i = 0; i < 10; i++){
					for (j = 0; j < 10; j++){
						boards[i][j] = new Button();
						boards[i][j].setMaxSize(40, 40);
						boards[i][j].setDisable(true);
						boards[i][j].setPadding(new Insets(0));
						grid.add(boards[i][j], j, i);
					}
				}
			}
		}
		board.getColumnConstraints().add(new ColumnConstraints(50));
		board.getColumnConstraints().add(new ColumnConstraints(400));
		board.getRowConstraints().add(new RowConstraints(50));
		board.getRowConstraints().add(new RowConstraints(400));
		Label row = new Label();
		Label col = new Label();
		try {
			row.setGraphic(new ImageView(new Image(new FileInputStream("rows.png"))));
			col.setGraphic(new ImageView(new Image(new FileInputStream("cols.png"))));
		} catch(Exception e) {
		}
		board.add(row, 0, 1);
		board.add(col, 1, 0);
		board.add(grid, 1, 1);
		board.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 3;" + "-fx-border-radius: 1;" + "-fx-border-color: black;");	
	}
	//Opens Files into Boards
	private void openFile(){
		Window stage = mainPane.getScene().getWindow();
		fileChooser.setTitle("Load File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
		
		try {
			File file = fileChooser.showOpenDialog(stage);
			fileChooser.setInitialDirectory(file.getParentFile());
				if (file.getName().equals("PLAYER.txt")) {
					loadFile(charPlayer, file);
					textPlayer.setText("File Loaded!");
				} else {
					loadFile(charCpu, file);
					textCpu.setText("File Loaded!");
				}
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (file.getName().equals("PLAYER.txt")) {
							buttonPlayer[i][j].setText(Character.toString(charPlayer[i][j]));
							buttonPlayer[i][j].setStyle("-fx-text-fill: #0000ff");
							buttonPlayer[i][j].setDisable(false);
						} else {
							buttonCpu[i][j].setText("*");
							buttonCpu[i][j].setStyle("-fx-text-fill: #ff0000");
							buttonCpu[i][j].setDisable(false);
						}
					}
				}
		} catch (Exception e) {
		}		
	}
	//Loads File
	private void loadFile(char[][] board, File files){
		try {
        	Scanner file = new Scanner(files);
        	while (file.hasNextLine()) {
        		for (int row = 0; row < board.length; row++) {
					for (int col = 0; col < board[row].length; col++) {
						board[row][col] = file.next().charAt(0);
					}
        		}
			}
		}
		catch (FileNotFoundException exception) {
			System.out.println("Error opening file");
		}
	}
	//Player and CPU attacks
	public static void Attack() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				buttonCpu[i][j].setOnAction(new EventHandler<ActionEvent>(){
					public void handle(ActionEvent event) {
						String fileName = "";
						Button source = new Button();
						source = (Button)event.getSource();
						buttonFind:
						for (int k = 0; k < 10; k++) {
							for (int l = 0; l < 10; l++) {
								if (buttonCpu[k][l] == source) {
									if (charCpu[k][l] != '*') {
										fileName = "H.png";
										counterPlayer++;
										textPlayer.setText("Direct hit sir, nice shot!");
									} else {
										fileName = "M.png";
										textPlayer.setText("You have missed sir!");
									}
									playerBoat += Character.toString(charCpu[k][l]);
									buttonCpu[k][l].setText("");
									try {
									source.setGraphic(new ImageView(new Image(new FileInputStream(fileName))));
									} catch (Exception e) {
									}
									source.setDisable(true);
									if (playerBoat.length() - playerBoat.replace("C", "").length() == 5) {
										textPlayer.setText("Direct hit sir, nice shot!\nYou have sunk the computer's Aircraft Carrier!");
										playerBoat = playerBoat.replace("C", "");
									} else if (playerBoat.length() - playerBoat.replace("B", "").length() == 4) {
										textPlayer.setText("Direct hit sir, nice shot!\nYou have sunk the computer's Battleship!");
										playerBoat = playerBoat.replace("B", "");
									} else if (playerBoat.length() - playerBoat.replace("S", "").length() == 3) {
										textPlayer.setText("Direct hit sir, nice shot!\nYou have sunk the computer's Submarine!");
										playerBoat = playerBoat.replace("S", "");
									} else if (playerBoat.length() - playerBoat.replace("D", "").length() == 3) {
										textPlayer.setText("Direct hit sir, nice shot!\nYou have sunk the computer's Destroyer!");
										playerBoat = playerBoat.replace("D", "");
									} else if (playerBoat.length() - playerBoat.replace("P", "").length() == 2) {
										textPlayer.setText("Direct hit sir, nice shot!\nYou have sunk the computer's Patrol Boat!");
										playerBoat = playerBoat.replace("P", "");
									}
									winCheck(buttonCpu, counterPlayer, true);
									break buttonFind;
								}
							}
						}
						int row = 0;
						int col = 0;
						do {
							row = (int)(Math.random() * 10);
							col = (int)(Math.random() * 10);
						}while(buttonPlayer[row][col].getText().equals(""));
						if (!buttonPlayer[row][col].getText().equals("*")) {
							fileName = "H.png";
							textCpu.setText("The computer has hit your ship at " + (char)(row + 'A') + col + "!");
							counterCpu++;
						} else {
							fileName = "M.png";
							textCpu.setText("The computer has attacked " + (char)(row + 'A') + col + " and missed!");
						}
						cpuBoat += buttonPlayer[row][col].getText();
						buttonPlayer[row][col].setText("");
						try {
						buttonPlayer[row][col].setGraphic(new ImageView(new Image(new FileInputStream(fileName))));
						} catch (Exception e) {
						} 
						buttonPlayer[row][col].setDisable(true);
						if (cpuBoat.length() - cpuBoat.replace("C", "").length() == 5) {
							textCpu.setText("The computer has hit your ship at " + (char)(row + 'A') + col + "!\nThe computer has sunk your Aircraft Carrier!");
							cpuBoat = cpuBoat.replace("C", "");
						} else if (cpuBoat.length() - cpuBoat.replace("B", "").length() == 4) {
							textCpu.setText("The computer has hit your ship at " + (char)(row + 'A') + col + "!\nThe computer has sunk your Battleship!");
							cpuBoat = cpuBoat.replace("B", "");
						} else if (cpuBoat.length() - cpuBoat.replace("S", "").length() == 3) {
							textCpu.setText("The computer has hit your ship at " + (char)(row + 'A') + col + "!\nThe computer has sunk your Submarine!");
							cpuBoat = cpuBoat.replace("S", "");
						} else if (cpuBoat.length() - cpuBoat.replace("D", "").length() == 3) {
							textCpu.setText("The computer has hit your ship at " + (char)(row + 'A') + col + "!\nThe computer has sunk your Destroyer!");
							cpuBoat = cpuBoat.replace("D", "");
						} else if (cpuBoat.length() - cpuBoat.replace("P", "").length() == 2) {
							textCpu.setText("The computer has hit your ship at " + (char)(row + 'A') + col + "!\nThe computer has sunk your Patrol Boat!");
							cpuBoat = cpuBoat.replace("P", "");
						}
						winCheck(buttonPlayer, counterCpu, false);
					}
								
				});
			}
		}
	}
	//Checks boards for a win
	public static void winCheck(Button[][] button, int counter, boolean player) {
		if (counter == 17) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					buttonPlayer[i][j].setDisable(true);
					buttonCpu[i][j].setDisable(true);
				}
			}
		}
		if (counter == 17 && player == true) {
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.getDialogPane().setPrefSize(250, 50);
			alert.setContentText("You have won!");
			alert.showAndWait();
		} else if (counter == 17 && player == false) {
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.getDialogPane().setPrefSize(250, 50);
			alert.setContentText("The computer has won!");
			alert.showAndWait();
		}
	}
}		   