package Try;

import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("please input your command: ");
        String command = input.nextLine();
        String[] commandList = command.split(" ");
        for (String s : commandList) {
            System.out.print(s + ",");
        }
    }
}
