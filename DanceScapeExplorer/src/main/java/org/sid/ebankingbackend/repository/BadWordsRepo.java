package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.BadWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BadWordsRepo extends JpaRepository<BadWords, Long>{
    @Query(value = "select word from bad_words",nativeQuery = true)
    List<String> getWordsList();
}
