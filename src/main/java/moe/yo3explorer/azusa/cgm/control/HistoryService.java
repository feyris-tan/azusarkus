package moe.yo3explorer.azusa.cgm.control;

import moe.yo3explorer.azusa.cgm.entity.HistoryEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Singleton
public class HistoryService {
    @PersistenceContext
    EntityManager em;

    public List<Date> getDates()
    {
        Query nativeQuery = em.createNativeQuery("SELECT DISTINCT date FROM dexcom.history");
        List resultList = nativeQuery.getResultList();
        return resultList;
    }

    public List<HistoryEntity> findByDay(Date theDate)
    {
        Query namedQuery = em.createNamedQuery(HistoryEntity.FIND_BY_DATE);
        namedQuery.setParameter("day",theDate);
        List resultList = namedQuery.getResultList();
        return resultList;
    }
}
