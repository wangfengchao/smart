package com.smart.spark.sort_second

/**
  * 二次排序
  * Created by fc.w on 2017/6/26.
  */
class Secondary_SortKey(val first: Int, val second: Int) extends Ordered[Secondary_SortKey] with Serializable {

  override def compare(other: Secondary_SortKey): Int = {
    if (this.first - other.first != 0) {
      return this.first - other.first
    } else {
      return this.second - other.second
    }
  }

}
