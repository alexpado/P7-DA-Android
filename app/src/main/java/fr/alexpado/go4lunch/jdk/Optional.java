package fr.alexpado.go4lunch.jdk;

import androidx.annotation.NonNull;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>(null);

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private final T value;

    /**
     * Returns an empty {@code Optional} instance.  No value is present for this {@code Optional}.
     *
     * @param <T>
     *         The type of the non-existent value
     *
     * @return an empty {@code Optional}
     *
     * @apiNote Though it may be tempting to do so, avoid testing if an object is empty by
     *         comparing with {@code ==} or {@code !=} against instances returned by
     *         {@code Optional.empty()}.  There is no guarantee that it is a singleton. Instead, use
     *         {@link #isEmpty()} or {@link #isPresent()}.
     */
    public static <T> Optional<T> empty() {

        return (Optional<T>) EMPTY;
    }

    /**
     * Constructs an instance with the described value.
     *
     * @param value
     *         the value to describe; it's the caller's responsibility to ensure the value is
     *         non-{@code null} unless creating the singleton instance returned by {@code empty()}.
     */
    private Optional(T value) {

        this.value = value;
    }

    /**
     * Returns an {@code Optional} describing the given non-{@code null} value.
     *
     * @param value
     *         the value to describe, which must be non-{@code null}
     * @param <T>
     *         the type of the value
     *
     * @return an {@code Optional} with the value present
     *
     * @throws NullPointerException
     *         if value is {@code null}
     */
    public static <T> Optional<T> of(T value) {

        return new Optional<>(Objects.requireNonNull(value));
    }

    /**
     * Returns an {@code Optional} describing the given value, if non-{@code null}, otherwise
     * returns an empty {@code Optional}.
     *
     * @param value
     *         the possibly-{@code null} value to describe
     * @param <T>
     *         the type of the value
     *
     * @return an {@code Optional} with a present value if the specified value is non-{@code null},
     *         otherwise an empty {@code Optional}
     */
    public static <T> Optional<T> ofNullable(T value) {

        return value == null ? (Optional<T>) EMPTY
                : new Optional<>(value);
    }

    /**
     * If a value is present, returns the value, otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code Optional}
     *
     * @throws NoSuchElementException
     *         if no value is present
     * @apiNote The preferred alternative to this method is {@link #orElseThrow()}.
     */
    public T get() {

        if (this.value == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    /**
     * If a value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public boolean isPresent() {

        return this.value != null;
    }

    /**
     * If a value is  not present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is not present, otherwise {@code false}
     *
     * @since 11
     */
    public boolean isEmpty() {

        return this.value == null;
    }

    /**
     * If a value is present, performs the given action with the value, otherwise does nothing.
     *
     * @param action
     *         the action to be performed, if a value is present
     *
     * @throws NullPointerException
     *         if value is present and the given action is {@code null}
     */
    public void ifPresent(Consumer<? super T> action) {

        if (this.value != null) {
            action.accept(this.value);
        }
    }

    /**
     * If a value is present, performs the given action with the value, otherwise performs the given
     * empty-based action.
     *
     * @param action
     *         the action to be performed, if a value is present
     * @param emptyAction
     *         the empty-based action to be performed, if no value is present
     *
     * @throws NullPointerException
     *         if a value is present and the given action is {@code null}, or no value is present
     *         and the given empty-based action is {@code null}.
     * @since 9
     */
    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {

        if (this.value != null) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    /**
     * If a value is present, and the value matches the given predicate, returns an {@code Optional}
     * describing the value, otherwise returns an empty {@code Optional}.
     *
     * @param predicate
     *         the predicate to apply to a value, if present
     *
     * @return an {@code Optional} describing the value of this {@code Optional}, if a value is
     *         present and the value matches the given predicate, otherwise an empty
     *         {@code Optional}
     *
     * @throws NullPointerException
     *         if the predicate is {@code null}
     */
    public Optional<T> filter(Predicate<? super T> predicate) {

        Objects.requireNonNull(predicate);
        if (!this.isPresent()) {
            return this;
        } else {
            return predicate.test(this.value) ? this : empty();
        }
    }

    /**
     * If a value is present, returns an {@code Optional} describing (as if by {@link #ofNullable})
     * the result of applying the given mapping function to the value, otherwise returns an empty
     * {@code Optional}.
     *
     * <p>If the mapping function returns a {@code null} result then this method
     * returns an empty {@code Optional}.
     *
     * @param mapper
     *         the mapping function to apply to a value, if present
     * @param <U>
     *         The type of the value returned from the mapping function
     *
     * @return an {@code Optional} describing the result of applying a mapping function to the value
     *         of this {@code Optional}, if a value is present, otherwise an empty {@code Optional}
     *
     * @throws NullPointerException
     *         if the mapping function is {@code null}
     * @apiNote This method supports post-processing on {@code Optional} values, without the
     *         need to explicitly check for a return status.  For example, the following code
     *         traverses a stream of URIs, selects one that has not yet been processed, and creates
     *         a path from that URI, returning an {@code Optional<Path>}:
     *
     *         <pre>{@code
     *                     Optional<Path> p =
     *                         uris.stream().filter(uri -> !isProcessedYet(uri))
     *                                       .findFirst()
     *                                       .map(Paths::get);
     *                 }</pre>
     *         <p>
     *         Here, {@code findFirst} returns an {@code Optional<URI>}, and then {@code map}
     *         returns an {@code Optional<Path>} for the desired URI if one exists.
     */
    public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {

        Objects.requireNonNull(mapper);
        if (!this.isPresent()) {
            return empty();
        } else {
            return Optional.ofNullable(mapper.apply(this.value));
        }
    }

    /**
     * If a value is present, returns the result of applying the given {@code Optional}-bearing
     * mapping function to the value, otherwise returns an empty {@code Optional}.
     *
     * <p>This method is similar to {@link #map(Function)}, but the mapping
     * function is one whose result is already an {@code Optional}, and if invoked, {@code flatMap}
     * does not wrap it within an additional {@code Optional}.
     *
     * @param <U>
     *         The type of value of the {@code Optional} returned by the mapping function
     * @param mapper
     *         the mapping function to apply to a value, if present
     *
     * @return the result of applying an {@code Optional}-bearing mapping function to the value of
     *         this {@code Optional}, if a value is present, otherwise an empty {@code Optional}
     *
     * @throws NullPointerException
     *         if the mapping function is {@code null} or returns a {@code null} result
     */
    public <U> Optional<U> flatMap(Function<? super T, ? extends Optional<? extends U>> mapper) {

        Objects.requireNonNull(mapper);
        if (!this.isPresent()) {
            return empty();
        } else {
            Optional<U> r = (Optional<U>) mapper.apply(this.value);
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns an {@code Optional} describing the value, otherwise returns an
     * {@code Optional} produced by the supplying function.
     *
     * @param supplier
     *         the supplying function that produces an {@code Optional} to be returned
     *
     * @return returns an {@code Optional} describing the value of this {@code Optional}, if a value
     *         is present, otherwise an {@code Optional} produced by the supplying function.
     *
     * @throws NullPointerException
     *         if the supplying function is {@code null} or produces a {@code null} result
     * @since 9
     */
    public Optional<T> or(Supplier<? extends Optional<? extends T>> supplier) {

        Objects.requireNonNull(supplier);
        if (this.isPresent()) {
            return this;
        } else {
            Optional<T> r = (Optional<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns the value, otherwise returns {@code other}.
     *
     * @param other
     *         the value to be returned, if no value is present. May be {@code null}.
     *
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {

        return this.value != null ? this.value : other;
    }

    /**
     * If a value is present, returns the value, otherwise returns the result produced by the
     * supplying function.
     *
     * @param supplier
     *         the supplying function that produces a value to be returned
     *
     * @return the value, if present, otherwise the result produced by the supplying function
     *
     * @throws NullPointerException
     *         if no value is present and the supplying function is {@code null}
     */
    public T orElseGet(Supplier<? extends T> supplier) {

        return this.value != null ? this.value : supplier.get();
    }

    /**
     * If a value is present, returns the value, otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code Optional}
     *
     * @throws NoSuchElementException
     *         if no value is present
     * @since 10
     */
    public T orElseThrow() {

        if (this.value == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    /**
     * If a value is present, returns the value, otherwise throws an exception produced by the
     * exception supplying function.
     *
     * @param <X>
     *         Type of the exception to be thrown
     * @param exceptionSupplier
     *         the supplying function that produces an exception to be thrown
     *
     * @return the value, if present
     *
     * @throws X
     *         if no value is present
     * @throws NullPointerException
     *         if no value is present and the exception supplying function is {@code null}
     * @apiNote A method reference to the exception constructor with an empty argument list
     *         can be used as the supplier. For example, {@code IllegalStateException::new}
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {

        if (this.value != null) {
            return this.value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this {@code Optional}. The other object is
     * considered equal if:
     * <ul>
     * <li>it is also an {@code Optional} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj
     *         an object to be tested for equality
     *
     * @return {@code true} if the other object is "equal to" this object otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj instanceof Optional<?>) {
            return Objects.equals(this.value, ((Optional<?>) obj).value);
        }
        return false;
    }

    /**
     * Returns the hash code of the value, if present, otherwise {@code 0} (zero) if no value is
     * present.
     *
     * @return hash code value of the present value or {@code 0} if no value is present
     */
    @Override
    public int hashCode() {

        return Objects.hashCode(this.value);
    }

    /**
     * Returns a non-empty string representation of this {@code Optional} suitable for debugging.
     * The exact presentation format is unspecified and may vary between implementations and
     * versions.
     *
     * @return the string representation of this instance
     *
     * @implSpec If a value is present the result must include its string representation in
     *         the result.  Empty and present {@code Optional}s must be unambiguously
     *         differentiable.
     */
    @NonNull
    @Override
    public String toString() {

        return this.value != null
                ? ("Optional[" + this.value + "]")
                : "Optional.empty";
    }

}
