package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category) {
        em.persist(category);
    }

    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    public Category findByName(String name) {
        return em.createQuery("select c from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
