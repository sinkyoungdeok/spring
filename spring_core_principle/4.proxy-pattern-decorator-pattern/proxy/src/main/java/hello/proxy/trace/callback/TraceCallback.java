package hello.proxy.trace.callback;

public interface TraceCallback<T> {
    T call();
}
