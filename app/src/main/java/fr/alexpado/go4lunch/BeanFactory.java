package fr.alexpado.go4lunch;

import java.util.HashMap;
import java.util.Map;

import fr.alexpado.go4lunch.services.AuthenticationService;

/**
 * Class handling the dependency injection across the application. You can create your own to give a
 * limited bean access to certain part of your application or use the global instance
 * {@link #INSTANCE}.
 */
public final class BeanFactory {

    /**
     * Global instance of {@link BeanFactory}
     */
    public static final BeanFactory INSTANCE = createInstance();

    /**
     * Create an instance of {@link BeanFactory} with all default global beans.
     *
     * @return A {@link BeanFactory}.
     */
    private static BeanFactory createInstance() {

        BeanFactory factory = new BeanFactory();

        factory.registerBean(AuthenticationService.class, new AuthenticationService());

        return factory;
    }

    /**
     * Utility method to directly access {@link BeanFactory#getBean(Class)} of the global instance
     * (sugar syntax)
     *
     * @param clazz
     *         A class
     * @param <T>
     *         Type of the bean
     *
     * @return A bean instance
     */
    public static <T> T getInstance(Class<T> clazz) {

        return INSTANCE.getBean(clazz);
    }

    private final Map<Class<?>, Object> beans = new HashMap<>();

    /**
     * Check if the current {@link BeanFactory} has a bean of the provided class registered.
     *
     * @param beanClass
     *         A class
     *
     * @return True if the class is a registered bean, false otherwise.
     */
    public boolean hasBean(Class<?> beanClass) {

        return this.beans.containsKey(beanClass);
    }

    /**
     * Register the provided class and its instance as bean in this {@link BeanFactory}.
     *
     * @param beanClass
     *         A class
     * @param instance
     *         The class instance
     * @param <T>
     *         Type of the bean
     */
    public <T> void registerBean(Class<T> beanClass, T instance) {

        if (this.hasBean(beanClass)) {
            throw new IllegalArgumentException(
                    String.format(
                            "The class '%s' is already registered as a bean",
                            beanClass.getName()
                    )
            );
        }

        this.beans.put(beanClass, instance);
    }

    /**
     * Unregister the bean matching the provided class.
     *
     * @param beanClass
     *         A class
     * @param <T>
     *         Type of the bean
     */
    public <T> void unregisterBean(Class<T> beanClass) {

        if (!this.hasBean(beanClass)) {
            throw new IllegalArgumentException(
                    String.format("The class '%s' is not a registered bean", beanClass.getName())
            );
        }
        this.beans.remove(beanClass);
    }

    /**
     * Retrieve the bean instance matching the provided class.
     *
     * @param beanClass
     *         A class
     * @param <T>
     *         Type of the bean
     *
     * @return The bean instance
     */
    public <T> T getBean(Class<?> beanClass) {

        if (!this.hasBean(beanClass)) {
            throw new IllegalArgumentException(
                    String.format("The class '%s' is not a registered bean", beanClass.getName())
            );
        }
        // This is a safe cast due to registerBean signature.
        return (T) this.beans.get(beanClass);
    }

}
