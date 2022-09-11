package com.wolves.mainproject.domain.frequently.question;

import com.wolves.mainproject.domain.common.Timestamped;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.dto.request.AdminNoteDto;
import com.wolves.mainproject.dto.request.FrequentlyQuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "frequently_question")
public class FrequentlyQuestion extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    @Lob
    private String reply;

    @Column(nullable = false, length = 50)
    private String category;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    public void update(FrequentlyQuestionDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.reply = requestDto.getReply();
    }

}
