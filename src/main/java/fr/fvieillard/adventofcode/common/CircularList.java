package fr.fvieillard.adventofcode.common;

import java.util.ArrayList;
import java.util.List;

public class CircularList<T> {
    private List<T> content;
    private int lastIndex;

    public CircularList(List<T> initialList) {
        content = new ArrayList<>(initialList);
        lastIndex = content.size() - 1;
    }

    public T get(int index) {
        return content.get(index % (lastIndex + 1));
    }

    public int indexOf(T obj) {
        return content.indexOf(obj);
    }

    public void move(T obj, long offset) {
        int index = content.indexOf(obj);
        if (index < 0){
            throw new IllegalArgumentException("List does not contain this value.");
        }
        int newIndex = (int)((index + offset) % lastIndex);
        if (newIndex <= 0) {
            newIndex = lastIndex + newIndex;
        }
        content.remove(index);
        content.add(newIndex, obj);
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
