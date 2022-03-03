import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CpvFromJSONExtractor {

    public List<String> getCPVList(List<String> list, String date){

        int numberToInt = (int)Math.ceil(list.size()/10.0);
        List<String> cpvList = new LinkedList<>();

        for(int i = 0; i < numberToInt; i++) {
            String url = "https://ezamowienia.gov.pl/mo-board/api/v1/Board/Search?noticeType=ContractNotice&isTenderAmountBelowEU=true&" +
                    "publicationDateFrom=" + date + "T00:00:00.000Z&orderType=Delivery&SortingColumnName=PublicationDate&SortingDirection=DESC&" +
                    "PageNumber=" + (i+1) + "&PageSize=10";

            String json = JsonReader.getJSON(url);
            assert json != null;
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
}
