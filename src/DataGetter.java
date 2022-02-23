import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGetter {

    public DataGetter() {

    }

    public List<String> getCSVData(String url) throws Exception {

        List<String> orders = new ArrayList<>();
        URL link = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(link.openStream())
        );
        in.readLine();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            orders.add(inputLine);
        }
        in.close();
        clearCPVData(orders);
        return orders;
    }

    public void clearCPVData(List<String> list) {

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
        list.forEach(t -> list.set(list.indexOf(t), t + ";"));
    }

    public List<String> getCPVList(List<String> list, String date){

        double number = (list.size()/10.0);
        int numberToInt = (int)Math.ceil(number);
        List<String> cpvList = new ArrayList<>();

        for(int i = 0; i < numberToInt; i++) {
            String url = "https://ezamowienia.gov.pl/mo-board/api/v1/Board/Search?noticeType=ContractNotice&isTenderAmountBelowEU=true&" +
                    "publicationDateFrom=" + date + "T00:00:00.000Z&orderType=Delivery&SortingColumnName=PublicationDate&SortingDirection=DESC&" +
                    "PageNumber=" + (i+1) + "&PageSize=10";
            String json = this.getJSON(url);
            Matcher m = Pattern.compile(
                            Pattern.quote("cpvCode\":\"") + "(.*?)" + Pattern.quote("\",\"submittingOffersDate"))
                    .matcher(json);
            while (m.find()) {
                String match = m.group(1);
                cpvList.add(match);
            }
        }
        return cpvList;
    }

    public String getJSON(String url) {
        HttpsURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();

            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
}
