package crypt

import scala.util.Random

object Util {
    def _createPair(key: String, value: Any) : String = value match {
        case a:String => "\"" + key + "\"" + ":" + "\"" + a + "\"";
        case b:Map[String,Any] => "\"" + key + "\"" + ":" + jsonFromMap(b);
        case _ => "\"" + key + "\"" + ":" + "\"" + "_unknown_" + "\"";
    }

    def _wrapJsonString(json: String) : String = {
        return "{ " + json + " }";
    }

    def jsonFromMap(map: Map[String,Any]) : String = {
        var jsonList:List[String] = List();

        map.keys.foreach {
            key =>
                var pair:String = _createPair(key, map(key));
                jsonList = jsonList ::: List(pair)
        }
        var jsonBody:String = jsonList.mkString(",");
        return _wrapJsonString(jsonBody);
    }

    // generate random string based on ASCII table
    def _randomStringFromASCII(length: Int, minCharCode: Int, maxCharCode: Int) : String = {
        val (min, max) = (minCharCode, maxCharCode);
        def nextDigit = Random.nextInt(max - min) + min
        return new String(Array.fill(length)(nextDigit.toByte), "ASCII");
    }

    // generate random string, sort of password
    def randomString(length: Int) : String = {
        return _randomStringFromASCII(length, 33, 126);
    }

    // generate random string using only letters
    def randomName(length: Int) : String = {
        return _randomStringFromASCII(length, 97, 122);
    }

    // random index
    def randomIndex(min:Int, max:Int): Int = {
        if (min >= max)
            return min;
        return Random.nextInt(max - min) + min;
    }
}