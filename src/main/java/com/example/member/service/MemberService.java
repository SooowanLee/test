package com.example.member.service;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;

import java.util.List;

public interface MemberService {

    Long save(MemberSaveDTO memberSaveDTO);
    boolean login(MemberLoginDTO memberLoginDTO);

    List<MemberDetailDTO> findAll();

    MemberDetailDTO findById(Long memberId);

    void deleteById(Long memberId);

    MemberDetailDTO findByEmail(String memberEmail);

    Long update(MemberDetailDTO memberDetailDTO);
}
