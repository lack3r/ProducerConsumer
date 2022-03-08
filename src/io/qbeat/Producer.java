package io.qbeat;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Producer implements Runnable {

    // This should be configurable in a property file
    private static final int THRESHOLD = 4;

    private final String paragraph;
    private final ConcurrentLinkedQueue<PositionWithWord> queueOfConsumer1;
    private final ConcurrentLinkedQueue<PositionWithWord> queueOfConsumer2;


    public Producer(String paragraph, ConcurrentLinkedQueue<PositionWithWord> queueOfConsumer1, ConcurrentLinkedQueue<PositionWithWord> queueOfConsumer2) {
        this.paragraph = paragraph;
        this.queueOfConsumer1 = queueOfConsumer1;
        this.queueOfConsumer2 = queueOfConsumer2;
    }

    @Override
    public void run() {
        final String[] words = paragraph.split(" ");
        for (int position = 0; position < words.length; position++) {
            String word = words[position];
            ConcurrentLinkedQueue<PositionWithWord> correspondingQueue = getCorrespondingQueue(word);
            correspondingQueue.add(new PositionWithWord(position, word));
        }

        // Add the following in both queues, to signal that no more elements will be added
        queueOfConsumer1.add(new PositionWithWord(PositionWithWord.EMPTY_POSITION, "FINISHED"));
        queueOfConsumer2.add(new PositionWithWord(PositionWithWord.EMPTY_POSITION, "FINISHED"));
    }

    private ConcurrentLinkedQueue<PositionWithWord> getCorrespondingQueue(String word) {
        return word.length() < THRESHOLD ? queueOfConsumer1 : queueOfConsumer2;
    }


}
