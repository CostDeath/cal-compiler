package model;

public class GenericVal<T> {
    private final T value;

    public T getValue() {
        return value;
    }

    public GenericVal(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value != null) return value.toString();
        return "null";
    }
}
