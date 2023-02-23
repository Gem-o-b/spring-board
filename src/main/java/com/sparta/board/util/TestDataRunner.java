package com.sparta.board.util;


import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Category;
import com.sparta.board.entity.UserAuthority;
import com.sparta.board.entity.Users;
import com.sparta.board.repository.CategoryRepository;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        Users testUser1 = Users.of("a12345789", passwordEncoder.encode("1234568901234A"), UserAuthority.ADMIN);
        Users testUser2 = Users.of("a1234578", passwordEncoder.encode("1234568901234A"), UserAuthority.USER);
        Category category1 = Category.of("카테고리1");
        Category category2 = Category.of("카테고리2");
        Category category3 = Category.of("카테고리3");
        testUser1 = userRepository.save(testUser1);
        testUser2 = userRepository.save(testUser2);
        category1 = categoryRepository.save(category1);
        category2 = categoryRepository.save(category2);
        category3 = categoryRepository.save(category3);
    }

}