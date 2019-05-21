import utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    //Program have to has last line "0\n", if you write program in cin
    public static void main(String[] args) throws ValidationException {
        /*Scanner reader = new Scanner(System.in);
        ArrayList<String> textProgram = new ArrayList<>();
        String str = reader.nextLine();

        while (!str.equals("0")){
            textProgram.add(str);
            str = reader.nextLine();
        }

        Program proga = new Program(Arrays.copyOf(textProgram.toArray(),
                textProgram.size(), String[].class));
        System.out.println(proga.decideProgram());*/ //Part for include from cin
        Test();
    }

    public static void Test() throws ValidationException{
        ArrayList<String> textProgram1 = new ArrayList<>();
        textProgram1.add("2");
        try{
            Program program1 = new Program(Arrays.copyOf(textProgram1.toArray(),
                    textProgram1.size(), String[].class));
            if(program1.decideProgram() == 2)
                System.out.println("Test 1: OK");
            else
                System.out.println("Test 1: FAILED");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }


        ArrayList<String> textProgram2 = new ArrayList<>();
        textProgram2.add("(2+2)");
        try{
            Program program2 = new Program(Arrays.copyOf(textProgram2.toArray(),
                    textProgram2.size(), String[].class));
            if(program2.decideProgram() == 4)
                System.out.println("Test 2: OK");
            else
                System.out.println("Test 2: FAILED");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> textProgram3 = new ArrayList<>();
        textProgram3.add("[((2*3)>5)]?((((7*6)/3)-5)):(0)");
        try{
            Program program3 = new Program(Arrays.copyOf(textProgram3.toArray(),
                    textProgram3.size(), String[].class));
            if(program3.decideProgram() == 9)
                System.out.println("Test 3: OK");
            else
                System.out.println("Test 3: FAILED");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> textProgram4 = new ArrayList<>();
        textProgram4.add("f(x)={(x+1)}");
        textProgram4.add("f(10)");
        try{
            Program program4 = new Program(Arrays.copyOf(textProgram4.toArray(),
                    textProgram4.size(), String[].class));
            if(program4.decideProgram() == 11)
                System.out.println("Test 4: OK");
            else
                System.out.println("Test 4: FAILED");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> textProgram5 = new ArrayList<>();
        textProgram5.add("f(x)={(x+y)}");
        textProgram5.add("f(10)");
        try{
            Program program5 = new Program(Arrays.copyOf(textProgram5.toArray(),
                    textProgram5.size(), String[].class));
            Integer res = program5.decideProgram();
            System.out.println("Test 5: FAILED");
        }
        catch (ParameterNotFound e){
            System.out.println("Test 5: OK");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> textProgram6 = new ArrayList<>();
        textProgram6.add("f(x)={(x+1)}");
        textProgram6.add("f(10,2)");
        try{
            Program program6 = new Program(Arrays.copyOf(textProgram6.toArray(),
                    textProgram6.size(), String[].class));
            Integer res = program6.decideProgram();
            System.out.println("Test 6: FAILED");
        }
        catch (ArgsNumMismatch e){
            System.out.println("Test 6: OK");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> textProgram7 = new ArrayList<>();
        textProgram7.add("f(x)={(x+5)}");
        textProgram7.add("g(10)");
        try{
            Program program7 = new Program(Arrays.copyOf(textProgram7.toArray(),
                    textProgram7.size(), String[].class));
            Integer res = program7.decideProgram();
            System.out.println("Test 7: FAILED");
        }
        catch (FunctionNotFound e){
            System.out.println("Test 7: OK");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> textProgram8 = new ArrayList<>();
        textProgram8.add("g(x)={(f(x)+f((x/2)))}");
        textProgram8.add("f(x)={[(x>1)]?((f((x-1))+f((x-2)))):(x)}");
        textProgram8.add("g(10)");
        try{
            Program program8 = new Program(Arrays.copyOf(textProgram8.toArray(),
                    textProgram8.size(), String[].class));
            Integer res = program8.decideProgram();
            if(program8.decideProgram() == 60)
                System.out.println("Test 8: OK");
            else
                System.out.println("Test 8: FAILED");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> textProgram9 = new ArrayList<>();
        textProgram9.add("f(x,y)={(x/y)}");
        textProgram9.add("f(10,0)");
        try{
            Program program9 = new Program(Arrays.copyOf(textProgram9.toArray(),
                    textProgram9.size(), String[].class));
            Integer res = program9.decideProgram();
            System.out.println("Test 9: FAILED");
        }
        catch (RuntimeError e){
            System.out.println("Test 9: OK");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }
}
//[((10+20)>(20+10))]?{1}:{0}