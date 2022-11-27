package com.wolves.mainproject.domain.dynamo.word;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import javax.persistence.Id;
import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "word")
public class Word {
    @Id
    @DynamoDBHashKey(attributeName = "wordstorage_id")
    private Long wordStorageId;

    @DynamoDBAttribute
    private List<String> words;

    @DynamoDBAttribute
    private List<List<String>> meanings;
}
