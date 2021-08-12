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

            //저장
            Team team = new Team();
            team.setName("TeamA");
            //team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            member.changeTeam(team);
            em.persist(member);
//            team.getMembers().add(member);

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시
            List<Member> members = findTeam.getMembers();

            for(Member m: members) {
                System.out.println("m = " + m.getName());
            }



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
