package gtPlusPlus.core.util.data;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.AutoMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.item.ItemStack;

public class ArrayUtils {

    public static <V> V[] expandArray(final V[] someArray, final V newValueToAdd) {
        V[] series = someArray;
        series = addElement(series, newValueToAdd);
        return series;
    }

    private static <V> V[] addElement(V[] series, final V newValueToAdd) {
        series = Arrays.copyOf(series, series.length + 1);
        series[series.length - 1] = newValueToAdd;
        return series;
    }

    public static <V> V[] insertElementAtIndex(V[] aArray, int aIndex, V aObjectToInsert) {
        V[] newArray = Arrays.copyOf(aArray, aArray.length + 1);
        for (int i = 0; i < aIndex; i++) {
            newArray[i] = aArray[i];
        }
        newArray[aIndex] = aObjectToInsert;
        for (int i = (aIndex + 1); i < newArray.length; i++) {
            newArray[i] = aArray[i - 1];
        }
        return newArray;
    }

    /*public static <V> Object getMostCommonElement(List<V> list) {
    	Optional r = list.stream().map(V::getTextureSet).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);
    	return r.get();
    }*/

    public static Object[] removeNulls(final Object[] v) {
        List<Object> list = new ArrayList<Object>(Arrays.asList(v));
        list.removeAll(Collections.singleton((Object) null));
        return list.toArray(new Object[0]);
    }

    public static ItemStack[] removeNulls(final ItemStack[] v) {
        List<ItemStack> list = new ArrayList<ItemStack>(Arrays.asList(v));
        list.removeAll(Collections.singleton((ItemStack) null));
        return list.toArray(new ItemStack[0]);
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> combineSetData(Set<T> S, Set<T> J) {
        Set<T> mData = new HashSet<T>();
        T[] array1 = (T[]) S.toArray();
        Collections.addAll(mData, array1);
        T[] array2 = (T[]) J.toArray();
        Collections.addAll(mData, array2);
        return mData;
    }

    public static <A> AutoMap<A> mergeTwoMaps(AutoMap<A> a, AutoMap<A> b) {
        AutoMap<A> c = new AutoMap<A>();
        for (A g : a.values()) {
            c.put(g);
        }
        for (A g : b.values()) {
            c.put(g);
        }
        return c;
    }

    public static <T> T[][] rotateArrayClockwise(T[][] mat) {
        Logger.INFO("Rotating Array 90' Clockwise");
        try {
            final int M = mat.length;
            final int N = mat[0].length;
            Logger.INFO("Dimension X: " + M);
            Logger.INFO("Dimension Z: " + N);
            @SuppressWarnings("unchecked")
            T[][] ret = (T[][]) new Object[N][M];
            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    ret[c][M - 1 - r] = mat[r][c];
                }
            }
            Logger.INFO("Returning Rotated Array");
            return ret;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public static String toString(Object[] aArray, String string) {
        return org.apache.commons.lang3.ArrayUtils.toString(aArray, string);
    }

    public static String toString(Object[] aArray) {
        return org.apache.commons.lang3.ArrayUtils.toString(aArray);
    }

    public static <T> Object[] getArrayFromArrayList(ArrayList<T> aArrayList) {
        Object[] aGenericArray = new Object[aArrayList.size()];
        int aIndex = 0;
        for (T object : aArrayList) {
            aGenericArray[aIndex++] = object;
        }
        return aGenericArray;
    }
}
