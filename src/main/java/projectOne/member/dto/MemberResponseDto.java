package projectOne.member.dto;

import lombok.Builder;
import lombok.Getter;
import projectOne.member.entity.Member;
import projectOne.stamp.Stamp;

@Getter
@Builder
public class MemberResponseDto {

    private long memberId;
    private String name;
    private String email;
    private String phone;

    private Stamp stamp;

    private Member.MemberStatus memberStatus;   // TODO 추가된 부분

    // TODO 수정된 부분
    public String getMemberStatus() {
        return memberStatus.getStatus();
    }

    public int getStamp() {
        return stamp.getStampCount();
    }
}
