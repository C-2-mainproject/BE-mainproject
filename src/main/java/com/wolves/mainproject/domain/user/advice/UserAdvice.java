package com.wolves.mainproject.domain.user.advice;

import com.wolves.mainproject.domain.common.Timestamped;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.dto.request.ReplyAdviceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "user_advice")
public class UserAdvice extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "evidence_image")
    private String evidenceImage;

    @Column(nullable = false, name = "personal_consent")
    private boolean personalConsent;

    @Builder.Default
    @Column(nullable = false, columnDefinition="boolean default false", name = "is_clear")
    private boolean isClear = false; // for hibernate

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    public void update(ReplyAdviceDto requestDto) {
        this.isClear = requestDto.getIsClear();
    }
}
