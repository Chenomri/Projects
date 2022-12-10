package quizMain;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import listeners.QuizUIEventsListener;
import model.AmericanQ;
import model.AmericanQanswer;
import model.Answer;
import model.OpenQ;
import model.Question;
import model.Quiz;
import view.AbstractQuizView;


public class MainFX extends Application implements AbstractQuizView {
	private ComboBox<Question> cmbAllQuestions = new ComboBox<Question>();
	

	public static void main(String[] args)  {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Welcome to Tamar's quiz ! ");
		GridPane gpRoot = new GridPane();
		gpRoot.setBackground(new Background(new BackgroundFill(Color.CORNSILK, CornerRadii.EMPTY, Insets.EMPTY)));
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		
		Button btnCase1 = new Button("Show all questions and Answers");
		Button btnCase2 = new Button("Add new question with answer");
		Button btnCase3 = new Button("Update questions wording");
		Button btnCase4 = new Button("Update answer wording");
		Button btnCase5 = new Button("Delete answer to question");
		Button btnCase6 = new Button("Create manual test ");
		Button btnCase7 = new Button("Create auotmatic test");
		Button btnCase8 = new Button("Create a copy of existent quiz");
		btnCase1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				// hard coded for example 
				// Answers
				Answer a1 = new Answer(" 1");
				Answer a2 = new Answer(" 2");
				Answer a3 = new Answer(" 3");
				Answer a4 = new Answer(" 4");
				Answer a5 = new Answer(" 12");
				List<AmericanQanswer> colors = new ArrayList<AmericanQanswer>();
				colors.add(new AmericanQanswer("blue", true));
				colors.add(new AmericanQanswer("green", false));
				colors.add(new AmericanQanswer("grey", false));
				colors.add(new AmericanQanswer("white", false));
				List<AmericanQanswer> numbers = new ArrayList<AmericanQanswer>();
				numbers.add(new AmericanQanswer("one", false));
				numbers.add(new AmericanQanswer("two", true)); 
				numbers.add(new AmericanQanswer("three", false));

				// Questions
				List<Question> allQuestions = new ArrayList<Question>();
				allQuestions.add(new OpenQ(" how many legs does a dog has ?", a4));
				allQuestions.add(new AmericanQ("Color of sky? ", colors));
				allQuestions.add(new OpenQ("How many months in a year ? ", a5));
				allQuestions.add(new AmericanQ("1+1 ? ", numbers));
				allQuestions.add(new OpenQ("How many seasons in a year? ", a4));
				
				final ObservableList<Quiz> data = FXCollections.observableArrayList(new Quiz(allQuestions));
				
				TableView<Quiz> table = new TableView<Quiz>();
				Label label = new Label("All Questions");
		        label.setFont(new Font("Arial", 20));
		        table.setEditable(true);
		        TableColumn<Quiz, String> serialNum = new TableColumn<Quiz, String>("Serial number");
		        serialNum.setMinWidth(100);
		     //   serialNum.cellValueFactoryProperty(new PropertyValueFactory<>("serialNumber"));
		        TableColumn<Quiz, String> qText = new TableColumn<Quiz, String>("Questions text");
		        qText.setMinWidth(400);
		        qText.setCellValueFactory(new PropertyValueFactory<>("questionText"));
		        TableColumn <Quiz, String>answers = new TableColumn<Quiz, String>("Answers");
		        answers.setMinWidth(700);
		        
		        table.getColumns().setAll(serialNum, qText, answers);
		        table.setItems(data);
		        
		        VBox vbox = new VBox();
		        vbox.setSpacing(10);
		        vbox.setPadding(new Insets(0, 0, 0, 0));
		        vbox.getChildren().addAll(label, table);
		        stage.setScene(new Scene(vbox, 1000, 1000));
		        stage.show();

			}


		});
		
		btnCase2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				GridPane case2Pane = new GridPane();
				case2Pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
				case2Pane.add(new Label("Choose type of question"),1,1);
				Button americanBtn = new Button("For american question");
				case2Pane.add(americanBtn, 2, 2);
				Button openBtn = new Button("For open question"); 
				case2Pane.add(openBtn, 2, 4);
				americanBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						VBox americanBox = new VBox(); 
						americanBox.setSpacing(5);
						americanBox.setAlignment(Pos.CENTER);
						Label Qtext = new Label("Insert your question text"); 
						TextField tfText = new TextField();
						Label correctAns = new Label("Insert your correct answer");
						TextField tfCorrectAns = new TextField();
						Label falseAns1 = new Label("Insert your first false answer"); 
						TextField tfFalse1 = new TextField();
						Label falseAns2 = new Label("Insert your second false answer");
						TextField tfFalse2 = new TextField();
						americanBox.getChildren().addAll(Qtext,tfText,correctAns,tfCorrectAns,falseAns1,tfFalse1,falseAns2,tfFalse2);
						stage.setScene(new Scene(americanBox,500,500));
						stage.show();
					
						
					}
				});
				openBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						VBox openBox = new VBox(); 
						openBox.setSpacing(5);
						openBox.setAlignment(Pos.CENTER);
						Label Qtext = new Label("Insert your question text"); 
						TextField tfText = new TextField();
						Label correctAns = new Label("Insert your correct answer");
						TextField tfCorrectAns = new TextField();
						openBox.getChildren().addAll(Qtext,tfText,correctAns,tfCorrectAns);
						stage.setScene(new Scene(openBox,250,250));
						stage.show();
						
					}
				});
				
				stage.setScene(new Scene(case2Pane,400,400));
				stage.show();
				
			}
		});
		gpRoot.add(btnCase1, 0, 1);
		gpRoot.add(btnCase2, 0, 2);
		gpRoot.add(btnCase3, 0, 3);
		gpRoot.add(btnCase4, 0, 4);
		gpRoot.add(btnCase5, 0, 5);
		gpRoot.add(btnCase6, 0, 6);
		gpRoot.add(btnCase7, 0, 7);
		gpRoot.add(btnCase8, 0, 8);
        stage.setScene(new Scene(gpRoot, 500, 500));
        stage.show();
		
	}

	@Override
	public void registerListener(QuizUIEventsListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addQuestionToUI(Question newQuestion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeQuestionFromUI(Question questionToRemove) {
		// TODO Auto-generated method stub
		
	}

}
