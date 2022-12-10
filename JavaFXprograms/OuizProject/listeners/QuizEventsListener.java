package listeners;

import model.Question;

public interface QuizEventsListener {
	public boolean addQuestion(Question newQuestion); 
	public String showQuestions(); 
	public void updateAnswer(String newText); 
	public void updateQuestion(String newText); 
	public void deleteQuestion(int index);
	public void createManualTest();
	public void createAutomaticTest(); 
	public Object cloneAtest() throws CloneNotSupportedException;
	

}
