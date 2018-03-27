import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Ronshmul on 25/03/2018.
 */
public class Assignment1 {

    public static void sendGet() {
        String urlStr = "http://www.youtube.com";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "mozilla/5.0");
            int responseCode = con.getResponseCode();
            //System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Assignment1 ass = new Assignment1();
        long startTime = System.currentTimeMillis();
        ass.modulateMessage("0308089671_0308465954", 2000);
        int[] arr = ass.asBinarySequence("0308089671_0308465954");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "," + arr[i] + ",");
        }
        System.out.println();
        System.out.println(startTime);
    }

    public void modulate1Bits(long window) {
        long tStart = System.currentTimeMillis();
        while ((System.currentTimeMillis() - tStart) < window * 0.8) {
            downloadPhoto();
        }
    }

    public void modulate0Bits(long window) {
        try {
            Thread.sleep(window);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void downloadPhoto() {
        Image img = null;
        try {
            URL imUrl = new URL("https://vignette.wikia.nocookie.net/marvel_dc/images/9/92/Pantha_Tiny_Titans.gif/revision/latest?cb=20111020190500");
            img = ImageIO.read(imUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modulateMessage(String message, long window) {
        int[] binarySequence = asBinarySequence(message);
        for (int index = 0; index < binarySequence.length; index++) {
            if (binarySequence[index] == 0)
                modulate0Bits(window);
            else
                modulate1Bits(window);
        }
    }

    public int[] asBinarySequence(String message) {
        byte[] byteArr = message.getBytes();

        StringBuilder binary = new StringBuilder();
        int size = 0;
        for (byte b : byteArr) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                binary.append(' ');
                val <<= 1;
                size++;
            }
        }
        int[] intArr = new int[size];
        String[] numbers = binary.toString().split(" ");//if spaces are uneven, use \\s+ instead of " "
        int i = 0;
        for (String number : numbers) {
            intArr[i] = Integer.valueOf(number);
            i++;
        }
        return intArr;
    }

}
