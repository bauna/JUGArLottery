package ar.com.itcarg.jugar.lottery

import java.util.Properties
import java.util.Random
import scala.collection.immutable._
import scala.collection.JavaConversions._
import scala.math.abs

class Lottery {
    val properties = new Properties()
    properties.load(getClass().getResourceAsStream("/conf/people.properties"))
    
    private def genRanges(n: Int): List[Range] = n match {
      case 1 => List(0 until 100)
      case 2 => List(0 until 66, 66 until 100)
      case _ => List(0 until 50, 50 until 80, 80 until 100)
    }
    
    properties.toMap
    def choose() = {
        var setIndex = 0
        val props = properties toMap 
        val lists = (props keys) groupBy (props.getOrElse(_, "0").toInt)
        
        val random = new Random()
        var n = abs(random.nextInt(100))
        var ranges = genRanges(lists size)
        
        
        while (!((ranges head) contains n)) {
          setIndex += 1
          ranges = ranges tail
        }
        
        val peopleLottery = lists.getOrElse(setIndex, Set())
        
        n = random.nextInt(peopleLottery size)
        val winner = peopleLottery.toList.get(n)
        
        val count = Integer.valueOf(properties.getOrElse(winner, "0")) + 1 
        properties.put(winner, "" + count)

        (winner, properties)
    }
}