package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface GenericDao<E> {
    EntityManager entityManager = Persistence
            .createEntityManagerFactory("jpaExamplePU")
            .createEntityManager();

    E save(E entity);
    E update(E entity);
    boolean delete(E entity);
    Optional<E> findOne(int id);
    Stream<E> findAll();
    Class<E> getEntityClass();
    String getEntityTableName();

    default <R> R runWithinTx(Supplier<R> toExecute){
        R result = null;
        try {
            entityManager.getTransaction().begin();
            result = toExecute.get();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        return result;

    }

    default Optional<E> findBy( Map<String, Object> params){

        String queryStr = params.keySet().stream()
                .map(key -> "u." + key + " = :" + key)
        .collect(Collectors.joining(" AND ", "SELECT u FROM User u WHERE ", ""));//:userName

        TypedQuery<E> query = entityManager.createQuery(queryStr, getEntityClass());

        params.keySet().forEach(key -> query.setParameter(key, params.get(key)));


        return query.getResultStream().findFirst();
    }

    static Optional<GenericDao> getDaoInstance(ENTITY entity) {
        Optional<GenericDao> dao = Optional.empty();
        switch (entity) {
            case USER: dao = Optional.of(new UserDao(entityManager));
        }
        return dao;

    }

}
