//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class InputGetter {
    private static final File inputs = new File("C:\\Users\\tempe\\IdeaProjects\\Aoc 2021 Ketkev Beating Year\\src\\Inputs");
    private static Scanner fileReader;

    public static String[] GetStringInputs() throws FileNotFoundException {
        ArrayList result = new ArrayList();

        while(fileReader.hasNextLine()) {
            result.add(fileReader.nextLine());
        }

        return GetStringArray(result);
    }

    public static int[] GetIntInputs() throws FileNotFoundException {
        ArrayList result = new ArrayList();

        while(fileReader.hasNextLine()) {
            result.add(Integer.parseInt(fileReader.nextLine()));
        }

        return result.stream().mapToInt((i) -> {
            return (int) i;
        }).toArray();
    }

    public static long[] GetLongInputs() throws FileNotFoundException {
        ArrayList result = new ArrayList();

        while(fileReader.hasNextLine()) {
            result.add(Long.parseLong(fileReader.nextLine()));
        }

        return result.stream().mapToLong((i) -> {
            return (long) i;
        }).toArray();
    }

    public static String[] GetStringArray(ArrayList<String> arr) {
        String[] str = new String[arr.size()];

        for(int j = 0; j < arr.size(); ++j) {
            str[j] = (String)arr.get(j);
        }

        return str;
    }

    static {
        try {
            fileReader = new Scanner(inputs);
        } catch (FileNotFoundException var1) {
            var1.printStackTrace();
        }

    }
}
