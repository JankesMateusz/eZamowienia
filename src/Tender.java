import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tender {

    public String number;
    public String title;
    public String purchaser;
    public String city;
    public String voivodeship;
    public String orderDate;
    public String offerDate;
    public String link;
    public String cpvList;
    public List<String> cpvAsList;

    public Tender(String number, String title, String purchaser, String city, String voivodeship, String orderDate, String offerDate, String link, String cpvList){

        this.number = number;
        this.title = title;
        this.purchaser = purchaser;
        this.city = city;
        this.voivodeship = voivodeship;
        this.orderDate = orderDate;
        this.offerDate = offerDate;
        this.link = link;
        this.cpvList = cpvList;
        this.cpvAsList = this.makeCpvList();
    }

    private List<String> makeCpvList(){

        String[] splitted = cpvList.split(",(?![^()]*\\))");

        return Arrays.stream(splitted).collect(Collectors.toList());
    }

    public String getTenderInfo(){

        return "ID: " + number +
                "\n\tTytuł: " + title +
                "\n\tZamawiający: " + purchaser +
                "\n\tMiasto: " + city +
                "\n\tWojewództwo: " + voivodeship +
                "\n\tData: " + orderDate +
                "\n\tTermin składania: " + offerDate +
                "\n\tSzczegóły: " + link + "\nCPV: \n" +
                cpvAsList +
                "\n-------------------------------------------------------------------------------------";

    }
}
