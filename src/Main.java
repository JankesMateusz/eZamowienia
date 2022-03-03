
public class Main {

    public static void main(String... args) throws Exception {

        String date = "2022-03-03";

        Controller c = new Controller(date, new CpvFileReader().readFile());
        c.display();
    }
}