package hello.aop.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

  private final ExamRepository examRepository;

  public void request(String itemId) {
    examRepository.save(itemId);
  }
}
