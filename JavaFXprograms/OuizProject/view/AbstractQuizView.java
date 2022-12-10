package view;

import listeners.QuizUIEventsListener;
import model.Question;

public interface AbstractQuizView {
	void registerListener(QuizUIEventsListener listener);
	void addQuestionToUI(Question newQuestion);
	void removeQuestionFromUI(Question questionToRemove);

}
