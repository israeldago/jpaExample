package launcher;

import entities.User;
import service.UserService;

public class appLauncher {
    public static void main(String[] args) {

        UserService.use(userService -> {
            User laGomisaille = userService.saveUser("LaGomisaille", "123456");
            userService.saveUser("MarieBelle", "123456");

            userService.findAll().forEach(System.out::println);
            System.out.println("****************************");
            laGomisaille.setUserName("LaGomisailleV2");
            userService.updateUser(laGomisaille);
            userService.findAll().forEach(System.out::println);
            System.out.println("****************************");
            userService.delete(laGomisaille.getId());
            userService.findAll().forEach(System.out::println);





        });


    }
}
