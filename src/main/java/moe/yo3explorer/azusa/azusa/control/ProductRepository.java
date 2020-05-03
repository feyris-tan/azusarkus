package moe.yo3explorer.azusa.azusa.control;

import moe.yo3explorer.azusa.azusa.entity.ProductInShelf;
import moe.yo3explorer.azusa.azusa.entity.ProductsEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.List;

@Singleton
public class ProductRepository {
    @PersistenceContext
    EntityManager em;

    public List<ProductsEntity> findByShelf(int shelfId)
    {
        Query namedQuery = em.createNamedQuery(ProductsEntity.FIND_BY_SHELF_WITHOUT_GRAPHICS);
        namedQuery.setParameter("shelf",shelfId);
        List resultList = namedQuery.getResultList();
        return resultList;
    }

    public Long findCoverLength(int productId)
    {
        Query nativeQuery = em.createNativeQuery("SELECT LENGTH(picture) FROM azusa.products WHERE id = :id");
        nativeQuery.setParameter("id",productId);
        List resultList = nativeQuery.getResultList();
        if (resultList.size() > 0)
            return ((Integer)resultList.get(0)).longValue();
        return null;
    }

    public Long findScreenshotLength(int productId)
    {
        Query nativeQuery = em.createNativeQuery("SELECT LENGTH(screenshot) FROM azusa.products WHERE id = :id");
        nativeQuery.setParameter("id",productId);
        List resultList = nativeQuery.getResultList();
        if (resultList.size() > 0) {
            Integer result = (Integer)resultList.get(0);
            if (result != null)
                return result.longValue();
        }
        return null;
    }
}
