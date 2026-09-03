public class GarbageCollectionDemo {

    private static class DemoObject {
        private final String label;
        private final byte[] payload = new byte[128];

        DemoObject(String label) {
            this.label = label;
        }
    }

    public static void main(String[] args) {
        System.out.println("===== Garbage Collection Demonstration =====");
        long startTime = System.nanoTime();

        MemoryMonitor.printMemoryReport("Before Allocation");

        DemoObject[] objects = new DemoObject[100000];
        System.out.println("Creating Objects...");
        int createdObjCount = 0;
        // DONE: fill objects[i] = new DemoObject("Object-" + i)
        for (int i = 0; i < objects.length; i++) {
            objects[i] = new DemoObject("Object-" + i);
            createdObjCount++;
        }
        // DONE: print Objects Created count; printMemoryReport After Allocation
        System.out.println("Objects Created : " + createdObjCount);
        MemoryMonitor.printMemoryReport("After Allocation");


        // DONE: set objects = null; trigger GC; print After GC report + elapsed ms
        // Tip: elapsedMillis = (System.nanoTime() - startTime) / 1_000_000
        objects = null;
        System.gc();
        MemoryMonitor.printMemoryReport("After GC");
        long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Elapsed time: " + elapsedMillis + "ms");


    }
}
