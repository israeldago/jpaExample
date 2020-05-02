package dao;

import entities.User;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.stream.Stream;

public class UserDao implements GenericDao<User> {

    private final EntityManager em;

    public UserDao(EntityManager em){
        this.em = em;
    }

    @Override
    public User save(User entity) {
        return this.runWithinTx(() -> {
            this.em.persist(entity);
            return this.findOne(entity.getId()).orElse(null);
        });
    }

    @Override
    public User update(User entity) {
        return this.runWithinTx(() -> this.em.merge(entity));
    }

    @Override
    public boolean delete(User entity) {
        return this.runWithinTx(() -> {
            this.em.remove(entity);
            return !this.findOne(entity.getId()).isPresent();
        });
    }

    @Override
    public Optional<User> findOne(int id) {
        return Optional.ofNullable(this.em.find(User.class, id));
    }

    @Override
    public Stream<User> findAll() {
        return this.em.createNamedQuery("User.findAll", User.class).getResultStream();
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public String getEntityTableName() {
        return User.class.getSimpleName();//User
    }
}
