package com.example.member.service;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    final String LOGIN_EMAIL = "loginEmail";

    //Repository 생성자 주입
    private final MemberRepository memberRepository;

    //회원가입 정보 저장
    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        return memberRepository.save(memberEntity).getId();
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberLoginDTO.getMemberEmail());

        if (memberEntity != null) {
            if (memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        //findAll이라는 메서드 호출 및 Entity타입의 List의 호출 결과 저장
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        //Entity타입의 List를 DTO타입의 List로 변환
        List<MemberDetailDTO> memberDetailDTOList = MemberDetailDTO.change(memberEntityList);
        return memberDetailDTOList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        return MemberDetailDTO.toMemberDetailDTO(memberRepository.findById(memberId).get());
    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        return MemberDetailDTO.toMemberDetailDTO(memberRepository.findByMemberEmail(memberEmail));
    }

    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateMember(memberDetailDTO);
        return memberRepository.save(memberEntity).getId();
    }

    @Override
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);

    }
}
