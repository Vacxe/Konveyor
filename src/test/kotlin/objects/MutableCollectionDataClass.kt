package objects

import java.util.HashMap

data class MutableCollectionDataClass(val array: ArrayList<Int>,
                                      val set: HashSet<String>,
                                      val map: HashMap<Double, Boolean>)