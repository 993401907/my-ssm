package org.wulizi.myssm.util;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * map的工具类
 * @author wulizi
 */
public class MapUtil {
    public static <K, V> int getSetValueCount(Map<K, Set<V>> map) {
        int count = 0;
        for (Map.Entry<K, Set<V>> entry : map.entrySet()) {
            Set<V> set = entry.getValue();
            count += set.size();
        }
        return count;
    }

    /**
     * 合并两个map
     *
     * @param mapOne map1
     * @param mapTwo map2
     * @return 合并后的新的map
     */
    public static <K,V> Map<K,V> mergeMap(Map<K,V> mapOne,Map<K,V> mapTwo) {

        //把两个map转换为流，然后合并
        Stream<Map.Entry<K,V>> combined = Stream.concat(mapOne.entrySet().stream()
                , mapTwo.entrySet().stream());
        return combined.collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldVal, newVal) -> newVal));
    }
}
