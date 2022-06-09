package lt.codeacademy;

import lt.codeacademy.controller.UserController;

public class Main {
    public static void main(String[] args) {
        UserController controller = new UserController();

        controller.menuAction();
    }
}
