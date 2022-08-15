package projectOne.member.mapper;

import org.mapstruct.Mapper;
import projectOne.member.dto.MemberPatchDto;
import projectOne.member.dto.MemberPostDto;
import projectOne.member.dto.MemberResponseDto;
import projectOne.member.entity.Member;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember (MemberPostDto memberPostDto);
    Member memberPatchDtoToMember (MemberPatchDto memberPatchDto);
    MemberResponseDto memberToMemberResponseDto (Member member);
    List<MemberResponseDto> membersToMemberResponseDtos (List<Member> members);
}
