package pl.zpi.museumguide;

/**
 * Created by mateu on 08.06.2017.
 */

import android.text.style.CharacterStyle;

public class SpanHolder {
    private CharacterStyle[] spans;
    private int start;
    private int end;
    private boolean textChunkPadded =false;
    private int wordHolderIndex;
    private int currentSpaces;
    public SpanHolder(CharacterStyle[] spans,int start,int end,int spaces){
        this.setSpans(spans);
        this.setStart(start);
        this.setEnd(end);
        this.setCurrentSpaces(spaces);
    }

    public static SpanHolder getNewInstance(CharacterStyle[] spans,int start,int end,int spaces){
        return new SpanHolder(spans,start,end,spaces);
    }


    public boolean isTextChunkPadded() {
        return textChunkPadded;
    }

    public void setTextChunkPadded(boolean textChunkPadded) {
        this.textChunkPadded = textChunkPadded;
    }

    public int getWordHolderIndex() {
        return wordHolderIndex;
    }

    public void setWordHolderIndex(int wordHolderIndex) {
        this.wordHolderIndex = wordHolderIndex;
    }

    public CharacterStyle[] getSpans() {
        return spans;
    }

    public void setSpans(CharacterStyle[] spans) {
        this.spans = spans;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCurrentSpaces() {
        return currentSpaces;
    }

    public void setCurrentSpaces(int currentSpaces) {
        this.currentSpaces = currentSpaces;
    }
}
