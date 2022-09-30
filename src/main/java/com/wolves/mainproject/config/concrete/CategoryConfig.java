package com.wolves.mainproject.config.concrete;

import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description : 초기 실행 시 Category 강제 주입
 * @Author : 장동하
 **/
@Component
public class CategoryConfig {
    public CategoryConfig(WordStorageCategoryRepository wordStorageCategoryRepository){
        insertCategory(wordStorageCategoryRepository);
    }

    @Transactional
    void insertCategory(WordStorageCategoryRepository wordStorageCategoryRepository){
        String[] names = {"토익", "토플", "텝스", "초등", "중등", "고등", "회화", "기타"};
        List<WordStorageCategory> categories = createCategory(names);

        wordStorageCategoryRepository.saveAll(categories);
    }

    private List<WordStorageCategory> createCategory(String[] names){
        List<WordStorageCategory> categories = new ArrayList<>();
        Arrays.stream(names).toList().forEach(name -> categories.add(WordStorageCategory.builder().name(name).build()));

        return categories;
    }
}
