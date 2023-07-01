import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                synchronized (sizeToFreq) {
                    String text = generateRoute("RLRFR", 100);
                    Integer count = ((text + "\0").split("R").length - 1);
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            }).start();
        }

        Thread.sleep(2000);
        Integer bufferKey = 0;
        Integer bufferValue = 0;
        for (Map.Entry<Integer,Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > bufferValue){
                bufferKey = entry.getKey();
                bufferValue = entry.getValue();
            }
        }
        System.out.println("Самое частое количество повторений " + bufferKey + " (встретилость " + bufferValue + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer,Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() == bufferKey){
                continue;
            }
            System.out.println("- "+ entry.getKey() + "(" + entry.getValue() + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}