package controller;

import listeners.QuizEventsListener;
import listeners.QuizUIEventsListener;
import model.Question;
import model.Quiz;
import view.AbstractQuizView;

public class QuizController implements QuizEventsListener , QuizUIEventsListener  {
	private Quiz quizModel; 
	private AbstractQuizView quizView;

	public QuizController(Quiz Model, AbstractQuizView View) {
		quizModel = Model;
		quizView = View;
		
		quizModel.registerListener(this);
		quizView.registerListener(this);
	}

	@Override
	public void addQuestionToUI(Question newQuestion) {
		quizView.addQuestionToUI(newQuestion);
		
	}

	@Override
	public void removeQuestionFromUI(Question question) {
		quizView.removeQuestionFromUI(question);
		
	}

	@Override
	public boolean addQuestion(Question newQuestion) {
		return quizModel.addQuestion(newQuestion);
	}

	@Override
	public String showQuestions() {
		return quizModel.showQuestions();
	}

	@Override
	public void updateAnswer(String newText) {
		quizModel.updateAnswer(newText);
		
	}

	@Override
	public void updateQuestion(String newText) {
		quizModel.updateQuestion(newText);
		
	}

	@Override
	public void deleteQuestion(int index) {
		quizModel.deleteQuestion(index);
		
	}

	@Override
	public void createManualTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAutomaticTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object cloneAtest() throws CloneNotSupportedException {
		return quizModel.cloneAtest();
	}


}
