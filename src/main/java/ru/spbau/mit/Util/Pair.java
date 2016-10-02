package ru.spbau.mit.Util;

/**
 * A pair class
 * @param <F>
 * @param <S>
 */
public class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
