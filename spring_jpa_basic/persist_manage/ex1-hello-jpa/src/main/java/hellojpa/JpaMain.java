package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 영속 엔티티 조회
            Member memberA = em.find(Member.class, "memberA");
            // 영속 엔티티 데이터 수정
            memberA.setName("hi");
            //em.update(member) 이런 코드가 있어야 하지 않을까?

            tx.commit();// [트랜잭션] 커밋
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
