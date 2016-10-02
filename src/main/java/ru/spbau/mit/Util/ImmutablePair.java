package ru.spbau.mit.Util;

/**
 * An immutable pair class, can't change once created
 *
 * @param <F> first parameter
 * @param <S> second parameter
 */
public class ImmutablePair<F, S> {
    public final F first;
    public final S second;

    public ImmutablePair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
