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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
//            em.persist(child1); // CASCADE 덕분에 쓸필요가 없어짐.
//            em.persist(child2); // CASCADE 덕분에 쓸필요가 없어짐.

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member1 = new Member();
//            member1.setName("member1");
//            member1.setTeam(team);
//            em.persist(member1);
//
//            em.flush();
//            em.clear();
//
//            Member m = em.find(Member.class, member1.getId());
//            System.out.println("m = " + m.getTeam().getClass()); // proxy
//
//            m.getTeam().getName(); // 이 시점에서 team에 대한 값을 가져옴 ( proxy 초기화 )

//            Member member1 = new Member();
//            em.persist(member1);
//
//            em.flush();
//            em.clear();
//
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass());
//
//            em.detach(refMember); // 또는 em.close() 또는 em.clear()  ( 준영속으로 만들기 )
//
//            refMember.getName(); // 여기에서 에러 뜸.. 준영속 상태인데 프록시를 초기화하면 문제 생긴다.


//            Member member1 = new Member();
//            em.persist(member1);
//            Member member2 = new Member();
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//
//            Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.find(Member.class, member2.getId());
//            Member m21 = em.getReference(Member.class, member2.getId());
//            System.out.println("m1 == m2" + (m1.getClass() == m2.getClass())); // true
//            System.out.println("m1 == m21" + (m1.getClass() == m2.getClass())); // false
//            System.out.println((m1 instanceof Member)); // true
//            System.out.println((m2 instanceof Member)); // true
//            System.out.println((m21 instanceof Member)); // true
//
//            Member m12 = em.getReference(Member.class, member1.getId()); // 프록시가 아니라, 실제 객체다 왜냐하면 영속성 컨텍스트에서 가져왔기 떄문 ..
//            // 위에서 객체를 가져온상태에서, 영속성 컨텍스트에서 가져오면 객체를 가져올 것이다.

//            Member member = new Member();
//            member.setName("hello");
//
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
////            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.getReference(Member.class, member.getId()); // 이 시점에는 DB쿼리 안날림
//            System.out.println("findMember = " + findMember.getId()); // 이렇게 실제로 사용하는 시점에 DB쿼리로 가져와서 처리

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
    private static void printMemberAndTeam(Member member) {
        Team team = member.getTeam();
    }
}
