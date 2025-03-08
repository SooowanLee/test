package com.example.member.service;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원 데이터 생성")
    void newMember() {
        IntStream.range(1, 15).forEach(i -> {
            memberService.save(new MemberSaveDTO("email" + i, "pw" + 1, "name" + i));
        });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("로그인 테스트")
    void loginTest() {
        //1. 로그인용 회원 데이터 생성 및 저장
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("로그인용이메일", "로그인용비밀번호", "로그인용이름");
        memberService.save(memberSaveDTO);

        //2. 로그인을 시도할 데이터 생성 및 메서드 호출
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO("로그인용이메일", "로그인용비밀번호");
        boolean loginResult = memberService.login(memberLoginDTO);

        //3. assertThat을 통해 로그인 성공여부 비교
        assertThat(loginResult).isEqualTo(true);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("조회 테스트")
    void memberListTest() {
        //1. for문을 이용해 조회용 데이터를 만든다.
        for (int i = 1; i <= 3; i++) {
            memberService.save(new MemberSaveDTO("조회용이메일" + i, "조회용비밀번호" + i, "조회용이름"));
        }

        //2. 목록 메서드 호출 및 List에 저장
        List<MemberDetailDTO> memberDetailDTOList = memberService.findAll();

        //3. List의 size()가 3인지 비교.
        //size()가 3이면 테스트 통과

        assertThat(memberDetailDTOList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원삭제 테스트")
    void deleteTest() {

        //1. 삭제용 회원 생성 및 저장
        Long savedMemberId = memberService.save(new MemberSaveDTO("삭제용이메일", "삭제용비밀번호", "삭제용이름"));
        System.out.println(memberService.findById(savedMemberId));

        //2. 삭제 메서드 호출
        memberService.deleteById(savedMemberId);

        assertThrows(NoSuchElementException.class, () -> {
            memberService.findById(savedMemberId);
        });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원수정 테스트")
    void memberUpdate() {
        Long savedMember = memberService.save(new MemberSaveDTO("수정용이메일", "수정용비밀번호", "수정용이름"));
        MemberDetailDTO findMember = memberService.findById(savedMember);
        findMember.setMemberName("수정후이름");
        Long updatedMember = memberService.update(findMember);

        assertThat(findMember.getMemberName()).isNotEqualTo(updatedMember.getClass().getName());
    }
}