package model;

import java.io.Serializable;

public class AmericanQanswer extends Answer implements Serializable {
	private boolean isCorrect;


	public AmericanQanswer(String answer, boolean isCorrect) {
		super(answer);
		this.isCorrect = isCorrect;
	}


	public boolean isCorrect() {
		return true;
	}
	
	public int getAnswerLength() {
		return answerText.length();
	}



	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		if (isCorrect)
			buffer.append(answerText + "--> This answer is correct!");
		else
			buffer.append(answerText + "--> This answer is not correct!");
		return buffer.toString() ;
	}


	

}
