package com.wolves.mainproject.domain.dynamo.word;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface WordRepository extends CrudRepository<Word, Long> {
}
