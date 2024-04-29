package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.ResultComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResultCommentRepository extends CrudRepository <ResultComment, Long> {



}
