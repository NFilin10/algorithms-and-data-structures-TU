import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;

class test {

    public static void failipuu(String tee) {
        Deque<File> queue = new ArrayDeque<>();
        File algneKataloog = new File(tee);
        queue.add(algneKataloog);

        while (!queue.isEmpty()) {
            File kataloog = queue.poll();
            int level = kataloog.getAbsolutePath().split(File.separator).length - algneKataloog.getAbsolutePath().split(File.separator).length;

            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }

            if (kataloog.isDirectory()) {
                System.out.print("[" + kataloog.getName() + "]\n");
                File[] sisu = kataloog.listFiles();

                if (sisu != null) {
                    for (File fail : sisu) {
                        if (fail.isDirectory()) {
                            queue.add(fail);
                        } else if (fail.length() <= 500 * 1024) {
                            System.out.println(fail.getName() + " (" + fail.length() / 1024.0 + " KB)");
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String algneKataloog = "/Users/nikita_filin/Desktop/anime"; // Muuda vastavalt oma kataloogi nimele
        System.out.println("[" + algneKataloog + "]");
        failipuu(algneKataloog);
    }
}
