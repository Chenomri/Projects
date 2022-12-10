package model;

import java.io.Serializable;

public class Answer implements Serializable{
public String answerText;
	

	
	public Answer(String answer) {
		this.answerText = answer;
	}

	public String getAnswer() {
		return answerText;
	}


	public void setAnswer(String answer) {
		this.answerText = answer;
	}
	
	public int getAnswerLength() {
		return answerText.length();
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answerText == null) ? 0 : answerText.hashCode());
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
		Answer other = (Answer) obj;
		if (answerText == null) {
			if (other.answerText != null)
				return false;
		} else if (!answerText.equals(other.answerText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "The answer is" + answerText;
	}

}
