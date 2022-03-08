package io.qbeat;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Consumer implements Runnable {

    private final String name;
    private final ConcurrentLinkedQueue<PositionWithWord> inputQueue;
    private final ConcurrentLinkedQueue<PositionWithWord> outputQueue;

    public Consumer(String name, ConcurrentLinkedQueue<PositionWithWord> inputQueue, ConcurrentLinkedQueue<PositionWithWord> outputQueue) {
        this.name = name;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        boolean isEndingSignalReceived = false;
        while (!isEndingSignalReceived) {
            for (PositionWithWord positionWithWord : inputQueue) {
                outputQueue.add(new PositionWithWord(positionWithWord.getPosition(), positionWithWord.getWord() + name));

                // As soon as we receive the ending signal, we terminate
                if (positionWithWord.getPosition() == PositionWithWord.EMPTY_POSITION) {
                    isEndingSignalReceived = true;
                }
            }
        }
    }
}
