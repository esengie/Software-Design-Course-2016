package ru.spbau.mit.Util;

/**
 * A pair class
 * @param <F>
 * @param <S>
 */
public class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F a_first, S a_second) {
        first = a_first;
        second = a_second;
    }
}
