package lt.codeacademy.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lt.codeacademy.client.ClientProvider;
import lt.codeacademy.data.User;
import org.bson.Document;

import java.util.Scanner;

public class UserController {

    private final Scanner scanner;
    private MongoClient client = ClientProvider.getObjectMongoClient();
    private MongoDatabase mongoDB = client.getDatabase("LBA");
    private MongoCollection<User> collection = mongoDB.getCollection("user", User.class);
    private MongoCollection<Document> userCollection = mongoDB.getCollection("user");

    public UserController() {
        scanner = new Scanner(System.in);
    }

    public void menuAction() {
        String action;
        do {
            menu();
            action = scanner.nextLine();
            selectAction(action);
        } while (!action.equals("3"));
    }

    private void selectAction(String action) {
        switch (action) {
            case "1" -> registracion();
            case "2" -> LogIn();
            case "3" -> System.out.println("Exiting!");
            default -> System.out.println("Bloga ivestis! prasome pabandyti vel");
        }
    }

    private void menu() {
        System.out.println("""
                1 - Registracija
                2 - Prisijungti
                3 - iseiti
                """);
    }

    private void registracion() {
        System.out.println("Iveskite varda: ");
        String name = scanner.nextLine();

        System.out.println("Iveskite pavarde: ");
        String surname = scanner.nextLine();

        System.out.println("Kiek pinigu turite saskaitoje? Irasykite: ");
        Double salary = scanner.nextDouble();

        collection.insertOne(new User(null, name, surname, salary));
    }

    private void LogIn() {
        System.out.println("Iveskite jusu varda: ");
        String name = scanner.nextLine();

        System.out.println("Iveskite jusu pavarde: ");
        String surname = scanner.nextLine();

        User user = getUser(name, surname);
        if (user != null) {
            userAction(name, surname, user);
        }
    }

    private User getUser(String name, String surname) {
        FindIterable<User> users = collection.find();
        for (User user : users) {
            if (user.getName().equals(name) && user.getSurname().equals(surname)) {
                return user;
            } else {
                System.out.println("Tokio vartotojo nera arba ivestas blogas vardas ar pavarde!");
            }
        }
        return null;
    }

    private void userMenu() {
        System.out.println("""
                1 - pervesti pinigus
                2 - pasiziureti saskaita
                3 - Iseiti
                """);
    }

    private void selectUserAction(String action, String name, String surname, User user) {
        switch (action) {
            case "1" -> checkIfUserExist(user);
            case "2" -> getBalance(name, surname);
            case "3" -> System.out.println("Exiting!");
            default -> System.out.println("Bloga ivestis! prasome pabandyti vel");
        }
    }

    private void userAction(String name, String surname, User user) {
        String action;
        do {
            userMenu();
            action = scanner.nextLine();
            selectUserAction(action, name, surname, user);
        } while (!action.equals("3"));
    }

    private void checkIfUserExist(User user) {
        System.out.println("Iveskite varda vartotojo kuriam norite siusti: ");
        String name = scanner.nextLine();

        System.out.println("Iveskite pavarde vartotojo kuriam norite siusti: ");
        String surname = scanner.nextLine();

        User sendMoneyTo = getUser(name, surname);
        if (sendMoneyTo != null) {
            System.out.println("Kokia suma jus pervesite? Irasykite: ");
            Double money = scanner.nextDouble();

            if (sendMoneyTo.getSalary() > money) {
                double newSalary = sendMoneyTo.getSalary() + money;
                double minusSalary = sendMoneyTo.getSalary() - money;
                userCollection.updateOne(Filters.eq("name", user.getName()), Updates.set("salary", minusSalary));
                userCollection.updateOne(Filters.eq("name", name), Updates.set("salary", newSalary));
                System.out.println("Pinigai sekmingai pervesti!");
            } else {
                System.out.println("Nepakanka pinigu!");
            }
        }
    }

    private void getBalance(String name, String surname) {
        System.out.println(getUser(name, surname).getSalary());
    }
}
