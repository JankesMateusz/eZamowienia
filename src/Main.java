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
        Collections.reverse(tenders);

        int counter = 1;
        int pageNumber = (int) Math.ceil(list.size()/10.0);
        System.out.println("-------------------- STRONA " + pageNumber + " --------------------");
        for(Tender t : tenders){
            counter++;
            System.out.println(t.getTenderInfo());
            if(counter % 10 == 0 & pageNumber != 0){
                pageNumber--;
                System.out.println("-------------------- STRONA " + pageNumber + " --------------------");
            }
        }
        System.out.println(tenders.size());
    }
}

