package activeobject.ex1.future;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RealResult<T> implements Result<T> {

    private final T resultValue;

    @Override
    public T getResultValue() {
        return resultValue;
    }
}
