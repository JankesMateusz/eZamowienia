import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<String> getCSVData(String date) throws Exception {

        String csv_url = "https://ezamowienia.gov.pl/mo-board/api/v1/Board/GetSearchNoticesListReport?noticeType=ContractNotice&isTenderAmountBelowEU=true&" +
                "publicationDateFrom=" + date + "T00:00:00.000Z&orderType=Delivery&SortingColumnName=PublicationDate&SortingDirection=DESC&reportType=0";

        List<String> orders = new ArrayList<>();
        URL link = new URL(csv_url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(link.openStream())
        );
        in.readLine();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            orders.add(inputLine);
        }
        in.close();
        clearCsvData(orders);
        return orders;
    }

    public void clearCsvData(List<String> list) {

        while(list.stream().
                anyMatch(x -> !x.contains("Ogłoszenie o zamówieniu"))) {
            String obj = list.stream()
                    .filter(x -> !x.contains("Ogłoszenie o zamówieniu"))
                    .findFirst()
                    .get();
            String a = list.get(list.indexOf(obj) - 1) + obj;
            list.set((list.indexOf(obj) - 1), a);
            list.remove(obj);
        }
        list.forEach(t -> list
                .set(list.indexOf(t), t + ";"));
    }
}
