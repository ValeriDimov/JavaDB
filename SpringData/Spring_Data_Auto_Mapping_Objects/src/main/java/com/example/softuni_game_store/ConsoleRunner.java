package com.example.softuni_game_store;

import com.example.softuni_game_store.exeptions.ValidationException;
import com.example.softuni_game_store.services.Execute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final Execute execute;

    @Autowired
    public ConsoleRunner(Execute execute) {
        this.execute = execute;
    }

    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);

        String initialInput = scanner.nextLine();

        while (!initialInput.equals("Stop")) {
            String[] input = initialInput.split("\\|");

            String result = null;
            try {
                result = this.execute.execute(input);
            } catch (ValidationException ex) {
                result = ex.getMessage();
                System.out.println();
            }
            System.out.println(result);

            initialInput = scanner.nextLine();
        }
    }
}
