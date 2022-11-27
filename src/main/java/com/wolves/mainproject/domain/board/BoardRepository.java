package com.wolves.mainproject.domain.board;


import com.wolves.mainproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board AS b inner join User AS u on b.user.id = u.id"
            + " WHERE b.title LIKE CONCAT('%', :search , '%') OR u.nickname LIKE CONCAT('%', :search , '%')")
    List<Board> findByTitleContainingOrUserNicknameContaining(String search);

}
    