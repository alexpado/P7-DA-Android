package fr.alexpado.go4lunch.jdk;

import java.util.Objects;

public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t
     *         the input argument
     */
    void accept(T t);

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this operation followed by
     * the {@code after} operation. If performing either operation throws an exception, it is
     * relayed to the caller of the composed operation.  If performing this operation throws an
     * exception, the {@code after} operation will not be performed.
     *
     * @param after
     *         the operation to perform after this operation
     *
     * @return a composed {@code Consumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     *
     * @throws NullPointerException
     *         if {@code after} is null
     */
    default java.util.function.Consumer<T> andThen(Consumer<? super T> after) {

        Objects.requireNonNull(after);
        return (T t) -> {
            this.accept(t);
            after.accept(t);
        };
    }

}