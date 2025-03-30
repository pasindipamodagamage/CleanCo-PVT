package lk.ijse.cleancopvt.service;

import lk.ijse.cleancopvt.dto.FeedbackDTO;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    FeedbackDTO addFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO updateFeedbackMessage(UUID id, String newComment);
    void removeFeedback(UUID id);
    List<FeedbackDTO> viewAllFeedbacks();
}
