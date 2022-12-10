package quizMain;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.AmericanQ;
import model.AmericanQanswer;
import model.Answer;
import model.OpenQ;
import model.Question;
import model.Quiz;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(System.in);
		
		try {

			// hard coded
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

			Quiz myQuiz = new Quiz(allQuestions);
			
			
			
			// menu
			System.out.println("Welcome to Tamar's exam system! \n   ---> please choose one of the options below:");
			boolean flag = true;
			
			String A = null;
			while (flag) {
				System.out.println(
						" 1) Show all questions and answers \n 2) Add question with answer \n 3) Update question wording \n "
								+ "4) Update answer wording \n 5) Delete answer to question \n 6) Create manual test \n 7) Create auotmatic test "
								+ "\n 8) Create a copy of existent quiz"+ " \n 9) Save questions and answers in text files" + "\n 10) EXIT & Create binary file ");
				
				int num = s.nextInt();
				switch (num) {
				case 1:
					System.out.println(myQuiz.showQuestions());
					break;
				case 2:
					System.out.println("for american question insert 1 , for open insert 2");
					try {
						int selection = s.nextInt();
						switch (selection) {
						case 1:						//case 1 of case 2
							System.out.print("Insert your question: ");
							s.nextLine();
							String newQtext = s.nextLine();
							System.out.print("Insert how many answers do you have: ");
							num = s.nextInt();
							List<AmericanQanswer> newAnswer = new ArrayList<AmericanQanswer>();

							for (int i = 0; i < num; i++) {
								s.nextLine();
								System.out.print("Insert " + (i + 1) + " answer: ");
								A = s.nextLine();
								try {
									System.out.print("---> Insert correct {True}/Incorrect {False}: <---");
									boolean b = s.nextBoolean();
									newAnswer.add(new AmericanQanswer(A, b));
								} catch (InputMismatchException e) {
									System.out.println("Insert true or false! ");
									s.nextBoolean();

								}

							}
							myQuiz.addQuestion(new AmericanQ(newQtext, newAnswer));
							break;
						case 2:    //case 2 of case 2
							System.out.print("Insert your question: ");
							s.nextLine();
							newQtext = s.nextLine();
							System.out.println("Insert your correct answer: ");
							String Answer = s.nextLine();
							Answer w = new Answer(Answer);
							myQuiz.addQuestion(new OpenQ(newQtext, w));
							break;
						default:
							System.out.println("########--Insert 1 or 2!!!!--########");
							break;
						}
					} catch (InputMismatchException e) {
						System.out.println("please insert valid choice ");
						s.next();
					} catch (Exception e) {
						System.out.println("Error!");
					}
					break;
				case 3:
					System.out.println("Choose num of question you want to change: ");
					try {
						for (int i = 0; i < myQuiz.getnumOfQuestions(); i++)
							System.out.println(i + ")" + myQuiz.getQtext(allQuestions.get(i)));
						int selection = s.nextInt();
						s.nextLine();
						System.out.println("Insert new text: ");
						String newText = s.nextLine();
						allQuestions.get(selection).changeQuestionText(newText);
					} catch (InputMismatchException e) {
						System.out.println("Please enter valid choice! ");
						s.next();
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("This question number isn't exist ! ");
					} catch (Exception e) {
						System.out.println("Error! ");
					}

					break;
				case 4:
					System.out.println("Choose num of question you would like to change it's answer: ");
					for (int i = 0; i < myQuiz.getnumOfQuestions(); i++)
						System.out.println(i + ")" + myQuiz.getQtext(allQuestions.get(i)));

					int selection = s.nextInt();
					s.nextLine();
					System.out.println("your chosen question and it's answer is: ");
					System.out.println(allQuestions.get(selection).toString());
					if (allQuestions.get(selection) instanceof OpenQ) {
						System.out.println("Insert new text: ");
						String newText = s.nextLine();
						s.nextLine();
						myQuiz.updateAnswer(newText);
					}
					if (allQuestions.get(selection) instanceof AmericanQ) {
						System.out.println("Choose num of answer you would like to change");
						for (int i = 0; i < ((AmericanQ) allQuestions.get(selection)).getNumOfAnswers(); i++)
							System.out.println(((AmericanQ) allQuestions.get(selection)).getAllAnswers().get(i).toString());
						int selection2 = s.nextInt();
						System.out.println("Insert new text: ");
						String newText = s.nextLine();
						s.nextLine();
//						 ((americanQ)
//						 allQuestions[selection]).getAllAnswers()[selection2].changeA(newText);

					}

					break;
				case 5:
					System.out.println("Please choose number of question to delete:");
					try {
						for (int i = 0; i < myQuiz.getnumOfQuestions(); i++)
							System.out.println(i + ")" + myQuiz.getQtext(allQuestions.get(i)));
						selection = s.nextInt();
						s.nextLine();
						myQuiz.deleteQuestion(selection);
					} catch (InputMismatchException e) {
						System.out.println("Please enter valid choice! ");
						s.next();
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("This question number isn't exist ! ");
					} catch (Exception e) {
						System.out.println("Error! ");
					}
					break;
				case 6:
					try {
						System.out.println("Insert number of question in your test : ");
						int num1 = s.nextInt();
						List<Question> yourNewQuiz = new ArrayList<Question>();
						System.out.println("insert true for qustion you want, false if not");
						System.out.println("----------------------->");
						for (int i = 0; i < allQuestions.size(); i++) {
							System.out.println(myQuiz.getQuestions().get(i).toString());
							boolean choose = s.nextBoolean();
							if (choose) 
									yourNewQuiz.add(allQuestions.get(i));
							 else {
								i++;
							}
						}
						Quiz yourQuiz = new Quiz(yourNewQuiz);
						System.out.println("Here is the quiz you built ! ");
						System.out.println("------------------------------------");
						for (int i = 0; i < yourQuiz.getnumOfQuestions(); i++) {
							System.out.println(yourQuiz.getQuestions().get(i).toString());
							System.out.println("----------------------------------");
						}
					} catch (InputMismatchException e) {
						System.out.println("Please enter valid choice! ");
						s.next();
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("This question number isn't exist ! ");
					} catch (Exception e) {
						System.out.println("Error! ");
					}
					break;
				case 7:
					try {
						System.out.println("Insert number of question in your test : ");
						int num1 = s.nextInt();
						List<Question> yourRandomQuestion = new ArrayList<Question>();
						Random r = new Random();
						for (int i = 0; i < num1; i++) {
							int randomNumber = r.nextInt(allQuestions.size());
							yourRandomQuestion.add(allQuestions.get(randomNumber));

						}

						Quiz yourRandomQuiz = new Quiz(yourRandomQuestion);
						// yourRandomQuiz.sortQuestions();
						System.out.println("### Here is the quiz you built ! ### ");
						System.out.println("------------------------------------");
						for (int i = 0; i < yourRandomQuiz.getnumOfQuestions(); i++) {
							System.out.println(yourRandomQuiz.getQuestions().get(i).toString());
							System.out.println("----------------------------------");
						}
					} catch (InputMismatchException e) {
						System.out.println("Please enter valid choice ! ");
						s.next();
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("This question numsber isn't exist ! ");
					} catch (Exception e) {
						System.out.println("Error! ");
					}
					break;
				case 8: 
					Quiz cloneQuiz = (Quiz)myQuiz.cloneAtest();
					System.out.println("Here's your cloned test : ");
					System.out.println(cloneQuiz.toString());
					break;
				case 9: 
					//Text File
					//can change to your path if necessary 
					
					File questionFile = new File("C:\\Users\\USER\\eclipse-workspace\\Exam_2022.05.09.txt"); 
					File answerFile = new File("C:\\Users\\USER\\eclipse-workspace\\solution_2022.05.09.txt");
					questionFile.createNewFile();
					answerFile.createNewFile();
					PrintWriter pw = new PrintWriter(questionFile);
					pw.print(allQuestions);
					pw.close();
					PrintWriter pw2 = new PrintWriter(answerFile);
					pw2.print(numbers + "\n" + colors + "\n");
					pw2.print(a1 + "\n" + a2 + "\n" + a3 + "\n" + a4 + "\n" + a5);
					pw2.close();
				
					break;
				case 10: 
					try {
						 FileOutputStream fileOut = new FileOutputStream("C:\\Users\\USER\\eclipse-workspace\\Binary_File.dat");
						 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
						 objectOut.writeObject(myQuiz);
						 objectOut.close();
						 System.out.println("Data succesfully written to a file");
						 
					} catch (Exception e) {
						System.out.println("Error while saving.");
					}
					flag = false;
					System.out.println("THANK YOU! BYE BYE :)");
					break;

				}
			}
		} catch (NullPointerException e) {
			System.out.println("Error!");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Out of bounds");
		} 
			catch (Exception e) {
			System.out.println("Error!");
		}
	}


	}


