import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TenderFilter {

    List<Tender> tenders;
    List<String> cpvList;

    public TenderFilter(List<Tender> tenders, List<String> cpvList){

        this.tenders = tenders;
        this.cpvList = cpvList;
    }

    public List<Tender> filterTenders(){

        List<Tender> filtered = new ArrayList<>();

        for (Tender tender : tenders) {

            if(tender.getCpvNumbers()
                    .stream()
                    .anyMatch(t -> cpvList.contains(t))){
                filtered.add(tender);
            }
        }
        return filtered;
    }
}