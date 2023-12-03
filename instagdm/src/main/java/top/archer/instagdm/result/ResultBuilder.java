package top.archer.instagdm.result;

public class ResultBuilder {

    private ResultBuilder() {}

    public static <T> Result<T> success(T t) {
        return new Result<>(0, t, "success");
    }

    public static <T> Result<T> success() {
        return new Result<>(0, null, "success");
    }

    public static <T> Result<T> fail(T t) {
        return new Result<>(-1, t, "fail");
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(-1, null, message);
    }
}
