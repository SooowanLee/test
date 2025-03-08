package com.example.member.entity;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberSaveDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment와 같은 역할
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, unique = true)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    @Column() //아무 지정을 하지 않으면 varChar(255) 크기로 생성된다.
    private String memberName;


    //MemberSaveDTO -> MemberEntity
    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());

        return memberEntity;
    }

    public static MemberEntity toUpdateMember(MemberDetailDTO memberDetailDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDetailDTO.getMemberId());
        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());
        return memberEntity;
    }
}
