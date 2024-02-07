package me.jishuna.customentitylib.adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Collection;

public class CustomTypeAdapterFactory implements TypeAdapterFactory {
    // Used as workaround for https://github.com/google/gson/issues/1028
    private final boolean wasCreatedByJsonAdapter;

    public static final CustomTypeAdapterFactory INSTANCE = new CustomTypeAdapterFactory(false);

    private CustomTypeAdapterFactory(boolean wasCreatedByJsonAdapter) {
        this.wasCreatedByJsonAdapter = wasCreatedByJsonAdapter;
    }

    private CustomTypeAdapterFactory() {
        this(true);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }

        // Safe; the check above made sure type is List
        @SuppressWarnings("unchecked")
        TypeAdapter<Collection<Object>> delegate = (TypeAdapter<Collection<Object>>) (this.wasCreatedByJsonAdapter ? gson.getAdapter(type) : gson.getDelegateAdapter(this, type));

        @SuppressWarnings("unchecked")
        TypeAdapter<T> adapter = (TypeAdapter<T>) new TypeAdapter<Collection<Object>>() {
            @Override
            public Collection<Object> read(JsonReader in) throws IOException {
                return delegate.read(in);
            }

            @Override
            public void write(JsonWriter out, Collection<Object> value) throws IOException {
                if (value == null || value.isEmpty()) {
                    // Call the delegate instead of directly writing null in case the delegate has
                    // special null handling
                    delegate.write(out, null);
                } else {
                    delegate.write(out, value);
                }
            }
        };
        return adapter;
    }
}
