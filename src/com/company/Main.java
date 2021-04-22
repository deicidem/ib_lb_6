package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int sizeX = 6;
        int sizeY = 4;
        String message = "Привет, сегодня 22 апреля 2021 года. Четверг";
        String order = "351420";
        System.out.println(encode(message, sizeX, sizeY, order));
    }
    public static String encode(String message,int sizeX,int sizeY,String order){
        StringBuilder result = new StringBuilder();
        List<String[][]> tables = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            int index = i / (sizeX * sizeY); // Номер таблицы
            if (tables.size() <= index ) {
                tables.add(new String[sizeY][sizeX]);
            }
            tables.get(index)[i/sizeX % sizeY][i%sizeX] = String.valueOf(message.charAt(i));
        }

        for (String[][] table: tables) {
            for (int i = 0; i < order.length(); i++) {
                int index = order.indexOf(String.valueOf(i)); // индекс номера столбца в order
                for (String[] strings : table) {
                    if (strings[index] != null) {
                        result.append(strings[index]);
                    }
                }
            }
        }

        for (String[][] table : tables) {
            for (String[] strings : table) {
                System.out.println(Arrays.toString(strings));
            }
        }
        return result.toString();
    }
}
