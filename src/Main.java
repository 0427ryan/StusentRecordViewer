
import java.util.Scanner;

public class Main {

    public static void main(String[] args)throws Exception{
        CommandProccessor proccessor = new CommandProccessor();
        Scanner input = new Scanner(System.in);
        String in;
        while(true){
            System.out.println("Please enter command");
            in = input.nextLine();
            proccessor.excute(in);
        }
    }
}