package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.Enum.PaymentStatus;
import lk.ijse.cleancopvt.Enum.Rating;
import lk.ijse.cleancopvt.dto.FeedbackDTO;
import lk.ijse.cleancopvt.entity.Feedback;
import lk.ijse.cleancopvt.entity.Payment;
import lk.ijse.cleancopvt.repo.FeedbackRepo;
import lk.ijse.cleancopvt.repo.PaymentRepo;
import lk.ijse.cleancopvt.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO) {
        // Convert DTO to entity
        Feedback feedback = modelMapper.map(feedbackDTO, Feedback.class);
        Feedback savedFeedback = feedbackRepo.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackDTO.class);
    }

    @Override
    public FeedbackDTO updateFeedbackMessage(UUID id, String newComment) {
        Optional<Feedback> feedbackOpt = feedbackRepo.findById(id);
        if (feedbackOpt.isPresent()) {
            Feedback feedback = feedbackOpt.get();
            feedback.setComment(newComment);
            Feedback updatedFeedback = feedbackRepo.save(feedback);
            return modelMapper.map(updatedFeedback, FeedbackDTO.class);
        }
        throw new RuntimeException("Feedback not found with id: " + id);
    }

    @Override
    public void removeFeedback(UUID id) {
        Optional<Feedback> feedbackOpt = feedbackRepo.findById(id);
        if (feedbackOpt.isPresent()) {
            feedbackRepo.delete(feedbackOpt.get());
        } else {
            throw new RuntimeException("Feedback not found with id: " + id);
        }
    }

    @Override
    public List<FeedbackDTO> viewAllFeedbacks() {
        List<Feedback> feedbackList = feedbackRepo.findAll();
        return feedbackList.stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
                .toList();
    }

    public void submitFeedback(UUID paymentId, Rating rating, String comment) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getPaymentStatus() != PaymentStatus.PAID) {
            throw new RuntimeException("Cannot submit feedback before payment is completed!");
        }

        Feedback feedback = new Feedback();
//        feedback.setPayment(payment);
        feedback.setRating(rating);
        feedback.setComment(comment);

        feedbackRepo.save(feedback);
    }

}
