package io.qbeat;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    public static void main(String[] args) {
        String paragraph = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam tristique tortor vitae nulla egestas, a vestibulum sapien pharetra. Vivamus hendrerit mattis tortor, nec faucibus tellus pellentesque vel. Proin a dolor dui. Fusce vel elementum ex. Suspendisse congue, enim in blandit ornare, mi erat tristique augue, et lobortis neque enim non dui. Nulla euismod gravida ex at vulputate. Aliquam eget ex vel tortor finibus elementum. Integer non leo rhoncus, porta lacus non, interdum lorem. Sed non condimentum ante, id ullamcorper magna. Phasellus elementum felis placerat sem tempus, et tincidunt justo interdum. Curabitur mauris massa, imperdiet sed neque sit amet, aliquet feugiat.";

        ConcurrentLinkedQueue<PositionWithWord> queueOfConsumer1 = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<PositionWithWord> queueOfConsumer2 = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<PositionWithWord> outputQueue = new ConcurrentLinkedQueue<>();

        new Thread(new Producer(paragraph, queueOfConsumer1, queueOfConsumer2)).start();
        new Thread(new Consumer("1", queueOfConsumer1, outputQueue)).start();
        new Thread(new Consumer("2", queueOfConsumer2, outputQueue)).start();
        new Thread(new FinalConsumer(outputQueue)).start();
    }
}
