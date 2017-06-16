package com.smart.cf

import com.esotericsoftware.kryo.Kryo
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.serializer.KryoRegistrator


/**
  * Rating Kryo序列化
  * Created by fc.w on 2017/5/25.
  */
class ALSRegistrator extends KryoRegistrator {

  override def registerClasses(kryo: Kryo) {
    kryo.register(classOf[Rating])
  }

}
