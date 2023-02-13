import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
//concat
//contains***
//compareto
//endsWith
//equalsIgnoreCase
//indexOf
//


public class HtmlRead  {
    public static int n = 1;
    public static ArrayList<HashSet<String>> list = new ArrayList<HashSet<String>>();
    public static void main(String[] args) {
        for(int i = 0; i<n; i++) {
            list.add(new HashSet<String>());
        }
        JFrame mf = new JFrame("Link Searcher");
        mf.setSize(1000, 800);
        mf.setLayout(new GridLayout(2, 1));
        JPanel top = new JPanel(new BorderLayout());
        JButton start = new JButton("Go!");

        top.add(start, BorderLayout.SOUTH);
        top.add(new JLabel("URL:", SwingConstants.CENTER), BorderLayout.NORTH);
        JPanel split = new JPanel(new GridLayout(2,1));
        JTextArea link = new JTextArea();
        link.setLineWrap(true);
        JPanel searcharea = new JPanel(new BorderLayout());
        JTextArea search = new JTextArea();
        searcharea.add(search, BorderLayout.CENTER);
        searcharea.add(new JLabel("Search Term:", SwingConstants.CENTER), BorderLayout.NORTH);
        split.add(link);
        split.add(searcharea);
        top.add(split, BorderLayout.CENTER);
        mf.add(top);

        JTextArea bottom = new JTextArea();
        bottom.setEditable(false);
        bottom.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(bottom);
//        scroll.setVerticalScrollBar(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scroll.add(bottom);
        mf.add(scroll);

        mf.setVisible(true);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottom.setText("");
                HtmlRead html = new HtmlRead(link.getText(), search.getText(), bottom, 0);

            }
        });

    }

    public HtmlRead(String link, String search, JTextArea bottom, int index) {
        bottom.setVisible(true);
        try {
            System.out.println();
            System.out.print("hello \n");
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ( (line = reader.readLine()) != null ) {
                    parseLine(line, search, bottom, index);
            }
            reader.close();
            if(bottom.getText().length()==0) {
                bottom.setText("No Links Found That Match Search Term");
            }
            HashSet<String> newC = list.get(index);
//            RECURSIVE CODE (N is depth):
//            if(index<n) {
//                for(String s: newC) {
//                    new HtmlRead(s, search, bottom, index+1);
//                }
//            }
        } catch(Exception ex) {
            System.out.println("error?");
            if(index==0) {
                bottom.setText("Exception: Invalid Link");
            }
        }

    }
    public void parseLine(String line, String search, JTextArea bottom, int index) {
        String[] starters = new String[]{"href='","href=\"", "src='", "src=\""};
        for(String s: starters) {
            if (line.contains(s)) {
//                System.out.println(line);
                int start = line.indexOf(s);
                char[] x = line.toCharArray();
                int end = x.length;
                int plus = s.length();
                if(x[start+plus]=='/' || x[start+plus]=='#') continue;
                for(int i = start+plus; i<x.length; i++) {
                    if(x[i]=='\"' || x[i]=='\'' || x[i]==' ') {
                        end = i;
                        break;
                    }
                }
                String link = line.substring(start + plus, end);
                if(link.toLowerCase().contains(search.toLowerCase()) && !bottom.getText().contains(link)) {
                    bottom.append(link + "\n\n");
                    list.get(index).add(link);
                }
                if(end==x.length) return;
                line = line.substring(end+1);
                parseLine(line, search, bottom, index);
                break;
            }

        }

    }


}
