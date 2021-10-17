
import java.util.function.BiFunction;
import java.util.List;
import java.util.ArrayList;

public class Sorts {

    public Sorts() {

    }

    public static<T> void quickSort(List<T> list, int front, int back, BiFunction<T, T, Boolean> o) {
        if(back - front < 1) {
            return;
        }

        int midIndex = (front + back) / 2;
        int begin = front, end = back;

        T temp = list.get(front + 1);
        list.set(front + 1, list.get(midIndex));
        list.set(midIndex, temp);
        midIndex = front + 1;

        while(front < back) {
            if(o.apply(list.get(front), list.get(midIndex))) {
                if(front < back - 1) {
                    temp = list.get(midIndex);
                    list.set(midIndex, list.get(midIndex + 1));
                    list.set(midIndex + 1, temp);
                    midIndex++;
                }
                front++;
            } else {
                temp = list.get(back);
                list.set(back, list.get(front));
                list.set(front, temp);
                back--;
            }
        }

        quickSort(list, begin, front, o);
        quickSort(list, back + 1, end, o);
    }
    public static<T> void quickSort(List<T> list, BiFunction<T, T, Boolean> o) {
        quickSort(list, 0, list.size() - 1, o);
    }

    public static<T> void insertionSort(List<T> list, BiFunction<T, T, Boolean> o) {
        for(int i = 0 ; i < list.size() ; i++) {
            for(int j = 0 ; j < i ; j++) {
                if(o.apply(list.get(i), list.get(j))) {
                    T temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }

    private static<T> List<T> merge(List<T> list1, List<T> list2, BiFunction<T, T, Boolean> o) {
        List<T> ret = new ArrayList<>();
        int index1 = 0, index2 = 0;
        int retIndex = 0;
        while(retIndex < list1.size() + list2.size()) {
            if(o.apply(list1.get(index1), list2.get(index2))) {
                ret.set(retIndex, list1.get(index1));
                index1++;
            } else {
                ret.set(retIndex, list2.get(index2));
                index2++;
            }
            retIndex++;
        }
        return ret;
    }

    private static<T> List<T> mergeSort2(List<T> list, BiFunction<T, T, Boolean> o) {

        System.out.println(list);

        if(list.size() <= 1) {
            return list;
        }

        return merge(mergeSort2(list.subList(0, list.size() / 2), o),
                     mergeSort2(list.subList(list.size() / 2, list.size()), o), o);
    }

    public static<T> void mergeSort(List<T> list, BiFunction<T, T, Boolean> o) {
        List<T> list2 = mergeSort2(list, o);
        for(int i = 0 ; i < list.size() ; i++) {
            list.set(i, list2.get(i));
        }
        return;
    }

    public static<T> void heapSort(List<T> list, BiFunction<T, T, Boolean> o) {
        for(int i = list.size() - 1 ; i >= 0 ; i--){
            heapify(list, i, list.size() - 1, o);
        }

        list.forEach(System.out::println);

        for(int i = list.size() - 1 ; i >= 0 ; ){
            T temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, temp);
            i--;
            heapify(list, 0, i, o);
        }
    }

    private static int getParent(int current) {
        return (current + 1) / 2 - 1;
    }

    private static int getLeftChild(int current) {
        return current * 2 + 1;
    }

    private static <T> void heapify(List<T> list, int current, int tail, BiFunction<T, T, Boolean> o) {

        if(getLeftChild(current) > tail) {
            return;
        }

        if( getLeftChild(current) == tail) {
            if(o.apply(list.get(current), list.get(getLeftChild(current)))) {
                T temp = list.get(current);
                list.set(current, list.get(getLeftChild(current)));
                list.set(getLeftChild(current), temp);
            }
            return;
        }


        if(o.apply(list.get(getLeftChild(current)), list.get(current)) && o.apply(list.get(getLeftChild(current) + 1), list.get(current))) {
            return;
        }

        if( o.apply(list.get(getLeftChild(current)), list.get(getLeftChild(current) + 1)) ) {
            T temp = list.get(current);
            list.set(current, list.get(getLeftChild(current) + 1));
            list.set(getLeftChild(current) + 1, temp);
            heapify(list, getLeftChild(current) + 1, tail, o);
        } else {
            T temp = list.get(current);
            list.set(current, list.get(getLeftChild(current)));
            list.set(getLeftChild(current), temp);
            heapify(list, getLeftChild(current), tail, o);
        }
    }

}