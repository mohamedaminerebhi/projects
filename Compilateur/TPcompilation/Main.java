import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
      /*  si vous vouler faire entrer un fichier texte  try {
            File file = new File( "code.txt");
            Scanner scanner = new Scanner(file);

            // Construisez une chaîne à partir du contenu du fichier
            StringBuilder inputBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                inputBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            // Utilisez la chaîne construite comme entrée pour l'analyseur lexical
            String input = inputBuilder.toString();
*/  try{
        String input ="int main() {"+
                "int arr array [5];"+
                "int sum ;"+
                "int max;"+
                "int min;"+
               " float average;"+
                "int i ;"+

              " sum = 0;" +
                "printf(\"Enter 5 integers:\" );"+
                "for ( i = 0 , i < 5 , i++) {"+
                    "scanf(\"% d \" , arr[i]) "+
                "} ; " +

                "for (i = 0, i < 5,i++) {"+
                    "sum =  sum + arr[i]"+
                "} ;"+

                "max = arr[0];"+
                "min = arr[0];"+

               " for ( i = 1 , i < 5 , i++) {"+
                    "if ( arr[i]  >  max) then  {"+
                        "max = arr[i]"+
                    "} ; "+
                    "if (arr[i] < min) then  {"+
                        "min = arr[i] "+
                    "}   "+
                "} ; "+

                "average = sum / 5;"+

                "printf(\"Sum of the entered integers: %d\" ,  sum);"+
       "         printf(\"Maximum integer entered: %d\" ,  max);"+
        "        printf(\"Minimum integer entered: %d\" , min);"+
         "       printf(\"Average of the entered integers: %.2f\" ,  average);"+

                "return 0;"+
            "}"
            ;
            Lexer lexer = new Lexer(input);
            List<Token> tokens = lexer.tokenize();
            System.out.println("--------------------------------------------ANALYSEUR LEXICALE--------------------------------------------");
            printTokens(tokens);

        /*} catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());*/
            System.out.println("--------------------------------------------ANALYSEUR SYNTAXQUE--------------------------------------------");
            String [] s =generate_code(tokens);

      Parser parser =new Parser (s);



        } catch (LexerException e) {
            System.err.println("Lexer error: " + e.getMessage());
        }}

    private static String[] generate_code(List<Token> tokens) {
        List<String> generatedCode = new ArrayList<>();

        for (Token token : tokens) {
            if (token.getType() == TokenType.id|| token.getType() == TokenType.nb || token.getType() == TokenType.REAL ) {
                generatedCode.add(token.getType().toString());
            } else if (token.getType() == TokenType.l) {

                String k = token.getValue();
                 if (k.contains("\"% d\"") || k.equals("\"% f\"") || k.equals("\"% c\"") || k.equals("\"% s\"")) {
                    generatedCode.add("\"%");
                    System.out.println("i am here 3");
                    generatedCode.add(k.substring(3, 4)); // Add the character at index 3 (format specifier)
                    generatedCode.add("\"");
                    generatedCode.add(",");
                    generatedCode.add("IDENTIFIER");
                } else {
                    generatedCode.add("l");
                }
            } else {
                generatedCode.add(token.getValue());
            }
        }
        generatedCode.add("$");
        generatedCode.add("$");
        return generatedCode.toArray(new String[0]);
    }



    private static void printTokens(List<Token> tokens) {
        Map<String, Integer> identifierMap = new HashMap<>();
        System.out.println("Table de Symboles:");
        System.out.println("+---+--------------------------------------------+-------------------------------+");
        System.out.println("| # |      Value                                 |           Type                |");
        System.out.println("+---+--------------------------------------------+-------------------------------+");
        int identifierCount = 1;
        for (Token token : tokens) {
            if (token.getType() == TokenType.id) {
                String value = token.getValue();
                if (!identifierMap.containsKey(value)) {
                    identifierMap.put(value, identifierCount);
                    identifierCount++;
                }
                System.out.printf("| %d | %-43s| %-30s|%n", identifierMap.get(value), value, token.getType());
            } else {
                System.out.printf("|   | %-43s| %-30s|%n", token.getValue(), token.getType());
            }
        }
        System.out.println("+---+--------------------------------------------+-------------------------------+");
    }
}
