package common.collectionManagement;

import common.exceptions.EmptyCollectionException;
import common.exceptions.IdDoesNotExistException;
import common.exceptions.KeyDoesNotExistException;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CollectionManager<K, V> {

    private LinkedHashMap<K, V> collection;
    private java.time.LocalDate initializationDate;

    public LinkedHashMap<K, V> getMap() {
        return collection;
    }

    public CollectionManager() {
        this.collection = new LinkedHashMap<K, V>();
        this.initializationDate = java.time.LocalDate.now();
    }

    public void setCollection(LinkedHashMap<K, V> collection) {
        this.collection = collection;
    }

    public LinkedHashMap<K, V> getCollection() {
        return collection;
    }

    public CollectionManager(Map<K, V> collection) {
        this.collection = (LinkedHashMap<K, V>) collection;
        this.initializationDate = java.time.LocalDate.now();
    }

    public java.time.LocalDate getInitializationDate() {
        return initializationDate;
    }

    public boolean containsKey(K key) {
        return collection.containsKey(key);
    }


    public int size() {
        return collection.size();
    }

    public void insert(K key, V value) {
        collection.put(key, value);
    }

    public void clear() {
        collection.clear();
    }

    public abstract void updateByID(K key, V value) throws IdDoesNotExistException;

    public abstract void removeByKey(K key) throws KeyDoesNotExistException;

    public abstract StringBuilder show() throws EmptyCollectionException;
}
