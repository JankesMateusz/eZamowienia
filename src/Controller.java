import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Controller {

    String date;
    List<String> orders;
    List<Tender> tenders;

    public Controller(String date) throws Exception{

        this.date = date;
        this.orders = new CsvReader().getCSVData(date);
        this.orders = new ListCombiner(orders, new CpvFromJSONExtractor().getCPVList(orders, date)).outcome();
        this.tenders = new TenderFactory().getTenderList(orders);
    }

    public void display(){

        System.out.println("Filter? y/n");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        if(answer.equals("y")){

            tenders = new TenderFilter(tenders).filterTenders();
        }

        Collections.reverse(tenders);

        for(Tender t : tenders){
            System.out.println("LP: " + (tenders.indexOf(t) + 1) + "\n" + t.getTenderInfo());
        }

        System.out.println("TOTAL: " + tenders.size() + " of " + orders.size());
    }
}
