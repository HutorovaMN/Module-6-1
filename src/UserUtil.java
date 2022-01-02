import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserUtil {

    private List<User> users;
    private String masterPassword;
    private File saveFile;

    public UserUtil() throws FileNotFoundException {
        userUtil();
    }

    private void userUtil() throws FileNotFoundException {
        users = new ArrayList<User>();
        saveFile = new File("src/users.txt");

        if (saveFile.exists() && saveFile.canRead()) {

             Scanner in = new Scanner(new FileReader(saveFile));

                masterPassword = in.nextLine();

                while (in.hasNextLine()) {
                    String login = in.nextLine();
                    String email = in.nextLine();
                    String passwordHash = in.nextLine();
                    String admin = in.nextLine();
                    boolean isAdmin = false;
                    if (admin.equals("y")) {
                        isAdmin = true;
                    }

                    User user = new User(login, email, passwordHash, isAdmin);
                    users.add(user);
                }
        }  
    }

    public void saveToFile() {
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (saveFile.setWritable(true)) {
            try (PrintWriter out = new PrintWriter(saveFile)) {
                out.println(masterPassword);

                for (User user : users) {
                    out.print(user.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String loginMaster(String password) {
        return masterPassword;
    }

    public boolean checkLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return false;
            }
        }
        return true;
    }

    public boolean addUser(User newUser) {
        for (User user : users) {
            if (user.getLogin().equals(newUser.getLogin())) {
                return false;
            }
        }
        users.add(newUser);
        return true;
    }

    public User login(String login, String password) {
        for (User user : users) {
            if (user.login(login, password)) {
                return user;
            }
        }
        return null;
    }
}