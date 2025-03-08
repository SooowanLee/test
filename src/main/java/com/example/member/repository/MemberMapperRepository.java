package com.example.member.repository;

import com.example.member.dto.MemberMapperDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapperRepository {

    //mapper를 호출하는 방식
    //회원가입
    void save1(MemberMapperDTO memberMapperDTO);

    @Insert("insert into member_table(member_email, member_password, member_name) values(#{member_email}), #{member_password}, #{member_name}")
    void save2(MemberMapperDTO memberMapperDTO);
}
