package paket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static paket.URLDepthPair.URL_PREFIX;

public class CrawlerTask implements Runnable {
    URLPool urlPool;

    public CrawlerTask(URLPool pool){
        urlPool = pool;
    }

    public static void request(PrintWriter out, URLDepthPair pair) throws MalformedURLException {
        out.println("GET " + pair.getPath() + " HTTP/1.1");
        out.println("Host: " + pair.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }

    @Override
    public void run() {
        while (true) {
            URLDepthPair currentPair = urlPool.getPair();
            try {
                Socket my_socket = new Socket(currentPair.getHost(), 80);
                my_socket.setSoTimeout(1000);
                try {
                    BufferedReader in =  new BufferedReader(new InputStreamReader(my_socket.getInputStream()));
                    PrintWriter out = new PrintWriter(my_socket.getOutputStream(), true);
                    request(out,currentPair);
                    String line;
                    while ((line = in.readLine())!=null){
                        if (line.contains(URL_PREFIX) && line.indexOf('"') != -1) {
                            StringBuilder currentLink = new StringBuilder();
                            int i = line.indexOf(URL_PREFIX);
                            while (line.charAt(i) != '"' && line.charAt(i) != ' ') {
                                currentLink.append(line.charAt(i));
                                i++;
                            }
                            URLDepthPair newPair = new URLDepthPair(currentLink.toString(), currentPair.depth + 1);
                            urlPool.addPair(newPair);
                        }
                    }
                    my_socket.close();
                } catch (SocketTimeoutException e) {
                    my_socket.close();
                }
            }
            catch (IOException e) {}
        }
    }
}