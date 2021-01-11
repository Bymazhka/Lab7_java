import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Crawler {

    public static final String HTTP_0 = "a href=\"http://";
    public static final String HTTPS_0 = "a href=\"https://";
    public static final String PREF_0 = "a href=";
    public static Queue queue;
    public static  int depth;

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter link: ");
        String URL = in.nextLine();
        System.out.print("Enter depth: ");
        depth = in.nextInt();
        in.close();
        queue = new Queue(depth);
        queue.Add(new URLDepthPair(URL, 0));
        calculate(depth);
        PrintResults();
    }

    public static void PrintResults()
    {
        for (URLDepthPair checked : queue.GetChecked())
        {
            System.out.println(checked.toString());
        }
    }

    public static void calculate(int max_depth) throws IOException {
        while(!queue.IsEmpty()) {
            // Временная переменная для хранения пары URLDepthPair
            URLDepthPair temp = queue.Get();

            // Опеределяем URL соединение
            URLConnection urlSocket = new URL(temp.GetURL()).openConnection();
            urlSocket.setConnectTimeout(10_1000);

            // Работа с потоками данных URL-соединения
            InputStream stream_in = urlSocket.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(stream_in));

            // Получение страницы и её обработка
            String str;
            while ((str = input.readLine()) != null) {
                if (temp.GetDepth() < max_depth) {
                    while(str.length() > 0) {
                        String newURL;
                        if (str.contains(HTTP_0)) {//если в строке есть http
                            newURL = str.substring(str.indexOf(HTTP_0) + PREF_0.length() + 1);
                            newURL = newURL.substring(0, newURL.indexOf("\""));
                        } else if (str.contains(HTTPS_0)) {//если в строке есть https
                            newURL = str.substring(str.indexOf(HTTPS_0) + PREF_0.length() + 1);
                            newURL = newURL.substring(0, newURL.indexOf("\""));
                        }
                        else break;

                        // Меняем строку
                        str = str.substring(str.indexOf(newURL) + newURL.length() + 1);

                        // Нашли новую ссылку
                        URLDepthPair foundURL = new URLDepthPair(newURL, temp.GetDepth() + 1);
                        if (!queue.GetChecked().contains(foundURL)) {
                            queue.Add(foundURL); // Добавили её в пул
                        }
                    }
                }
                else break;
            }
            // Закрываем потоки
            input.close();
            stream_in.close();
            urlSocket.getInputStream().close();

            // Добавляем просмотренную ссылку в список просмотренных
            if (temp.GetDepth() < max_depth && !queue.GetChecked().contains(temp)) queue.AddChecked(temp);
        }
    }
}
