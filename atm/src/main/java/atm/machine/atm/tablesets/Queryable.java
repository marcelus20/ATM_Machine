package atm.machine.atm.tablesets;

/**
 * Queryable interface
 *
 * The reason I'm creating it, is because I'm not using database,
 * but data will be manipulated in the memory.
 *
 * The AccountSets and SessionSets will implement it.
 * @param <T>
 */
public interface Queryable<T> {
    public T retrieveOneOrNull(T object);

    void write(T object);
}
