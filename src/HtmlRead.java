import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
//concat
//contains***
//compareto
//endsWith
//equalsIgnoreCase
//indexOf
//


public class HtmlRead {

    public static void main(String[] args) {
        HtmlRead html = new HtmlRead();
    }

    public HtmlRead() {

        try {
            System.out.println();
            System.out.print("hello \n");
            URL url = new URL("https://www.milton.edu/");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ( (line = reader.readLine()) != null ) {
//                if(line.contains("http:") || line.contains("https:") || line.contains("www."))
//                System.out.println(line);
                parseLine(line);
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }

    }
    public void parseLine(String line) {
        String[] starters = new String[]{"href='","href=\""};
        for(String s: starters) {
            if (line.contains(s)) {
//                System.out.println(line);
                int start = line.indexOf(s);
                char[] x = line.toCharArray();
                int end = x.length;
                if(x[start+6]=='/' || x[start+6]=='#') continue;
                for(int i = start+6; i<x.length; i++) {
                    if(x[i]=='\"' || x[i]=='\'' || x[i]==' ') {
                        end = i;
                        break;
                    }
                }
//                if(start+6==end) {
//                    System.out.println("PROBLEM: " + line);
//                }
                System.out.println(line.substring(start + 6, end));
                if(end==x.length) return;
                line = line.substring(end+1);
                parseLine(line);
                break;
            }

        }
    }

}
