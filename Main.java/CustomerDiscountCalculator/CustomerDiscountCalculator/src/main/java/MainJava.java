// GitHub Repository: https://github.com/JulioGraySartori/CustomerDiscountCalculator

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "data/customers.txt";
        String outputFilePath = "data/customerdiscount.txt";

        try {
            processCustomerData(inputFilePath, outputFilePath);
        } catch (IOException e) {
            System.out.println("An error occurred while processing the file: " + e.getMessage());
        }
    }

    public static void processCustomerData(String inputFilePath, String outputFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String firstName = line.trim();
                String secondName = reader.readLine().trim();
                double totalPurchase;
                int classValue;
                int lastPurchaseYear;

                try {
                    totalPurchase = Double.parseDouble(reader.readLine().trim());
                    classValue = Integer.parseInt(reader.readLine().trim());
                    lastPurchaseYear = Integer.parseInt(reader.readLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing data for customer: " + firstName + " " + secondName);
                    continue; // Skip to the next customer entry
                }

                if (isValidData(firstName, secondName, classValue, lastPurchaseYear)) {
                    double finalValue = calculateDiscount(totalPurchase, classValue, lastPurchaseYear);
                    writer.write(firstName + " " + secondName + "\n" + finalValue + "\n");
                } else {
                    System.out.println("Invalid data for customer: " + firstName + " " + secondName);
                }
            }
        }
    }

    public static boolean isValidData(String firstName, String secondName, int classValue, int lastPurchaseYear) {
        return firstName.matches("[a-zA-Z]+") &&
               secondName.matches("[a-zA-Z0-9]+") &&
               classValue >= 1 && classValue <= 3 &&
               lastPurchaseYear >= 2000 && lastPurchaseYear <= 2024;
    }

    public static double calculateDiscount(double purchase, int classValue, int lastPurchaseYear) {
        int currentYear = 2024;
        double discount;

        switch (classValue) {
            case 1:
                if (lastPurchaseYear == currentYear) discount = 0.30;
                else if (lastPurchaseYear >= currentYear - 5) discount = 0.20;
                else discount = 0.10;
                break;
            case 2:
                if (lastPurchaseYear == currentYear) discount = 0.15;
                else if (lastPurchaseYear >= currentYear - 5) discount = 0.13;
                else discount = 0.05;
                break;
            case 3:
                if (lastPurchaseYear == currentYear) discount = 0.03;
                else discount = 0.0;
                break;
            default:
                discount = 0.0;
                break;
        }

        return purchase * (1 - discount);
    }
}
