//package tools;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//
//public class JSONChange {
//    /*
//     * 001.json转换成对象
//     * @param:传入对象，json字符串
//     * @return:Object
//     */
//    public static Object json2Obj(Class c, String jsonStr) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        Object obj = mapper.readValue(jsonStr, c);
//        return obj;
//    }
//
//    /*
//     * 002.对象转换成json
//     * @param:传入对象
//     * @return:json字符串
//     */
//    public static String obj2Json(Object obj) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(obj);
//    }
//}