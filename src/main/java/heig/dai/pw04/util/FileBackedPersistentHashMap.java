package heig.dai.pw04.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * A persistent map that stores its content in a file.
 *
 * @author Olin Bourquin
 * @author Kevin Farine
 * @author Loïc Herman
 * @author Massimo Stefani
 * @apiNote This is a very simplified version of a map, not all API methods are
 *         transitively supported, the underlying entrySet is made immutable to
 *         avoid inconsistencies between the persisted state and the delegate state.
 */
public class FileBackedPersistentHashMap<
        K extends Serializable,
        V extends Serializable
        > extends AbstractMap<K, V> {

    private final Path file;
    private final Map<K, V> delegate;

    public FileBackedPersistentHashMap(Path file) {
        this.file = file;
        this.delegate = load();
    }

    @SuppressWarnings("unchecked")
    private HashMap<K, V> load() {
        if (!Files.exists(file)) {
            return new HashMap<>();
        }

        try (var in = new BufferedInputStream(Files.newInputStream(file, READ))) {
            var input = new ObjectInputStream(in);
            return (HashMap<K, V>) input.readObject();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private void save() {
        try (var out = new BufferedOutputStream(
                Files.newOutputStream(
                        file,
                        WRITE, CREATE, TRUNCATE_EXISTING
                )
        )) {
            var output = new ObjectOutputStream(out);
            output.writeObject(delegate);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(delegate.entrySet());
    }

    @Override
    public V get(Object key) {
        return delegate.get(key);
    }

    @Override
    public V put(K key, V value) {
        var result = delegate.put(key, value);
        save();
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
        save();
    }

    @Override
    public V remove(Object key) {
        var result = delegate.remove(key);
        save();
        return result;
    }

    @Override
    public void clear() {
        delegate.clear();
        save();
    }
}
