package projectOne.member.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import projectOne.member.entity.Member;
import projectOne.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectOne.exception.BusinessLogicException;
import projectOne.exception.ExceptionCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 가입
     */
    public Member createMember(Member member) {
        // 이메일 검증메서드 사용
        verifyExistEmail(member.getEmail());
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);

        // 회원 저장
        return memberRepository.save(member);
    }

    /**
     * 회원 업데이트
     */
    public Member updateMember(Member member) {

        // 존재하는 회원인지 확인
        Member findMember = findVerifiedMember(member.getMemberId());

        // 이름정보와 휴대폰 번호 업데이트
        Optional.ofNullable(member.getName()).ifPresent(findMember::setName);
        Optional.ofNullable(member.getPhone()).ifPresent(findMember::setPhone);
        Optional.ofNullable(member.getEmail()).ifPresent(findMember::setEmail);
        Optional.ofNullable(member.getMemberStatus()).ifPresent(findMember::setMemberStatus);

//        findMember.setModifiedDate(LocalDateTime.now()); // 확인해봐

        // 정보 업데이트
        return memberRepository.save(findMember);
    }

    /**
     * 특정 회원 조회
     */
    public Member findMember(long memberId) {

        return findVerifiedMember(memberId);
    }

    /**
     * 전체 회원 조회
     */
    public Page<Member> findMembers(int page, int size) {

        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }


    /**
     * 특정 회원 삭제
     */
    public void deleteMember(long memberId) {

        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        return optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 이메일 검증 메서드
    public void verifyExistEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXIST);

    }
}