import java.util.List;

public class Main {

    public static void main(String... args) throws Exception {

        String date = "2022-03-03";

        List<String> cpvFromFileList = new CpvFileReader().readFile();
        Controller c = new Controller(date, cpvFromFileList);
        c.display();
    }
}

