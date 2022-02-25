import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String... args) throws Exception {

        String date = "";          /*   PROBLEM Z ASC <--- */
//        if(args.length > 0){
//            date = args[0];
//        }

        System.out.println("Filter? y/n");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

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

        if(answer.equals("y")){

            tenders = new TenderFilter(tenders).filterTenders();
            System.out.println(tenders);
        }

        Collections.reverse(tenders);

        for(Tender t : tenders){
            System.out.println("LP: " + (tenders.indexOf(t) + 1) + "\n" + t.getTenderInfo());
        }
        System.out.println("TOTAL: " + tenders.size());


        scanner.nextLine();
    }
}

