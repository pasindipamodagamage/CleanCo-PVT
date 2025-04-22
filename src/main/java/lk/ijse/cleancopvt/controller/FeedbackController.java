package lk.ijse.cleancopvt.controller;

import io.jsonwebtoken.Claims;
import lk.ijse.cleancopvt.Enum.Role;
import lk.ijse.cleancopvt.dto.FeedbackDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.service.impl.FeedbackServiceImpl;
import lk.ijse.cleancopvt.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feedback")
@CrossOrigin("*")
public class FeedbackController {

    private final FeedbackServiceImpl feedbackService;

    private final JwtUtil jwtUtil;

    private final ResponseDTO responseDTO;

    public FeedbackController(FeedbackServiceImpl feedbackService, JwtUtil jwtUtil, ResponseDTO responseDTO) {
        this.feedbackService = feedbackService;
        this.jwtUtil = jwtUtil;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/saveFeedback")
    public ResponseEntity<FeedbackDTO> addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO savedFeedback = feedbackService.addFeedback(feedbackDTO);
        return ResponseEntity.ok(savedFeedback);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedbackMessage(@PathVariable UUID id, @RequestBody String newComment) {
        FeedbackDTO updatedFeedback = feedbackService.updateFeedbackMessage(id, newComment);
        return ResponseEntity.ok(updatedFeedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFeedback(@PathVariable UUID id) {
        feedbackService.removeFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/viewAllFeedback")
    public ResponseEntity<List<FeedbackDTO>> viewAllFeedbacks() {
        List<FeedbackDTO> feedbacks = feedbackService.viewAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    private boolean hasRequiredRole(String authorization, Role... roles) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return false;
        }

        String token = authorization.substring(7);
        Claims claims = jwtUtil.getUserRoleCodeFromToken(token);
        String userRole = claims.get("role", String.class);

        for (Role role : roles) {
            if (role.name().equalsIgnoreCase(userRole)) {
                return true;
            }
        }

        return false;
    }
}
