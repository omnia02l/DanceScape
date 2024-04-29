package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Result;
import org.sid.ebankingbackend.entities.ResultComment;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.repository.ResultCommentRepository;
import org.sid.ebankingbackend.repository.ResultRepository;
import org.sid.ebankingbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class ResultCommentServiceIMP implements ResultCommentService {

    @Autowired
    private ResultCommentRepository resultCommentRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ResultComment> getAllResultComments() {
        return (List<ResultComment>) resultCommentRepository.findAll();
    }

    public ResultComment getResultCommentById(Long id) {
        return resultCommentRepository.findById(id).orElse(null);
    }


    public ResultComment getResultCommentByIdResult(Long id) {
        return resultCommentRepository.findById(id).orElse(null);
    }

    public ResultComment addCommentToResult(Long resultId, Long userId, ResultComment comment) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + resultId));
        comment.setResult(result);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        comment.setUser(user);
        return resultCommentRepository.save(comment);
    }

    public void deleteResultComment(Long id) {
        resultCommentRepository.deleteById(id);
    }

    private List<String> badWords = Arrays.asList("zah", "PIPI");

    public ResultComment moderateComment(ResultComment comment) {
        for (String badWord : badWords) {
            if (comment.getComment().toLowerCase().contains(badWord)) {
                throw new IllegalArgumentException("Comment contains inappropriate content");
            }
        }
        return resultCommentRepository.save(comment);
    }
}
