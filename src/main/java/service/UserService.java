package service;

import dao.ENTITY;
import dao.GenericDao;
import entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class UserService {

    public static void use(Consumer<UserService> serviceConsumer){
        serviceConsumer.accept(new UserService());
    }

    private final GenericDao<User> userDao = GenericDao
            .getDaoInstance(ENTITY.USER)
            .orElseThrow(RuntimeException::new);


    public User saveUser(String userName, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        if (userDao.findBy(params).isPresent()) {
            throw new RuntimeException("Username "+ userName + " already taken");
        }
        return userDao.save(new User(userName, password));
    }

    public User updateUser(User user) {
        return userDao.findOne(user.getId())
                .map(userDao::update)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean delete(int id) {
        return userDao.findOne(id)
                .map(userDao::delete)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Optional<User> findOne(int id) {
        return userDao.findOne(id);
    }

    public Stream<User> findAll() {
        return userDao.findAll();
    }


}
