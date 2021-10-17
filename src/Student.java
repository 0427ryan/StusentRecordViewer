
public class Student {
    
    private int number;
    private String name;
    private int chineseScore;
    private int mathScore;

    public Student(){
        
    }
    
    public Student(int number, String name, int chineseScore, int mathScore){
        this.number = number;
        this.name = name;
        this.chineseScore = chineseScore;
        this.mathScore = mathScore;
    }

    public int getNumber(){
        return this.number;
    }

    public String getName(){
        return this.name;
    }

    public int getChineseScore(){
        return this.chineseScore;
    }

    public int getMathScore(){
        return this.mathScore;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setChineseScore(int score){
        this.chineseScore = score;
    }

    public void setMathScore(int score){
        this.mathScore = score;
    }

    @Override
    public String toString(){
        return String.format("%2d   %4s   %13d   %10d", this.number, this.name, this.chineseScore, this.mathScore);
    }

}