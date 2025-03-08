package com.example.member.repository;

import com.example.member.dto.MemberMapperDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperRepositoryTest {

    @Autowired
    MemberMapperRepository mmr;

    @Test
    @Transactional
    @Rollback
    @Disabled("mybatis 회원가입 테스트")
    void memberSaveTest1() {
        mmr.save1(new MemberMapperDTO("mybatis회원이메일", "mybatis회원비밀번호", "mybatis회원이름"));

    }

    @Test
    @Transactional
    @Rollback
    @Disabled("mybatis 회원가입 테스트 mapper X")
    void memberSaveTest2() {
        mmr.save2(new MemberMapperDTO("mybatis회원이메일", "mybatis회원비밀번호", "mybatis회원이름"));

    }

}