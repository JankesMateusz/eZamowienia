import java.util.ArrayList;
import java.util.List;

public class TenderFactory {

    public List<Tender> getTenderList(List<String> list){

        List<Tender> tenders = new ArrayList<>();
        list.forEach(t -> {
            String[] splitted = t.split(";(?=\\S)");
            tenders.add(
                    new Tender(splitted[1], splitted[2], splitted[3],
                            splitted[4], splitted[5], splitted[6],
                            splitted[7], splitted[8],splitted[9])
            );
        });
        return tenders;
    }
}
