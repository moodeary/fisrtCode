package projectOne.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "멤버가 존재하지 않습니다."),
    MEMBER_EXIST(404,"멤버가 이미 존재합니다."),
    ORDER_NOT_FOUND(404,"이미 해당 주문이 있습니다. "),
    PRODUCT_EXIST(404,"제품이 이미 존재합니다."),
    PRODUCT_NOT_FOUND(404,"제품이 존재하지 않습니다."),
    CANNOT_CHANGE_ORDER(404,"주문을 취소할 수 없습니다.");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
