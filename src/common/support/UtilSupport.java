package common.support;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

public class UtilSupport extends StringUtils {

	public static String[] str2array(String str, String spliter) {
		return str.split(spliter);
	}

	public static String array2str(String[] str) {
		return join(str, ",");
	}

	public static String strCopy(int copy_times, String copy_str) {
		return String.join("", Collections.nCopies(copy_times, copy_str));
	}

	@SafeVarargs
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	public static String strReplace(String old_str, String new_str, int beginIndex, int endIndex) {
		return new StringBuffer(old_str).replace(beginIndex, endIndex, new_str).toString();
	}

	public static String subAfter(String str, String subIndex) {
		return str.substring(str.indexOf(subIndex) + 1);
	}

	public static String subBefore(String str, String subIndex) {
		return str.substring(0, str.indexOf(subIndex));
	}

	public static String scaleF(float f, int floatScale) {
		return new DecimalFormat("#." + strCopy(floatScale, "0")).format(f);
	}

	public static String scaleFloat(String str, int floatScale) {
		return new DecimalFormat("#." + strCopy(floatScale, "0")).format(str);
	}

	public static String[] addBlank(String[] str, int idealLength) {
		String[] sv = new String[idealLength - str.length];
		System.out.println("Ìî¿ÕÔªËØÊý£º" + sv.length);
		for (int i = 0; i < sv.length; i++) {
			sv[i] = "";
		}
		return concatAll(sv, str);
	}

	public static String[][] Transpose(String[][] matrix) {
		int list = matrix.length;
		int line = matrix[0].length;
		String[][] MatrixC = new String[line][list];
		for (int i = 0; i < list; i++) {
			for (int j = 0; j < line; j++) {
				MatrixC[j][i] = matrix[i][j];
			}
		}
		return MatrixC;
	}

	public static int filterOrder(String[] origins, Predicate<String> condition) {
		for (int i = 0; i < origins.length; i++) {
			if (condition.test(origins[i])) {
				return i;
			}
		}
		return -1;
	}

	public static int getMax(int[] a) {
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			max = Integer.max(max, a[i]);
		}
		return max;
	}
}
