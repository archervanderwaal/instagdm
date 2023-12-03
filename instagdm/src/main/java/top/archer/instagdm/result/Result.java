package top.archer.instagdm.result;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Result<T> {
    private final int code;
    private final T data;
    private final String message;

    public boolean success() {
        return code == 0;
    }

    public boolean fail() {
        return code == -1;
    }
}
