import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class main {
    static int p1Wins = 0;
    static int p2ins = 0;
    public static void Day1() throws FileNotFoundException {
        int[] inputs = InputGetter.GetIntInputs();
        int bigger = 0;
        for (int i = 1; i < inputs.length - 2; i++)
        {
            int a = inputs[i - 1] + inputs[i] + inputs[i + 1];
            int b = inputs[i] + inputs[i + 1] + inputs[i + 2];
            if ((b - a) > 0)
                bigger++;
        }
        System.out.println(bigger);
    }
    public static void Day2() throws FileNotFoundException {
        String[] inputs = InputGetter.GetStringInputs();
        int pos = 0;
        int height = 0;
        int aim = 0;
        for (String command : inputs) {
            if (command.contains("up")) {
                //height -= Integer.parseInt(String.valueOf(command.charAt(3)));
                aim -= Integer.parseInt(String.valueOf(command.charAt(3)));
            }
            else if (command.contains("down")) {
                //height += Integer.parseInt(String.valueOf(command.charAt(5)));
                aim += Integer.parseInt(String.valueOf(command.charAt(5)));
            }
            else {
                pos += Integer.parseInt(String.valueOf(command.charAt(8)));
                height += aim * Integer.parseInt(String.valueOf(command.charAt(8)));
            }
        }
        System.out.println(pos * height);
    }
    public static void Day3P1() throws FileNotFoundException {
        String[] inputs = InputGetter.GetStringInputs();
        int[] ones = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};
        int[] zeros = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};
        for (String line : inputs)
        {
            for (int i = 0; i < 12; i++)
            {
                if (line.charAt(i) == '1')
                    ones[i]++;
                else
                    zeros[i]++;
            }
        }
        String common = "";
        String least = "";
        for (int i = 0; i < 12; i++)
        {
            if (ones[i] > zeros[i]) {
                common += "1";
                least += "0";
            }
            else {
                common += "0";
                least += "1";
            }
        }
        System.out.println(common);
        System.out.println(least);
    }
    public static void Day3P2() throws FileNotFoundException {
        String[] inputs = InputGetter.GetStringInputs();
        List<String> allNumbers = new ArrayList<>();
        for (String string : inputs)
            allNumbers.add(string);
        for (int i = 0; i < 12; i++)
            if (allNumbers.size() == 1)
                break;
            else
                allNumbers = CheckList(allNumbers, i, true);
        long answer = 0;
        for (String str : allNumbers)
                answer = Integer.parseInt(str, 2);;
        System.out.println(answer);
        allNumbers = new ArrayList<>();
        for (String string : inputs)
            allNumbers.add(string);
        for (int i = 0; i < 12; i++)
            if (allNumbers.size() == 1)
                break;
            else
                allNumbers = CheckList(allNumbers, i, false);
        for (String str : allNumbers)
            answer *= Integer.parseInt(str, 2);
        System.out.println(answer);
    }
    public static List<String> CheckList(List<String> allNumbers, int idx, boolean lessThan)
    {
        int one = 0;
        int zero = 0;
        for (String str : allNumbers)
        {
            if (str.charAt(idx) == '1')
                one++;
            else
                zero++;
        }
        boolean zeroCommon;
        if (lessThan)
            zeroCommon = zero > one;
        else
            zeroCommon = zero <= one;
        System.out.println("At iteration " + idx + " zero common is " + zeroCommon);
        List<String> toRemove = new ArrayList<>();
        char check;
        if (zeroCommon)
            check = '0';
        else
            check = '1';
        for (String line : allNumbers)
        {
            if (line.charAt(idx) != check)
                toRemove.add(line);
        }
        for (String remove : toRemove)
            allNumbers.remove(remove);
        return allNumbers;
    }
    public static void Day4() throws FileNotFoundException {
        String[] inputs = InputGetter.GetStringInputs();
        List<Integer> calledNumber = getSubInts(inputs[0], false);
        List<Integer> toDelete = new ArrayList<>();
        for (int i = 4; i < calledNumber.size(); i++)
        {
           for (int boardNum = 2; boardNum < inputs.length; boardNum += 6)
           {
               if (toDelete.contains(boardNum))
                   continue;
               List<Integer> usingNumbers = new ArrayList<>();
               for (int num = 0; num < i; num++)
               {
                    usingNumbers.add(calledNumber.get(num));
               }
               if (CheckBoard(new String[] {inputs[boardNum], inputs[boardNum + 1], inputs[boardNum + 2], inputs[boardNum + 3], inputs[boardNum + 4]}, usingNumbers, usingNumbers.get(usingNumbers.size() - 1)))
               {
                   toDelete.add(boardNum);
               }
           }
        }
    }
    public static boolean CheckBoard(String[] board, List<Integer> markedNumbers, int lastCalled)
    {
        int[][] bingo = new int[][] {getSubInts(board[0], true).stream().mapToInt(i->i).toArray(), getSubInts(board[1], true).stream().mapToInt(i->i).toArray(), getSubInts(board[2], true).stream().mapToInt(i->i).toArray(), getSubInts(board[3], true).stream().mapToInt(i->i).toArray(), getSubInts(board[4], true).stream().mapToInt(i->i).toArray()};
        for (int i = 0; i < 5; i++)
        {
            boolean won = true;
            for (int j = 0; j < 5; j++)
            {
                if (!markedNumbers.contains(bingo[i][j]))
                {
                    won = false;
                    break;
                }
            }
            if (won)
            {
                int sum = 0;
                for (int idx = 0; idx < 5; idx++)
                    for (int j = 0; j < 5; j++)
                        if (!markedNumbers.contains(bingo[idx][j]))
                            sum += bingo[idx][j];
                System.out.println("Winning board: " + sum * lastCalled);
                return true;
            }
        }

        for (int i = 0; i < 5; i++)
        {
            boolean won = true;
            for (int j = 0; j < 5; j++)
            {
                if (!markedNumbers.contains(bingo[j][i]))
                {
                    won = false;
                    break;
                }
            }
            if (won)
            {
                int sum = 0;
                for (int idx = 0; idx < 5; idx++)
                for (int j = 0; j < 5; j++)
                    if (!markedNumbers.contains(bingo[j][idx]))
                        sum += bingo[j][idx];
                System.out.println("Winning board: " + sum * lastCalled);
                return true;
            }
        }

        return false;
    }
    public static List<Integer> getSubInts(String input, boolean useSpace)
    {
        String checkChar = " ";
        if (!useSpace)
            checkChar = ",";
        List<Integer> calledNumber = new ArrayList<>();
        while (input.contains(checkChar)) {
            if (input.charAt(0) == ' ')
            {
                input = input.substring(1);
                if (input.length() <= 2) {
                    calledNumber.add(Integer.parseInt(input));
                    return calledNumber;
                }
            }
                calledNumber.add(Integer.parseInt(input.substring(0, input.indexOf(checkChar))));
                input = input.substring(input.indexOf(checkChar) + 1);
        }
        calledNumber.add(Integer.parseInt(input.strip()));
        return calledNumber;
    }

    public static void Day5() throws FileNotFoundException {
        String[] inputs = InputGetter.GetStringInputs();
        List<int[]> coordinates = new ArrayList<>();
        for (String line : inputs)
        {
            String startCoords = line.substring(0, line.indexOf("-"));
            String endCoords = line.substring(line.indexOf(">") + 2);
            List<Integer> firstCoords = getSubInts(startCoords, false);
            List<Integer> secondCoords = getSubInts(endCoords, false);
            coordinates.add(new int[]{firstCoords.get(0), firstCoords.get(1), secondCoords.get(0), secondCoords.get(1)});
        }
        List<int[]> allCoords = new ArrayList<>();
        for (int[] coord : coordinates)
        {
            if (coord[0] == coord[2])
            {
                int start;
                int end;
                if (coord[1] > coord[3])
                {
                    start = coord[3];
                    end = coord[1];
                }
                else
                {
                    start = coord[1];
                    end = coord[3];
                }
                for (int i = start; i <= end; i++)
                {
                    allCoords.add(new int[] {coord[0], i});
                }
            }
            else if (coord[1] == coord[3])
            {
                int start;
                int end;
                if (coord[0] > coord[2])
                {
                    start = coord[2];
                    end = coord[0];
                }
                else
                {
                    start = coord[0];
                    end = coord[2];
                }
                for (int i = start; i <= end; i++)
                {
                    allCoords.add(new int[] {i, coord[1]});
                }
            }
            else
            {
                int xStart;
                int yStart;
                int xEnd;
                int yEnd;
                int xSlope;
                int ySlope;
                if (coord[0] > coord[2])
                {
                    xStart = coord[2];
                    xEnd = coord[0];
                    yStart = coord[3];
                    yEnd = coord[1];
                    xSlope = (xEnd - xStart)/(xEnd - xStart);
                    ySlope = (yEnd - yStart)/(xEnd - xStart);
                }
                else
                {
                    xStart = coord[0];
                    xEnd = coord[2];
                    yStart = coord[1];
                    yEnd = coord[3];
                    xSlope = (xEnd - xStart)/(xEnd - xStart);
                    ySlope = (yEnd - yStart)/(xEnd - xStart);
                }

                for (int i = 0; i <= (xEnd - xStart); i++)
                {
                    //System.out.println("Adding coord (" + (xStart + (i * xSlope)) + " , " + (yStart + (i * ySlope)) + ")");
                    allCoords.add(new int[] {xStart + (i * xSlope), yStart + (i * ySlope)});
                }
            }
        }
        int overlap = 0;
        List<int[]> testedCoords = new ArrayList<>();
        for (int[] coord1 : allCoords)
        {
            boolean tested = false;
            for (int[] coord : testedCoords)
            {
                if (coord[0] == coord1[0] && coord[1] == coord1[1]) {
                    tested = true;
                    break;
                }
            }
            if (tested)
                continue;
            testedCoords.add(coord1);

            //System.out.println("Testing cord (" + coord1[0] + "," + coord1[1] + ")");
            boolean firstDiscovered = false;
            for (int[] coord2 : allCoords)
            {
                if (coord1[0] == coord2[0] && coord1[1] == coord2[1]) {
                    if (!firstDiscovered) {
                        firstDiscovered = true;
                        //System.out.println("Found first instance of coord.");
                    }
                    else
                    {
                        overlap++;
                        break;
                        //System.out.println("Found second+ instance of coord.");
                    }
                }
            }
        }
        System.out.println(overlap);
    }
    public static void Day6() throws FileNotFoundException {
        String[] input = InputGetter.GetStringInputs();
        List<Integer> inputs = getSubInts(input[0], false);
        long state0 = 0;
        long state1 = 0;
        long state2 = 0;
        long state3 = 0;
        long state4 = 0;
        long state5 = 0;
        long state6 = 0;
        long state7 = 0;
        long state8 = 0;
        for (long number : inputs)
        {
            if (number == 1)
                state1++;
            else if (number == 2)
                state2++;
            else if (number == 3)
                state3++;
            else if (number == 4)
                state4++;
            else if (number == 5)
                state5++;
        }
        for (int i = 0; i < 256; i++)
        {
            long curState0 = state0;
            long curState1 = state1;
            long curState2 = state2;
            long curState3 = state3;
            long curState4 = state4;
            long curState5 = state5;
            long curState6 = state6;
            long curState7 = state7;
            long curState8 = state8;
            state0 = state1 = state2 = state3 = state4 = state5 = state6 = state7 = state8 = 0;
            state8 += curState0;
            state7 += curState8;
            state6 += curState7;
            state6 += curState0;
            state5 += curState6;
            state4 += curState5;
            state3 += curState4;
            state2 += curState3;
            state1 += curState2;
            state0 += curState1;
        }
        System.out.println(state0 + state1 + state2 + state3 + state4 + state5 + state6 + state7 + state8);
    }
    public static void Day7() throws FileNotFoundException {
        String[] inputBruh = InputGetter.GetStringInputs();
        List<Integer> inputs = getSubInts(inputBruh[0], false);
        int max = -999;
        int min = 999;
        for (int num : inputs)
        {
            if (num > max)
                max = num;
            else if (num < min)
                min = num;
        }
        int totalFuel = 6969;
        for (int i = min; i <= max; i++)
        {
            int newTotalFuel = 0;
            for (int num : inputs)
                //newTotalFuel += Math.abs(num - i);
                for (int j = 0; j <= Math.abs(num-i) ; j++)
                    newTotalFuel += j;
            if (newTotalFuel < totalFuel || totalFuel == 6969 )
                totalFuel = newTotalFuel;
        }
        System.out.println(totalFuel);
    }
    public static void Day8() throws FileNotFoundException {
        String[] inputs = InputGetter.GetStringInputs();
        long total = 0;
        for (int i = 0; i < inputs.length; i++ )
        {
            String[] allStrings = new String[10];
            String key = inputs[i].substring(0, inputs[i].indexOf("| ") -1);
            String toGet = inputs[i].substring(inputs[i].indexOf("| ") + 1);
            List<String> fiveStr = new ArrayList<>();
            List<String> sixStr = new ArrayList<>();
            for (String str : getSubStr(key, true))
            {
                if (str.length() == 2)
                    allStrings[1] = str;
                else if (str.length() == 4)
                    allStrings[4] = str;
                else if (str.length() == 3)
                    allStrings[7] = str;
                else if (str.length() == 7)
                    allStrings[8] = str;
                else if (str.length() == 5)
                {
                    fiveStr.add(str);
                }
                else
                    sixStr.add(str);
            }
            for (String str : fiveStr)
            {
                char[] yaBoi = str.toCharArray();
                int index = 0;
                boolean inc1 = false;
                boolean inc2 = false;
                for (String str2 : fiveStr)
                {
                    if (str2 == str)
                        continue;
                    if (hasInCommon(yaBoi, str2, 4)) {
                        if (!inc1)
                            inc1 = true;
                        else
                            inc2 = true;
                    }
                }
            }
            for (String str : sixStr)
            {
                if (str.charAt(0) == allStrings[8].charAt(1) && str.charAt(1) == allStrings[7].charAt(1)) // 0
                    allStrings[0] = str;
                else if (str.charAt(5) == allStrings[7].charAt(2) && str.charAt(0) == allStrings[8].charAt(1)) // 6
                    allStrings[6] = str;
                else
                    allStrings[9] = str;
            }

            String builder = "";
            for (String str : getSubStr(toGet, true))
            {
                //if (str.length() == 3 || str.length() == 2 || str.length() == 4 || str.length() == 7)
                for (int j = 0; j < 10; j++)
                {
                    if (allStrings[j] == null || allStrings[j].length() != str.length())
                        continue;
                    for (int idx = 0; idx < allStrings[j].length() + 1; idx++) {
                        allStrings[j] = shift(allStrings[j]);
                        if (allStrings[j].equals(str)) {
                            builder += j;
                            break;
                        }
                    }
                }
            }
            total += Integer.parseInt(builder);
        }
        System.out.println(total);
    }

    public static boolean hasInCommon(char[] seq1, String seq2, int required)
    {
        int total = 0;
        for (char boi : seq1)
        {
            if (seq2.contains(String.valueOf(boi)))
                total++;
        }
        return total == required;
    }

    public static String shift(String s) {
        return s.charAt(s.length()-1)+s.substring(0, s.length()-1);
    }
    public static List<String> getSubStr(String input, boolean useSpace)
    {
        String checkChar = " ";
        if (!useSpace)
            checkChar = ",";
        List<String> calledNumber = new ArrayList<>();
        while (input.contains(checkChar)) {
            if (input.charAt(0) == ' ')
            {
                input = input.substring(1);
                if (input.length() <= 2) {
                    calledNumber.add(input);
                    return calledNumber;
                }
            }
            calledNumber.add(input.substring(0, input.indexOf(checkChar)));
            input = input.substring(input.indexOf(checkChar) + 1);
        }
        calledNumber.add(input.strip());
        return calledNumber;
    }
    public static void Day21() throws FileNotFoundException {
        long p1score = 0;
        long p2score = 0;
        long p1Space = 4;
        long p2Space = 8;
        long dieRolls = 0;

        long[] dirac1 = dirac(1, p1Space, p2Space, p1score, p2score, true, 0, 0, 1);
        long[] dirac2 = dirac(2, p1Space, p2Space, p1score, p2score, true, 0, 0, 1);
        long[] dirac3 = dirac(3, p1Space, p2Space, p1score, p2score, true, 0, 0, 1);
        System.out.println((dirac1[0] + dirac2[0] + dirac3[0]) + " " +  (dirac1[1] + dirac2[1] + dirac3[1]) + " " + totalUniverses);

        /*while (p1score < 21 && p2score < 21)
        {
            long roll1 = dieRolls + 1;
            long roll2 = dieRolls + 2;
            long roll3 = dieRolls + 3;
            dieRolls += 3;
            if (roll1 > 100)
                roll1 -= 100;
            if (roll2 > 100)
                roll2 -= 100;
            if (roll3 > 100)
                roll3 -= 100;
            int lastSpace = p1Space;
            for (int i = 0; i < (roll1 + roll2 + roll3); i++)
            {
                p1Space++;
                if (p1Space == 11)
                    p1Space = 1;
            }
            p1score += p1Space;
            if (p1score >= 21)
                break;
            roll1 = dieRolls + 1;
            roll2 = dieRolls + 2;
            roll3 = dieRolls + 3;
            dieRolls += 3;
            if (roll1 > 100)
                roll1 -= 100;
            if (roll2 > 100)
                roll2 -= 100;
            if (roll3 > 100)
                roll3 -= 100;
            //p2score += roll1 + roll2 + roll3;
            for (int i = 0; i < (roll1 + roll2 + roll3); i++)
            {
                p2Space++;
                if (p2Space == 11)
                    p2Space = 1;
            }
            p2score += p2Space;
        }
        if (p1score > p2score)
            System.out.println(p2score * dieRolls + " " + p2score + " " + dieRolls + " " + p1score);
        else
            System.out.println(p1score * dieRolls);*/
    }
    static long totalUniverses = 0;
    public static long[] dirac(long roll, long p1_cur_space, long p2_cur_space, long p1_cur_score, long p2_cur_score, boolean p1, long p1Wins, long p2Wins, long rollNum)
    {
        totalUniverses++;
        for (int i = 0; i < (roll); i++)
        {
            if (p1)
            {
                p1_cur_space++;
                if (p1_cur_space == 11)
                    p1_cur_space = 1;
            }
            else
            {
                p2_cur_space++;
                if (p2_cur_space == 11)
                    p2_cur_space = 1;
            }
        }
        if (p1) {
            p1_cur_score += p1_cur_space;
            if (p1_cur_score >= 21) {
                p1Wins++;
                return new long[] {p1Wins, p2Wins};
            }
        }
        else {
            p2_cur_score += p2_cur_space;
            if (p2_cur_score >= 21) {
                p2Wins++;
                return new long[] {p1Wins, p2Wins};
            }
        }
        if (rollNum % 3 == 0)
            p1 = !p1;
        rollNum++;
        long[] dirac1 = dirac(1, p1_cur_space, p2_cur_space, p1_cur_score, p2_cur_score, p1, p1Wins, p2Wins, rollNum);
        long[] dirac2 = dirac(2, p1_cur_space, p2_cur_space, p1_cur_score, p2_cur_score, p1, p1Wins, p2Wins, rollNum);
        long[] dirac3 = dirac(3, p1_cur_space, p2_cur_space, p1_cur_score, p2_cur_score, p1, p1Wins, p2Wins, rollNum);
        return new long[] {dirac1[0] + dirac2[0] + dirac3[0], dirac1[1] + dirac2[1] + dirac3[1]};
    }
    public static void main(String[] args) throws FileNotFoundException {
    Day21();
    }
}

/*
0: 6
1: 2
2: 5
3: 5
4: 4
5: 5
6: 6
7: 3
8: 7
9: 6

2 has 4 in common with 3 and 3 in common with 5
3 has 4 in common with 2 and 4 in common in 5
5 has 3 in common with 2 and 4 in common with 3
 */