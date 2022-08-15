package projectOne.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import projectOne.member.dto.MemberPatchDto;
import projectOne.member.dto.MemberPostDto;
import projectOne.member.entity.Member;
import projectOne.member.mapper.MemberMapper;
import projectOne.member.service.MemberService;
import projectOne.dto.MultiResponseDto;
import projectOne.dto.SingleResponseDto;
import projectOne.stamp.Stamp;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/members")
@Validated
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping
    public ResponseEntity postMember(@Validated @RequestBody MemberPostDto memberPostDto) {


//
//        Member response =
//                memberService.createMember(mapper.memberPostDtoToMember(memberPostDto));

        Member member = mapper.memberPostDtoToMember(memberPostDto);
        member.setStamp(new Stamp());

        Member response = memberService.createMember(member);



        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponseDto(response)),
                HttpStatus.CREATED);
    }



    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(
            @PathVariable("member-id") @Positive long memberId,
            @Validated @RequestBody MemberPatchDto memberPatchDto ) {

        memberPatchDto.setMemberId(memberId);

        Member response =
                memberService.updateMember(mapper.memberPatchDtoToMember(memberPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponseDto(response)),HttpStatus.OK);
    }



    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") long memberId) {

        Member response = memberService.findMember(memberId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponseDto(response)), HttpStatus.OK);
    }



    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.membersToMemberResponseDtos(members),
                        pageMembers),
                HttpStatus.OK);
    }


    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(
            @PathVariable("member-id") @Positive long memberId) {

        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}