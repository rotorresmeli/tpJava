import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OperacionServiceImpl {
    public static <T extends Comparable<T>> T obtenerElementoMinimo(T[] arr){
        Arrays.sort(arr);
        return arr[0];
    }

    public static <T extends Comparable<T>> T obtenerElementoMaximo(T[] arr){
        Arrays.sort(arr);
        return arr[arr.length -1];
    }

    public static <T extends Comparable<T>> int obtenerPosicionElemento(T[] arr, T elemento){
        Arrays.sort(arr);
        return Arrays.binarySearch(arr, elemento);
    }

    public static <T extends Comparable<T>> void ordenarArrayAscendente(List<T> arr){
        Collections.sort(arr);
    }

    public static <T extends Comparable<T>> void ordenarArrayDescendente(List<T> arr){
        Collections.sort(arr, Collections.reverseOrder());
    }

}
