import java.util.List;

public class ListCombiner {

    private final List<String> list1;
    private final List<String> list2;

    public ListCombiner(List<String> list1, List<String> list2){

        this.list1 = list1;
        this.list2 = list2;
    }

    public List<String> outcome() {

        if (list1.size() == list2.size()) {

            list1.forEach(t -> {
                int index = list1.indexOf(t);
                list1.set(index, t + list2.get(index));
            });
        }
        return list1;
    }
}
