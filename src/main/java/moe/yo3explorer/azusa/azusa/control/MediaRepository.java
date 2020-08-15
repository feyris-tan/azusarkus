package moe.yo3explorer.azusa.azusa.control;

import moe.yo3explorer.azusa.azusa.entity.MediaEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class MediaRepository
{
    @PersistenceContext
    EntityManager em;

    public List<MediaEntity> findKeysByProduct(int pid)
    {
        Query namedQuery = em.createNamedQuery(MediaEntity.FIND_KEYS_BY_PRODUCT);
        namedQuery.setParameter("pid",pid);
        List resultList = namedQuery.getResultList();
        return resultList;
    }

    public List<MediaEntity> listAll()
    {
        Query namedQuery = em.createNamedQuery(MediaEntity.FIND_FULL_LIST_APP);
        List resultList = namedQuery.getResultList();
        return resultList;
    }
}
