package moe.yo3explorer.azusa.azusa.control;

import moe.yo3explorer.azusa.azusa.entity.ProductsEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
}
