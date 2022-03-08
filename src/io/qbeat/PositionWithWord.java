package io.qbeat;

/**
 * Immutable class that holds the a word (String), along with the location of where that word was located.
 */
public final class PositionWithWord {

    // Denotes that this does not have a position in the paragraph
    public static final int EMPTY_POSITION = Integer.MIN_VALUE;

    private final int position;
    private final String word;

    public PositionWithWord(int position, String word) {
        this.position = position;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public int getPosition() {
        return position;
    }
}
