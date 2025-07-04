package bussiness;

import java.util.ArrayList;
import model.FeastMenu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FeastMenuList {

    public ArrayList<FeastMenu> feastMenus;

    private boolean isSaved = false;

    public FeastMenuList() {
        this.feastMenus = new ArrayList<>();
    }

    public ArrayList<FeastMenu> getFeastMenus() {
        return feastMenus;
    }

    public FeastMenu dataToObject(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length > 3) {
            return new FeastMenu(parts[0].trim(),
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim());
        } else {
            return null;
        }
    }

    public ArrayList<FeastMenu> readFromFile() {
            String pathFile = "src/data/FeastMenu.csv";
            try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
                String line = br.readLine();
                while ((line = br.readLine()) != null) {
                    FeastMenu feastMenu = dataToObject(line);
                    if (feastMenu != null) {
                        feastMenus.add(feastMenu);
                    }
                }
                System.out.println("Load data from 'FeastMenu.csv' successfully.");             
            } catch (FileNotFoundException e) {
                System.out.println("Can not find 'FeastMenu.csv' file.");
            } catch (IOException e) {
                System.out.println("Cannot read data from feastMenu.csv. Please check it.");
            }
            Collections.sort(feastMenus);
        return feastMenus;
    }

    public String formatIngredient(String text) {
        text = text.replaceAll("\"", "");
        text = text.replaceAll("\\#", "\n");
        return text;
    }

    public void showAll() {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("List of Set Menus for ordering party:");
        System.out.println("-------------------------------------------------------------------------------------");
        for (FeastMenu fm : feastMenus) {
            System.out.println("Code       : " + fm.getMenuCode());
            System.out.println("Name       : " + fm.getName());
            System.out.println("Price      : " + formatPrice(Double.parseDouble(fm.getPrice())));
            System.out.println("Ingredients: \n" + formatIngredient(fm.getIngredient()));
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    public String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
        String finalPrice = decimalFormat.format(price);
        return finalPrice;
    }

    public FeastMenu findFeastMenuByCode(String setMenuCode) {
        for (FeastMenu fm : feastMenus) {
            if (setMenuCode.equalsIgnoreCase(fm.getMenuCode())) {
                return fm;
            }
        }
        return null;
    }

    public void displayFeastMenu() {
    Set<String> displayedCodes = new HashSet<>();

    System.out.println("Menus List:");
    for (FeastMenu fm : feastMenus) {
        String setCode = fm.getMenuCode();
        String setName = fm.getName();

        if (displayedCodes.add(setCode)) {
            System.out.println(setCode + " - " + setName);
        }
    }
}
}
