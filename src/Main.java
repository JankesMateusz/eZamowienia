import java.util.Collections;
import java.util.List;


public class Main {

    public static void main(String... args) throws Exception {

        String date = "2022-02-24";          /*   PROBLEM Z ASC <--- */

        String csv_url = "https://ezamowienia.gov.pl/mo-board/api/v1/Board/GetSearchNoticesListReport?noticeType=ContractNotice&isTenderAmountBelowEU=true&" +
                "publicationDateFrom=" + date + "T00:00:00.000Z&orderType=Delivery&SortingColumnName=PublicationDate&SortingDirection=DESC&reportType=0";

        DataGetter c = new DataGetter();    // to generuje listę zamówień

        // Initial List
        List<String> list = c.getCSVData(csv_url);
        // CPV list extracted from JSON response
        List<String> cpvList = c.getCPVList(list, date);
        // List - combined elements (tenders + CPVs)
        list = new ListCombiner(list, cpvList).outcome();

        List<Tender> tenders = new TenderFactory().getTenderList(list);
        tenders = new TenderFilter(tenders).filterTenders();
        Collections.reverse(tenders);

        for(Tender t : tenders){
            System.out.println(t.getTenderInfo());
        }
        System.out.println(tenders.size());
    }
}

