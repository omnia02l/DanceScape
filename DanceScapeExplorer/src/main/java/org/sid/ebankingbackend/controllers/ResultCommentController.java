package org.sid.ebankingbackend.controllers;


import org.sid.ebankingbackend.entities.ResultComment;
import org.sid.ebankingbackend.services.ResultCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/result-comments")
public class ResultCommentController {

    @Autowired
    private ResultCommentService resultCommentService;

    @GetMapping("/AllRc")
    public ResponseEntity<List<ResultComment>> getAllResultComments() {
        List<ResultComment> resultComments = resultCommentService.getAllResultComments();
        return new ResponseEntity<>(resultComments, HttpStatus.OK);
    }

    @GetMapping("/getRbyC/{id}")
    public ResponseEntity<ResultComment> getResultCommentById(@PathVariable("id") Long id) {
        ResultComment resultComment = resultCommentService.getResultCommentById(id);
        return new ResponseEntity<>(resultComment, resultComment != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }



    @PostMapping("/AddCommenttoR/{resultId}/{userId}")
    public ResponseEntity<?> addResultComment(@PathVariable Long resultId,
                                              @PathVariable Long userId,
                                              @RequestBody ResultComment comment) {
        try {
            ResultComment moderatedComment = resultCommentService.moderateComment(comment);
            ResultComment newResultComment = resultCommentService.addCommentToResult(resultId, userId, moderatedComment);
            return ResponseEntity.ok(newResultComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Le commentaire ne peut pas être ajouté : " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ResultComment> updateResultComment(@PathVariable("id") Long id,
                                                             @RequestBody ResultComment resultComment) {
        ResultComment existingComment = resultCommentService.getResultCommentById(id);
        if (existingComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resultComment.setIdCom(id);
        ResultComment updatedResultComment = resultCommentService.addCommentToResult(existingComment.getResult().getIdResultat(), existingComment.getUser().getId(), resultComment);
        return new ResponseEntity<>(updatedResultComment, HttpStatus.OK);
    }

    @DeleteMapping("/DeleteCR/{id}")
    public ResponseEntity<Void> deleteResultComment(@PathVariable("id") Long id) {
        if (resultCommentService.getResultCommentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resultCommentService.deleteResultComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
