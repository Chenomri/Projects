package model;

import java.io.Serializable;

public class OpenQ extends Question implements Comparable<OpenQ> , Serializable{
	private Answer correctAnswer;


	public OpenQ(String questionText, Answer correctAnswer) {
		super(questionText);
		this.correctAnswer = correctAnswer;
	}



	public Answer getCorrectAnswer() {
		return correctAnswer;
	}



	public void setCorrectAnswer(Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	@Override
	public int compareTo(OpenQ openQuestion) {
		if (this.getCorrectAnswer().getAnswerLength() > openQuestion.getCorrectAnswer().getAnswerLength())
			return 1;
		if (this.getCorrectAnswer().getAnswerLength() < openQuestion.getCorrectAnswer().getAnswerLength())
			return -1;
		else return 0;
	}

	@Override
	public String toString() {
		return super.toString() + "\n answer to question is: " +correctAnswer ;
	}






}
