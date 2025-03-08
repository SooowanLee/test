package com.example.member.controller;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;
import com.example.member.service.MemberServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberServiceImpl memberService;
    final String LOGIN_EMAIL = "loginEmail";

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 회원가입 페이지 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "member/save";
    }

    //회원가입 정보 저장
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) {
        Long memberId = memberService.save(memberSaveDTO);
        return "member/login";
    }

    //로그인 페이지 요청
    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }

    //로그인
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session) {
        if (memberService.login(memberLoginDTO)) {
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
            String redirectURL = (String) session.getAttribute("redirectURL");


            if (redirectURL != null) {
                return "redirect:" + redirectURL;
            } else {
                //로그인된 회원이므로 index 페이지로 가게 설정
                return "redirect:/";
            }
        } else {
            return "member/login";
        }
    }

    //수정화면 요청
    @GetMapping("/member/update")
    public String updateForm(Model model, HttpSession session) {
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO memberDetailDTO = memberService.findByEmail(memberEmail);
        model.addAttribute("member", memberDetailDTO);

        return "member/update";
    }

    //일반 수정
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDetailDTO memberDetailDTO) {
        Long memberId = memberService.update(memberDetailDTO);
        //수정 완료 후 해당회원의 상세 페이지 출력
        return "redirect:/member/" + memberId;
    }

    //수정처리(put)
    public ResponseEntity update2(@RequestBody MemberDetailDTO memberDetailDTO) {
        Long memberId = memberService.update(memberDetailDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    //회원조회
    @GetMapping("/member/")
    public String findAll(Model model) {

        List<MemberDetailDTO> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);

        return "member/findAll";
    }

    //상세조회
    // /member/2, /member/15 => /member/{memberId}\
    @GetMapping("/member/{memberId}")
    public String findById(@PathVariable Long memberId, Model model) {
        MemberDetailDTO MemberDetailDTO = memberService.findById(memberId);
        model.addAttribute("member", MemberDetailDTO);

        return "/member/findById";
    }

    //회원삭제(/member/delete/5)
    @GetMapping("/member/delete/{memberId}")
    public String deleteById(@PathVariable Long memberId) {
        memberService.deleteById(memberId);
        return "redirect:/member/";
    }

    //회원삭제(/member/5)
    @DeleteMapping("/member/delete/{memberId}")
    public ResponseEntity deleteById2(@PathVariable Long memberId) {
        memberService.deleteById(memberId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
