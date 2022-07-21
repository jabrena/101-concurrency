package info.jab.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpClient {

    public Integer getValue(String param) {
        System.out.println("Demo");
        try {
            URL ejemplo = new URL("http", "localhost", 8080, "/v1/color" + param);

            URLConnection ejemploCon = ejemplo.openConnection();
            ejemploCon.setConnectTimeout(5000);
            ejemploCon.setReadTimeout(5000);

            if (ejemploCon.getContentLength() > 0) {
                System.out.println("===CONTENIDO===");

                InputStream input = ejemploCon.getInputStream();
                int c;
                int i = ejemploCon.getContentLength();

                while (((c = input.read()) != -1) && (i > 0)) {
                    System.out.println((char) c);
                }
                input.close();
            } else {
                System.out.println("NO HAY CONTENIDO");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
