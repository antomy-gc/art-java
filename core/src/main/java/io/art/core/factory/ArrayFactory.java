package io.art.core.factory;

import io.art.core.collection.*;
import lombok.experimental.*;
import static io.art.core.caster.Caster.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.collection.ImmutableArray.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.*;

@UtilityClass
public class ArrayFactory {
    public static <T> List<T> fixedArrayOf(Collection<T> elements) {
        return isEmpty(elements) ? emptyList() : new ArrayList<>(elements);
    }

    @SafeVarargs
    public static <T> List<T> fixedArrayOf(T... elements) {
        return isEmpty(elements) ? emptyList() : asList(elements);
    }

    public static <T> List<T> fixedArrayOf(Stream<T> stream) {
        return isEmpty(stream) ? emptyList() : asList(cast(stream.toArray()));
    }


    public static <T> ImmutableArray<T> immutableArrayOf(Collection<T> elements) {
        return isEmpty(elements) ? ImmutableArray.emptyImmutableArray() : new ImmutableArray<>(elements);
    }

    public static <T> ImmutableArray<T> immutableSortedArrayOf(Comparator<T> comparator, Collection<T> elements) {
        return isEmpty(elements) ? emptyImmutableArray() : immutableSortedArray(elements, comparator);
    }

    @SafeVarargs
    public static <T> ImmutableArray<T> immutableArrayOf(T... elements) {
        return isEmpty(elements) ? ImmutableArray.emptyImmutableArray() : new ImmutableArray<>(Arrays.asList(elements));
    }

    public static <T> ImmutableArray<T> immutableArrayOf(Stream<T> stream) {
        return isNull(stream) ? ImmutableArray.emptyImmutableArray() : stream.collect(ImmutableArray.immutableArrayCollector());
    }


    public static <T> List<T> dynamicArray() {
        return new ArrayList<>();
    }

    public static <T> List<T> dynamicArray(int size) {
        return size <= 0 ? new ArrayList<>() : new ArrayList<>(size);
    }

    @SafeVarargs
    public static <T> List<T> dynamicArrayOf(T... elements) {
        return isEmpty(elements) ? new ArrayList<>() : new ArrayList<>(asList(elements));
    }

    public static <T> List<T> dynamicArrayOf(Stream<T> stream) {
        return isEmpty(stream) ? emptyList() : stream.collect(toCollection(ArrayFactory::dynamicArray));
    }

    public static <T> List<T> dynamicArrayOf(Collection<T> elements) {
        return isEmpty(elements) ? new ArrayList<>() : new ArrayList<>(elements);
    }

    public static List<Long> dynamicArrayOf(long[] elements) {
        if (isEmpty(elements)) return emptyList();
        List<Long> array = dynamicArray(elements.length);
        for (long element : elements) array.add(element);
        return array;
    }

    public static List<Short> dynamicArrayOf(short[] elements) {
        if (isEmpty(elements)) return emptyList();
        List<Short> array = dynamicArray(elements.length);
        for (short element : elements) array.add(element);
        return array;
    }

    public static List<Integer> dynamicArrayOf(int[] elements) {
        if (isEmpty(elements)) return emptyList();
        List<Integer> array = dynamicArray(elements.length);
        for (int element : elements) array.add(element);
        return array;
    }

    public static List<Byte> dynamicArrayOf(byte[] elements) {
        if (isEmpty(elements)) return emptyList();
        List<Byte> array = dynamicArray(elements.length);
        for (byte element : elements) array.add(element);
        return array;
    }

    public static List<Double> dynamicArrayOf(double[] elements) {
        if (isEmpty(elements)) return emptyList();
        List<Double> array = dynamicArray(elements.length);
        for (double element : elements) array.add(element);
        return array;
    }

    public static List<Float> dynamicArrayOf(float[] elements) {
        if (isEmpty(elements)) return emptyList();
        List<Float> array = dynamicArray(elements.length);
        for (float element : elements) array.add(element);
        return array;
    }

    public static List<Boolean> dynamicArrayOf(boolean[] elements) {
        if (isEmpty(elements)) return emptyList();
        List<Boolean> array = dynamicArray(elements.length);
        for (boolean element : elements) array.add(element);
        return array;
    }
}
