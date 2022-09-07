package com.wolves.mainproject.domain.word.storage.category;

import com.wolves.mainproject.domain.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "word_storage_category")
public class WordStorageCategory extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;
}
