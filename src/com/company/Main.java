package com.company;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Scanner;
        import java.util.stream.Collectors;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Введите ширину таблиц: ");
        int sizeX = Integer.parseInt(sc.nextLine());
//        int sizeX = 6;

        System.out.print("Введите высоту таблиц: ");
        int sizeY = Integer.parseInt(sc.nextLine());
//        int sizeY = 4;

        System.out.print("Введите порядок столбцов (" + sizeX + " цифр): ");
        String order = sc.nextLine();
//        String order = "351420";

        System.out.print("Введите координаты пустых ячеек (" + sizeX + " пар, max x: "+(sizeX-1)+", max y: "+(sizeY-1)+"), в формате y1,x1 y2,x2: ");

        List<int[]> emptyCells = Arrays.stream(sc.nextLine().split(" "))
                .map(str -> str.split(","))
                .map(arr -> new int[]{Integer.parseInt(arr[0]), Integer.parseInt(arr[1])})
                .collect(Collectors.toList());

        System.out.println("Введите сообщение:");
        String message = sc.nextLine();
//        String message = "Привет сегодня 16 апреля";

        message = encode(message, sizeX, sizeY, order, emptyCells);
        System.out.println("Зашифрованное сообщение: " + message);
        System.out.println("Расшифрованное сообщение: " + decode(message, sizeX, sizeY, order, emptyCells));
    }

    public static String encode(String message, int sizeX, int sizeY, String order, List<int[]> emptyCells) {
        StringBuilder result = new StringBuilder();
        List<String[][]> tables = new ArrayList<>();
        // Заранее создаем нужное количество таблиц
        for (int i = 0; i < message.length() / (sizeX * sizeY - emptyCells.size()) + 1; i++) {
            tables.add(new String[sizeY][sizeX]);
//          Добавляем пустые ячейки из ключа
            for (int[] cell : emptyCells) {
                tables.get(i)[cell[0]][cell[1]] = "";
            }
        }

        int ind = 0; // Индекс для работы с таблицей, независимый от индекса буквы в строке
        for (int i = 0; i < message.length(); i++) {
            int index = ind / (sizeX * sizeY); // Номер таблицы
            // Добавляем символ в таблицу
            if (tables.get(index)[ind / sizeX % sizeY][ind % sizeX] == null) {
                tables.get(index)[ind / sizeX % sizeY][ind % sizeX] = String.valueOf(message.charAt(i));
                ind++;
            } else {
                ind++;
                i--;
            }
        }

//      Собираем символы в строку
        for (String[][] table : tables) {
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
            System.out.println();
        }
        return result.toString();
    }

    public static String decode(String message, int sizeX, int sizeY, String order, List<int[]> emptyCells) {
        StringBuilder result = new StringBuilder();
        List<String[][]> tables = new ArrayList<>();

        // Заранее создаем нужное количество таблиц
        for (int i = 0; i < message.length() / (sizeX * sizeY - emptyCells.size()) + 1; i++) {
            tables.add(new String[sizeY][sizeX]);
//            Добавляем пустые ячейки из ключа
            for (int[] cell : emptyCells) {
                tables.get(i)[cell[0]][cell[1]] = "";
            }
        }

        // Количество пустых ячеек
        int nOfNull = sizeX * sizeY - message.length() % (sizeX * sizeY) - emptyCells.size() * tables.size();
        System.out.println(nOfNull);
        //Заполняем последнюю таблицу с конца пустыми ячейками, отличными от null
        String[][] lastTable = tables.get(tables.size() - 1);
        for (int i = lastTable.length - 1; i >= 0; i--) {
            for (int j = lastTable[i].length - 1; j >= 0; j--) {
                if (nOfNull > 0 && lastTable[i][j] == null) {
                    lastTable[i][j] = "";
                    nOfNull--;
                }
            }
        }
        int ind = 0; // Индекс для работы с таблицей, независимый от индекса буквы в строке
        for (int i = 0; i < message.length(); i++) {
            int colIndex = order.indexOf(String.valueOf(ind / sizeY % sizeX)); // индекс номера столбца в order
            int index = ind / (sizeX * sizeY); // Номер таблицы
            // Проверяем ячейку на контент
            if (tables.get(index)[ind % sizeY][colIndex] == null) {
                tables.get(index)[ind % sizeY][colIndex] = String.valueOf(message.charAt(i));
                ind++;
            } else {
                ind++;
                i--;
            }
        }

//      Собираем символы в строку
        for (String[][] table : tables) {
            for (String[] strings : table) {
                for (String string : strings) {
                    if (string != null) {
                        result.append(string);
                    }
                }
            }
        }

//        for (String[][] table : tables) {
//            for (String[] strings : table) {
//                System.out.println(Arrays.toString(strings));
//            }
//            System.out.println();
//        }
        return result.toString();
    }
}
