package utils;

import java.io.Serializable;
import java.util.function.Function;

public interface ISerializableFunction<T,R> extends Function<T,R>, Serializable {

}
