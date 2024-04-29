package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.ResultComment;

import java.util.List;

public interface ResultCommentService {

     List<ResultComment> getAllResultComments() ;
     ResultComment getResultCommentById(Long id);

     ResultComment addCommentToResult(Long resultId,  Long userId ,ResultComment comment) ;

     void deleteResultComment(Long id) ;

     ResultComment moderateComment(ResultComment comment) ;
}
