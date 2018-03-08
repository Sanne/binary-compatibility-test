package somepack;

import org.hibernate.FlushMode;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Testing {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "mainPU" );
        try {
            dostuff( entityManagerFactory );
        }
        finally {
            entityManagerFactory.close();
        }
    }

    private static void dostuff(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);
        verifyFlushModes( session );
    }

    private static void verifyFlushModes(Session session) {
        FlushMode[] values = FlushMode.values();
        for ( FlushMode f : values ) {
            verifyFlushMode( session, f );
        }
    }

    private static void verifyFlushMode(Session session, FlushMode f) {
        session.setFlushMode( f );
        FlushMode mode = session.getFlushMode();
        if ( mode.equals( f ) ) {
            System.out.println( "Flush mode accepted: " + f );
        }
        else {
            throw new RuntimeException( "Flush mode not matching: " + f );
        }
    }

}
