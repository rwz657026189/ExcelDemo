package com.rwz.lib_excel.entity

/**
 * date： 2020/12/13 14:53
 * author： rwz
 * description：
 **/
data class Entity<K, V>(
    val first: K,
    var second: V? = null
)