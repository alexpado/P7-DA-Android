package fr.alexpado.go4lunch.jdk;

public interface Supplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get();

}