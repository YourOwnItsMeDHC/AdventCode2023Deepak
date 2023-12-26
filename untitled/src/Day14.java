import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\AdventOfCode2023_Deepak\\untitled\\src\\input\\day14.txt"));
            long val = 0;

            List<String> lines = new ArrayList<>();
            for (String line : br.lines().toList()) { // text.split("\n")) {//
                if (line.isBlank()) continue;
                lines.add(line);
            }

            tiltNorth(lines);
            val = calculate(lines);
            System.out.println(val);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int calculate(List<String> lines) {
        int val = 0;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                String c = lines.get(y).substring(x, x+1);
                if (c.equals("O")) {
                    val += lines.size() - y;
                }
            }
        }
        return val;
    }

    private static void setCharAt(List<String> lines, int x,  int y, String val) {
        String s = lines.get(y).substring(0, x);
        s += val;
        s += lines.get(y).substring(x+1);
        lines.set(y, s);
    }

    private static void tiltNorth(List<String> lines) {
        for (int y = 1; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                String c = lines.get(y).substring(x, x+1);
                if (c.equals("O")) {
                    setCharAt(lines, x, y, ".");
                    for (int i = y - 1; i > -1; i--) {
                        String v = lines.get(i).substring(x, x+1);
                        if(v.equals("#") || v.equals("O")) {
                            setCharAt(lines, x, i+1, "O");
                            break;
                        }
                        if(i == 0 && v.equals(".")) {
                            setCharAt(lines, x, 0, "O");
                        }
                    }
                }
            }
        }

    }
}

