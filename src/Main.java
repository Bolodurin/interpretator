import utils.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    //Program have to has last line "0\n"
    public static void main(String[] args) throws ValidationException {
        Scanner reader = new Scanner(System.in);
        ArrayList<String> textProgram = new ArrayList<>();
        String str = reader.nextLine();

        while (!str.equals("0")){
            textProgram.add(str);
            str = reader.nextLine();
        }

        Program proga = new Program(Arrays.copyOf(textProgram.toArray(),
                textProgram.size(), String[].class));
        System.out.println(proga.decideProgram());
    }
}
//[((10+20)>(20+10))]?{1}:{0}