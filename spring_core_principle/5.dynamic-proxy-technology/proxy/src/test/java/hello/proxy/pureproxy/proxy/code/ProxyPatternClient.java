package hello.proxy.pureproxy.proxy.code;

public class ProxyPatternClient {

  private Subject subject;

  public ProxyPatternClient(Subject subject) {
    this.subject = subject;
  }

  public void execute() {
    subject.operation();
  }
}
