import java.util.LinkedList;

public class Queue {

    private final int maxSize;
    private LinkedList<URLDepthPair> pairs;
    private LinkedList<URLDepthPair> checked;

    public  Queue(int maxSize) {
        this.maxSize = maxSize;
        this.pairs = new LinkedList<>();
        this.checked = new LinkedList<>();
    }

    public synchronized void Add(URLDepthPair obj) {
        if (pairs.size() < maxSize)
        {
            pairs.addLast(obj);
        }
    }

    public synchronized URLDepthPair Get() {
        if (pairs.size() > 0) return pairs.removeFirst(); // получаем объект из начала списка
        else return null;
    }

    public synchronized boolean IsEmpty() {
        return this.pairs.isEmpty();//проверяем пуст ли текущий лист проверяемых пар
    }

    public synchronized void AddChecked(URLDepthPair obj) {
        checked.add(obj); //добавляем в список проверенных очредную пару
    }

    public synchronized LinkedList<URLDepthPair> GetChecked() {
        return this.checked;//получаем весь лист проверенных ссылок
    }
    public synchronized LinkedList<URLDepthPair> GetPairs() {
        return this.pairs;//получаем весь лист проверенных ссылок
    }

}
