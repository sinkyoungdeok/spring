package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //Criteria 사용 준비
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
            //루트 클래스 (조회를 시작할 클래스)
            Root<Member> m = query.from(Member.class);
            //쿼리 생성
            CriteriaQuery<Member> cq =
            query.select(m).where(cb.equal(m.get("username"), "kim"));
            List<Member> resultList = em.createQuery(cq).getResultList();



//            List<Member> resultList = em.createQuery(
//                    "select m From Member m where m.name like '%kim%'",
//                    Member.class
//            ).getResultList();





            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
