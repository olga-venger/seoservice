package com.pullenti.unisharp;

public class Utils {

    public static <T> T cast(Object o, Class<T> clazz) { 
        if(clazz == null || o == null) return null;
		if(clazz.isAssignableFrom(o.getClass())) 
            return (T) o;
        return null;
    }
    public static boolean referenceEquals(Object o1, Object o2) { 
        if(o1 == null || o2 == null) return o1 == null && o2 == null;
        return o1.equals(o2);
    }
    public static Object notnull(Object o1, Object o2) { 
        if(o1 == null) return o2; 
        return o1; 
    }
    public static String notnullstr(String o1, String o2) { 
        if(o1 == null) return o2; 
        return o1; 
    }
    public static boolean isNullOrEmpty(String s) { 
        return s == null || s.length() == 0; 
    }
    public static boolean stringsEq(String s1, String s2) { 
        if(s1 == null || s2 == null) return s1 == null && s2 == null; 
        return s1.equals(s2); 
    }
    public static boolean stringsNe(String s1, String s2) { 
        if(s1 == null || s2 == null) return s1 != null || s2 != null; 
        return !s1.equals(s2); 
    }
    public static int stringsCompare(String s1, String s2, boolean ignoreCase) { 
        if(s1 == null || s2 == null) return 0; 
        if(ignoreCase) return s1.compareToIgnoreCase(s2); 
        return s1.compareTo(s2); 
    }
    public static String createString(char ch, int count) { 
        StringBuilder tmp = new StringBuilder();
        for(int i = 0; i < count; i++) tmp.append(ch);
        return tmp.toString();
    }
    static java.util.ArrayList<Integer> m_Whitespaces = new java.util.ArrayList<Integer>(java.util.Arrays.asList(0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x20, 0x85, 0xA0, 0x1680, 0x2000, 0x2001, 0x2002, 0x2003, 0x2004, 0x2005, 0x2006, 0x2007, 0x2008, 0x2009, 0x200A, 0x2028, 0x2029, 0x202F, 0x205F, 0x3000 ));
    public static boolean isWhitespace(char ch) {
    	if(m_Whitespaces.contains((Integer)(int)ch)) return true;
        return false;
    }
    public static String trimStart(String s) { 
        for(int i = 0; i < s.length(); i++) 
            if(!isWhitespace(s.charAt(i))) {
                if(i == 0) return s;
                return s.substring(i);
            }
        return "";
    }
    public static String trimEnd(String s) { 
        for(int i = s.length() - 1; i >=0; i--) 
            if(!isWhitespace(s.charAt(i))) {
                if(i == s.length() - 1) return s;
                return s.substring(0, i + 1);
            }
        return "";
    }

    public static String removeString(String str, int pos, int len) {
    	String tail = null;
		if(len <= 0) len = str.length() - pos;
    	if(pos == 0 && len == str.length()) return "";
    	if(pos + len < str.length()) {
    		tail = str.substring(pos + len);
    		if(pos == 0) return tail;
    	}
    	String res = str.substring(0, pos);
    	if(tail != null) res += tail;
    	return res;
    }
    public static int indexOfAny(String str, Character[] chars, int pos, int cou) {
    	for(int i = pos; i < str.length(); i++) {
	        if(cou > 0 && i - pos > cou) break;
    		char ch = str.charAt(i);
    		for(int j = 0; j < chars.length; j++)
    			if(chars[j] == ch) return i;
    	}
    	return -1;
    }
    public static int indexOfAny(String str, char[] chars, int pos, int cou) {
    	for(int i = pos; i < str.length(); i++) {
	        if(cou > 0 && i - pos > cou) break;
    		char ch = str.charAt(i);
    		for(int j = 0; j < chars.length; j++)
    			if(chars[j] == ch) return i;
    	}
    	return -1;
    }

    public static String[] split(String str, String dels, boolean ignoreEmpty) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        int i0 = 0, i = 0;
		if(str != null)
        for(i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(dels.indexOf(ch) >= 0) {
                if(i > i0) res.add(str.substring(i0, i));
                else if(!ignoreEmpty && i > 0) res.add("");
                i0 = i + 1;
            }
        }
        if(i > i0)
            res.add(str.substring(i0, i));
        String[] arr = new String[res.size()];
        arr = res.toArray(arr);
        return arr;
    }

    public static boolean startsWithString(String str, String sub, boolean caseIgnore) {
    	if(!caseIgnore) return str.startsWith(sub);
    	for(int i = 0; i < str.length(); i++) {
    		if(i >= sub.length()) return true;
    		char ch = Character.toUpperCase(str.charAt(i));
    		char ch1 = Character.toUpperCase(sub.charAt(i));
    		if(ch != ch1) return false;
    	}
    	return true;
    }
    public static boolean endsWithString(String str, String sub, boolean caseIgnore) {
    	if(!caseIgnore) return str.endsWith(sub);
    	int j = sub.length() - 1;
    	for(int i = str.length() - 1; i >=0; i--, j--) {
    		if(j < 0) return true;
    		char ch = Character.toUpperCase(str.charAt(i));
    		char ch1 = Character.toUpperCase(sub.charAt(j));
    		if(ch != ch1) return false;
    	}
    	return true;
    }


    public static StringBuilder append(StringBuilder sb, char ch, int count) {
        for(int i = 0; i < count; i++) sb.append(ch);
        return sb;
    }

    public static StringBuilder replace(StringBuilder sb, char oldChar, char newChar) {
        for (int i = sb.length() - 1; i >= 0; i--) {
            if(sb.charAt(i) == oldChar)
                sb.setCharAt(i, newChar);
        }
        return sb;
    }
    
    public static StringBuilder replace(StringBuilder sb, String oldStr, String newStr) {
        for (int i = sb.length() - oldStr.length(); i >= 0; i--) {
            int j;
            for (j = 0; j < oldStr.length(); j++) {
                if (sb.charAt(i + j) != oldStr.charAt(j)) {
                    break;
                }
            }
            if (j == oldStr.length()) {
                sb.replace(i, i + j, newStr);
            }
        }
        return sb;
    }

    public static Stopwatch startNewStopwatch() {
        Stopwatch sw = new Stopwatch();
        sw.start();
        return sw;
    }

    public static double mathTruncate(double val) {
        if (val < 0) {
            return Math.ceil(val);
        } else {
            return Math.floor(val);
        }
    }

    public static double mathRound(double val, int prec) {
        for (int i = 0; i < prec; i++) {
            val *= 10;
        }
        val = Math.round(val);
        for (int i = 0; i < prec; i++) {
            val /= 10;
        }
        return val;
    }
    
    public static String setDecimalSeparator(java.text.DecimalFormat nf, String sep) {
    	java.text.DecimalFormatSymbols sym = nf.getDecimalFormatSymbols();
    	if(sep.length() > 0)
    		sym.setDecimalSeparator(sep.charAt(0));
    	nf.setDecimalFormatSymbols(sym);
    	return String.valueOf(nf.getDecimalFormatSymbols().getDecimalSeparator());
    }

    public static boolean parseByte(String str, Outargwrapper<Byte> res) {
        try {
            res.value = Byte.parseByte(str);
            return true;
        } catch (Exception ex) {
            res.value = (byte) 0;
            return false;
        }
    }

    public static byte parseByte(String str) throws NumberFormatException {
        return Byte.parseByte(str);
    }

    public static boolean parseShort(String str, int ty, java.text.DecimalFormat frm,  Outargwrapper<Short> res) {
        try {
			if(frm != null)
				res.value = (short)((long)frm.parse(str));
			else if((ty & 512) != 0)
				res.value = Short.decode("0x" + str);
			else
				res.value = Short.parseShort(str);
            return true;
        } catch (Exception ex) {
            res.value = (short) 0;
            return false;
        }
    }

    public static short parseShort(String str, int ty, java.text.DecimalFormat frm) throws NumberFormatException {
		try {
			if(frm != null)
				return (short)((long)frm.parse(str));
			else if((ty & 512) != 0)
				return Short.decode("0x" + str);
			else
				return Short.parseShort(str);
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseInteger(String str, int ty, java.text.DecimalFormat frm, Outargwrapper<Integer> res) {
        try {
			if(frm != null)
				res.value = (int)((long)frm.parse(str));
			else if((ty & 512) != 0)
				res.value = Integer.decode("0x" + str);
			else
				res.value = Integer.parseInt(str);
            return true;
        } catch (Exception ex) {
            res.value = (int) 0;
            return false;
        }
    }

    public static int parseInteger(String str, int ty, java.text.DecimalFormat frm) throws NumberFormatException {
		try {
			if(frm != null)
				return (int)((long)frm.parse(str));
			else if((ty & 512) != 0)
				return Integer.decode("0x" + str);
			else
				return Integer.parseInt(str);
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseLong(String str, int ty, java.text.DecimalFormat frm, Outargwrapper<Long> res) {
        try {
			if(frm != null)
				res.value = (long)frm.parse(str);
			else if((ty & 512) != 0)
				res.value = Long.decode("0x" + str);
			else
				res.value = Long.parseLong(str);
            return true;
        } catch (Exception ex) {
            res.value = (long) 0;
            return false;
        }
    }

    public static long parseLong(String str, int ty, java.text.DecimalFormat frm) throws NumberFormatException {
		try {
			if(frm != null)
				return (long)frm.parse(str);
			else if((ty & 512) != 0)
				return Long.decode("0x" + str);
			else
				return Long.parseLong(str);
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseFloat(String str, java.text.DecimalFormat frm, Outargwrapper<Float> res) {
        try {
			if(frm == null)
				res.value = Float.parseFloat(str);
			else {
				Object o = frm.parse(str);
				if(o instanceof Double)
					res.value = (float)((double)frm.parse(str));
				else if(o instanceof Long)
					res.value = (float)((long)frm.parse(str));
				else 
					throw new Exception("Bad number");
			}
            return true;
        } catch (Exception ex) {
            res.value = (float) 0;
            return false;
        }
    }

    public static float parseFloat(String str, java.text.DecimalFormat frm) throws NumberFormatException {
		if(frm == null)
			return Float.parseFloat(str);
		try {
			Object o = frm.parse(str);
			if(o instanceof Double)
				return (float)((double)frm.parse(str));
			else if(o instanceof Long)
				return (float)((long)frm.parse(str));
			else 
				throw new Exception("Bad number");
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseDouble(String str, java.text.DecimalFormat frm, Outargwrapper<Double> res) {
        try {
			if(frm == null)
				res.value = Double.parseDouble(str);
			else {
				Object o = frm.parse(str);
				if(o instanceof Double)
					res.value = (double)frm.parse(str);
				else if(o instanceof Long)
					res.value = (double)((long)frm.parse(str));
				else 
					throw new Exception("Bad number");
			}
            return true;
        } catch (Exception ex) {
            res.value = (double) 0;
            return false;
        }
    }

    public static double parseDouble(String str, java.text.DecimalFormat frm) throws NumberFormatException {
		if(frm == null)
			return Double.parseDouble(str);
		try {
			Object o = frm.parse(str);
			if(o instanceof Double)
				return (double)frm.parse(str);
			else if(o instanceof Long)
				return (double)((long)frm.parse(str));
			else 
				throw new Exception("Bad number");
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseUri(String str, Outargwrapper<java.net.URI> res) {
        try {
            res.value = java.net.URI.create(str);
            return true;
        }
        catch(Exception ex) {
            res.value = null;
            return false;
        }
    }
    public static String encodeUri(String str) {
        StringBuilder sb = new StringBuilder(str);
        for(int i = 0; i < sb.length(); i++) {
            char ch = sb.charAt(i);
            if(ch != ' ' && ((int)ch) < 0x100) continue;
            try {
                String ee = java.net.URLEncoder.encode(String.valueOf(ch), "ASCII");
                sb.replace(i, i + 1, ee);
            }
            catch(Exception ex) {
                continue;
            }
        }
        return sb.toString();
    }

    public static boolean parseDateTime(String str, Outargwrapper<java.time.LocalDateTime> res) {
        res.value = java.time.LocalDateTime.MIN;
        if (str == null) {
            return false;
        }
        try {
            res.value = java.time.LocalDateTime.parse(str);
            return true;
        } catch (Exception ex) {
        }
        java.util.ArrayList<Integer> ints = new java.util.ArrayList<Integer>();
        boolean isT = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                int v = Character.getNumericValue(str.charAt(i));
                for (++i; i < str.length(); i++) {
                    if (!Character.isDigit(str.charAt(i))) {
                        break;
                    } else {
                        v = (v * 10) + (Character.getNumericValue(str.charAt(i)));
                    }
                }
                ints.add(v);
                if (ints.size() == 3 && (i < str.length()) && str.charAt(i) == 'T') {
                    isT = true;
                }
            } else if (ints.size() == 3 && str.charAt(i) == 'T') {
                isT = true;
            }
        }
        try {
            if (ints.size() == 3) {
                res.value = java.time.LocalDateTime.of(ints.get(0), ints.get(1), ints.get(2), 0, 0);
                return true;
            }
            if (ints.size() == 6 || ((ints.size() >= 6 && isT))) {
                res.value = java.time.LocalDateTime.of(ints.get(0), ints.get(1), ints.get(2), ints.get(3), ints.get(4), ints.get(5));
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
	}

    public static byte[] toBytesArray(Byte[] arr) {
        byte[] res = new byte[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static byte[] toBytesArray(byte[] arr) {
        return arr;
    }
    public static Byte[] toBytesArrayObj(byte[] arr) {
        Byte[] res = new Byte[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Byte[] toBytesArrayObj(Byte[] arr) {
        return arr;
    }
    public static char[] toCharactersArray(Character[] arr) {
        char[] res = new char[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static char[] toCharactersArray(char[] arr) {
        return arr;
    }
    public static Character[] toCharactersArrayObj(char[] arr) {
        Character[] res = new Character[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Character[] toCharactersArrayObj(Character[] arr) {
        return arr;
    }
    public static short[] toShortsArray(Short[] arr) {
        short[] res = new short[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static short[] toShortsArray(short[] arr) {
        return arr;
    }
    public static Short[] toShortsArrayObj(short[] arr) {
        Short[] res = new Short[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Short[] toShortsArrayObj(Short[] arr) {
        return arr;
    }
    public static int[] toIntegersArray(Integer[] arr) {
        int[] res = new int[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static int[] toIntegersArray(int[] arr) {
        return arr;
    }
    public static Integer[] toIntegersArrayObj(int[] arr) {
        Integer[] res = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Integer[] toIntegersArrayObj(Integer[] arr) {
        return arr;
    }
    public static long[] toLongsArray(Long[] arr) {
        long[] res = new long[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static long[] toLongsArray(long[] arr) {
        return arr;
    }
    public static Long[] toLongsArrayObj(long[] arr) {
        Long[] res = new Long[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Long[] toLongsArrayObj(Long[] arr) {
        return arr;
    }
    public static float[] toFloatsArray(Float[] arr) {
        float[] res = new float[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static float[] toFloatsArray(float[] arr) {
        return arr;
    }
    public static Float[] toFloatsArrayObj(float[] arr) {
        Float[] res = new Float[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Float[] toFloatsArrayObj(Float[] arr) {
        return arr;
    }
    public static double[] toDoublesArray(Double[] arr) {
        double[] res = new double[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static double[] toDoublesArray(double[] arr) {
        return arr;
    }
    public static Double[] toDoublesArrayObj(double[] arr) {
        Double[] res = new Double[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Double[] toDoublesArrayObj(Double[] arr) {
        return arr;
    }

    public static <T> void addToArrayList(java.util.ArrayList<T> res, Iterable<T> src) {
        for(T i : src) res.add(i);
    }
    public static <T> void insertToArrayList(java.util.ArrayList<T> res, Iterable<T> src, int pos) {
        for(T i : src) {
        	res.add(pos, i); pos++;
        }
    }

    public static <T> java.util.ArrayList<T> toArrayList(Iterable<T> src) {
        if(src == null) return null;
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : src) res.add(i);
        return res;
    }
    public static <T> Iterable<T> concatIterable(Iterable<T> src, Iterable<T> src2) {
        if(src == null) return null;
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : src) res.add(i);
        for(T i : src2) res.add(i);
        return res;
    }
    public static <T> Iterable<T> reverseIterable(Iterable<T> src) {
        if(src == null) return null;
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : src) res.add(0, i);
        return res;
    }
    public static <T> Iterable<T> whereIterable(Iterable<T> src, Predicate<T> cond) {
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
    	for(T i : src) {
  			if(cond.call(i))
  				res.add(i);
    	}
    	return res;
    }
    public static <T, TResult> Iterable<TResult> selectIterable(Iterable<T> src, Func<T, TResult> func) {
        java.util.ArrayList<TResult> res = new java.util.ArrayList<TResult>();
    	for(T i : src) res.add(func.call(i));
    	return res;
    }
    public static <T> int countIterable(Iterable<T> src, Predicate<T> cond) {
    	int cou = 0;
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		cou++;
    	}
    	return cou;
    }
    public static <T> boolean anyIterable(Iterable<T> src, Predicate<T> cond) {
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		return true;
    	}
    	return false;
    }
    public static <T> boolean allIterable(Iterable<T> src, Predicate<T> cond) {
    	for(T i : src) {
    		if(!cond.call(i)) return false; 
    	}
    	return true;
    }
    public static <T> T firstIterable(Iterable<T> src, Predicate<T> cond) throws Exception {
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		return i;
    	}
    	throw new Exception("No items in iterable");
    }
    public static <T> T firstOrDefaultIterable(Iterable<T> src, Object nullVal, Predicate<T> cond) {
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		return i;
    	}
    	return (T)nullVal;
    }
    public static <T> T elementAtIterable(Iterable<T> src, int index) throws Exception {
    	for(T i : src) {
    		if(index == 0) return i;
    		index--;
    	}
    	throw new Exception("No items in iterable");
    }
    public static <T> T elementAtOrDefaultIterable(Iterable<T> src, int index, Object nullVal)  {
    	for(T i : src) {
    		if(index == 0) return i;
    		index--;
    	}
    	return (T)nullVal;
    }
    public static <T> T lastIterable(Iterable<T> src, Predicate<T> cond) throws Exception {
    	T ret = null;
    	boolean has = false;
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		ret = i; has = true; 
    	}
    	if(has) return ret;
    	throw new Exception("No items in iterable");
    }
    public static <T> T lastOrDefaultIterable(Iterable<T> src, Object nullVal, Predicate<T> cond) {
    	T ret = null;
    	boolean has = false;
    	for(T i : src) { 
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		ret = i; has = true; 
    	}
    	if(has) return ret;
    	return (T)nullVal;
    }

    public static <T> T putArrayValue(java.util.ArrayList<T> arr, int index, T value) {
        if (index < arr.size()) 
            arr.set(index, value);
        else
        	arr.add(index, value);
        return value;
    }

    public static <T> void forEachList(java.util.ArrayList<T> arr, Action<T> act) {
        for(T i : arr) 
        	act.call(i);
    }
    public static <T> boolean trueForAllList(java.util.ArrayList<T> arr, Predicate<T> cond) {
        for(T i : arr) 
        	if(!cond.call(i))
        		return false;
        return true;
    }
    public static <T> java.util.ArrayList<T> findAllList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : arr) 
        	if(cond.call(i))
        		res.add(i);
        return res;
    }
    public static <T> T findList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
        for(T i : arr) 
        	if(cond.call(i))
        		return i;
        return null;
    }
    public static <T> T findLastList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
    	T res = null;
        for(T i : arr) 
        	if(cond.call(i))
        		res = i;
        return res;
    }
    public static <T> int findIndexList(java.util.ArrayList<T> arr, Predicate<T> cond, int start, int cou) {
    	if(cou == 0) cou = arr.size() - start;
    	for(int i = start; i < start + cou; i++) 
    		if(cond.call(arr.get(i)))
    			return i;
        return -1;
    }
    public static <T> int findLastIndexList(java.util.ArrayList<T> arr, Predicate<T> cond, int start, int cou) {
    	if(start == 0) start = arr.size() - 1;
    	if(cou == 0) cou = arr.size() - start;
        for(int i = start; i >= start - cou && i >= 0; i--) 
         	if(cond.call(arr.get(i)))
           		return i;
        return -1;
    }
    public static <T> boolean existsList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
        for(T i : arr) 
        	if(cond.call(i))
        		return true;
        return false;
    }



    public static <T> int indexOf(T[] arr, T value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static int indexOfBytes(byte[] arr, byte value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static int indexOfChars(char[] arr, char value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static int indexOfInts(int[] arr, int value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static <T> int indexOf(java.util.ArrayList<T> arr, T value, int fromIndex) {
        for(int i = fromIndex; i < arr.size(); i++)
            if(arr.get(i) == value) return i;
        return -1;
    }
    public static <T> int lastIndexOf(java.util.ArrayList<T> arr, T value, int fromIndex) {
        for(int i = fromIndex; i >= 0; i--)
            if(arr.get(i) == value) return i;
        return -1;
    }
    public static int getCapacity(java.util.ArrayList<?> l) {
        try {
        java.lang.reflect.Field dataField = java.util.ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
        }
        catch(Exception ex) {
            return l.size();
        }
    }

    public static int daysInMonth(int year, int month) {
        switch (month) {
            case 2:
                return (((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0) ? 29 : 28);
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    public static <K, V> boolean tryGetValue(java.util.Map<K, V> dic, K key, Outargwrapper<V> res) {
        if (dic == null || key == null || !dic.containsKey(key)) {
            return false;
        }
        res.value = dic.get(key);
        return true;
    }
    public static String getFileExt(String s) {
	    if(s == null) return null;
        for (int i = s.length() - 1; i > 0; i--) {
            if (s.charAt(i) == '.') {
                return s.substring(i);
            } else if (s.charAt(i) == '\\' || s.charAt(i) == '/' || s.charAt(i) == ':') {
                break;
            }
        }
        return ".";
    }
    public static String getFileWithoutExt(String s) {
	    if(s == null) return null;
        for (int i = s.length() - 1; i > 0; i--) {
            if (s.charAt(i) == '.') {
                return s.substring(0, i);
            } else if (s.charAt(i) == '\\' || s.charAt(i) == '/' || s.charAt(i) == ':') {
                break;
            }
        }
        return s;
    }
    public static String changeFileExt(String s, String ext) {
	    if(s == null) return null;
        String res = getFileWithoutExt(s);
        if(ext != null && !ext.isEmpty()) {
            if(ext.charAt(0) == '.') res += ext;
            else res += "." + ext;
        }
        return res;
    }
	public static String getRootPath(String fileName) {
    	if(fileName == null) return null;
    	if(fileName.length() < 4) return "";
    	if(fileName.charAt(1) == ':') {
    		for(int i = 2; i < fileName.length(); i++) {
    			char ch = fileName.charAt(i);
				if(ch != '\\' && ch != '/')
					return fileName.substring(0, i);
    		}
    	}
    	return "";
    }
	public static boolean isPathRooted(String path) {
		return !isNullOrEmpty(getRootPath(path));
	}

    public static java.time.LocalDateTime getDateTimeFromFileTime(long ftime) {
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(ftime),
                java.util.TimeZone.getDefault().toZoneId());
    }

    public static java.time.LocalDateTime getFileCreationTime(java.io.File f) {
    	try {
    	java.nio.file.attribute.BasicFileAttributes attr = java.nio.file.Files.readAttributes(f.toPath(), java.nio.file.attribute.BasicFileAttributes.class);
    	java.nio.file.attribute.FileTime ft = attr.creationTime();
        long lo = ft.toMillis();
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(lo),
                java.util.TimeZone.getDefault().toZoneId());
    	}
    	catch(Exception ee) {
    		return java.time.LocalDateTime.MIN;
    	}
    }

    public static java.time.LocalDateTime getFileModifiedTime(java.io.File f) {
        long lo = f.lastModified();
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(lo),
                java.util.TimeZone.getDefault().toZoneId());
    }

   static boolean checkPattern(String fname, int j, String pattern) {
        if(pattern.equals("*") || pattern.equals("*.*")) return true;
        int i;
        for(i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            if(ch == '?') { j++; continue; }
            if(ch != '*') {
                if(j >= fname.length()) return false;
                char ch2 = fname.charAt(j);
                if(Character.toUpperCase(ch) != Character.toUpperCase(ch2)) return false;
                j++; continue; 
            }
            if(j >= fname.length() || i == pattern.length() - 1) { i++; break; }
            String spat = pattern.substring(i + 1);
            for(int jj = j; jj < fname.length() - 1; jj++)
                if(checkPattern(fname, jj, spat)) 
                    return true;
            return false;
        }
        if(i < pattern.length()) return false;
        if(j < fname.length()) return false;
        return true;
    }

   public static boolean deleteDirectory(java.io.File path) throws java.io.FileNotFoundException{
       if (!path.exists()) throw new java.io.FileNotFoundException(path.getAbsolutePath());
       boolean ret = true;
       if (path.isDirectory()) {
           for (java.io.File f : path.listFiles()){
               ret = ret && Utils.deleteDirectory(f);
           }
       }
       return ret && path.delete();
   }   

   public static String[] getDirectoryItems(String path, String pattern, boolean dirs) {
        java.io.File file = new java.io.File(path);
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        
        java.io.File[] files = file.listFiles();
        if(files != null) 
            for(int i = 0; i < files.length; i++)
            {
                if(files[i].isDirectory()) { if(!dirs) continue; }
                else if(files[i].isFile()) { if(dirs) continue; }
                if(pattern != null)
                    if(!checkPattern(files[i].getName(), 0, pattern))
						continue;
                res.add(files[i].getAbsolutePath());
            }
        
        String[] arr = new String[res.size()];
        arr = res.toArray(arr);
        return arr;
    }

    public static boolean setCurrentDirectory(String directory_name) {
        boolean result = false;  
        java.io.File directory = new java.io.File(directory_name).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs()) {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
        }
        return result;
    }
    public static String getFullPath(String path) {
        java.io.File f = new java.io.File(path);
        try {
            return f.getCanonicalPath();
        }
        catch(Exception e) {
        }
        return f.getAbsolutePath();
    }
    public static String getTempFileName() {
    	java.io.File baseDir = new java.io.File(System.getProperty("java.io.tmpdir"));
    	for(int val = (int)System.currentTimeMillis(); ; val++) {
    		java.io.File res = new java.io.File(baseDir, "tmp" + val + ".tmp");
    		if(!res.exists()) return res.getAbsolutePath();
    	}
    }
    public static void copyFile(String fromFile, String toFile) throws java.io.IOException {
        java.nio.file.Files.copy(java.nio.file.Paths.get(fromFile), java.nio.file.Paths.get(toFile), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
    public static void moveFile(String fromFile, String toFile) throws java.io.IOException {
        java.nio.file.Files.move(java.nio.file.Paths.get(fromFile), java.nio.file.Paths.get(toFile), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }

    public static byte[] encodeCharset(java.nio.charset.Charset cs, String str) {             
	    java.nio.ByteBuffer bb = cs.encode(str + "!");             
		byte[] res = bb.array();             
		for(int i = res.length - 1; i >= 0; i--)                  
		    if(res[i] == (byte)33) {                      
		        res = java.util.Arrays.copyOf(res, i); break;                  
		    }            
	    return res;     
	}     
	public static String decodeCharset(java.nio.charset.Charset cs, byte[] dat, int offset, int length) {
	    if(length < 0) length = dat.length; 
		if(length > 2 && dat[offset] == (byte)0xEF && dat[offset + 1] == (byte)0xBB && dat[offset + 2] == (byte)0xBF) {
		    offset += 3; length -= 3;
		}
        java.nio.CharBuffer cb = cs.decode(java.nio.ByteBuffer.wrap(dat, offset, length)); 
        String res = cb.toString(); 
        return res; 
    }
	public static char[] decodeCharsetArr(java.nio.charset.Charset cs, byte[] dat, int offset, int length) {
	    if(length < 0) length = dat.length; 
		if(length > 2 && dat[0] == (byte)0xEF && dat[1] == (byte)0xBB && dat[2] == (byte)0xBF) {
		    offset += 3; length -= 3;
		}
        java.nio.CharBuffer cb = cs.decode(java.nio.ByteBuffer.wrap(dat, offset, length)); 
        return cb.array(); 
    }
    public static byte[] preambleCharset(java.nio.charset.Charset cs) {             
        if(cs.name().equalsIgnoreCase("UTF-8"))
            return new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF };
        if(cs.name().equalsIgnoreCase("UTF-16LE"))
            return new byte[] { (byte)0xFF, (byte)0xFE };
        if(cs.name().equalsIgnoreCase("UTF-16BE"))
            return new byte[] { (byte)0xFE, (byte)0xFF };
        return new byte[0];
    }
    public static java.nio.charset.Charset getCharsetByName(String name) throws IllegalArgumentException {
        if(name.equalsIgnoreCase("UTF-16")) name = "UTF-16LE";
        else if(name.equalsIgnoreCase("UNICODEFFFE")) name = "UTF-16BE";
        else if(name.equalsIgnoreCase("US-ASCII")) name = "ASCII";
        else if(name.toUpperCase().startsWith("windows-")) {
            name = "cp" + name.substring(8);
        }
        try {
            return java.nio.charset.Charset.forName(name);
        }
        catch(Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static byte[] readAllBytes(String fname) throws java.io.FileNotFoundException, java.io.IOException { 
        try(java.io.RandomAccessFile f = new java.io.RandomAccessFile(fname, "r")) { 
            byte[] res = new byte[(int)f.length()];
            f.read(res, 0, res.length); 
            return res; 
        } 
    }

    public static String getXmlLocalName(org.w3c.dom.Node nod) {
    	if(nod == null) return null;
    	String res = nod.getNodeName(); if(res == null) return null;
    	for(int i = res.length() - 1; i >=0; i--)
    		if(res.charAt(i) == ':') 
    			return res.substring(i + 1);
    	return res;
    }

    public static org.w3c.dom.Attr getXmlAttrByName(org.w3c.dom.NamedNodeMap map, String name) { 
        //org.w3c.dom.NamedNodeMap map = node.getAttributes();
		if(map == null) return null;
		org.w3c.dom.Node res = map.getNamedItem(name);
		if(res instanceof org.w3c.dom.Attr) return (org.w3c.dom.Attr)res;
		return null;
    }

    public static void writeAllBytes(String fname, byte[] data) throws java.io.FileNotFoundException, java.io.IOException { 
        try(java.io.RandomAccessFile f = new java.io.RandomAccessFile(fname, "rw")) { 
            f.setLength(0); 
            f.write(data, 0, data.length); 
        } 
    }
    public static String readAllText(String fname, java.nio.charset.Charset cs) throws java.io.FileNotFoundException, java.io.IOException { 
        byte[] dat = readAllBytes(fname);
		if(dat == null || dat.length == 0) return null;
		if(cs == null) cs = java.nio.charset.StandardCharsets.UTF_8;
        return decodeCharset(cs, dat, 0, dat.length); 
    }
    public static void writeAllText(String fname, String txt, java.nio.charset.Charset cs) throws java.io.FileNotFoundException, java.io.IOException { 
		if(cs == null) cs = java.nio.charset.StandardCharsets.UTF_8;
	    byte[] dat = encodeCharset(cs, txt);
		writeAllBytes(fname, dat);
    }

    public static byte[] readAllBytes(java.io.InputStream istr) throws java.io.IOException {
        byte[] buffer = new byte[10240];
        int len;
        java.io.ByteArrayOutputStream  ba = new java.io.ByteArrayOutputStream ();
        while ((len = istr.read(buffer, 0, buffer.length)) != -1) {
            if(len > 0) ba.write(buffer, 0, len);
        }
        ba.flush();
        return ba.toByteArray();
    }

    public static byte[] readAllBytes(Stream istr) throws java.io.IOException {
		if(istr instanceof MemoryStream)
			return ((MemoryStream)istr).toByteArray();
        byte[] buffer = new byte[10240];
        int len;
        java.io.ByteArrayOutputStream  ba = new java.io.ByteArrayOutputStream ();
        while ((len = istr.read(buffer, 0, buffer.length)) != -1) {
            if(len > 0) ba.write(buffer, 0, len);
        }
        ba.flush();
        return ba.toByteArray();
    }

    public static long getTotalMemory(boolean collect) {
        if(collect) System.gc();
        return Runtime.getRuntime().totalMemory();
    }

    public static String getMachineName() {
    	try {
    		return java.net.InetAddress.getLocalHost().getHostName();
    	}
        catch(Exception e) {
        }
    	return null;
    }

    public static RegexMatchWrapper createMatchRegex(java.util.regex.Pattern p, String txt, int from) {
    	return new RegexMatchWrapper(p, txt, from);
    }
	public static java.util.ArrayList<RegexMatchWrapper> matchesRegex(java.util.regex.Pattern p, String txt, int from) {
		java.util.ArrayList<RegexMatchWrapper> res = new java.util.ArrayList<RegexMatchWrapper>();
		RegexMatchWrapper wr = new RegexMatchWrapper(p, txt, from); 
		for(; wr != null && wr.success;) {
			res.add(wr);
			wr = wr.nextMatch();
		}
		return res;
	}

	public static String consoleReadLine() {
		try(java.util.Scanner s = new java.util.Scanner(System.in)) {
			return s.nextLine();
		}
	}


    public static final Object EMPTYEVENTARGS = new Object();

    public static final java.util.UUID EMPTYUUID = new java.util.UUID(0, 0);

    public static byte[] getBytesFromUUID(java.util.UUID uuid) {
        java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        byte[] res = bb.array();
        byte b;
        b = res[0]; res[0] = res[3]; res[3] = b;
        b = res[1]; res[1] = res[2]; res[2] = b;
        b = res[4]; res[4] = res[5]; res[5] = b;
        b = res[6]; res[6] = res[7]; res[7] = b;
        return res;
    }

    public static java.util.UUID getUUIDFromBytes(byte[] bytes) {
    	byte[] res = new byte[bytes.length];
    	for(int i = 0; i < res.length; i++) res[i] = bytes[i];
        byte b;
        b = res[0]; res[0] = res[3]; res[3] = b;
        b = res[1]; res[1] = res[2]; res[2] = b;
        b = res[4]; res[4] = res[5]; res[5] = b;
        b = res[6]; res[6] = res[7]; res[7] = b;
    	java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.wrap(res);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();
        return new java.util.UUID(high, low);
    }    
}
