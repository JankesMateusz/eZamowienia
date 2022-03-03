import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CpvFileReader {

    public List<String> readFile() throws Exception{

        List<String> result;
        try(Stream<String> lines = Files.lines(Paths.get("cpv/cpvs.txt"))){
            result = lines.collect(Collectors.toList());
        }
        return result;
    }
}
