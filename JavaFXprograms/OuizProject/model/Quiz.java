package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import listeners.QuizEventsListener;
import listeners.QuizEventsListener;

public class Quiz implements Serializable, Cloneable, QuizEventsListener {
	private List<Question> allQuestions;
	private List<QuizEventsListener> listeners; 

	public Quiz(List<Question> allQuestions) {
		this.allQuestions = new ArrayList<Question>();
		this.allQuestions = allQuestions;
		this.listeners = new ArrayList<QuizEventsListener>();

	}
	
	public void registerListener(QuizEventsListener listener) {
		listeners.add(listener);
	}

	public List<Question> getQuestions() {
		return allQuestions;
	}

	public int getnumOfQuestions() {
		return allQuestions.size();
	}

	public String getQtext(Question q) {
		return q.getQuestionText();
	}

	public void changeQText(String newText) {
		
	}


	
	public void sort() { 
		Collections.sort(allQuestions, new Comparator<Question>() {

			@Override
			public int compare(Question q1, Question q2) {
				if(q1.getQuestionText().length() > q2.getQuestionText().length()) {
					return 1;
				}
				else return 0;
			}
		});
	}


	@Override
	public String showQuestions() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("--------------------------------------------------");
		buffer.append("\nAll Questions are : ------> ");
		for (int i = 0; i < allQuestions.size(); i++) {
			buffer.append("\n" + (i + 1) + ")" + " " + allQuestions.get(i).toString());
			buffer.append("\n###-----------------------------------------###");
		}
		return buffer.toString();
	}

	@Override
	public void updateAnswer(String newText) {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ)
				((OpenQ) allQuestions.get(i)).getCorrectAnswer().setAnswer(newText);
			if (allQuestions.get(i) instanceof AmericanQ)
				((AmericanQ) allQuestions.get(i)).changeAmericanAnswer(newText);

		}
	}
	
	private void fireAddQuestionEvent(Question newQuestion) {
		for (QuizEventsListener l : listeners) 
			l.addQuestion(newQuestion);
	}
	
	private void fireRemoveQuestionEvent(int index) {
		for (QuizEventsListener l : listeners) {
			l.deleteQuestion(index);
		}
	}

	@Override
	public void updateQuestion(String newText) {
		for (int i = 0; i < allQuestions.size(); i++)
			allQuestions.get(i).setQuestionText(newText);
		
	}

	@Override
	public void deleteQuestion(int index) {
			allQuestions.remove(index);

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
		return super.clone();
	}

	@Override
	public boolean addQuestion(Question newQuestion) {
		for (int i = 0; i < allQuestions.size(); i++)
			if (newQuestion.equals(allQuestions.get(i)))
				return false;
		allQuestions.add(newQuestion);
		return true;
	}

}
