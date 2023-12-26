import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

    private static Map<Integer, List<String>> map = new HashMap<>();

    public static void main(String[] args) {

//        String text = """
//                rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
//                """;


        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\AdventOfCode2023_Deepak\\untitled\\src\\input\\day15.txt"));
            long val = 0;

            // A series of 256 boxes numbered 0 through 255
            for (int i = 0; i < 256; i++) {
                map.put(i, new ArrayList<>());
            }

            // Finding hash of each character of a particular line from the input provided, and adding it up
            for (String line : br.lines().toList()) { //text.split("\n")) {//
                if (line.isBlank()) continue;
                for (String s : line.split(",")) {
                    // val += hash(s); // // PART-01 - Finding hash of a specific character
                    if (s.contains("=")) {
                        String[] arr = s.split("=");
                        insert(hash(arr[0]), arr[0], s);
                    } else {
                        String[] arr = s.split("-");
                        remove(hash(arr[0]), arr[0]);
                    }
                }
            }

            val = calculateBoxes(map);
            System.out.println(val);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insert(int hash, String label, String value) {
        List<String> box = map.get(hash);
        boolean found = false;
        for (int i = 0; i < box.size(); i++) {
            if (box.get(i).startsWith(label + "=")) {
                box.set(i, value);
                found = true;
                break;
            }
        }

        if (!found) {
            box.add(value);
        }
    }

    private static void remove(int hash, String label) {
        List<String> box = map.get(hash);
        for (int i = 0; i < box.size(); i++) {
            if (box.get(i).startsWith(label + "=")) {
                box.remove(i);
                break;
            }
        }
    }


    /*
     rn: 1 (box 0) * 1 (first slot) * 1 (focal length) = 1
     cm: 1 (box 0) * 2 (second slot) * 2 (focal length) = 4
     ot: 4 (box 3) * 1 (first slot) * 7 (focal length) = 28
     ab: 4 (box 3) * 2 (second slot) * 5 (focal length) = 40
     pc: 4 (box 3) * 3 (third slot) * 6 (focal length) = 72
    */
    private static long calculateBoxes(Map<Integer, List<String>> map) {
        long val = 0;
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            long boxValue = entry.getKey() + 1;
            List<String> list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                String[] operation = str.split("=");
                val += Integer.parseInt(operation[1]) * (i+1) * boxValue;
            }
        }
        return val;
    }



    // Getting hash values for a particular character
    private static int hash(String hash) {
        int hashVal = 0;
        for (int i = 0; i < hash.length(); i++) {
            hashVal += hash.codePointAt(i);
            hashVal *= 17;
            hashVal %= 256;
        }
        return hashVal;
    }
}


/*   ----- PART - 01 -----

Find the result of running the HASH algorithm on the string HASH:

The current value starts at 0.
The first character is H; its ASCII code is 72.
The current value increases to 72.
The current value is multiplied by 17 to become 1224.
The current value becomes 200 (the remainder of 1224 divided by 256).
The next character is A; its ASCII code is 65.
The current value increases to 265.
The current value is multiplied by 17 to become 4505.
The current value becomes 153 (the remainder of 4505 divided by 256).
The next character is S; its ASCII code is 83.
The current value increases to 236.
The current value is multiplied by 17 to become 4012.
The current value becomes 172 (the remainder of 4012 divided by 256).
The next character is H; its ASCII code is 72.
The current value increases to 244.
The current value is multiplied by 17 to become 4148.
The current value becomes 52 (the remainder of 4148 divided by 256).
So, the result of running the HASH algorithm on the string HASH is 52.
 */






/*   ----- PART - 02 -----
If the operation character is a dash (-) ==>
 -Go to the relevant box and remove the lens with the given label if it is present in the box.
 -Then, move any remaining lenses as far forward in the box as they can go without changing their order,
 -filling any space made by removing the indicated lens.
  (If no lens in that box has the given label, nothing happens.)

If the operation character is an equals sign (=) ==>
-It will be followed by a number indicating the focal length of the lens that needs to go into the relevant box;
 be sure to use the label maker to mark the lens with the label given in the beginning of the step so you can
 find it later.

 There are two possible situations:

  -If there is already a lens in the box with the same label, replace the old lens with the new lens:
   remove the old lens and put the new lens in its place, not moving any other lenses in the box.
  -If there is not already a lens in the box with the same label, add the lens to the box immediately
  behind any lenses already in the box. Don't move any of the other lenses when you do this.
  If there aren't any lenses in the box, the new lens goes all the way to the front of the box.

Here is the contents of every box after each step in the example initialization sequence above:

After "rn=1":
Box 0: [rn 1]

After "cm-":
Box 0: [rn 1]

After "qp=3":
Box 0: [rn 1]
Box 1: [qp 3]

After "cm=2":
Box 0: [rn 1] [cm 2]
Box 1: [qp 3]

After "qp-":
Box 0: [rn 1] [cm 2]

After "pc=4":
Box 0: [rn 1] [cm 2]
Box 3: [pc 4]

After "ot=9":
Box 0: [rn 1] [cm 2]
Box 3: [pc 4] [ot 9]

After "ab=5":
Box 0: [rn 1] [cm 2]
Box 3: [pc 4] [ot 9] [ab 5]

After "pc-":
Box 0: [rn 1] [cm 2]
Box 3: [ot 9] [ab 5]

After "pc=6":
Box 0: [rn 1] [cm 2]
Box 3: [ot 9] [ab 5] [pc 6]

After "ot=7":
Box 0: [rn 1] [cm 2]
Box 3: [ot 7] [ab 5] [pc 6]

 */