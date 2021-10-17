
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.Scanner;

public class CommandProccessor {

    private String fileName;
    private ArrayList<Student> data;

    public CommandProccessor() {

    }

    public CommandProccessor(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.data = new BufferedReader(new FileReader(fileName)).lines()
        .collect(ArrayList::new, (list, line) -> {
            String[] datas = line.split(",");
            list.add(new Student(Integer.parseInt(datas[0]), datas[1], Integer.parseInt(datas[2]), Integer.parseInt(datas[3])));
        }, ArrayList::addAll);
    }

    public void excute(String input) {
        String[] command = input.split(" ");

        if(command[0].equalsIgnoreCase("R")) {
            try {
                this.fileName = command[1];
                this.data = new BufferedReader(new FileReader(fileName)).lines()
                .collect(ArrayList::new, (list, line) -> {
                    String[] datas = line.split(",");
                    list.add(new Student(Integer.parseInt(datas[0]), datas[1], Integer.parseInt(datas[2]), Integer.parseInt(datas[3])));
                }, ArrayList::addAll);
                this.printRecord();
            }catch(IOException e){
                System.out.println(command[1] + " can't be opened.");
            }
            return;
        }

        if(command[0].equalsIgnoreCase("IS") || command[0].equalsIgnoreCase("QS") || command[0].equalsIgnoreCase("HS")) {
            if(command.length < 2) {
                System.out.println("指令輸入錯誤");
                return;
            }

            if(this.data == null){
                System.out.println("No data found");
                return;
            }
            BiFunction<Student, Student, Boolean> comp;
            if(command[1].equalsIgnoreCase("math") && command[2].equalsIgnoreCase("-a")) {
                comp = (a, b) -> a.getMathScore() < b.getMathScore();
            } else if(command[1].equalsIgnoreCase("math") && command[2].equalsIgnoreCase("-d")) {
                comp = (a, b) -> a.getMathScore() > b.getMathScore();
            } else if(command[1].equalsIgnoreCase("no") && command[2].equalsIgnoreCase("-a")) {
                comp = (a, b) -> a.getNumber() < b.getNumber();
            } else if(command[1].equalsIgnoreCase("no") && command[2].equalsIgnoreCase("-d")) {
                comp = (a, b) -> a.getNumber() > b.getNumber();
            } else if(command[1].equalsIgnoreCase("chin") && command[2].equalsIgnoreCase("-a")) {
                comp = (a, b) -> a.getChineseScore() < b.getChineseScore();
            } else if(command[1].equalsIgnoreCase("chin") && command[2].equalsIgnoreCase("-d")) {
                comp = (a, b) -> a.getChineseScore() > b.getChineseScore();
            } else if(command[1].equalsIgnoreCase("name") && command[2].equalsIgnoreCase("-a")) {
                comp = (a, b) -> b.getName().compareTo(a.getName()) > 0;
            } else if(command[1].equalsIgnoreCase("name") && command[2].equalsIgnoreCase("-d")) {
                comp = (a, b) -> a.getName().compareTo(b.getName()) > 0;
            } else if(command[1].equalsIgnoreCase("avg") && command[2].equalsIgnoreCase("-a")) {
                comp = (a, b) -> a.getMathScore() + a.getChineseScore() < b.getMathScore() + b.getChineseScore();
            } else if(command[1].equalsIgnoreCase("avg") && command[2].equalsIgnoreCase("-d")) {
                comp = (a, b) -> a.getMathScore() + a.getChineseScore() > b.getMathScore() + b.getChineseScore();
            } else {
                System.out.println("指令輸入錯誤");
                return;
            }


            if(command[0].equalsIgnoreCase("HS")) {
                Sorts.heapSort(this.data, comp);
            } else if(command[0].equalsIgnoreCase("QS")) {
                Sorts.quickSort(this.data, comp);
            } else if(command[0].equalsIgnoreCase("IS")) {
                Sorts.insertionSort(this.data, comp);
            } else {
                System.out.println("指令輸入錯誤");
                return;
            }

            this.printRecord();

        }

        if(command[0].equalsIgnoreCase("ANA")) {
            if(this.data == null){
                System.out.println("No data found");
                return;
            }
            boolean isMath = command[1].equalsIgnoreCase("math");
            int[] counter = new int[11];
            data.forEach(data -> {
                if(isMath) {
                    counter[data.getMathScore() / 10]++;
                } else {
                    counter[data.getChineseScore() / 10]++;
                }
            });
            System.out.printf("100~90: %3d人\n 89~80: %3d人\n 79~70: %3d人\n 69~60: %3d人\n 59~50: %3d人\n 49~40: %3d人\n 39~30: %3d人\n 29~20: %3d人\n 19~10: %3d人\n  9~ 0: %3d人\n",
                              counter[10] + counter[9], counter[8], counter[7],    counter[6],   counter[5], counter[4], counter[3], counter[2], counter[1], counter[0]);
            System.out.println("最高分：" + data.stream().mapToInt((data) -> isMath ? data.getMathScore() : data.getChineseScore()).max().getAsInt());
            System.out.println("最低分：" + data.stream().mapToInt((data) -> isMath ? data.getMathScore() : data.getChineseScore()).min().getAsInt());
            double avg = data.stream().mapToInt((data) -> isMath ? data.getMathScore() : data.getChineseScore()).average().getAsDouble();
            System.out.printf("平均分：%3.2f\n", avg);
            System.out.printf("標準差：%3.2f\n", Math.sqrt(data.stream().mapToDouble((data) ->
                              isMath ? data.getMathScore() : data.getChineseScore()).reduce(0, (a, b)-> a + (b - avg) * (b - avg) ) / this.data.size() ));

        }

        if(command[0].equalsIgnoreCase("BS")) {
            if(!(command[1].equalsIgnoreCase("no") || command[1].equalsIgnoreCase("name"))) {
                System.out.println("指令輸入錯誤");
                return;
            }
            if(this.data == null){
                System.out.println("No data found");
                return;
            }
            Scanner in = new Scanner(System.in);
            System.out.print("Please input " + command[1].toLowerCase() + ": ");
            if(command[1].equalsIgnoreCase("no")) {
                int find = Integer.parseInt(in.nextLine());
                Sorts.quickSort(data, (a, b) -> a.getNumber() < b.getNumber());
                int high = data.size() - 1;
                int low = 0;
                while(high > low) {
                    int mid = high + low / 2;
                    if(data.get(mid).getNumber() == find) {
                        System.out.printf("Student no %3d is found\n", find);
                        printRecord(data.get(mid));
                        return;
                    }
                    if(data.get(mid).getNumber() < find) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                System.out.printf("Student no %3d is not found\n", find);
            } else {
                String find = in.nextLine();
                Sorts.quickSort(data, (a, b) -> a.getName().compareTo(b.getName()) < 0);
                int high = data.size() - 1;
                int low = 0;
                while(high > low) {
                    int mid = high + low / 2;
                    if(data.get(mid).getName().equals(find)) {
                        System.out.printf("Student named %s is found\n", find);
                        printRecord(data.get(mid));
                        return;
                    }
                    if(data.get(mid).getName().compareTo(find) < 0) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                System.out.printf("Student named %s is not found\n", find);
            }
        }

    }

    private void printRecord() {
        System.out.println("No | Name | Chinese score | Math score");
        data.forEach(System.out::println);
    }

    private void printRecord(Student s) {
        System.out.println("No | Name | Chinese score | Math score");
        System.out.println(s);
    }
}