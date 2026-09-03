public class GcObserve {
    public static void main(String[] args) {
        long checksum = 0;

        for (int round = 1; round <= 20; round++) {
            // About 12.5 MB per temporary batch.
            // DONE: byte[][] batch = new byte[200][];
            byte[][] batch = new byte[200][];

            for (int i = 0; i < batch.length; i++) {
                // DONE: batch[i] = new byte[64 * 1024];
                batch[i] = new byte[64 * 1024];
                // DONE: checksum += batch[i].length;
                checksum += batch[i].length;
            }

            if (round % 5 == 0) {
                // DONE: println "Completed round " + round
                System.out.println("Completed round " + round);
            }

            // On the next iteration, this batch can become unreachable.
        }

        // DONE: println "Allocated bytes over time: " + checksum
        System.out.println("Allocated bytes over time: " + checksum);
    }
}
