package listeners;

import model.Question;

public interface QuizUIEventsListener {
	void addQuestionToUI(Question newQuestion);
	void removeQuestionFromUI(Question question);
}
