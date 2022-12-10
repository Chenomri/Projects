package model;

import java.util.Scanner;

public abstract class Question  {
	private int serialNumber; 
	private static int counter = 1000;
	private String questionText; 
	
	static Scanner s = new Scanner(System.in); 

	public Question(String questionText) {
		this.serialNumber = counter++;
		this.questionText = questionText;
	}



	public int getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public void changeQuestionText (String newText) {
		this.questionText = newText;
	}

	

	public String getQuestionText() {
		return questionText;
	}


	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Question number " +serialNumber + ", Text: " + questionText ;
	}





}
