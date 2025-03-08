package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.item.Album;
import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.domain.item.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public Book findBookById(Long id) {
        return em.createQuery("select b from Book b where b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Album findAlbumById(Long id) {
        return em.createQuery("select a from Album a where a.id = :id", Album.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Movie findMovieById(Long id) {
        return em.createQuery("select m from Movie m where m.id = :id", Movie.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Item findByName(String name) {
        return em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
