package projectOne.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import projectOne.exception.ExceptionCode;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private List<FieldError> fieldErrors;
    private List<ConstraintViolationError> violationErrors;

//    private List<RuntimeError> runtimeErrors;


    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
//        this.runtimeErrors = runtimeErrors;
    }

    //(4) BindingResult에 대한 ErrorResponse 객체 생성

    public static ErrorResponse of(BindingResult bindingResult) { // BindingResult 는 에러 정보를 얻기 위해 필요한 것
        return new ErrorResponse(FieldError.of(bindingResult), null); // 에러 호출할 때 bindingResult 파라미터로 받기
    }      // 실제 에러 정보를 추출하고 가공하는 일은 ErrorResponse 클래스의 static 멤버 클래스인 FiledError 클래스에 위임한다.

    //(5)Set<ConstraintViolation<?>> 객체에 대한 ErrorResponse 객체 생성
    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) { //에러 정보를 얻기위해 필요한 것Set<ConstraintViolation<?>>
        return new ErrorResponse(null, ConstraintViolationError.of(violations)); // of 메서드를 호출하는 쪽에 위 객체를 넘겨준다.
    }       // 위 객체를 가지고 에러정보를 추출하고 가공하는 일은 ErrorResponse 클래스의 static 멤버인 ConstraintViolationError 클래스에 위임


    public static ErrorResponse of(ExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
    }

    public static ErrorResponse of(HttpStatus httpStatus) {
        return new ErrorResponse(httpStatus.value(),httpStatus.getReasonPhrase());
    }




    //(6) Field Error 가공 - DTO 유효성 검증에서 발생하는 에러정보 생성
   @Getter
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ?
                                    "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }


    //// (7) ConstraintViolation Error 가공 - URI 변수값에 대한 에러정보 생성
    @Getter
    public static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        private ConstraintViolationError(String propertyPath, Object rejectedValue, String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(
                Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                    .map(constraintViolation -> new ConstraintViolationError(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue().toString(),
                            constraintViolation.getMessage()
                    )).collect(Collectors.toList());
        }
    }
}