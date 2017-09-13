
package com.cheers.taskfirst.utils;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

public class DozerHelper {

	public static <T, U> List<U> mapList(final Mapper mapper, final List<T> source, final Class<U> destType) {

		final List<U> dest = new ArrayList<>();

		for (T element : source) {
			dest.add(mapper.map(element, destType));
		}

		return dest;
	}
}