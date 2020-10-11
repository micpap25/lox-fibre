import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lox {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1){
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1){
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile (String path) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        run(new String(b, Charset.defaultCharset()));
        if (hadError) System.exit(65);
    }

    private static void runPrompt () throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("> ");
            run(line);
            hadError = false;
        }
    }

    private static void run (String src) {
        LoxScanner scan = new LoxScanner(src);
        List<Token> tokens = scan.scanTokens();

        for (Token t : tokens)
            System.out.println(t);
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    public static void report(int line, String where, String message) {
        System.out.println("[line " + line + "] Error " + where + ": " + message);
        hadError = true;
    }
}
