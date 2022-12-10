package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AmericanQ extends Question implements Comparable<AmericanQ> , Serializable {

	private List<AmericanQanswer> allAnswers; 

	public AmericanQ(String questionText, List<AmericanQanswer> allAnswers) {
		super(questionText);
		this.allAnswers = new ArrayList<AmericanQanswer>();
		this.allAnswers =allAnswers;

	}

	public Answer getAnswer (int index) {  
			return allAnswers.get(index); 

	}
	

	public List<AmericanQanswer> getAllAnswers() {
		return allAnswers;
	}

	public void setAllAnswers(List<AmericanQanswer> allAnswers) {
		this.allAnswers = allAnswers;
	}

	public void changeAmericanAnswer (String newAnswer) {
		for (int i = 0; i < allAnswers.size(); i++) 
			allAnswers.get(i).setAnswer(newAnswer);
	}
	

	public int getNumOfAnswers() {
		return allAnswers.size();
	}
	
	@Override
	public int compareTo(AmericanQ americanQues) {
		int value1=0;
		int value2=0;
		for (int i = 0; i < this.allAnswers.size(); i++) 
			value1 += this.allAnswers.get(i).getAnswerLength();
		for (int i = 0; i < americanQues.getNumOfAnswers(); i++) 
			value2 += americanQues.getAllAnswers().get(i).getAnswerLength();
		if (value1 > value2)
			return 1;
		if (value1 < value2)
			return -1;
		else return 0;
	}
	

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(super.toString());
		buffer.append("\n All answers:");
		for (int i = 0; i < allAnswers.size(); i++) {
			buffer.append("\n" + (i + 1) + ")" + " " + allAnswers.get(i).toString());
		}
		return buffer.toString();
	}



}
