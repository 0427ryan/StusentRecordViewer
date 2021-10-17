
import java.util.ArrayList;
import java.util.Arrays;

import java.util.function.BiFunction;

public class Heap<T> {
    
    private ArrayList<T> list = new ArrayList<>();

    public Heap(){
        list.add(null);
        System.out.println(list.get(0));
    }

    @SuppressWarnings("unchecked")
    public void addAll(T... datas){
        list.addAll(Arrays.asList(datas));
    }

    public void sort(BiFunction<T, T, Boolean> o){

    }

    public ArrayList<T> getList(){
        @SuppressWarnings("unchecked")
        ArrayList<T> ret = (ArrayList<T>)this.list.clone();
        ret.remove(0);
        return ret;
    }
    
}