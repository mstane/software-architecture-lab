package org.sm.lab.mybooks.server.util;

import org.sm.lab.mybooks.server.AppProps;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DbBackup {
    
    private static final String BACKUP_FILE_NAME = "mybooksdb-%s.tar.gz";
        
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(AppProps.getDbPersistenceUnit());
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();

        Query q = em.createNativeQuery("BACKUP DATABASE TO '" + getBackupFilePath() + "' BLOCKING");
        q.executeUpdate();
        
        em.getTransaction().commit();
        
        em.close();
        
        emf.close();
        
    }

    private static String getBackupFilePath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss.SSS");
        String dateStr = sdf.format(new Date());
        return AppProps.getAppBackupDirectoryName() + String.format(BACKUP_FILE_NAME, dateStr);
    }
    
}
