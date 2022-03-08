package io.qbeat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FinalConsumer implements Runnable {
    public static final int INTERMEDIATE_CONSUMERS = 2;
    private final ConcurrentLinkedQueue<PositionWithWord> outputQueue;
    private final Map<Integer, String> correctOrder = new HashMap<>();

    public FinalConsumer(ConcurrentLinkedQueue<PositionWithWord> outputQueue) {
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        int intermediateConsumersFinished = 0;

        // We should read values, while
        // a. The intermediate consumers still run (and thus they produce values)
        // b. There are still values in the queue, even if the intermediate consumers finished their work
        while (intermediateConsumersFinished < INTERMEDIATE_CONSUMERS || outputQueue.size() > 0) {

            final PositionWithWord positionWithWord = outputQueue.poll();

            if (positionWithWord == null) {
                // Proceed to get the next element.
                // We want to do that, until the queue is empty
                // AND until we receive the messages that both consumers have finished
                // Note: In production system, we should also have timeouts as well.
                continue;
            }

            if (positionWithWord.getPosition() == PositionWithWord.EMPTY_POSITION) {
                intermediateConsumersFinished++;
            } else {
                correctOrder.put(positionWithWord.getPosition(), positionWithWord.getWord());
            }
        }

        for (int position = 0; position < correctOrder.size(); position++) {
            final String word = correctOrder.get(position);
            System.out.print(word + " ");
        }
    }
}
