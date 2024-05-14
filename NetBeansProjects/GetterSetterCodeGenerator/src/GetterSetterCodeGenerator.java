
import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class GetterSetterCodeGenerator {

    public static void main(String[] args) {
        GUI.starte();
    }

    public static String[] generateGetSetJava(String[] lines) {
        LinkedList<String> varnames = new LinkedList();
        LinkedList<String> vartypes = new LinkedList();
        for (String a : lines) {
            // kommentare löschen
            if (a.contains("//")) {
                a = a.substring(0, a.indexOf("//"));
            }
            // führende Leerzeichen löschen
            while (a.startsWith(" ")) {
                a = a.substring(1);
            }
            // keywords überlesen
            if (a.startsWith("public ")) {
                a = a.replaceFirst("public ", "");
            } else if (a.startsWith("private ")) {
                a = a.replaceFirst("private ", "");
            } else if (a.startsWith("protected ")) {
                a = a.replaceFirst("protected ", "");
            }
            // Variablen typ und name ausschneiden
            int index = a.indexOf(" ");
            String typ = a.substring(0, index);
            vartypes.add(typ);
            a = a.substring(index + 1);
            while (a.contains(",")) {
                while (a.startsWith(" ")) {
                    a = a.substring(1);
                }
                index = a.indexOf(",");
                vartypes.add(typ);
                varnames.add(a.substring(0, index));
                a = a.substring(index + 1);
            }
            while (a.startsWith(" ")) {
                a = a.substring(1);
            }
            varnames.add(a.substring(0, a.indexOf(";")));
        }
        String[] codeLines = new String[varnames.size() * 8];
        int i = 0;
        for (int j = 0; i < codeLines.length && j < varnames.size(); j++) {
            String camelcaseName = varnames.get(j);
            camelcaseName = camelcaseName.substring(0, 1).toUpperCase() + camelcaseName.substring(1);
            // code generieren
            codeLines[i] = "public " + vartypes.get(j) + " get" + camelcaseName + "()";
            i++;
            codeLines[i] = "{";
            i++;
            codeLines[i] = "    return " + varnames.get(j) + ";";
            i++;
            codeLines[i] = "}";
            i++;
            codeLines[i] = "public void set" + camelcaseName + "(" + vartypes.get(j) + " " + varnames.get(j) + ")";
            i++;
            codeLines[i] = "{";
            i++;
            codeLines[i] = "    this." + varnames.get(j) + " = " + varnames.get(j) + ";";
            i++;
            codeLines[i] = "}";
            i++;
        }
        return codeLines;
    }

    public static String[] generateGetSetC(String[] attribute) {
        String[] code = new String[attribute.length * 8];
        int i = 0;
        for (String a : attribute) {
            while (a.startsWith(" ")) {
                a = a.substring(1);
            }
            int index = a.indexOf(" ");
            String typ = a.substring(0, index);
            String name = a.substring(index + 1, a.indexOf(";"));
            code[i] = typ + " get" + name + "()";
            i++;
            code[i] = "{";
            i++;
            code[i] = "    return " + name + ";";
            i++;
            code[i] = "}";
            i++;
            code[i] = "void set" + name + "(" + typ + " " + name + "neu)";
            i++;
            code[i] = "{";
            i++;
            code[i] = "    " + name + " = " + name + "neu;";
            i++;
            code[i] = "}";
            i++;
        }
        return code;
    }
}
